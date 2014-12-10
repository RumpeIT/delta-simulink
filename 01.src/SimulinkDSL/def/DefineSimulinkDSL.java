import java.io.*;

import antlr.*;
import mc.*;
import mc.matlab.simulink.*;

public class DefineSimulinkDSL {

	/**
	 * @param args
	 * @throws TokenStreamException 
	 * @throws RecognitionException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, RecognitionException, TokenStreamException {
		MontiCore m = new MontiCore();
		m.main(new String[]{"-o","gen","def","-mp","./def"});

		// ParserSimulink.main(new String[]{});
	}

}
