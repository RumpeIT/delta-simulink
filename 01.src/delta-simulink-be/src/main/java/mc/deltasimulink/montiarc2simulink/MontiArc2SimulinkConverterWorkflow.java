/**
 * 
 */
package mc.deltasimulink.montiarc2simulink;

import mc.DSLWorkflow;
import mc.ast.Visitor;
import mc.deltasimulink.helper.MatlabProxyHelper;
import mc.umlp.arc._tool.MontiArcRoot;

/*******************************************************************************
 * Delta-Simulink
 * Copyright (c) 2013, RIDT, All rights reserved.
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
public class MontiArc2SimulinkConverterWorkflow extends DSLWorkflow<MontiArcRoot> {

    protected MatlabProxyHelper helper;
    
    protected String outDir;
    /**
     * @param responsibleClass
     */
    public MontiArc2SimulinkConverterWorkflow(MatlabProxyHelper helper, String outDir) {
        super(MontiArcRoot.class);
        this.helper = helper;
        this.outDir = outDir;
    }

    /* (non-Javadoc)
     * @see mc.DSLWorkflow#run(mc.DSLRoot)
     */
    @Override
    public void run(MontiArcRoot dslroot) {
        MontiArc2SimulinkConverter conv = new MontiArc2SimulinkConverter(helper, outDir);
        Visitor.run(conv, dslroot.getAst());
        
    }
    
}
