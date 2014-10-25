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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import mc.deltasimulink.SimulinkBlockType;
import mc.deltasimulink.SimulinkFunction;
import mc.deltasimulink.SimulinkParameter;
import mc.deltasimulink.SimulinkType;

public class MatlabProxyHelper {

	private MatlabProxy proxy = null;
	
	private static MatlabProxyHelper instance = null;
	
	protected Set<File> addedPaths;

	protected String matlabLocation;

	protected MatlabProxyFactoryOptions factoryOptions;

	private MatlabProxyHelper() {
		addedPaths = new HashSet<File>();
		matlabLocation="";
		factoryOptions = null;
	}
	
	public static MatlabProxyHelper getInstance() {
		if (instance == null) {
			instance = new MatlabProxyHelper();
		}
		
		return instance;
	}


	public boolean connect() {
		File baseFolder = new File("").getAbsoluteFile();
		MatlabProxyFactoryOptions defaultOptions = new MatlabProxyFactoryOptions.Builder()
			.setHidden(true)
			.setMatlabStartingDirectory(baseFolder)
			.setProxyTimeout(60000)
			.build();

		if (factoryOptions == null) {
			factoryOptions = defaultOptions;
		}

		MatlabProxyFactory factory = new MatlabProxyFactory(factoryOptions);

		try {
			proxy  = factory.getProxy();
			while (!isConnected()) {
				// wait until it connects
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
			}
		} catch (MatlabConnectionException e) {
			e.printStackTrace();
		}
		
		return isConnected();
	}

	public boolean isConnected() {
		return (proxy != null) && proxy.isConnected();
	}
	
	public boolean disconnect() {
		if (isConnected()) {
		    addedPaths.clear();
			return proxy.disconnect();
		}
		
		return true;
	}
	
	public boolean isDisconnected() {
		return !isConnected();
	}
	
	public boolean exitAndDisconnect() {
		try {
			proxy.exit();
			disconnect();
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void loadModel(String pathToModel, String modelName) {
		File path = new File(pathToModel).getAbsoluteFile();
		addFolderAndSubsToPath(path);
		
		loadModel(modelName);
	}

	public void loadModel(String modelName) {
		feval(SimulinkFunction.load_system.toString(), modelName);
	}
	
	
	public void addFolderAndSubsToPath(File path) {
		if (path.isDirectory() && !addedPaths.contains(path) && !path.toString().contains(".svn")) {
			
			feval("addpath", path.getAbsoluteFile().toString());
			addedPaths.add(path);
			
			for (File sub : path.listFiles()) {
				addFolderAndSubsToPath(sub);
			}
		}
	}
	
	public void clearPath() {
	    
	    // TODO this is just a quick fix...lock up removepath command
	    exitAndDisconnect();
	    connect();
//	    for (File path : addedPaths) {
//	        feval("removepath", path.getAbsoluteFile().toString());	        
//	    }
//	    addedPaths.clear();
	}
	
	
	public void feval(String fname, Object... args) {
		try {
			proxy.feval(fname, args);
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			// TODO PN handle
		} catch (Exception e) {
			e.printStackTrace();
			// TODO PN handle
		}
	}
	
	public Object[] reval(String command, int nargout) {
		try {
			return proxy.returningEval(command, nargout);
	
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			// TODO PN handle
		} catch (Exception e) {
			e.printStackTrace();
			// TODO PN handle
		}
		return null;
	}
	
	public void setvar(String variableName, double value) {
		try {
			 proxy.setVariable(variableName, value);
	
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			// TODO PN handle
		} catch (Exception e) {
			e.printStackTrace();
			// TODO PN handle
		}
		
	}
	public void setvarO(String variableName, Object value) {
		try {
			 proxy.setVariable(variableName, value);
	
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			// TODO PN handle
		} catch (Exception e) {
			e.printStackTrace();
			// TODO PN handle
		}
		
	}
	public void eval(String command) {
		try {
			 proxy.eval(command);
	
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			// TODO PN handle
		} catch (Exception e) {
			e.printStackTrace();
			// TODO PN handle
		}
		
	}
	
	public Object returningFeval (SimulinkFunction fname, Object... args) {
		return returningFeval(fname.toString(), args);
	}
	
	public Object returningFeval (String fname, Object... args) {
		return returningFeval(1, fname, args)[0];
	}
	
	public Object[] returningFeval(int numReturnArgs, SimulinkFunction fname, Object... args) {
		return returningFeval(1, fname.toString(), args);
	}
	
	public Object[] returningFeval(int numReturnArgs, String fname, Object... args) {
		// makes it possible to pass corresponding Simulink enums directly (converts them to strings)
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				if ((arg instanceof SimulinkParameter) || (arg instanceof SimulinkBlockType) || (arg instanceof SimulinkType)) {
					args[i] = arg.toString();
				}
			}
		}
		
		try {
			return proxy.returningFeval(fname, numReturnArgs, args);
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			// TODO PN handle
		} catch (Exception e) {
			e.printStackTrace();
			// TODO PN handle
		}
		
		return null;
	}

	public static String getSimpleNameLimiterBased(String qualifiedName) {
	    return getInstance().doGetSimpleNameLimiterBased(qualifiedName);
	}
	
	public String doGetSimpleNameLimiterBased(String qualifiedName) {
	       int index = qualifiedName.lastIndexOf("/");
	        String simpleName = qualifiedName;
	        if (index != -1) {
	            simpleName = simpleName.substring(index + 1);
	        }
	        
	        return simpleName;
	}
	
}
