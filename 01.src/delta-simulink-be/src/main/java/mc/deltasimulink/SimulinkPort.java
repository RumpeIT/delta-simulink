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

public class SimulinkPort {
	
	private String name;
	private int number;
	
	private String qualifiedParentBlock;
	
	public SimulinkPort(String portName, int portNumber, String qualifiedParentBlock) {
		this.name = portName;
		this.number = portNumber;
		this.qualifiedParentBlock = qualifiedParentBlock;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public String getQualifiedParentBlock() {
		return qualifiedParentBlock;
	}
	
	public String getQualifiedName() {
		if ((qualifiedParentBlock != null) && !qualifiedParentBlock.equals("")) {
			return qualifiedParentBlock + "/" + name;
		}
		
		return name;
	}

}
