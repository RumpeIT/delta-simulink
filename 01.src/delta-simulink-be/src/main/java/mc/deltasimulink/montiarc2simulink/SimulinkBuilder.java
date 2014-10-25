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
package mc.deltasimulink.montiarc2simulink;

import java.io.File;

import mc.deltasimulink.SimulinkBlockType;
import mc.deltasimulink.helper.MatlabProxyHelper;

public class SimulinkBuilder {

	// root path of built in (block) types
	private static final String BUILT_IN = "built-in/";

	protected String outputFolder;
	protected String rootModelName;
	protected MatlabProxyHelper matlab;
	protected double inportX=30.0;
	protected double inportY=30.0;
	protected double inportOffset=0.0;
	protected double outportX=150.0;
	protected double outportY=30.0;
	protected double outportOffset=0.0;
	protected double blockX=90.0;
	protected double blockY=30.0;
	protected double blockOffset=0.0;
	protected double offset=60.0;
	protected double w=30.0;
	protected double h=30.0;
	
	public SimulinkBuilder(MatlabProxyHelper matlab, String outputFolder, String modelName) {
		this.matlab = matlab;
		File outdir = new File(outputFolder).getAbsoluteFile();
		this.outputFolder = outdir.getAbsolutePath();
		this.rootModelName = modelName;
		
		
		if (matlab.isDisconnected()) {
			matlab.connect();
		}
        
        // add folders to path
        matlab.addFolderAndSubsToPath(outdir);

        // sets the rights for the new folder (write (+w) rights for all (a) users)
        matlab.returningFeval("fileattrib", this.outputFolder, "+w", "a");
        
		// create and load new model
		matlab.returningFeval("new_system", modelName, "model");
		matlab.loadModel(outputFolder, modelName);
	}
	
	public void addInport(String portNameRelativeToModel) {
		double[] pos={inportX,inportY+inportOffset+h/4,inportX+w,inportY+inportOffset+h*.75};
		inportOffset=inportOffset+offset;
		

		matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.Inport.toString(), rootModelName + "/" + portNameRelativeToModel,"Position",pos);
	}
	
	public void addInport(String portNameRelativeToModel, int portNumber) {
		double[] pos={inportX,inportY+inportOffset+h/4,inportX+w,inportY+inportOffset+h*.75};
		inportOffset=inportOffset+offset;
		if (portNumber != -1) {
			matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.Inport.toString(), rootModelName + "/" + portNameRelativeToModel, "Port", portNumber+"","Position",pos);
		}
		else {
			matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.Inport.toString(), rootModelName + "/" + portNameRelativeToModel,"Position",pos);
		}
	}
	
	public void addOutport(String portNameRelativeToModel) {
		double[] pos={outportX,outportY+outportOffset+h/4,outportX+w,outportY+outportOffset+h*.75};
		outportOffset=outportOffset+offset;
		matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.Outport.toString(), rootModelName + "/" + portNameRelativeToModel,"Position",pos);
	}
	
	public void addOutport(String portNameRelativeToModel, int portNumber) {
		double[] pos={outportX,outportY+outportOffset+h/4,outportX+w,outportY+outportOffset+h*.75};
		outportOffset=outportOffset+offset;
		if (portNumber != -1) {
			matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.Outport.toString(), rootModelName + "/" + portNameRelativeToModel, "Port", portNumber+"","Position",pos);
		}
		else {
			matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.Outport.toString(), rootModelName + "/" + portNameRelativeToModel,"Position",pos);
		}
	}
	
	public void addModelReference(String modelNameRelativeToRootModel, String modelType) {
		double[] pos={blockX,blockY+blockOffset+h,blockX+w,blockY+blockOffset+h};
		blockOffset=blockOffset+offset;
		matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.ModelReference.toString(), rootModelName + "/" + modelNameRelativeToRootModel, "ModelName", modelType,"Position",pos);
	}
	
	public void addSubsystem(String subSystemNameRelativeToModel) {
		double[] pos={blockX,blockY+blockOffset+h,blockX+w,blockY+blockOffset+h};
		blockOffset=blockOffset+offset;
		matlab.returningFeval("add_block", BUILT_IN + SimulinkBlockType.SubSystem.toString(), rootModelName + "/" + subSystemNameRelativeToModel,"Position",pos);
	}
	

	public void addConnector(String rootBlock, String srcPort, int srcPortNumber, String destPort, int destPortNumber) {
		matlab.returningFeval("add_line", rootBlock, srcPort + "/" + srcPortNumber, destPort + "/" + destPortNumber);
	}
	
	public void save() {
		matlab.returningFeval("save_system", rootModelName, outputFolder +  File.separator + rootModelName);
	}
	
	
	
	
	
	
}
