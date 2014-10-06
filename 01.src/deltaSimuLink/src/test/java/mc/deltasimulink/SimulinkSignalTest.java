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
package mc.deltasimulink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SimulinkSignalTest {

	@Test
	public void testPortNames() {
		SimulinkSignal signal = new SimulinkSignal("src", "dst");
		assertFalse(signal.hasSrcPortName());
		assertFalse(signal.hasDstPortName());
		
		signal.setSrcPortName("");
		assertFalse(signal.hasSrcPortName());
		signal.setDstPortName("");
		assertFalse(signal.hasDstPortName());
		
		signal.setSrcPortName("SrcName");
		assertTrue(signal.hasSrcPortName());
		signal.setDstPortName("DstName");
		assertTrue(signal.hasDstPortName());
	}
	
	@Test
	public void testSimpleNames() {
		SimulinkSignal signal = new SimulinkSignal("qualified/src", "qualified/dst");
		assertEquals("src", signal.getSrcSimpleName());
		assertEquals("dst", signal.getDstSimpleName());
		
		signal = new SimulinkSignal("simpleSrc", "simpleDst");
		assertEquals(signal.getSrcSimpleName(), signal.getQualifiedSrcName());
		assertEquals(signal.getDstSimpleName(), signal.getQualifiedDstName());
	}
	
}
