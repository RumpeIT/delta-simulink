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
package mc.deltasimulink.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import mc.deltasimulink.SimulinkBlockType;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class MatlabProxyHelperTest {

	private static final String SEP = File.separator;
	//private static final String PATH_TO_MODELS = "src" + SEP + "test" + SEP + "resources" + SEP + "02.simulink" + SEP;
	private static final String PATH_TO_MODELS = "src" + SEP + "test" + SEP + "resources" + SEP + "Simulink" + SEP;
	private MatlabProxyHelper matlab;
	
	@Before
	public void setUp() {
		matlab = MatlabProxyHelper.getInstance();
	}
	
	@After
	public void cleanUp() {
		disconnect();
	}

	@Test
	public void testConnectAndDisconnect() {
		assertTrue(matlab.connect());
		assertTrue(matlab.isConnected());
		assertFalse(matlab.isDisconnected());
		
		assertTrue(matlab.exitAndDisconnect());
		assertTrue(matlab.isDisconnected());
		assertFalse(matlab.isConnected());
	}
	
	@Test
	public void testLoadModelInfos() {
		
		connect();
		
		String modelName = "ZBrakingSystem";
		matlab.loadModel(PATH_TO_MODELS + "BrakingSystem/core/", modelName);
		
		String stringResult = (String) matlab.returningFeval("get_param", modelName, "Name");
		assertEquals(modelName, stringResult);
		
		stringResult = (String) matlab.returningFeval("get_param", modelName, "Type");
		assertEquals("block_diagram", stringResult);
		
		
		String[] stringArrayResult = (String[]) matlab.returningFeval("find_system");
		
		assertEquals(7, stringArrayResult.length);
		assertEquals(modelName, stringArrayResult[0]);
		assertEquals(modelName + "/brake", stringArrayResult[1]);
		assertEquals(modelName + "/brakefunction", stringArrayResult[2]);
		assertEquals(modelName + "/brakePressure1", stringArrayResult[3]);
		assertEquals(modelName + "/brakePressure2", stringArrayResult[4]);
		assertEquals(modelName + "/brakePressure3", stringArrayResult[5]);
		assertEquals(modelName + "/brakePressure4", stringArrayResult[6]);
		
		stringArrayResult = (String[]) matlab.returningFeval("find_system", "Type", "block_diagram");
		assertEquals(1, stringArrayResult.length);
		assertEquals(modelName, stringArrayResult[0]);
		
		stringArrayResult = (String[]) matlab.returningFeval("find_system", "SearchDepth", 1, "BlockType", SimulinkBlockType.Inport.toString());
		assertEquals(1, stringArrayResult.length);
		assertEquals(modelName + "/brake", stringArrayResult[0]);
		
		stringResult = (String) matlab.returningFeval("get_param", modelName + "/brake", "BlockType");
		assertEquals(SimulinkBlockType.Inport.toString(), stringResult);
		stringResult = (String) matlab.returningFeval("get_param", modelName + "/brake", "Name");
		assertEquals("brake", stringResult);
		
		
		stringArrayResult = (String[]) matlab.returningFeval("find_system", "SearchDepth", 1, "BlockType", SimulinkBlockType.Outport.toString());
		assertEquals(4, stringArrayResult.length);
		// returns ports ordered
		assertEquals(modelName + "/brakePressure1", stringArrayResult[0]);
		assertEquals(modelName + "/brakePressure2", stringArrayResult[1]);
		assertEquals(modelName + "/brakePressure3", stringArrayResult[2]);
		assertEquals(modelName + "/brakePressure4", stringArrayResult[3]);
		
		
		stringResult = (String) matlab.returningFeval("get_param", modelName + "/brakefunction", "Type");
		assertEquals("block", stringResult);
		stringResult = (String) matlab.returningFeval("get_param", modelName + "/brakefunction", "BlockType");
		assertEquals(SimulinkBlockType.ModelReference.toString(), stringResult);
		stringResult = (String) matlab.returningFeval("get_param", modelName + "/brakefunction", "ModelName");
		assertEquals("PressureCalculator" , stringResult);
		stringResult = (String) matlab.returningFeval("get_param", modelName + "/brakefunction", "ModelFile");
		assertEquals("PressureCalculator.mdl" , stringResult);
		
	}
	
	@Test
	public void testHandles() {
		connect();
		
		String modelName = "SupportedCoreModelElements";
		matlab.loadModel(PATH_TO_MODELS + "abstract", modelName);
		
		double[] res = (double[]) matlab.returningFeval("get_param", modelName + "/In1", "Handle");
		double handle = res[0];
		
		assertEquals("In1", matlab.returningFeval("get_param", handle, "Name"));
	}
	
	@Test
	public void testConnectors() {
		connect();
		
		String modelName = "TestConnectors";
		matlab.loadModel(PATH_TO_MODELS + "abstract", modelName);
		
		Object[] res = (Object[]) matlab.returningFeval(1, "get_param", modelName + "/In3", "PortConnectivity")[0];
		Object[] portConnectivity = (Object[]) ((Object[]) res[1])[0];
		double[] dstBlocks = (double[]) portConnectivity[4];
		
		// two outgoing signals
		assertEquals(2, dstBlocks.length);
		assertEquals("Out4", matlab.returningFeval("get_param", dstBlocks[0], "Name"));
		assertEquals("Model", matlab.returningFeval("get_param", dstBlocks[1], "Name"));
		
	}
	
	@Test
	public void testClearPath() {
	    matlab.connect();
	    matlab.addFolderAndSubsToPath(new File("inputForTests/simulink"));
	    matlab.clearPath();
	    assertEquals(0, matlab.addedPaths.size());
	    matlab.exitAndDisconnect();
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
