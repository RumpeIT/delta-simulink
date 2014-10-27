package mc.deltasimulink.simulink2montiarc;


// TODO consider that blocks can have breaks in their names. Not allowed in MontiArc

import static mc.deltasimulink.SimulinkBlockType.ModelReference;
import static mc.deltasimulink.SimulinkBlockType.SubSystem;
import static mc.deltasimulink.SimulinkFunction.find_system;
import static mc.deltasimulink.SimulinkFunction.get_param;
import static mc.deltasimulink.SimulinkParameter.BlockType;
import static mc.deltasimulink.SimulinkParameter.DstBlock;
import static mc.deltasimulink.SimulinkParameter.DstPort;
import static mc.deltasimulink.SimulinkParameter.FindAll;
import static mc.deltasimulink.SimulinkParameter.LineType;
import static mc.deltasimulink.SimulinkParameter.ModelName;
import static mc.deltasimulink.SimulinkParameter.SearchDepth;
import static mc.deltasimulink.SimulinkParameter.SrcBlock;
import static mc.deltasimulink.SimulinkParameter.SrcPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import mc.IErrorDelegator;
import mc.deltasimulink.DeltaOperation;
import mc.deltasimulink.DeltaSimulinkKeyWords;
import mc.deltasimulink.SimulinkBlockType;
import mc.deltasimulink.SimulinkParameter;
import mc.deltasimulink.SimulinkPort;
import mc.deltasimulink.SimulinkSignal;
import mc.deltasimulink.SimulinkType;
import mc.deltasimulink.helper.MatlabProxyHelper;
import mc.deltasimulink.helper.SimulinkNameHelper;

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
public class Simulink2MontiArcConverter {

	protected MatlabProxyHelper matlab;
	
	protected IErrorDelegator handler;
	
	protected Stack<String> currentComponentName;

	/**
	 * Creates a new simulink 2 MontiArc converter.
	 * @param matlab used to communicate with Matlab
	 * @param delegator used for error reporting
	 */
	public Simulink2MontiArcConverter(MatlabProxyHelper matlab, IErrorDelegator delegator) {
		this.matlab = matlab;
		this.handler = delegator;
		currentComponentName = new Stack<String>();
	}
	
	public String convertDeltaModel(String path, String deltaModelName) {
		if (matlab.isDisconnected()) {
			matlab.connect();
		}
		DeltaMontiArcModelBuilder builder = new DeltaMontiArcModelBuilder();
		matlab.loadModel(path, deltaModelName);
		
		// create delta head
		String aoc = getApplicationOrderCondition(deltaModelName);
		builder.startCreateDelta(deltaModelName, aoc);

		// create modify blocks at top lvl
		convertDeltaSubSystems(deltaModelName, builder);		    
		builder.closeBody();
		
		return builder.getConvertedModel();
	}



	/**
     * @param deltaModelName
     */
    protected void convertDeltaSubSystems(String deltaModelName, DeltaMontiArcModelBuilder builder) {
        List<String> subSystems = loadSubSystems(deltaModelName);
        
        for (String subSys : subSystems) {
            DeltaOperation op = getDeltaOperationForElement(subSys);
            String simpleName = matlab.doGetSimpleNameLimiterBased(subSys);
            
            // modify block
            if (op == DeltaOperation.MODIFY) {
                modifySubSystemBlock(builder, subSys, simpleName);            
            }
            // add subsystem block 
            else if (op == DeltaOperation.ADD) {
                builder.printOperation(op);
                convertSubSystemsAndRoot(subSys, builder, true);
            }
            // replace subsystem block
            else if (op == DeltaOperation.REPLACE) {
                int withIndex = simpleName.indexOf(DeltaSimulinkKeyWords.REPLACE_WITH);
                if (!simpleName.startsWith(DeltaSimulinkKeyWords.REPLACE) || withIndex == -1) {
                    handler.addError("Unable to parse replace statement!", simpleName);
                }
                else {
                    String toReplace = simpleName.substring(DeltaSimulinkKeyWords.REPLACE.length(), withIndex);
                    
                    String newName = matlab.doGetSimpleNameLimiterBased(subSys);

                    newName = newName.substring(newName.indexOf(DeltaSimulinkKeyWords.REPLACE_WITH) + DeltaSimulinkKeyWords.REPLACE_WITH.length());
                    newName = newName.trim();
                    
 
                    StringBuffer typeName = new StringBuffer();
                    String sep = "";
                    for (String part : currentComponentName) {
                        typeName.append(sep);
                        sep = ".";
                        typeName.append(part);
                    }
                    typeName.append(sep);
                    typeName.append(newName);
                    
                    // remove old component definition
                    builder.addSingleLineComment("replace subsystem " + toReplace);
                    
                    builder.printOperation(DeltaOperation.ADD);
                    convertSubSystemsAndRoot(subSys, builder, false);
                    builder.createReplaceStatement(toReplace, typeName.toString() + " " + newName);
                }
            }
        }
    }
    
    /**
     * @param deltaModelName
     */
    protected void convertDeltaRemoveSubSystems(String deltaModelName, DeltaMontiArcModelBuilder builder) {
        List<String> subSystems = loadSubSystems(deltaModelName);
        
        for (String subSys : subSystems) {
            DeltaOperation op = getDeltaOperationForElement(subSys);
            String simpleName = matlab.doGetSimpleNameLimiterBased(subSys);
           if (op == DeltaOperation.REMOVE) {
                builder.createRemoveStatement("component", simpleName);
            }
        }
    }

    /**
     * Creates a modify subsystem block
     * 
     * @param builder builder to use
     * @param subSys qualified subsystem name
     * @param simpleName simple name
     */
    protected void modifySubSystemBlock(DeltaMontiArcModelBuilder builder, String subSys, String simpleName) {
        String toModify = simpleName;
        int index = toModify.indexOf(DeltaSimulinkKeyWords.AOC);
        if (index != -1) {
            toModify = toModify.substring(0, index);
        }
        if (toModify.startsWith(DeltaSimulinkKeyWords.MODIFY_MODEL)) {
            toModify = toModify.substring(DeltaSimulinkKeyWords.MODIFY_MODEL.length()).trim();
            // add package for models
            toModify = MontiArcStringBuilder.EXPORT_PACKAGE + "." + toModify.trim();
        }
        else if (toModify.startsWith(DeltaSimulinkKeyWords.MODIFY)) {
            toModify = toModify.substring(DeltaSimulinkKeyWords.MODIFY.length()).trim();
        }
        else {
            handler.addError("Unable to parse modify statement!", subSys);
            return;
        }
        currentComponentName.push(toModify);
        builder.startCreateModifyComponent(toModify);
        
        convertDeltaAddPorts(subSys, builder);
        
        convertDeltaSubSystems(subSys, builder);
        
        convertDeltaModelBlocks(subSys, builder);
        
        convertDeltaConnectors(subSys, builder);
        
        convertDeltaRemovePorts(subSys, builder);
        
        convertDeltaRemoveSubSystems(subSys, builder);
        
        convertDeltaRemoveModelBlocks(subSys, builder);
        
        builder.closeBody();
        currentComponentName.pop();
    }

    /**
     * @param subSys
     * @param builder
     */
    protected void convertDeltaConnectors(String subSys, DeltaMontiArcModelBuilder builder) {
        List<SimulinkSignal> signals = getSignals(subSys);
        for (SimulinkSignal signal : signals) {
        	
            DeltaOperation sourceOp = getDeltaOperationForElement(signal.getQualifiedSrcName());
            DeltaOperation targetOp = getDeltaOperationForElement(signal.getQualifiedDstName());
            DeltaOperation conOp = getConnectorOperation(sourceOp, targetOp, signal);
            
            String srcBlockName = convertConnectorElement(signal.getSrcSimpleName(), signal.getSrcPortName());
            String targetBlockName = convertConnectorElement(signal.getDstSimpleName(), signal.getDstPortName());
            
            switch (conOp) {
                case ADD:
                    builder.createConnector(srcBlockName, targetBlockName);
                    break;
                case REMOVE:
                    builder.createDisconnect(srcBlockName, targetBlockName);
                    break;
                default:
                    break;
            }
        }
    }
    
    protected String convertConnectorElement(String blockName, String portName) {
        String result = blockName;
        if (result.startsWith(DeltaSimulinkKeyWords.MODIFY)) {
            result = result.substring(DeltaSimulinkKeyWords.MODIFY.length());
        }
        else if (result.startsWith(DeltaSimulinkKeyWords.REPLACE) && result.contains(DeltaSimulinkKeyWords.REPLACE_WITH)) {
            result = result.substring(result.lastIndexOf(DeltaSimulinkKeyWords.REPLACE_WITH) + DeltaSimulinkKeyWords.REPLACE_WITH.length()).trim();
            
            int spaceIndex = result.indexOf(" ");
            // model block
            if (spaceIndex != -1) {
                result = result.substring(spaceIndex + 1);
            }
        }
        if (portName != null) {
            result = result + "." + portName;
        }
        return result;
    }
    
    protected DeltaOperation getConnectorOperation(DeltaOperation sourceOp, DeltaOperation targetOp, SimulinkSignal signal) {
        DeltaOperation result = DeltaOperation.UNDEFINED;
        matlab.setvar("TEMP_DOUBLE_VAL", signal.getSrcConnHandle());
        String command="if(exist('TEMP_DOUBLE_VAL','var')) TEMP_VAR=get_param(get_param(TEMP_DOUBLE_VAL,'SrcBlockHandle'),'UserData'); end;";
        matlab.eval(command);
        String command2 = "a=''; if(exist('TEMP_VAR','var')) if isKey(TEMP_VAR.lineMap,getfullname(TEMP_DOUBLE_VAL)) a=TEMP_VAR.lineMap(getfullname(TEMP_DOUBLE_VAL)).opName; end; end; a;";
        matlab.eval(command2);
        Object[] opResult = matlab.reval("a",1);
        //"a=''; if isKey(TEMP_VAR.lineMap,TEMP_DOUBLE_VAL) a=TEMP_VAR.lineMap(TEMP_DOUBLE_VAL).opName; end; a"
        String operation="";
             

        if (opResult != null && opResult.length == 1 && opResult[0] instanceof String)  {
        	operation = (String) opResult[0];
        }
        
        if (operation.equals("add")) {
            result = DeltaOperation.ADD;
        }
        else if (operation.equals("remove")) {
            result = DeltaOperation.REMOVE;
        }
        else if (operation.equals("modify")) {
            result = DeltaOperation.MODIFY;
        }
        else if (operation.equals("replace")) {
            result = DeltaOperation.REPLACE;
        }
        else {
            result = DeltaOperation.UNDEFINED;
        }
        
        return result;
    }

    /**
     * Converts all model blocks of the given subsystem.
     * 
     * @param subSys sub system which model references should be converted
     * @param builder builder to use
     */
    protected void convertDeltaModelBlocks(String subSys, DeltaMontiArcModelBuilder builder) {
        String[] modelReferences = getModelReference(subSys);
        for (String modelRef : modelReferences) {
            DeltaOperation op = getDeltaOperationForElement(modelRef);
            String simpleName = matlab.doGetSimpleNameLimiterBased(modelRef);
            switch (op) {
                case ADD:
                    builder.printOperation(op);
                    String type = getModelType(modelRef);
                    // type = MontiArcStringBuilder.EXPORT_PACKAGE + "." + type.trim();
                    builder.createComponentReference(type, simpleName);
                    
                    break;
                case REPLACE:
                    int withIndex = simpleName.indexOf(DeltaSimulinkKeyWords.REPLACE_WITH);
                    if (!simpleName.startsWith(DeltaSimulinkKeyWords.REPLACE) || withIndex == -1) {
                        handler.addError("Unable to parse replace statement!", simpleName);
                    }
                    else {
                        
                        String toReplace = simpleName.substring(DeltaSimulinkKeyWords.REPLACE.length(), withIndex);
                        String with = simpleName.substring(withIndex + DeltaSimulinkKeyWords.REPLACE_WITH.length());
                        // with = MontiArcStringBuilder.EXPORT_PACKAGE + "." + with.trim();
                        builder.createReplaceStatement(toReplace, with);
                    }
                    break;
                default:
                    break;
            }
        }
        
    }
    
    /**
     * Converts all model blocks of the given subsystem.
     * 
     * @param subSys sub system which model references should be converted
     * @param builder builder to use
     */
    protected void convertDeltaRemoveModelBlocks(String subSys, DeltaMontiArcModelBuilder builder) {
        String[] modelReferences = getModelReference(subSys);
        for (String modelRef : modelReferences) {
            DeltaOperation op = getDeltaOperationForElement(modelRef);
            String simpleName = matlab.doGetSimpleNameLimiterBased(modelRef);
            switch (op) {
                case REMOVE:
                    builder.createRemoveStatement("component", simpleName);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param subSys
     */
    protected void convertDeltaAddPorts(String subSys, DeltaMontiArcModelBuilder builder) {
        List<SimulinkPort> inPorts = loadPorts(subSys, true);
        List<SimulinkPort> outPorts = loadPorts(subSys, false);
        
        List<SimulinkPort> inPortsToAdd = new ArrayList<SimulinkPort>();
        List<SimulinkPort> outPortsToAdd = new ArrayList<SimulinkPort>();
        
        for (SimulinkPort in : inPorts) {
            DeltaOperation op = getDeltaOperationForElement(in.getQualifiedName());
            switch (op) {
                case ADD:
                    inPortsToAdd.add(in);
                    break;
                default:
                    break;
            }
            
        }
        for (SimulinkPort out : outPorts) {
            DeltaOperation op = getDeltaOperationForElement(out.getQualifiedName());
            switch (op) {
                case ADD:
                    outPortsToAdd.add(out);
                    break;
                default:
                    break;
            }
        }
        builder.createPorts(DeltaOperation.ADD, inPortsToAdd, outPortsToAdd);        
    }
    
    protected void convertDeltaRemovePorts(String subSys, DeltaMontiArcModelBuilder builder) {
        List<SimulinkPort> inPorts = loadPorts(subSys, true);
        List<SimulinkPort> outPorts = loadPorts(subSys, false);
        
        List<SimulinkPort> inPortsToRemove = new ArrayList<SimulinkPort>();
        List<SimulinkPort> outPortsToRemove = new ArrayList<SimulinkPort>();
        
        for (SimulinkPort in : inPorts) {
            DeltaOperation op = getDeltaOperationForElement(in.getQualifiedName());
            switch (op) {
                case REMOVE:
                    inPortsToRemove.add(in);
                    break;
                default:
                    break;
            }
            
        }
        for (SimulinkPort out : outPorts) {
            DeltaOperation op = getDeltaOperationForElement(out.getQualifiedName());
            switch (op) {
                case REMOVE:
                    outPortsToRemove.add(out);
                    break;
                default:
                    break;
            }
        }
        builder.createPorts(DeltaOperation.REMOVE, inPortsToRemove, outPortsToRemove);
        
    }
    
    protected DeltaOperation getDeltaOperationForElement(String element) {
        String command="TEMP_DATA=get_param('DAddPort/modify model ModelToModify','UserData');";
        StringBuilder sb=new StringBuilder("TEMP_DATA=get_param('");
        sb.append(element);
        sb.append("','UserData');");
        command=sb.toString(); 
        matlab.eval(command);
        Object[] opResult = matlab.reval("TEMP_DATA.opName", 1);
        
        
        String operation="";
        DeltaOperation result;
        
                
        if (opResult != null && opResult.length == 1 && opResult[0] instanceof String)  {
        	operation = (String) opResult[0];
        }
        
        if (operation.equals("add")) {
            result = DeltaOperation.ADD;
        }
        else if (operation.equals("remove")) {
            result = DeltaOperation.REMOVE;
        }
        else if (operation.equals("modify")) {
            result = DeltaOperation.MODIFY;
        }
        else if (operation.equals("replace")) {
            result = DeltaOperation.REPLACE;
        }
        else {
            result = DeltaOperation.UNDEFINED;
        }
        
        return result;
    }

    /**
	 * 
	 * @param deltaName
	 * @return
	 */
    protected String getApplicationOrderCondition(String deltaName) {
        String aoc = null;
        String[] subSys = getSubSystems(deltaName);
            for (String s : subSys) {
                int index = s.indexOf(DeltaSimulinkKeyWords.AOC);
                if (index != -1) {
                    aoc = s.substring(index + 6).trim();
                    break;
                }
            }            
            
        
        return aoc;
    }

    public String convertModel(String path, String modelName) {
		if(matlab.isDisconnected()) {
			matlab.connect();
		}

		MontiArcStringBuilder builder = new MontiArcStringBuilder();
		
		if (matlab.isConnected()) {
			matlab.loadModel(path, modelName);
			
			builder.addPackage(MontiArcStringBuilder.EXPORT_PACKAGE);
			
			builder.startCreateComponent(modelName);
			
			convertPorts(modelName, builder);
			
			convertModelReferences(modelName, builder);
			
			convertSubSystems(modelName, builder);
			
			convertSignals(modelName, builder);

			builder.endCreateComponent();
		}
		else {
			// TODO handle
		}
		
		return builder.getConvertedModel();
	}
    
	protected void convertPorts(String rootElement, MontiArcStringBuilder builder) {
		List<SimulinkPort> inPorts = loadUnqualifiedPorts(rootElement, true);
		List<SimulinkPort> outPorts = loadUnqualifiedPorts(rootElement, false);
		builder.createPorts(inPorts, outPorts);
	}

	protected List<SimulinkPort> loadPorts(String rootElement, boolean loadInports) {
		String portType = loadInports ? SimulinkBlockType.Inport.toString() : SimulinkBlockType.Outport.toString();
		
		Object[] loadedPorts = matlab.returningFeval(1, find_system, rootElement, SearchDepth, 1, BlockType, portType);
		
		List<SimulinkPort> qualifiedPorts = new ArrayList<SimulinkPort>();
		
		if (loadedPorts.length == 1) {
			if (loadedPorts[0] != null) {
				String[] ports = (String[]) loadedPorts[0];
				for (String qp : ports) {
					// get number of port
					int portNumber = Integer.valueOf((String) matlab.returningFeval(get_param, qp, SimulinkParameter.Port));
					SimulinkPort simPort = new SimulinkPort(SimulinkNameHelper.getSimpleName(qp),
															portNumber,
															SimulinkNameHelper.getQualifierName(qp));
					
				    qualifiedPorts.add(simPort);
				}
			}
		}
		
		return qualifiedPorts;
	}
	
	protected List<SimulinkPort> loadUnqualifiedPorts(String rootElement, boolean loadInports) {
	    return loadPorts(rootElement, loadInports);
	}
	
	protected void convertModelReferences(String modelName,
			MontiArcStringBuilder builder) {
		String[] qualifiedModelReferences = (String[]) matlab.returningFeval("find_system", modelName, "SearchDepth", 1, "BlockType", SimulinkBlockType.ModelReference.toString());
		
		if (qualifiedModelReferences != null) {
			for (int i=0; i < qualifiedModelReferences.length; i++) {
				String qualifiedModelReference = qualifiedModelReferences[i];
				String unqualifiedModelReference = getSimpleName(qualifiedModelReference);
				String typeOfReferencedModel = getModelType(qualifiedModelReference);
				
				builder.createComponentReference(typeOfReferencedModel, unqualifiedModelReference);
			}
		}
	}
	
    protected void convertSubSystems(String rootElement, MontiArcStringBuilder builder) {
        List<String> qualifiedSubSystems = loadSubSystems(rootElement);
        
        for (String qualifiedSubSystem : qualifiedSubSystems) {
            
            String unqualifiedSubSystem = getSimpleName(qualifiedSubSystem);
            
            builder.startCreateInnerComponent(unqualifiedSubSystem, unqualifiedSubSystem);
            convertPorts(qualifiedSubSystem, builder);
            
            convertSubSystems(qualifiedSubSystem, builder);
            
            convertModelReferences(qualifiedSubSystem, builder);
            
            convertSignals(qualifiedSubSystem, builder);
            
            builder.endCreateInnerComponent();
        }
    }
    
    
    protected void convertSubSystemsAndRoot(String rootElement, MontiArcStringBuilder builder, boolean instantiate) {
        String simpleName = matlab.doGetSimpleNameLimiterBased(rootElement);

        if (getDeltaOperationForElement(rootElement) == DeltaOperation.REPLACE) {
            simpleName = simpleName.substring(simpleName.indexOf(DeltaSimulinkKeyWords.REPLACE_WITH) + DeltaSimulinkKeyWords.REPLACE_WITH.length());
        }
        simpleName = simpleName.trim();
        if (instantiate) {
            builder.startCreateInnerComponent(simpleName, simpleName);            
        }
        else {
            builder.startCreateComponent(simpleName);
        }
        convertPorts(rootElement, builder);
        
        convertSubSystems(rootElement, builder);
        
        convertModelReferences(rootElement, builder);
        
        convertSignals(rootElement, builder);
        
        builder.endCreateInnerComponent();
    }
    
    protected void convertSignals(String rootName, MontiArcStringBuilder builder) {
    	List<SimulinkSignal> signals = getSignals(rootName);
    	
    	for (SimulinkSignal signal : signals) {
    	    String srcBlockName = convertConnectorElement(signal.getSrcSimpleName(), signal.getSrcPortName());
            String targetBlockName = convertConnectorElement(signal.getDstSimpleName(), signal.getDstPortName());
            
            builder.createConnector(srcBlockName, targetBlockName);
    	}
    }
    
    protected List<SimulinkSignal> getSignals(String rootName) {
    	List<SimulinkSignal> signals = new ArrayList<SimulinkSignal>();

    	// find all (top-level) connectors in the model or block 
    	Object[] foundConnectors = matlab.returningFeval(1, find_system,
    											rootName,
    											SearchDepth, 1,
    											FindAll, "on",
    											LineType, SimulinkType.signal);
    	
    	if ((foundConnectors != null) && (foundConnectors.length == 1)) {
    		// handles (ids) of the connectors
    		double[] connHandles = (double[]) foundConnectors[0];
    		
    		
    		
    		for (int i = 0; i < connHandles.length; i++) {
    			double connHandle = connHandles[i];
    			double srcConnHandle = connHandle;
    			
    			double[] lineChildren = ((double[])(matlab.returningFeval(1, get_param, connHandle, "LineChildren"))[0]);
    			
    			// Signals with children are not handled (directly). This is the case when a
    			// block has more than one outgoing signal.
    			if (lineChildren.length > 0) {
    				continue;
    			}
    			
    			double lineParent = ((double[])(matlab.returningFeval(1, get_param, connHandle, "LineParent"))[0])[0];

    			// If lineParent has another value than -1.0 the source block has more than one outgoing signal.
    			// The id of the source block can now only be get by the root line parent...
    			if (lineParent != -1.0) {
    				 // ...Started by the source block every signal fork creates line children for the current signal.
    				// These children can also have children of their own (if they have a fork).
    				// So to get the source block we need to get the source block of the root line parent.
    				double rootLineParent = lineParent;
    				do {
    					srcConnHandle = rootLineParent;
    					rootLineParent = ((double[])(matlab.returningFeval(1, get_param, rootLineParent, "LineParent"))[0])[0];
    				}
    				while (rootLineParent != -1.0);
    			}
    			
    			String srcBlockName = (String) matlab.returningFeval(get_param, srcConnHandle, SrcBlock);
    			String srcQualifiedBlockName = rootName + "/" + srcBlockName;
				String srcBlockType = (String) matlab.returningFeval(get_param, srcQualifiedBlockName, BlockType);
    			String srcPortNumber = (String) matlab.returningFeval(get_param, srcConnHandle, SrcPort);
    			
    			String dstBlockName = (String) matlab.returningFeval(get_param, connHandle, DstBlock);
    			String dstQualifiedBlockName = rootName + "/" + dstBlockName;
				String dstBlockType = (String) matlab.returningFeval(get_param, dstQualifiedBlockName, BlockType);
    			String dstPortNumber = (String) matlab.returningFeval(get_param, connHandle, DstPort);
    			
    			SimulinkSignal signal = new SimulinkSignal(srcQualifiedBlockName, Integer.valueOf(srcPortNumber),
    							        				   dstQualifiedBlockName, Integer.valueOf(dstPortNumber), srcConnHandle);
    			
    			// ====== Handle Source ======= //
    			
    			if (srcBlockType.equals(SubSystem.toString())) {
    				// src block is a sub system
    				String srcPortName = getPortNameByNumber(srcQualifiedBlockName, srcPortNumber, false);
    				signal.setSrcPortName(srcPortName);
    			}
    			else if (srcBlockType.equals(ModelReference.toString())) {
    				// src block is a model reference
    				String referencedModelName = (String) matlab.returningFeval(get_param, srcQualifiedBlockName, ModelName); 
    				matlab.loadModel(referencedModelName);
    				
    				String srcPortName = getPortNameByNumber(referencedModelName, srcPortNumber, false);
    				signal.setSrcPortName(srcPortName);
    			}
    			
    			
    			// ====== Handle Destination ======= //
    			
    			if (dstBlockType.equals(SubSystem.toString())) {
    				// dst block is a sub system
    				String dstPortName = getPortNameByNumber(dstQualifiedBlockName, dstPortNumber, true);
    				signal.setDstPortName(dstPortName);
    			}
    			else if (dstBlockType.equals(ModelReference.toString())) {
    				// dst block is a model reference
    				String referencedModelName = (String) matlab.returningFeval(get_param, dstQualifiedBlockName, ModelName); 
    				matlab.loadModel(referencedModelName);
    				
    				String dstPortName = getPortNameByNumber(referencedModelName, dstPortNumber, true);
    				signal.setDstPortName(dstPortName);
    			}
    			
    			signals.add(signal);
    		}
    	}
    	
    	return signals;
    	
	}

	protected String getPortNameByNumber(String blockName, String number, boolean inPort) {
		List<SimulinkPort> ports = loadPorts(blockName, inPort);
		
		for (SimulinkPort port : ports) {
			String portNumber = String.valueOf(port.getNumber());
			
			// look for port with the same number
			if (portNumber.equals(number)) {
				return port.getName();
			}
		}
		return "";
	}

    /**
     * 
     * @param qualifiedModelReference qualified name of the model reference
     * @return the type name of the given model reference
     */
    protected String getModelType(String qualifiedModelReference) {
        return (String) matlab.returningFeval("get_param", qualifiedModelReference, "ModelName");
    }

	protected List<String> getSimpleNames(String[] qualifiedNames) {
		List<String> simpleNames = new ArrayList<String>(qualifiedNames.length);
		for (String qualifiedName : qualifiedNames) {
			simpleNames.add(getSimpleName(qualifiedName));
		}
		
		return simpleNames;
	}
	
	protected String getSimpleName(String qualifiedName) {
		// The name is of the type like "(./)*elementName". If it is always like this,
		// it should be ok just to take the string "elementName" to get the searched name.
		// This should be checked. Until then, the slower way to get the element name 
		// directly from Matlab is used.
		
		return (String) matlab.returningFeval("get_param", qualifiedName, "Name");
	}
	
	/**
	 * 
	 * @param blockName
	 * @return subsystems of the given block
	 */
	protected String[] getSubSystems(String blockName) {
        String[] loadedSubSystems = (String[]) matlab.returningFeval("find_system", blockName, "SearchDepth", 1, "BlockType", SimulinkBlockType.SubSystem.toString());
        if (loadedSubSystems == null) {
        	loadedSubSystems = new String[]{};
        }
        
        return loadedSubSystems;
	}
	
	protected List<String> loadSubSystems(String blockName) {
	    List<String> result = new ArrayList<String>();
	    Object loadedSubSystems = matlab.returningFeval("find_system", blockName, "SearchDepth", 1, "BlockType", SimulinkBlockType.SubSystem.toString());
	    if (loadedSubSystems != null) {
	        for (String ss : (String[]) loadedSubSystems) {
	            if (!ss.equals(blockName)) {
	                result.add(ss);	                
	            }
	        }
	    }
	    return result;
	}
	
	protected String[] getModelReference(String blockName) {
	    String[] result;
        Object[] loadedSubSystems = matlab.returningFeval(1, "find_system", blockName, "SearchDepth", 1, "BlockType", SimulinkBlockType.ModelReference.toString());
        if (loadedSubSystems.length == 1) {
            result = (String[]) loadedSubSystems[0];
        }
        else {
            result = new String[]{};
        }
        return result;
	}

}
