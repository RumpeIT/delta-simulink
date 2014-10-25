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

import org.junit.Test;

public class SimulinkNameHelperTest {

	
	@Test
	public void testNames() {
		assertEquals("", SimulinkNameHelper.getSimpleName(""));
		assertEquals("SimpleName", SimulinkNameHelper.getSimpleName("SimpleName"));
		assertEquals("SimpleName", SimulinkNameHelper.getSimpleName("SimpleName/"));
		assertEquals("SimpleName", SimulinkNameHelper.getSimpleName("a/SimpleName"));
		assertEquals("SimpleName", SimulinkNameHelper.getSimpleName("a/b/SimpleName"));
		assertEquals("SimpleName", SimulinkNameHelper.getSimpleName("a/b/SimpleName/"));
		
		assertEquals("", SimulinkNameHelper.getQualifierName(""));
		assertEquals("", SimulinkNameHelper.getQualifierName("SimpleName"));
		assertEquals("a", SimulinkNameHelper.getQualifierName("a/SimpleName"));
		assertEquals("a", SimulinkNameHelper.getQualifierName("a/SimpleName/"));
		assertEquals("a/b", SimulinkNameHelper.getQualifierName("a/b/SimpleName"));
	}
}

