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

public class SimulinkNameHelper {

	public static String getSimpleName(String name) {
		if (name.endsWith("/")) {
			name = name.substring(0, name.length()-1);
		}
		
		int i = name.lastIndexOf("/");
		
		if (i != -1) {
			return name.substring(i+1);
		}
		
		return name;
	}

	public static String getQualifierName(String name) {
		if (name.endsWith("/")) {
			name = name.substring(0, name.length()-1);
		}
		
		int i = name.lastIndexOf("/");
		
		if (i != -1) {
			return name.substring(0, i);
		}
		
		return "";
		
	}

}
