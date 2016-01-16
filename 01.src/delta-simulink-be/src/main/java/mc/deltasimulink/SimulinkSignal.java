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

import mc.deltasimulink.helper.MatlabProxyHelper;

public class SimulinkSignal {
	


	private String qualifiedSrcName;
	private String qualifiedDstName;
	
	private int srcPortNumber;
	private int dstPortNumber;
	
	private String srcPortName;
	private String dstPortName;
	private double srcConnHandle;
	
	public SimulinkSignal(String qualifiedSrcName, String qualifiedDstName) {
		this(qualifiedSrcName, 1, qualifiedDstName, 1,0);
	}
	
	public SimulinkSignal(String qualifiedSrcName, int srcPortNumber, String qualifiedDstName, int dstPortNumber, double srcConnHandle) {
		this.qualifiedSrcName = qualifiedSrcName;
		this.srcPortNumber = srcPortNumber;
		this.qualifiedDstName = qualifiedDstName;
		this.dstPortNumber = dstPortNumber;
		this.srcConnHandle=srcConnHandle;
	}
	
	public double getSrcConnHandle() {
		return srcConnHandle;
	}

	public void setSrcConnHandle(double srcConnHandle) {
		this.srcConnHandle = srcConnHandle;
	}	
	
	public void setSrcPortNumber(int srcPortNumber) {
		this.srcPortNumber = srcPortNumber;
	}

	public int getSrcPortNumber() {
		return srcPortNumber;
	}
	
	public void setDstPortNumber(int dstPortNumber) {
		this.dstPortNumber = dstPortNumber;
	}

	public int getDstPortNumber() {
		return dstPortNumber;
	}

	public String getQualifiedSrcName() {
		return qualifiedSrcName;
	}

	public String getQualifiedDstName() {
		return qualifiedDstName;
	}

	public void setSrcPortName(String srcPortName) {
		this.srcPortName = srcPortName;
	}

	public String getSrcPortName() {
		return srcPortName;
	}

	public void setDstPortName(String dstPortName) {
		this.dstPortName = dstPortName;
	}

	public String getDstPortName() {
		return dstPortName;
	}
	
	public boolean hasSrcPortName() {
		return (srcPortName != null) && !srcPortName.isEmpty();
	}

	public boolean hasDstPortName() {
		return (dstPortName != null) && !dstPortName.isEmpty();
	}

	public String getSrcSimpleName() {
		return MatlabProxyHelper.getSimpleNameLimiterBased(qualifiedSrcName);
	}

	public String getDstSimpleName() {
		return MatlabProxyHelper.getSimpleNameLimiterBased(qualifiedDstName);
	}
	
}
