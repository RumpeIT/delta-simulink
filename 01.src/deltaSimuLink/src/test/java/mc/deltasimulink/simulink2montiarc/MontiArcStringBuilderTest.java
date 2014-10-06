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

import java.util.Arrays;

import mc.deltasimulink.SimulinkPort;
import mc.deltasimulink.helper.TestHelper;

import org.junit.Test;

public class MontiArcStringBuilderTest extends TestHelper {
	
	
	@Test
	public void testBuild() {
		MontiArcStringBuilder builder = new MontiArcStringBuilder();
		
		assertEquals("", builder.getConvertedModel());
		
		
		String expectedPackage = "package pack.name;";
		assertEquals(expectedPackage, builder.addPackage("pack.name"));
		
		String expectedImport = "import a.b.c;";
		assertEquals(expectedImport, builder.addImport("a.b.c"));
		
		String expected1 = "component ModelName {";
		assertEquals(expected1, builder.startCreateComponent("ModelName"));
		
		String expected2 = "port\n" +
					 " in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " in1,\n" +
					 " in " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " in2,\n" +
					 " out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " out1,\n" +
					 " out " + MontiArcStringBuilder.DUMMY_PORT_TYPE + " out2;\n";
		assertEquals(expected2, builder.createPorts(Arrays.asList(new SimulinkPort("in1", 1, ""), new SimulinkPort("in2", 2, "")),
													Arrays.asList(new SimulinkPort("out1", 1, ""), new SimulinkPort("out2", 2, ""))));
		
		
		String expected3 = "component ReferencedModel refName;";
		assertEquals(expected3, builder.createComponentReference("ReferencedModel", "refName"));
		
		String expected6 = "component InnerComponent insName {";
		assertEquals(expected6, builder.startCreateInnerComponent("InnerComponent", "insName"));
		
		String expected7 = "}";
		assertEquals(expected7, builder.endCreateInnerComponent());
		
		String expected9 = "connect refName.out1 -> out1;";
		assertEquals(expected9, builder.createConnector("refName.out1", "out1"));

		String expected10 = "}";
		assertEquals(expected10, builder.endCreateComponent());
		
		String expectedConversion = expectedPackage + "\n" + expectedImport + "\n" + expected1 + expected2 + expected3 + 
									expected6 + expected7 + expected9 + expected10;
		
		compareMontiArcModelStrings(expectedConversion, builder.getConvertedModel());
		
		
	}
	
}
