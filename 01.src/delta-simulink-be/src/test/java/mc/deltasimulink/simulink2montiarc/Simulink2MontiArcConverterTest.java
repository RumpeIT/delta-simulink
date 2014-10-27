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
package mc.deltasimulink.simulink2montiarc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import mc.ProblemReport.Type;
import mc.deltasimulink.helper.TestHelper;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class Simulink2MontiArcConverterTest extends TestHelper {
	
	private static final String SEP = File.separator;
	//private static final String PATH_TO_MODELS = ".." + SEP + "02.simulink" + SEP + "abstract";
	private static final String PATH_TO_MODELS = "src" + SEP + "test" + SEP + "resources" + SEP + "simulink" + SEP + "abstract" + SEP;
    private static final String INPUT_FOLDER = "src/test/resources";
	
	

	@BeforeClass
	public static void connect() {
	    matlab.connect();
        assertTrue(matlab.isConnected());	
    }
/*	
//	@Test
//	@Ignore
//	public void testConvertModel() {
//		String modelName = "SupportedCoreModelElements";
//				
//		Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
//		String montiArcModel = conv.convertModel(PATH_TO_MODELS, modelName);
//		
//		String expected = "package " + MontiArcStringBuilder.EXPORT_PACKAGE + ";" +
//		                  "component SupportedCoreModelElements {\n\n" +
//						  "port\n" +
//						  "  in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
//						  "  out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1,\n" +
//						  "  out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out2,\n" +
//						  "  out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out3;\n" +
//						  "\n" +
//						  "component DAbstractOperations Model;\n" +
//						  "\n" +
//						  "component Subsystem Subsystem {\n" +
//						  "port\n" +
//						  "  in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
//						  "  out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" +
//						  "component Subsystem Subsystem {\n" +
//						  "port\n" +
//						  "  in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
//						  "  out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" +
//						  "connect In1 -> Out1;" +
//						  
//						  "}\n" +
//						  "component ToReplace Model;\n" +
//
//						  "connect Model.brakePressure1 -> Terminator;" +
//
//						  "connect Model.brakePressureOut1 -> Subsystem.In1;" +
//
//						  "connect Subsystem.Out1 -> Out1;" +
//
//						  "connect In1 -> Model.In1;" +
//						  
//						  "}\n" +
//						  
//						  "connect MathFunction -> Out3;" +
//						  
//						  "connect Model.brakePressure1 -> MathFunction;" +
//						  
//						  "connect Add -> Out2;" +
//						  
//						  "connect Model.brakePressureOut1 -> Add;" +
//						  
//						  "connect Model.brakePressureOut1 -> Add;" +
//						  
//						  "connect Subsystem.Out1 -> Out1;" +
//						  
//						  "connect In1 -> Model.In1;" +
//						  
//						  "connect In1 -> Subsystem.In1;" +
//		"\n}\n";
//		// fails due to not supported core elements (function blocks)
//		compareMontiArcModelStrings(expected, montiArcModel);
//	}
//	
//	@Test
//	public void testConvertConnectors() {
//		String modelName = "TestConnectors";
//		
//		Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
//		
//		String montiArcModel = conv.convertModel(PATH_TO_MODELS, modelName);
//		
//		String expected = 
//		        "package " + MontiArcStringBuilder.EXPORT_PACKAGE + ";" +
//		        "component " + modelName + "{" +
//						  
//							  "port " +
//							  	"in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1," +
//							  	"in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In2," +
//							  	"in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In3," +
//							  	"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1," +
//							  	"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out2," +
//							  	"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out3," +
//							  	"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out4," +
//							  	"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out5;" +
//						  
//							  "component TestReferenceModel Model;" +
//							  	
//							  "component Sub1 Sub1 {" +
//							  	"port " +
//							  		"in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1," +
//							  		"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1," +
//							  		"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out2," +
//							  		"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out3," +
//							  		"out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out4;" +
//							  		
//									"connect In1 -> Out4;" +
//									"connect In1 -> Out2;" +
//									"connect In1 -> Out3;" +
//									"connect In1 -> Out1;" +
//							  "}" +
//						  
//							  "connect In1 -> Out1;" +
//							  "connect Sub1.Out1 -> Out5;" +
//							  "connect Sub1.Out1 -> Out2;" +
//							  "connect Model.Out1 -> Out3;" +
//							  "connect In3 -> Out4;" +
//							  "connect In3 -> Model.In1;" +
//							  "connect In2 -> Sub1.In1;" +
//						  
//						  "}";
//		
//		// TODO test hierarchical Sub
//		// TODO test Sub 2 Sub
//		// TODO test Ref 2 Ref
//		// TODO test Sub 2 Ref
//		// TODO test Ref 2 Sub
//		// TODO test Port with 2 outgoing connectors
//		// TODO test incorrect connector
//		
//		compareMontiArcModelStrings(expected, montiArcModel);
//	}
*/	
	@Ignore @Test
	public void testConvertDeltaModels() {
	    String modelName = "SupportedDeltaModelElements";
        
        Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
        
        String actualDelta = conv.convertDeltaModel(PATH_TO_MODELS, modelName);
        
        String expectedDelta = 
                "delta SupportedDeltaModelElements after (A || B) && !C {\n" + 
                "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".SupportedCoreModelElements {\n" + 
                "       add port\n" +
                "           in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In2,\n" +
                "           out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out4;\n" + 

                "       add component Subsystem1 Subsystem1 {\n" + 
                "           port\n" + 
                "               in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                "               out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                "           component SubsystemBla SubsystemBla {\n" + 
                "               port\n" + 
                "                   in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                "                   out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                "               connect In1 -> Out1;" +
                "           }\n" + 
                "           component SubsystemBla1 SubsystemBla1 {\n" + 
                "               port\n" + 
                "                   in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                "                   out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                "               component InBla1 InBla1 {\n" + 
                "                   port\n" + 
                "                       in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                "                       out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                "                   connect In1 -> Out1;" +
                "               }\n" + 
                "               connect InBla1.Out1 -> Out1;"+
                "               connect In1 -> InBla1.In1;"+
                "           }\n" + 
                "           connect SubsystemBla.Out1 -> Out1;"+
                "           connect In1 -> SubsystemBla.In1;"+
                "       }\n" + 
                "       modify component brakefunction {\n" + 
                "           remove port In1;\n" +
                "           remove port Out1;\n" +
                "       }\n" + 
                "       // replace subsystem Subsystem\n" + 
                "       add component newModel {\n" + 
                "           port\n" + 
                "               in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                "               out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                "           connect In1 -> Out1;" + 
                "       }\n" + 
                "       replace component Subsystem with component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".SupportedCoreModelElements.newModel newModel;\n" + 
                "       add component ToReplace newModel;\n" + 
                "       replace component Model with component ToReplace newModel;\n" + 
                "       connect newModel.brakePressure1 -> Out3;\n" +
                "connect newModel.brakePressureOut1 -> Out2;\n" +
                "       connect Subsystem1.Out1 -> Out4;\n" + 
                "       connect In2 -> Subsystem1.In1;\n" + 
                "       disconnect Subsystem.Out1 -> Out1;\n" + 
                "       disconnect In1 -> Subsystem.In1;\n" + 

                "       remove port In1;\n" +
                "       remove port Out1;\n" +
                "       remove component Subsystem;\n" + 
                "       remove component Model;\n" + 
	            "   }\n" + 
	            "}";
        compareDeltaStrings(expectedDelta, actualDelta);

        
        assertEquals(0, super.handler.getReports(Type.ERROR).size());
	}
	
	@Test
	public void testDeltaEmptyModify() {
	    String modelName = "DEmptyModify";
        
        Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
        
        String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
        
        String expectedDelta = 
                "delta DEmptyModify {\n" + 
                "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                "   }\n" + 
                "}";
        
        compareDeltaStrings(expectedDelta, actualDelta);
	}
	
   @Test
    public void testDeltaModifyAOC() {
        String modelName = "DModifyAOC";
        
        Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
        
        String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
        
        String expectedDelta = 
                "delta DModifyAOC after X && !(A || B) {\n" + 
                "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                "   }\n" + 
                "}";
        
        compareDeltaStrings(expectedDelta, actualDelta);
    }
	   
       @Test
       public void testDeltaModifySubsystem() {
           String modelName = "DModifySubsystem";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DModifySubsystem {\n" + 
                   "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        modify component subSystemToModify {\n" +
                   "        }\n" + 
                   "   }\n" + 
                   "}";
           
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaModifySubsystemAndReplace() {
           String modelName = "DModifySubsystemAndReplace";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DModifySubsystemAndReplace {\n" + 
                   "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        modify component subSystemToModify {\n" +
                   "            add component newSubSysName {\n" +
                   "                port\n" + 
                   "                    in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
                   "                    out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "            }\n" +
                   "        replace component subSysToReplace with component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify.subSystemToModify.newSubSysName newSubSysName;\n" + 
                   "        }\n" + 
                   "   }\n" + 
                   "}";
           
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaAddModelBlock() {
           String modelName = "DAddModelReference";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DAddModelReference {\n" + 
                   "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        add component ModRef modelBlockToAdd;\n" +
                   "   }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaAddSubSystemBlock() {
           String modelName = "DAddSubSystem";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DAddSubSystem {\n" + 
                   "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        add component subsystemToAdd subsystemToAdd {\n" +
                   "            port in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "            connect In1 -> Out1;\n" + 
                   "        }\n" + 
                   "   }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaAddPort() {
           String modelName = "DAddPort";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DAddPort {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        add port\n" + 
                   "            in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
                   "            out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "   }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       
       @Test
       public void testDeltaRemovePort() {
           String modelName = "DRemovePort";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DRemovePort {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        remove port In1;\n" +
                   "        remove port Out1;\n" +
                   "   }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaRemoveModelBlock() {
           String modelName = "DRemoveModelReference";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DRemoveModelReference {\n" + 
                   "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        remove component modelBlockToRemove;\n" +
                   "   }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaRemoveSubSystem() {
           String modelName = "DRemoveSubSystem";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DRemoveSubSystem {\n" + 
                   "   modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" + 
                   "        remove component subsystemToRemove;\n" +
                   "   }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaReplaceModelBlock() {
           String modelName = "DReplaceModel";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DReplaceModel {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
                   "        replace component modToReplace with component ModRef2 newBlockName;\n" +
                   "    }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
	
       @Test
       public void testDeltaReplaceSubSystem() {
           String modelName = "DReplaceSubSystem";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DReplaceSubSystem {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
                   "        // replace subsystem subSysToReplace\n" + 
                   "        add component newSubSysName {\n" +
                   "            port\n" + 
                   "                in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "        }\n" + 
                   "        replace component subSysToReplace with component " +
                   "       " +  MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify.newSubSysName newSubSysName;\n" + 
                   "    }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaAddConnectorAA() {
           String modelName = "DAddConAA";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DAddConAA {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
                   "        add port\n" +
                   "            in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
                   "            in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In2,\n" +
                   "            out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1,\n" +
                   "            out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out2;\n" +
                   "        add component subsystemToAdd subsystemToAdd {\n" + 
                   "            port\n" +
                   "                in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "            connect In1 -> Out1;\n" + 
                   "        }" + 
                   "        add component ModRef modelBlockToAdd;\n" +
                   "        connect In2 -> modelBlockToAdd.In1;\n"+
                   "        connect modelBlockToAdd.Out1 -> Out2;\n"+
                   "        connect subsystemToAdd.Out1 -> Out1;\n"+
                   "        connect In1 -> subsystemToAdd.In1;\n" + 
                   "    }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaAddConnectorMA_AM() {
           String modelName = "DAddConMA_AM";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DAddConMA_AM {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
                   "        add port\n" +
                   "            in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
                   "            out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" +
                   "        modify component subsysToModify {\n" + 
                   "            add port\n" +
                   "                in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "            connect In1 -> Out1;\n" + 
                   "        }" + 
                   "        connect subsysToModify.Out1 -> Out1;" +
                   "        connect In1 -> subsysToModify.In1;" +
                   "    }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaRemoveConnectorRR() {
           String modelName = "DRemoveConRR";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DRemoveConRR {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
                   "        disconnect modelBlockToRemove.Out1 -> Out2;\n" + 
                   "        disconnect subsystemToRemove.Out1 -> Out1;\n" +
                   "        disconnect In2 -> modelBlockToRemove.In1;\n" +
                   "        disconnect In1 -> subsystemToRemove.In1;\n" +
                   "        remove port In1;\n" +
                   "        remove port In2;\n" +
                   "        remove port Out1;\n" +
                   "        remove port Out2;\n" +
                   "        remove component subsystemToRemove;\n" + 
                   "        remove component modelBlockToRemove;\n" +
                   "    }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaRemoveConnectorMA_AM() {
           String modelName = "DRemoveConMA_AM";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DRemoveConMA_AM {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
                   "        modify component subsysToModify {\n" + 
                   "            disconnect In1 -> Out1;\n" + 
                   "            remove port In1;\n" +
                   "            remove port Out1;\n" +
                   "        }" + 
                   "        disconnect subsysToModify.Out1 -> Out1;" +
                   "        disconnect In1 -> subsysToModify.In1;" +
                   "        remove port In1;\n" +
                   "        remove port Out1;\n" +
                   "    }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
       @Test
       public void testDeltaAddConnectorAR_RA() {
           String modelName = "DAddConRA_AR";
           
           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
           
           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
           
           String expectedDelta = 
                   "delta DAddConRA_AR {\n" + 
                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
                   "        add port\n" +
                   "            in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" +
                   "            in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In2,\n" +
                   "            out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1,\n" +
                   "            out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out2;\n" +
                   "        add component newSubSys1 {\n" + 
                   "            port\n" +
                   "                in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "            connect In1 -> Out1;\n" + 
                   "        }" + 
                   "        replace component subsys1 with component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify.newSubSys1 newSubSys1;" + 
                   "        add component newSubSys2 {\n" + 
                   "            port\n" +
                   "                in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
                   "            connect In1 -> Out1;\n" + 
                   "        }" + 
                   "        replace component subsys2 with component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify.newSubSys2 newSubSys2;" +
                   "        replace component modBlock1 with component ModRef2 newModBlock1;\n" +
                   "        replace component modBlock2 with component ModRef2 newModBlock2;\n" +
                   "        connect newModBlock2.Out1 -> Out2;\n" +
                   "        connect newModBlock1.Out1 -> newModBlock2.In1;\n" +
                   "        connect In2 -> newModBlock1.In1;\n" +
                   "        connect newSubSys2.Out1 -> Out1;\n" +
                   "        connect newSubSys1.Out1 -> newSubSys2.In1;\n" +
                   "        connect In1 -> newSubSys1.In1;\n" +
                   "    }\n" + 
                   "}";
           compareDeltaStrings(expectedDelta, actualDelta);
       }
       
//       @Test
//       public void testDeltaRemoveConnectorRepRem_RemRep() {
//           String modelName = "DRemoveConRepRem_RemRep";
//           
//           Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(matlab, delegator);
//           
//           String actualDelta = conv.convertDeltaModel(INPUT_FOLDER + "/simulink/deltas", modelName);
//           
//           String expectedDelta = 
//                   "delta DRemoveConRepRem_RemRep {\n" + 
//                   "    modify component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify {\n" +
//                   "        add component newSubSys1 {\n" + 
//                   "            port\n" +
//                   "                in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
//                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
//                   "            connect In1 -> Out1;\n" + 
//                   "        }" + 
//                   "        replace component subsys1 with component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify.newSubSys1 newSubSys1;" +
//                   "        add component newSubSys2 {\n" + 
//                   "            port\n" +
//                   "                in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " In1,\n" + 
//                   "                out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " Out1;\n" + 
//                   "            connect In1 -> Out1;\n" + 
//                   "        }" + 
//                   "        replace component subsys2 with component " + MontiArcStringBuilder.EXPORT_PACKAGE + ".ModelToModify.newSubSys2 newSubSys2;" +
//                   "        replace component modBlock1 with component ModRef2 newModBlock1;\n" +
//                   "        replace component modBlock2 with component ModRef2 newModBlock2;\n" +
//                   
//                   "        disconnect newModBlock2.Out1 -> Out2;\n" +
//                   "        connect newModBlock1.Out1 -> newModBlock2.In1;\n" +
//                   "        disconnect In2 -> newModBlock1.In1;\n" +
//                   "        disconnect newSubSys2.Out1 -> Out1;\n" +
//                   "        connect newSubSys1.Out1 -> newSubSys2.In1;\n" +
//                   "        disconnect In1 -> newSubSys1.In1;\n" +
//                   
//                   "        remove port In1;\n" +
//                   "        remove port In2;\n" +
//                   "        remove port Out1;\n" +
//                   "        remove port Out2;\n" +
//                   "    }\n" + 
//                   "}";
//           compareDeltaStrings(expectedDelta, actualDelta);
//       }
}
