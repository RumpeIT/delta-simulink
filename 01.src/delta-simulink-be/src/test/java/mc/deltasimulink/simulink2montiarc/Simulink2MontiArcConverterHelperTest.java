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

import org.junit.Ignore;
import org.junit.Test;

public class Simulink2MontiArcConverterHelperTest {

//	@Ignore("Just for CKs own testing") 
	@Test
	public void test() {
		Simulink2MontiArcConverterHelper s2ma=new Simulink2MontiArcConverterHelper();
		s2ma.convert("C:\\MATLAB\\Delta", "DAntiLockBrakingSystem", "out.txt");
	}

}
