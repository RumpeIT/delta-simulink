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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mc.ConsoleErrorHandler;
import mc.IErrorDelegator;
import mc.StandardErrorDelegator;
import mc.TestErrorHandler;
import mc.deltasimulink.helper.MatlabProxyHelper;

public class Simulink2MontiArcConverterHelper {
	protected MatlabProxyHelper matlab;

	protected IErrorDelegator delegator;

	public Simulink2MontiArcConverterHelper() {
		super();
		matlab = MatlabProxyHelper.getInstance();

		TestErrorHandler handler;

		StandardErrorDelegator delegatorTmp = new StandardErrorDelegator();
		handler = new TestErrorHandler();
		delegatorTmp.addErrorHandler(handler);
		delegatorTmp.addErrorHandler(new ConsoleErrorHandler());
		this.delegator = delegatorTmp;

	}

	public void convert(String path, String inputMdl, String outputTxt) {
		Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(
				matlab, delegator);
		String output="";
		try{
		output = conv.convertDeltaModel(path, inputMdl);
		}catch(Exception e){
			
		}
		FileWriter fileWriter = null;
		try {

			File newTextFile = new File(outputTxt);
			fileWriter = new FileWriter(newTextFile);
			fileWriter.write(output);
			fileWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(Simulink2MontiArcConverterHelper.class.getName())
					.log(Level.SEVERE, null, ex);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException ex) {
				Logger.getLogger(
						Simulink2MontiArcConverterHelper.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}
	public void convert2MA(String path, String inputMdl, String outputTxt) {
		Simulink2MontiArcConverter conv = new Simulink2MontiArcConverter(
				matlab, delegator);
		String output="";
		try{
		output = conv.convertModel(path, inputMdl);
		}catch(Exception e){
			
		}
		FileWriter fileWriter = null;
		try {

			File newTextFile = new File(outputTxt);
			fileWriter = new FileWriter(newTextFile);
			fileWriter.write(output);
			fileWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(Simulink2MontiArcConverterHelper.class.getName())
					.log(Level.SEVERE, null, ex);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException ex) {
				Logger.getLogger(
						Simulink2MontiArcConverterHelper.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

}
