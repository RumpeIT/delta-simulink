package mc.matlab.simulink;

import java.io.*;
import java.util.*;

import mc.*;
import mc.ast.*;
import mc.grammar.*;
import mc.helper.*;
import antlr.*;

public class ParserSimulink {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws TokenStreamException
	 * @throws RecognitionException
	 */
	public static void main(String[] args) throws FileNotFoundException, RecognitionException, TokenStreamException {
		(new ParserSimulink()).parse();

	}

	private void parse() throws FileNotFoundException, RecognitionException, TokenStreamException {
		
		MCG.initMonticoreGlobals();
		// MCG.err = new PrintStream(new FileOutputStream(new File("c:\\out.txt")));

		List<File> f = AllFilesWithFileExtension.getAllFilesWithExtension("examples", "mdl");

		// f = new ArrayList<File>();
		// f.add(new File("examples/simdemos/sl_subsys_fcncall1.mdl"));
		
		for (File x : f) {

			Reader reader = new FileReader(x);

			
			MonticoreParser p = setUpMonticoreParser(x.getAbsolutePath(), reader);
			ASTNode n = p.parse();

			System.out.println("Sucessful? " + !p.hasErrors());

		}

	}

	/*
	 * parser {
	 * 
	 * def OD mc.umlp.od.OD; def Java mc.java.Java;
	 * 
	 * start OD.Definition;
	 * 
	 * OD.Expression -> Java.Expression; }
	 */

	/**
	 * Sets up the main parser to parse MontiCore grammars
	 * 
	 * @return The Main parser.
	 */
	private MonticoreParser setUpMonticoreParser(String filename, Reader reader) {
		MonticoreParser overallparser = null;

		// Create overall parser
		overallparser = new MonticoreParser(filename, reader);

		// Create Lexer (all with inputState of MontiCoreParser)
		SimulinkLexer outlex = new SimulinkLexer(overallparser.getLexerState());

		// Create Parser (all with shared input state)
		SimulinkParser outparser = new SimulinkParser(overallparser.getParserState());

		// Use all together to form concrete Parser
		MCConcreteParser outc = new SimulinkSimulinkFileMCConcreteParser("simulink", filename, outlex, outparser);

		overallparser.addMCConcreteParser(outc);

		// Select parser to start with
		overallparser.setStartParser("simulink");

		return overallparser;
	}

}
