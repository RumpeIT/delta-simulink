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
package mc.deltasimulink.montiarc2simulink;

import static org.junit.Assert.assertTrue;
import interfaces2.language.ModelingLanguage;

import java.io.File;

import mc.deltasimulink.helper.MatlabProxyHelper;
import mc.umlp.arc.MontiArcLanguage;
import mc.umlp.arc.MontiArcTool;

import org.junit.Before;
import org.junit.Test;

public class MontiArc2SimulinkConverterTest {
	private static final String GEN_PATH = "target/gen";
	private static final String OUTPUT_PATH = "target/output";
	
    @Before
    public void setUp() {
        File out = new File(OUTPUT_PATH);
        if (out.exists()) {
            clean(out);
        }

        File gen = new File(GEN_PATH);
        if (gen.exists()) {
            clean(gen);
        }
        
    }
    
    protected void clean(File f) {
        if (f.isDirectory()) {
            for (File sub : f.listFiles()) {
                clean(sub);
            }
        }
        f.delete();
    }

	@Test
	public void testConversion() {
		MatlabProxyHelper matlab = MatlabProxyHelper.getInstance();
		
			matlab.connect();
			assertTrue(matlab.isConnected());
			
			assertTrue(importToMatlab("src/test/resources/montiarc/testconversion", OUTPUT_PATH));
			
			matlab.exitAndDisconnect();
			assertTrue(matlab.isDisconnected());
	}
	
	
	  /**
     * 
     * @param modelFolder contains MontiArc models to import
     * @param matlabFolder where should the models be created
     */
    protected boolean importToMatlab(String modelFolder, String matlabFolder) {
        MatlabProxyHelper matlab = MatlabProxyHelper.getInstance();
        
        String[] args = new String[]{modelFolder, 
                "-mp", modelFolder,
                "-symtabdir", "target/testsymtab",
                "-analysis", "ALL", "parse", 
                "-analysis", "javadsl", "setname", 
                "-analysis", "javadsl", "addImports", 
                "-analysis", "ALL", "init", 
                "-analysis", "ALL", "createExported", 
                "-synthesis", "ALL", "prepareCheck", 
//                "-synthesis", "arc", "preCheckTransformation",
                "-synthesis", "arc", "importToMatlab"};
        
        MontiArcTool tool = new MontiArcTool(args);
        for (ModelingLanguage l : tool.getLanguages().getLanguages()) {
            if (l instanceof MontiArcLanguage) {
                l.addExecutionUnit("importToMatlab", new MontiArc2SimulinkConverterWorkflow(matlab, matlabFolder));
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
        return tool.run();
    }
	
}
