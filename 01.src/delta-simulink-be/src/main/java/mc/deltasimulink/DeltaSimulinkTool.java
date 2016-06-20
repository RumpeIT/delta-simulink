/**
 * 
 */
package mc.deltasimulink;

import interfaces2.language.ModelingLanguage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import mc.ConsoleErrorHandler;
import mc.StandardErrorDelegator;
import mc.deltamontiarc.MADeltaConstants;
import mc.deltamontiarc.MADeltaTool;
import mc.deltamontiarc.generator.check.MADeltaContextConditionsCreator;
import mc.deltasimulink.helper.MatlabProxyHelper;
import mc.deltasimulink.helper.SimulinkErrorHandler;
import mc.deltasimulink.montiarc2simulink.MontiArc2SimulinkConverterWorkflow;
import mc.deltasimulink.simulink2montiarc.MontiArcStringBuilder;
import mc.deltasimulink.simulink2montiarc.Simulink2MontiArcConverter;
import mc.umlp.arc.MontiArcConstants;
import mc.umlp.arc.MontiArcLanguage;
import mc.umlp.arc.MontiArcTool;

/*******************************************************************************
 * Delta-Simulink
 * Copyright (c) 2013, RIT, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project.
 *******************************************************************************/
public class DeltaSimulinkTool {
    
    protected static final String TMP_CORE_FOLDER = "./target/tmp_core/";
    
    protected static final String TMP_DELTA_FOLDER = "./target/tmp_deltas/";
    
    protected static final String TMP_GEN_FOLDER = "./target/tmp_gen/";
    
    public static final String BOOTSTRAP_JAR = "./de.mc.deltasimulink.bootstrap.jar";
    
    protected File coreInputFolder;
    
    protected File deltaInputFolder;
    
    protected File configFile;
    
    protected File outputFolder;
    
    protected Simulink2MontiArcConverter converter;
    
    protected StandardErrorDelegator handler;
    
    protected SimulinkErrorHandler slHandler;
    
    protected static final String PARAMETER_MESSAGE = "Invalid input parameters. The following parameters have to be given:\n" +
            "[0] core model directory\n" +
            "[1] delta model directory\n" +
            "[2] configuration file\n" +  
            "[3] output directory (mdl files are generated here)";

    

    /**
     * 
     * Creates a new {@link DeltaSimulinkTool} using the given arguments.
     * 
     * @param args 
     *      [0] core model directory
     *      [1] delta model directory
     *      [2] configuration file 
     *      [3] output directory (mdl files are generated here) 
     *       
     */
    public DeltaSimulinkTool(String[] args) {
        this();
        init(args);
    }
    
    /**
     * Uninitialized tool for tests only.
     */
    protected DeltaSimulinkTool() {
        StandardErrorDelegator handler = new StandardErrorDelegator();
        handler.addErrorHandler(new ConsoleErrorHandler());
        slHandler = new SimulinkErrorHandler();
        handler.addErrorHandler(slHandler);
        this.handler = handler;
        
        converter = new Simulink2MontiArcConverter(MatlabProxyHelper.getInstance(), this.handler);
    }
    
    

    /**
     * Checks the given arguments and setups all needed parameters.
     * 
     * @param args arguments to check
     */
    protected void init(String[] args) {
        if (args.length != 4) {
            throw new RuntimeException(PARAMETER_MESSAGE);
        }
        coreInputFolder = new File(args[0]);
        deltaInputFolder = new File(args[1]);
        configFile = new File(args[2]);
        outputFolder = new File(args[3]);
        
        String errorMsg = "";
        // core folder
        if (!coreInputFolder.exists() || !coreInputFolder.isDirectory()) {
            errorMsg += "Core model directory does not exist. ";
        } 
        
        
        // delta folder
        if (!deltaInputFolder.exists() || !deltaInputFolder.isDirectory()) {
            errorMsg += "Delta model directory does not exist. ";
        } 
        
        // config file
        if (!configFile.exists() || !configFile.isFile()) {
            errorMsg += "Configuration file does not exist. ";
        } 
        
        // setuo output folder & tmp dirs
        if ((outputFolder.exists() && !outputFolder.isDirectory())) {
            errorMsg += "Output directory must be a directory.";
        }
        else {
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            File tmpCoreFolder = new File(TMP_CORE_FOLDER);
            File tmpDeltaFolder = new File(TMP_DELTA_FOLDER);
            File tmpGen = new File(TMP_GEN_FOLDER);
            
            if (tmpCoreFolder.exists()) {
                clean(tmpCoreFolder);
            }
            tmpCoreFolder.mkdirs();
            if (tmpDeltaFolder.exists()) {
                clean(tmpDeltaFolder);
            }
            tmpDeltaFolder.mkdirs();
            if (tmpGen.exists()) {
                clean(tmpGen);
            }
            tmpGen.mkdirs();
        }
        
        if (!errorMsg.isEmpty()) {
            throw new RuntimeException(errorMsg);
        }
        
    }

    /**
     * @param f
     */
    protected void clean(File f) {
        if (f.isDirectory()) {
            for (File sub : f.listFiles()) {
                clean(sub);
            }
        }
        f.delete();
    }
    
    /**
     * Exports Simulink models to MontiArc & Delta-MontiArc, starts product generation,
     * and imports generated Simulink products back into Matlab.
     *  
     * @return true, if it runs successful.
     */
    public boolean run() {
        handler.addInfo("Connecting to Matlab.");
        MatlabProxyHelper.getInstance().connect();
        
        addPhaseInfo("Started exporting core models.");
        exportCore(coreInputFolder, TMP_CORE_FOLDER);
        
        addPhaseInfo("Started exporting delta models.");
        exportDeltas(deltaInputFolder, TMP_DELTA_FOLDER);
        
        addPhaseInfo("Started product generation.");
        runDeltaMontiArc(configFile.toString(), TMP_CORE_FOLDER, TMP_DELTA_FOLDER, TMP_GEN_FOLDER);
        
        addPhaseInfo("Started importing generated models to Matlab.");
        importToMatlab(TMP_GEN_FOLDER, outputFolder.toString());
        
        MatlabProxyHelper.getInstance().exitAndDisconnect();
        return !slHandler.hasError();
    }
    

    /**
     * Starts the Delta MontiArc tool using given 'core' and 'deltas'
     * as core respectively delta input folder, 'cfg' as configuration file 
     * and generates into 'out'.
     * 
     * @param cfg product configuration file
     * @param core core directory
     * @param deltas deltas directory
     * @param out output directory
     * 
     * @return true, if product generation has been successful, else false.
     */
    protected boolean runDeltaMontiArc(String cfg, String core, String deltas, String out) {
        final String symtabdir = "./target/delta_symtab";
        String[] args = new String[] { 
                cfg, 
                "-core", core,
                "-deltas", deltas,
                "-symtabdir", symtabdir,
                "-mp", core,
                "-mp", deltas,
                "-mp", BOOTSTRAP_JAR,
                "-out", out,
                "-analysis", "ALL", "parse", 
                "-analysis", "javadsl", "setname", 
                "-analysis", "javadsl", "addImports",
                "-synthesis", MADeltaConstants.MADELTA_FILE_ENDING, "generate" 
        };
        
        MADeltaTool tool = new MADeltaTool(args, 
                MADeltaContextConditionsCreator.createDefaultPreConfiguration(),
                MADeltaContextConditionsCreator.createDefaultPostConfiguration());

        tool.init();
        tool.addErrorHandler(slHandler);
        return tool.run();
    }
    
    /**
     * 
     * @param modelFolder contains MontiArc models to import
     * @param matlabFolder where should the models be created
     */
    protected void importToMatlab(String modelFolder, String matlabFolder) {
        MatlabProxyHelper matlab = MatlabProxyHelper.getInstance();
        
        String[] args = new String[]{modelFolder, 
                "-mp", modelFolder,
                "-symtabdir", "target/ma_symtab",
                "-analysis", "ALL", "parse", 
                "-analysis", "javadsl", "setname", 
                "-analysis", "javadsl", "addImports", 
                "-analysis", "ALL", "init", 
                "-analysis", "ALL", "createExported", 
                "-synthesis", "ALL", "prepareCheck",
                "-synthesis", "arc", "importToMatlab"};
//                "-synthesis", "arc", "preCheckTransformation",
                
        
        MontiArcTool tool = new MontiArcTool(args);
        for (ModelingLanguage l : tool.getLanguages().getLanguages()) {
            if (l instanceof MontiArcLanguage) {
            	MontiArc2SimulinkConverterWorkflow M2SW = new MontiArc2SimulinkConverterWorkflow(matlab, matlabFolder);
                l.addExecutionUnit("importToMatlab", M2SW);
                tool.getConfiguration().addExecutionUnit("importToMatlab", M2SW);
            }
        }
        
        String outDir = new File(matlabFolder).getAbsolutePath();
        // deletes output folder if it exists
        matlab.returningFeval("rmdir", outDir, "s");
        
        // creates output folder
        matlab.returningFeval("mkdir", outDir);

        // clear path to "disconnect" core and delta models
        matlab.clearPath();
        
        tool.init();
        tool.run();
    }
    
    protected void addPhaseInfo(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("-----------------------------------------------------------------\n");
        sb.append(msg);
        sb.append("\n");
        sb.append("-----------------------------------------------------------------\n");
        handler.addInfo(sb.toString());
    }

    /**
     * Export delta Simulink models.
     * 
     * @param f file or directory to export
     * @param outputDir directory where the exported files are stored
     */
    protected void exportDeltas(File f, String outputDir) {
        String name = f.getName();
        if (f.isDirectory()) {
            for (File sub : f.listFiles()) {
                exportDeltas(sub, outputDir);
            }
        }
        else if (name.endsWith(".mdl")) {
            String path;
            if (f.getParentFile() != null) {
                path = f.getParentFile().getAbsolutePath();
            }
            else {
                path = "." + File.separator;
            }
            
            name = name.substring(0, name.length() - 4);
            
            try {
                handler.addInfo("Converting delta model: " + name, f.toString());
                String model = converter.convertDeltaModel(path, name);
                writeModel(model, name + "." + MADeltaConstants.MADELTA_FILE_ENDING, outputDir);
            }
            catch (Exception e) {
                handler.addError(e.getMessage(), f.toString());
            }
        }
    }



    /**
     * @param f file or directory that contains core models
     * @param outputDir directory, where exported files are stored
     */
    protected void exportCore(File f, String outputDir) {
        String name = f.getName();
        if (f.isDirectory()) {
            for (File sub : f.listFiles()) {
                exportCore(sub, outputDir);
            }
        }
        else if (name.endsWith(".mdl")) {
            String path;
            if (f.getParentFile() != null) {
                path = f.getParentFile().getAbsolutePath();
            }
            else {
                path = "." + File.separator;
            }
            
            name = name.substring(0, name.length() - 4);
            
            try {
                handler.addInfo("Converting core model: " + name, f.toString());
                String model = converter.convertModel(path, name);
                writeModel(model, name + "." + MontiArcConstants.MONTI_ARC_FILE_ENDING, outputDir + File.separator + MontiArcStringBuilder.EXPORT_PACKAGE);
            }
            catch (Exception e) {
                handler.addError(e.getMessage(), f.toString());
            }
        }
        
    }



    /**
     * @param model
     */
    protected void writeModel(String model, String name, String folder) {
        File outDir = new File(folder);
        if (! outDir.exists()) {
            outDir.mkdirs();
        }
        File output = new File(folder + File.separator + name);
        try {
            output.createNewFile();
            FileOutputStream fos = new FileOutputStream(output);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(model);
            bw.close();
            fos.close();
            handler.addInfo("Model " + name + " written.", output.toString());
        }
        catch (FileNotFoundException e) {
            handler.addError(e.getMessage(), output.toString());
        }
        catch (IOException e) {
            handler.addError(e.getMessage(), output.toString());
        }
        
        
    }



    /**
     * @param args 
     *      [0] core model directory
     *      [1] delta model directory
     *      [2] configuration file 
     *      [3] output directory (mdl files are generated here) 
     *       
     */
    public static void main(String[] args) {
        DeltaSimulinkTool tool = new DeltaSimulinkTool(args);
        tool.run();
        
    }
    
}
