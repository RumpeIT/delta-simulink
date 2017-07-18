package mc.deltasimulink.simulink2montiarc;

import java.util.List;

import mc.deltasimulink.SimulinkPort;
import mc.helper.IndentPrinter;

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
public class MontiArcStringBuilder {

	public static final String DUMMY_PORT_TYPE = "com.Signal";
	
	public static final String EXPORT_PACKAGE = "exp";
	
	protected IndentPrinter printer;
	
	public MontiArcStringBuilder() {
		StringBuilder sb = new StringBuilder();
		printer = new IndentPrinter(sb);
	}
	
	
	public String addPackage(String packName) {
		String res = "package " + packName + ";";
		printer.addLine(res);
		return res;
	}

	public String addImport(String importedPackName) {
		String res = "import " + importedPackName + ";";
		printer.addLine(res);
		return res;
	}
	
	public void addSingleLineComment(String comment) {
	    printer.print("// ");
	    printer.println(comment);
	}
	
	
	public String startCreateComponent(String componentName) {
		String res = "component " + componentName + " {";
		printer.addLine(res);
		printer.indent();
		return res;
	}

	
	public String getConvertedModel() {
		return printer.getContent().toString();
	}
	
	public String createPorts(List<SimulinkPort> inPorts, List<SimulinkPort> outPorts) {
		if ((inPorts.size() == 0) && (outPorts.size() == 0))  {
			return "";
		}
		
		StringBuilder res = new StringBuilder();
		res.append("port\n");
		
		if (inPorts.size() > 0) {
			for (int i = 0; i < inPorts.size() - 1; i++) {
				res.append(" in " + DUMMY_PORT_TYPE + " " + inPorts.get(i).getName() + ",\n");
			}
			
			res.append(" in " + DUMMY_PORT_TYPE + " " + inPorts.get(inPorts.size()-1).getName());

			if (outPorts.size() == 0) {
				res.append(";\n");
			}
			else {
				res.append(",\n");
			}
		}
		
		if (outPorts.size() > 0) {
			for (int i=0; i < outPorts.size()-1; i++) {
				res.append(" out " + DUMMY_PORT_TYPE + " " + outPorts.get(i).getName() + ",\n");
			}
			
			res.append(" out " + DUMMY_PORT_TYPE + " " + outPorts.get(outPorts.size()-1).getName() + ";\n");
		}
		
		
		printer.addLine(res.toString());
		return res.toString();
	}

	public String endCreateComponent() {
		String res = "}";
		printer.unindent();
		printer.addLine(res);
		return res;
	}

	public String createComponentReference(String componentName, String refName) {
		String res = "component " + componentName + " " + refName + ";";
		printer.addLine(res);
		return res;
	}

	public String startCreateInnerComponent(String componentName, String insName) {
		String res = "component " + componentName + " " + insName + " {";
		printer.addLine(res);
		printer.indent();
		return res;
	}

	public String endCreateInnerComponent() {
		return endCreateComponent();
	}


    public void openBody() {
        printer.println(" {");
        printer.indent();
    }


    public void closeBody() {
        printer.unindent();
        printer.println("}");
    }


	public String createConnector(String srcPort, String destPort) {
		String res = "connect " + srcPort + " -> " + destPort + ";";
		printer.addLine(res);
		return res;
	}
	
}
