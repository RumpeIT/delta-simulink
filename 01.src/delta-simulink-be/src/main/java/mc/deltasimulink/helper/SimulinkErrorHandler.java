/**
 * 
 */
package mc.deltasimulink.helper;

import mc.ProblemReport;
import mc.TestErrorHandler;

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
public class SimulinkErrorHandler extends TestErrorHandler {
    
    protected boolean errorsAdded;
    
    protected boolean warningAdded;
    
    /**
     * 
     */
    public SimulinkErrorHandler() {
        errorsAdded = false;
        warningAdded = false;
    }
    
    @Override
    public void add(ProblemReport r) {
        super.add(r);
        if (!errorsAdded && (r.getType().equals(ProblemReport.Type.ERROR) || r.getType().equals(ProblemReport.Type.FATALERROR))) {
            errorsAdded = true;
        }
        else if (!warningAdded && r.getType().equals(ProblemReport.Type.WARNING)) {
            warningAdded = true;
        }
    }
    
    public boolean hasError() {
        return errorsAdded;
    }
    
    public boolean hasWarning() {
        return warningAdded;
    }
}
