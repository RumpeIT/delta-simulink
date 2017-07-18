/**
 * 
 */
package mc.deltasimulink.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringReader;

import mc.ConsoleErrorHandler;
import mc.IErrorDelegator;
import mc.StandardErrorDelegator;
import mc.TestErrorHandler;
import mc.antlr.RecognitionException;
import mc.antlr.TokenStreamException;
import mc.ast.PrettyPrinter;
import mc.deltamontiarc._parser.MADeltaMCCompilationUnitMCConcreteParser;
import mc.deltamontiarc.prettyprint.MADeltaConcretePrettyPrinter;
import mc.grammar.MonticoreParser;
import mc.helper.IndentPrinter;
import mc.umlp.arc.prettyprint.MontiArcConcretePrettyPrinter;
import mc.umlp.arcd._ast.ASTMCCompilationUnit;
import mc.umlp.arcd._parser.ArchitectureDiagramMCCompilationUnitMCConcreteParser;
import mc.umlp.arcd.prettyprint.ArchitectureDiagramConcretePrettyPrinter;

import org.junit.AfterClass;
import org.junit.Before;

import de.mc.logic._parser.LogicEquivalenceExpressionMCConcreteParser;
import de.mc.logic.prettyprint.LogicPrettyPrinter;

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
public class TestHelper {
    
    protected static MatlabProxyHelper matlab = MatlabProxyHelper.getInstance();
    
    protected IErrorDelegator delegator;
    
    protected TestErrorHandler handler;
    
    @Before
    public void setUp() {
        StandardErrorDelegator delegator = new StandardErrorDelegator();
        handler = new TestErrorHandler();
        delegator.addErrorHandler(handler);
        delegator.addErrorHandler(new ConsoleErrorHandler());
        this.delegator = delegator;
    }
    
    
    
    /**
     * Compares two MontiArc models given as strings by parsing and pretty printing them. 
     * 
     * @param expected expected model
     * @param actual actual model
     */
    public void compareMontiArcModelStrings(String expected, String actual) {
        
        try {
            ArchitectureDiagramMCCompilationUnitMCConcreteParser p = ArchitectureDiagramMCCompilationUnitMCConcreteParser.createSimpleParser(new StringReader(expected));
            ASTMCCompilationUnit expectedUnit = p.parse();
            p = ArchitectureDiagramMCCompilationUnitMCConcreteParser.createSimpleParser(new StringReader(actual));
            ASTMCCompilationUnit actualUnit = p.parse();
            
            IndentPrinter expectedContent = new IndentPrinter(new StringBuilder());
            IndentPrinter actualContent = new IndentPrinter(new StringBuilder());
            
            
            PrettyPrinter printer = new PrettyPrinter();
            printer.addConcretePrettyPrinter( new ArchitectureDiagramConcretePrettyPrinter());
            printer.addConcretePrettyPrinter(new MontiArcConcretePrettyPrinter());
            
            printer.prettyPrint(expectedUnit, expectedContent);
            printer.prettyPrint(actualUnit, actualContent);
            
            assertEquals(expectedContent.getContent(), actualContent.getContent());
            
        } 
        catch (RecognitionException e) {
            fail(e.getMessage());
        } 
        catch (TokenStreamException e) {
            fail(e.getMessage());
        }
    }
    
    public void compareDeltaStrings(String expectedDelta, String actualDelta) {
        try {
            
            MonticoreParser parser = createDeltaParser(expectedDelta);
            
            
            mc.deltamontiarc._ast.ASTMCCompilationUnit expectedUnit = (mc.deltamontiarc._ast.ASTMCCompilationUnit) parser.parse();
            
            parser = createDeltaParser(actualDelta);
            mc.deltamontiarc._ast.ASTMCCompilationUnit actualUnit = (mc.deltamontiarc._ast.ASTMCCompilationUnit) parser.parse();
            
            IndentPrinter expectedContent = new IndentPrinter(new StringBuilder());
            IndentPrinter actualContent = new IndentPrinter(new StringBuilder());
            
            
            PrettyPrinter printer = new PrettyPrinter();
            printer.addConcretePrettyPrinter(new MADeltaConcretePrettyPrinter());
            printer.addConcretePrettyPrinter(new LogicPrettyPrinter());
            printer.addConcretePrettyPrinter(new ArchitectureDiagramConcretePrettyPrinter());
            printer.addConcretePrettyPrinter(new MontiArcConcretePrettyPrinter());
            
            printer.prettyPrint(expectedUnit, expectedContent);
            printer.prettyPrint(actualUnit, actualContent);
            
            assertEquals(expectedContent.getContent(), actualContent.getContent());
        }
        catch (RecognitionException e) {
            fail(e.getMessage());
        } 
        catch (TokenStreamException e) {
            fail(e.getMessage());
        }
    }



    /**
     * @param delta
     * @return
     */
    protected MonticoreParser createDeltaParser(String delta) {
        MonticoreParser parser = new MonticoreParser("Expected", new StringReader(delta));
        parser.addMCConcreteParser(new MADeltaMCCompilationUnitMCConcreteParser("deltaPaser"));
        parser.addMCConcreteParser(new LogicEquivalenceExpressionMCConcreteParser("aoc"));
        parser.addExtension("deltaPaser", "aoc", "Constraint");
        parser.setStartParser("deltaPaser");
        return parser;
    }
    
    @AfterClass
    public static final void tearDown() {
        if (matlab.isConnected()) {
            matlab.exitAndDisconnect();
        }
        assertTrue(matlab.isDisconnected());
    }
    
}
