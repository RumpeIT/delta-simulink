/**
 * 
 */
package mc.deltasimulink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import mc.ProblemReport.Type;
import mc.deltasimulink.helper.TestHelper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

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
public class DeltaSimulinkToolTest extends TestHelper {
    
    protected static final String OUTPUT = "./target/outputDeltaSimulinkToolTest";
    
    @BeforeClass
    public static void beforeClass() {
        DeltaSimulinkToolTest test = new DeltaSimulinkToolTest();
        test.setUp();
        
        DeltaSimulinkTool t = new DeltaSimulinkTool();
        t.exportCore(new File("./src/test/resources/core"), DeltaSimulinkTool.TMP_CORE_FOLDER);
        assertFalse(t.slHandler.hasError());
        assertFalse(t.slHandler.hasWarning());
        
        t.exportDeltas(new File("./src/test/resources/deltas"), DeltaSimulinkTool.TMP_DELTA_FOLDER);
        assertFalse(t.slHandler.hasError());
        assertFalse(t.slHandler.hasWarning());
        //MatlabProxyHelper.getInstance().exitAndDisconnect();
    }
    
    @Before
    public void setUp() {
        File out = new File(OUTPUT);
        if (out.exists()) {
            clean(out);
        }
        out.mkdirs();
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
    public void testImportToMatlab() {
        DeltaSimulinkTool t = new DeltaSimulinkTool();
        t.importToMatlab(DeltaSimulinkTool.TMP_GEN_FOLDER, OUTPUT);
        assertFalse(t.slHandler.hasError());
        assertFalse(t.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripABS() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/01.ABS/ABS.delta", 
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);
        
        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripABS_TC() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/02.ABS_TC/ABS_TC.delta",  
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);
        
        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripABS_TC_ESC() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/03.ABS_TC_ESC/ABS_TC_ESC.delta",  
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);
        
        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripABS_TC_ESC_ACC() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/04.ABS_TC_ESC_ACC/ABS_TC_ESC_ACC.delta",  
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);
        
        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripABS_TC_FWD() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/05.ABS_TC_FWD/ABS_TC_FWD.delta",  
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);
        
        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripABS_TC_ESC_ACC_FWD() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/06.ABS_TC_ESC_ACC_FWD/ABS_TC_ESC_ACC_FWD.delta",  
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);
        
        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripABS_TC_ESC_RG() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/07.ABS_TC_ESC_RG/ABS_TC_ESC_RG.delta",  
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);

        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    
    @Test
    public void testRoundtripABS_TWD() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/08.ABS_TWD/ABS_TWD.delta",  
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);

        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testRoundtripTWD() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/09.TWD/TWD.delta",   
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);

        assertFalse(tool.slHandler.hasError());
        assertTrue(tool.slHandler.hasWarning());
        // weak remove of wheelSpeed2 and wheelSpeed4
        assertEquals(2, tool.slHandler.getReports(Type.WARNING).size());
    }
    
    @Test
    public void testRoundtripABS_TC_ESC_FWD() {
        DeltaSimulinkTool tool = new DeltaSimulinkTool();
        tool.runDeltaMontiArc("./src/test/resources/simulink/BrakingSystem/products/10.ABS_TC_ESC_FWD/ABS_TC_ESC_FWD.delta",   
                DeltaSimulinkTool.TMP_CORE_FOLDER, 
                DeltaSimulinkTool.TMP_DELTA_FOLDER, 
                DeltaSimulinkTool.TMP_GEN_FOLDER);

        assertFalse(tool.slHandler.hasError());
        assertFalse(tool.slHandler.hasWarning());
    }
    
    @Test
    public void testInvalidCoreFolder() {
        try {
            new DeltaSimulinkTool(new String[]{
            		"../bla bla bla", 
                    "./src/test/resources/simulink/BrakingSystem/deltas", 
                    "./src/test/resources/simulink/BrakingSystem/products/01.ABS/ABS.delta", 
            OUTPUT});
            fail("Expected exception");
        }
        catch (RuntimeException e) {
            assertEquals("Core model directory does not exist. ", e.getMessage());
        }
    }
    
    @Test
    public void testFileCoreFolder() {
        try {
            new DeltaSimulinkTool(new String[]{
                    "./src/test/resources/simulink/BrakingSystem/core/ABS.mdl", 
                    "./src/test/resources/simulink/BrakingSystem/deltas", 
                    "./src/test/resources/simulink/BrakingSystem/products/01.ABS/ABS.delta", 
            OUTPUT});
            fail("Expected exception");
        }
        catch (RuntimeException e) {
            assertEquals("Core model directory does not exist. ", e.getMessage());
        }
    }
    
    @Test
    public void testInvalidDeltaFolder() {
        try {
            new DeltaSimulinkTool(new String[]{
                    "./src/test/resources/simulink/BrakingSystem/core", 
                    "../foo/bar", 
                    "./src/test/resources/simulink/BrakingSystem/products/01.ABS/ABS.delta", 
            OUTPUT});
            fail("Expected exception");
        }
        catch (RuntimeException e) {
            assertEquals("Delta model directory does not exist. ", e.getMessage());
        }
    }
    
    @Test
    public void testFileDeltaFolder() {
        try {
            new DeltaSimulinkTool(new String[]{
                    "./src/test/resources/simulink/BrakingSystem/core", 
                    "./src/test/resources/simulink/BrakingSystem/deltas/DElectronicStabilityControl.mdl",
                    "./src/test/resources/simulink/BrakingSystem/products/01.ABS/ABS.delta", 
            OUTPUT});
            fail("Expected exception");
        }
        catch (RuntimeException e) {
            assertEquals("Delta model directory does not exist. ", e.getMessage());
        }
    }
    
    @Test
    public void testInvalidCfgFile() {
        try {
            new DeltaSimulinkTool(new String[]{
                    "./src/test/resources/simulink/BrakingSystem/core", 
                    "./src/test/resources/simulink/BrakingSystem/deltas", 
                    "./src/test/resources/simulink/BrakingSystem/products/Foo.delta", 
            OUTPUT});
            fail("Expected exception");
        }
        catch (RuntimeException e) {
            assertEquals("Configuration file does not exist. ", e.getMessage());
        }
    }
    
    @Test
    public void testFolderCfgFile() {
        try {
            new DeltaSimulinkTool(new String[]{
                    "./src/test/resources/simulink/BrakingSystem/core", 
                    "./src/test/resources/simulink/BrakingSystem/deltas", 
                    "./src/test/resources/products", 
            OUTPUT});
            fail("Expected exception");
        }
        catch (RuntimeException e) {
            assertEquals("Configuration file does not exist. ", e.getMessage());
        }
    }
    
    @Test
    public void testFileOutputFile() {
        try {
            new DeltaSimulinkTool(new String[]{
                    "./src/test/resources/simulink/BrakingSystem/core", 
                    "./src/test/resources/simulink/BrakingSystem/deltas",
                    "./src/test/resources/bla.cfg",
                    "./src/test/resources/simulink/BrakingSystem/products/01.ABS/ABS.delta"} 
                    );
            fail("Expected exception");
        }
        catch (RuntimeException e) {
            assertEquals("Output directory must be a directory.", e.getMessage());
        }
    }
    
    @Test
    public void testNoArguments() {
        try {
            new DeltaSimulinkTool(new String[]{});            
        }
        catch (RuntimeException e) {
            assertEquals(DeltaSimulinkTool.PARAMETER_MESSAGE, e.getMessage());
        }
    }
    
}
