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

import interfaces2.STEntryState;
import interfaces2.helper.EntryLoadingErrorException;
import interfaces2.helper.SymbolTableInterface;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import mc.ast.ConcreteVisitor;
import mc.deltasimulink.SimulinkFunction;
import mc.deltasimulink.SimulinkParameter;
import mc.deltasimulink.helper.MatlabProxyHelper;
import mc.helper.NameHelper;
import mc.types._ast.ASTQualifiedName;
import mc.types.helper.TypesPrinter;
import mc.umlp.arcd._ast.ASTArcComponentBody;
import mc.umlp.arc._ast.ASTMCCompilationUnit;
import mc.umlp.arcd._ast.ASTArcComponent;
import mc.umlp.arcd._ast.ASTArcConnector;
import mc.umlp.arcd._ast.ASTArcElement;
import mc.umlp.arcd._ast.ASTArcPort;
import mc.umlp.arcd._ast.ASTArcSubComponent;
import mc.umlp.arcd._ast.ASTArcSubComponentInstance;
import mc.umlp.arcd.ets.entries.ComponentEntry;
import mc.umlp.arcd.ets.entries.PortEntry;
import mc.umlp.arcd.ets.entries.SubComponentEntry;

public class MontiArc2SimulinkConverter extends ConcreteVisitor {

	protected MatlabProxyHelper matlab;
	protected String outputPath;
	protected SimulinkBuilder builder;
	
	protected SymbolTableInterface symtab;
	
	protected String rootName = null;
	
	protected Stack<String> components = new Stack<String>();
	
	protected Stack<ComponentEntry> compEntries = new Stack<ComponentEntry>();
	
	protected boolean rootIsHandled = false;

	public MontiArc2SimulinkConverter(MatlabProxyHelper matlab, String outputPath) {
		this.matlab = matlab;
		
		String absolutePath = new File(outputPath).getAbsolutePath();
		this.outputPath = absolutePath;
	}
	
	public void visit(ASTMCCompilationUnit node) {
		String name = node.getName();
		builder = new SimulinkBuilder(matlab, outputPath, name);
		rootName = name;
		symtab = new SymbolTableInterface(node);
	}

	public void endVisit(ASTMCCompilationUnit node) {
		builder.save();
	}
	
	public void visit(ASTArcComponent node) {
	    ComponentEntry entry = (ComponentEntry) symtab.resolve(node.getName(), ComponentEntry.KIND, node);
	    if (entry != null) {
	        compEntries.push(entry);	        
	    }
	    else {
	        symtab.getErrorDelegator().addError("No symbol found!", node.getName());
	    }
	    

		if (rootIsHandled) {
			boolean hasInstanceName = (node.getInstanceName() != null) && !node.getInstanceName().equals("");

			if (hasInstanceName) {
				String name = node.getName();
				// We only consider inner components which are instantiated.
				String qualifiedName = relativeToRootWithoutRoot(name);
				
				builder.addSubsystem(qualifiedName);
	
				components.add(name);
			}
		}
		else {
			// This is the root component and already handled in visit(ASTMCCompilationUnit node)
			rootIsHandled = true;
		}
		
	}
	
	public void endVisit(ASTArcComponent node) {
        if (!components.isEmpty()) {
            components.pop();
        }
        if (!compEntries.isEmpty()) {
            compEntries.pop();
        }
	}
	
	
	public void ownVisit(ASTArcComponentBody node) {
	    // visit everything but connectors
        for (ASTArcElement elem : node.getArcElement()) {
            if (!(elem instanceof ASTArcConnector)) {
                getVisitor().startVisit(elem);
            }
        }
        // visit connectors
        for (ASTArcElement elem : node.getArcElement()) {
            if (elem instanceof ASTArcConnector) {
                getVisitor().startVisit(elem);
            }
        }
	}
	
	
	public void visit(ASTArcPort node) {
		String qualifiedName = relativeToRootWithoutRoot(node.getName());
		
		if (node.getStereotype() != null) {
			throw new RuntimeException("Not allowed");
		}
		
		if (node.isIncoming()) {
			builder.addInport(qualifiedName);
		}
		else {
			builder.addOutport(qualifiedName);
		}
	}
	
	public void visit(ASTArcSubComponent node) {
		String typeName = TypesPrinter.printType(node.getType());

		if (node.getInstances() != null) {
			for (ASTArcSubComponentInstance instance : node.getInstances()) {
				String instanceName = instance.getName();
				String qualifiedInstanceName = relativeToRootWithoutRoot(instanceName);
				
				builder.addModelReference(qualifiedInstanceName, typeName);
			}
		}
	}
	
	public void visit(ASTArcConnector node){
//		
//		int srcPortNumber = 1;
//		int dstPortNumber = 1;
//		if (node.getStereotype() != null) {
//			srcPortNumber = Integer.valueOf(node.getStereotype().getStereoValue(SimulinkParameter.SrcPort.toString()).getValue());
//			dstPortNumber = Integer.valueOf(node.getStereotype().getStereoValue(SimulinkParameter.DstPort.toString()).getValue());
//		}
	    
	    String qualiSrc = NameHelper.dotSeparatedStringFromList(node.getSource().getParts());
	    String srcBlockName = NameHelper.getQualifier(qualiSrc);
        String portName = NameHelper.getSimplenameFromComplexname(qualiSrc);
        int srcNr = getPortID(srcBlockName, portName, node, true);
	    
	    
        String rootBlock = relativeToRootWithRoot("");
	    
		for (ASTQualifiedName dst : node.getTargets()){
		     String qualiDst = NameHelper.dotSeparatedStringFromList(dst.getParts());
		        String dstBlockName = NameHelper.getQualifier(qualiDst);
		        String dstPortName = NameHelper.getSimplenameFromComplexname(qualiDst);
		        int dstNr = getPortID(dstBlockName, dstPortName, node, false);
			
			builder.addConnector(rootBlock, getSignalBlockName(qualiSrc), srcNr, getSignalBlockName(qualiDst), dstNr);
		}
	}

	/**
     * @param blockName
     * @param portName
     * @param node
     * @return
     */
    private int getPortID(String blockName, String portName, ASTArcConnector node, boolean isSrc) {
        int id = 0;
        List<PortEntry> ports = new LinkedList<PortEntry>();
        if (blockName == null || blockName.isEmpty()) {
            id = 1;
        }
        else {
        	try {
            SubComponentEntry sc = (SubComponentEntry) symtab.resolve(blockName, SubComponentEntry.KIND, node);
            ComponentEntry subCompType = sc.getComponentType();
            if (subCompType.getEntryState() == STEntryState.QUALIFIED) {
                subCompType.loadFullVersion(symtab.getModelloader(), symtab.getDeserializers());
            }
            if (isSrc) {
                ports = subCompType.getOutgoingPorts();
            }
            else {
                ports = subCompType.getIncomingPorts();
            }
        	}  
        	catch (EntryLoadingErrorException e) { //TODO fill this catch with life
        		            
        	}
        }
        for (PortEntry p : ports) {
            id++;
            if (p.getName().equals(portName)) {
                break;
            }
        }
        return id;
    }

    protected String getSignalBlockName(String qualifiedMontiArcPortName) {
		String blockName = NameHelper.getQualifier(qualifiedMontiArcPortName);
		String portName = NameHelper.getSimplenameFromComplexname(qualifiedMontiArcPortName);
		

		String resultBlockName = null;
		
		if ((blockName != null) && !blockName.equals("")) {
			// Port can be in a sub system or in a referenced model
			resultBlockName = blockName;
		}
		else {
			// block is a port and in the root.
			resultBlockName = portName;
		}
		
		return resultBlockName;
	}

	protected int getPortNumberByName(String qualifiedPortName) {
		int portNumber;
		portNumber = Integer.valueOf((String) matlab.returningFeval(SimulinkFunction.get_param, qualifiedPortName, SimulinkParameter.Port));
		return portNumber;
	}
	
	protected String relativeToRootWithRoot (String name) {
		String qualification = relativeToRootWithoutRoot(name);
		
		return rootName + "/" + qualification;
	}
	
	protected String relativeToRootWithoutRoot (String name) {
		StringBuilder qualification = new StringBuilder(); 
		
		for (String component : components) {
			qualification.append(component + "/");
		}
		
		qualification.append(name);
		
		return qualification.toString();
	}
	 
	
	
}
