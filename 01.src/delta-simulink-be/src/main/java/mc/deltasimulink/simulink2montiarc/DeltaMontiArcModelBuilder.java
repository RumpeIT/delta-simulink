package mc.deltasimulink.simulink2montiarc;

import java.util.LinkedList;
import java.util.List;

import mc.deltasimulink.DeltaOperation;
import mc.deltasimulink.SimulinkPort;

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
public class DeltaMontiArcModelBuilder extends MontiArcStringBuilder {
    
    public void startCreateDelta(String delta, String applicationOrderCondition) {
        printer.print("delta ");
        printer.print(delta);
        if (applicationOrderCondition != null && !applicationOrderCondition.isEmpty()) {
            printer.print(" after ");
            printer.print(applicationOrderCondition);
        }
        openBody();
    }
    
    /**
     * @param toModify
     */
    public void startCreateModifyComponent(String toModify) {
        printer.print("modify component ");
        printer.print(toModify);
        openBody();
    }
    
    
    
    public void createPorts(DeltaOperation op, List<SimulinkPort> inPorts, List<SimulinkPort> outPorts) {
        if (!(inPorts.isEmpty() && outPorts.isEmpty())) {
            if (op == DeltaOperation.ADD) {
                printOperation(op);
                super.createPorts(inPorts, outPorts);                            
            }
            else if (op == DeltaOperation.REMOVE) {
                List<SimulinkPort> all = new LinkedList<SimulinkPort>();
                all.addAll(inPorts);
                all.addAll(outPorts);
                
                for (SimulinkPort p : all) {
                    createRemoveStatement("port", p.getName());
                }
                
            }
        }
    }

    
    
    
    public void printOperation(DeltaOperation op) {
        if (op == DeltaOperation.ADD) {
            printer.print("add ");   
        }
        else if (op == DeltaOperation.REMOVE) {
            printer.print("remove ");
        }
        else if (op == DeltaOperation.REPLACE) {
            printer.print("replace ");
        }
    }

    /**
     * @param toReplace name of the model block that should be replaced
     * @param with type and name of the new model block
     */
    public void createReplaceStatement(String toReplace, String with) {
        printOperation(DeltaOperation.REPLACE);
        printer.print("component ");
        printer.print(toReplace.trim());
        printer.print(" with component ");
        printer.print(with.trim());
        printer.println(";");
        
    }
    
    public void createRemoveStatement(String type, String name) {
        printOperation(DeltaOperation.REMOVE);
        printer.print(type);
        printer.print(" ");
        printer.print(name.trim());
        printer.println(";");
    }

    /**
     * @param srcBlockName
     * @param targetBlockName
     */
    public void createDisconnect(String srcBlockName, String targetBlockName) {
        printer.print("dis");
        createConnector(srcBlockName, targetBlockName);
        
    }

    
    
}
