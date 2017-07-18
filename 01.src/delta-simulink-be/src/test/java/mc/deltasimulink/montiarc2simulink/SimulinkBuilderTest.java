/*******************************************************************************
 * Delta-Simulink
 * Copyright (c) 2013, RIDT, All rights reserved.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import mc.deltasimulink.SimulinkBlockType;
import mc.deltasimulink.SimulinkFunction;
import mc.deltasimulink.SimulinkParameter;
import mc.deltasimulink.helper.TestHelper;

import org.junit.Ignore;
import org.junit.Test;

public class SimulinkBuilderTest extends TestHelper {

	
	private static final String SEP = File.separator;
	private static final String ROOT_PATH = ".." + SEP + "02.simulink" + SEP;
	
	
	@Test
	public void testBuild() {
		
		connect();
		
		String modelName = "GeneratedModel";

		File path = new File(ROOT_PATH).getAbsoluteFile();
		String pathToOutput = path.getAbsolutePath() + SEP + "gen";
		
		// deletes output folder if it exists
		matlab.returningFeval("rmdir", pathToOutput, "s");
		
		// creates output folder
		matlab.returningFeval("mkdir", pathToOutput);

		// sets the rights for the new folder (write (+w) rights for all (a) users)
		matlab.returningFeval("fileattrib", pathToOutput, "+w", "a");

		
		SimulinkBuilder simBuilder = new SimulinkBuilder(matlab, pathToOutput, modelName);
		simBuilder.addInport("in1");
		// set also port number
		simBuilder.addInport("in2", 13);
		simBuilder.addInport("in3");
		simBuilder.addOutport("out1");
		simBuilder.addOutport("out2", 15);
		simBuilder.addModelReference("ReferenceName", "ReferencedModel");
		simBuilder.addSubsystem("SubSystem1");
		// inport of sub system
		simBuilder.addInport("SubSystem1/in1");
		simBuilder.addInport("SubSystem1/in2", 6);
		// outport of sub system
		simBuilder.addOutport("SubSystem1/out1");
		// nested sub system
		simBuilder.addSubsystem("SubSystem1/SubSubSystem");
		
		// signal port to port
		simBuilder.addConnector(modelName, "in1", 1, "out1", 1);
		// signal port to sub system
		simBuilder.addConnector(modelName, "in1", 1, "SubSystem1", 1);
		
		assertBuild(modelName);

		simBuilder.save();
		// disconnect and connect to test if model is saved correctly
		disconnect();
		connect();
		matlab.loadModel(pathToOutput, modelName);
		assertBuild(modelName);
		
		// cleanup: deletes output folder
		matlab.returningFeval(3, "rmdir", pathToOutput, "s");
		
		
		disconnect();
		
	}

	private void assertBuild(String modelName) {
		assertEquals("block_diagram", matlab.returningFeval("get_param", modelName, "Type"));
		assertEquals(SimulinkBlockType.Inport.toString(), matlab.returningFeval("get_param", modelName + "/in1", "BlockType"));
		assertEquals("1", matlab.returningFeval(SimulinkFunction.get_param, modelName + "/in1", SimulinkParameter.Port));
		assertEquals(SimulinkBlockType.Inport.toString(), matlab.returningFeval("get_param", modelName + "/in2", "BlockType"));
		assertEquals("13", matlab.returningFeval(SimulinkFunction.get_param, modelName + "/in2", SimulinkParameter.Port));
		assertEquals(SimulinkBlockType.Inport.toString(), matlab.returningFeval("get_param", modelName + "/in3", "BlockType"));
		assertEquals("2", matlab.returningFeval(SimulinkFunction.get_param, modelName + "/in3", SimulinkParameter.Port));
		assertEquals(SimulinkBlockType.Outport.toString(), matlab.returningFeval("get_param", modelName + "/out1", "BlockType"));
		assertEquals("1", matlab.returningFeval(SimulinkFunction.get_param, modelName + "/out1", SimulinkParameter.Port));
		assertEquals(SimulinkBlockType.Outport.toString(), matlab.returningFeval("get_param", modelName + "/out2", "BlockType"));
		assertEquals("15", matlab.returningFeval(SimulinkFunction.get_param, modelName + "/out2", SimulinkParameter.Port));
		assertEquals(SimulinkBlockType.ModelReference.toString(), matlab.returningFeval("get_param", modelName + "/ReferenceName", "BlockType"));
		assertEquals("ReferencedModel", matlab.returningFeval("get_param", modelName + "/ReferenceName", "ModelName"));
		assertEquals(SimulinkBlockType.SubSystem.toString(), matlab.returningFeval("get_param", modelName + "/SubSystem1", "BlockType"));
		assertEquals(SimulinkBlockType.Inport.toString(), matlab.returningFeval("get_param", modelName + "/SubSystem1/in1", "BlockType"));
		assertEquals(SimulinkBlockType.Inport.toString(), matlab.returningFeval("get_param", modelName + "/SubSystem1/in2", "BlockType"));
		assertEquals("6", matlab.returningFeval(SimulinkFunction.get_param, modelName + "/SubSystem1/in2", SimulinkParameter.Port));
		assertEquals(SimulinkBlockType.Outport.toString(), matlab.returningFeval("get_param", modelName + "/SubSystem1/out1", "BlockType"));
		assertEquals(SimulinkBlockType.SubSystem.toString(), matlab.returningFeval("get_param", modelName + "/SubSystem1/SubSubSystem", "BlockType"));
		
		// TODO assert signals
	}
	
	private void disconnect() {
		matlab.exitAndDisconnect();
		assertTrue(matlab.isDisconnected());
	}

	private void connect() {
		matlab.connect();
		assertTrue(matlab.isConnected());
	}
	
}
