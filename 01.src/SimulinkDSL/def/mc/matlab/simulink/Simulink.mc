grammar "Simulink" 
	package "mc.matlab.simulink"
	parseroptions "k=4;"
	lexeroptions "k=2; charVocabulary='\\\\u0003'..'\\\\u7FFE';testLiterals=false;" 
	nows noslcomments nomlcomments 
{

  ident / ESC
        "'\\\\'('n' { $setText(\"\\n\"); } |'r'{ $setText(\"\\r\");}|'t'{ $setText(\"\\t\"); }|'\"'{ $setText(\"\\\"\"); }|'\\\\' {$setText(\"\\\\\");})";
        
  ident STRING "'\"'! (ESC|~('\"'|'\\\\'|'\\n'|'\\r'))* '\"'!"; 
	            
  ident IDENT
        "options {testLiterals=true;}"
        "( 'a'..'z' | 'A'..'Z' | '_' | '$' )( 'a'..'z' | 'A'..'Z' | '_' | '0'..'9' | '$' | '.' )*";
  
  ident NUM
        "options {testLiterals=true;}"
        "( '0'..'9'| '-' )( '0'..'9' | '.' )*";
  
  
  ident WS "options {generateAmbigWarnings=false;}"
		   "(' '|'\\t'){$setType(Token.SKIP);}";
  
  ident NL "options {generateAmbigWarnings=false;}"
  		   "('\\r''\\n'{newline();}|'\\r'{newline();}|'\\n'{newline();})";
  
  ident COMMENT "'#' (~('\\n'|'\\r'))* NL {$setType(Token.SKIP);}";
  
  // go on here
  
        
  SimulinkFile:
  	(Model:Model| MatData:MatData|Libraries:Library|Stateflows:Stateflow|NL)*
	 EOF;
  
  
  Model:
  	!"Model" "{" NL
  		// !"Name" Name:STRING NL
  		// !"Version" Version:NUM NL
  		// !"MdlSubVersion" SubVersion:NUM NL
		(Elements:Element NL)+	
  		
  	 "}" ;

  MatData:
  	!"MatData" "{" NL
		(Elements:Element NL)+			
  	 "}" ;

 Library:
  	!"Library" "{" NL
		(Elements:Element NL)+			
  	 "}" ;

 Stateflow:
  	!"Stateflow" "{" NL+
		(Elements:StateflowElement (NL)+ )+			
  	 "}" ;

  SimpleElementString(Element,StateflowElement):
  	Name:IDENT Value:STRING;	 

  MLElementString(Element,StateflowElement):
  	Name:IDENT Value:STRING ( NL Value:STRING)+;	  

  SimpleElementBool(Element,StateflowElement):
  	Name:IDENT Value:IDENT;	 

  SimpleElementNumber(Element,StateflowElement):
  	Name:IDENT Value:NUM;	 

  VectorElementNumber(Element):
  	Name:IDENT "[" (Dimensions:Dimension (";" Dimensions:Dimension)* )?  "]";	 

  ListElementNumber(StateflowElement):
  	Name:IDENT "[" (Values:NUM)* "]";	 


  Dimension:
	(Value:NUM ("," Value:NUM)*)?;

  NestedElement(Element):
  	Name:IDENT "{" NL 
	(Elements:Element NL)*	
  	"}";

  Block(Element):
  	!"Block" "{" NL 
	!"BlockType" (Type:IDENT|Type:STRING) NL
	(Elements:Element NL)*	
  	"}";

 List(Element):
  	!"List" "{" NL 
	!"ListType" Type:IDENT NL
	(Elements:Element NL)*	
  	"}";

DataRecord(Element):
  	!"DataRecord" "{" NL 
	(Elements:Element NL)*	
  	"}";

SFNestedElement(StateflowElement):
  	Name:IDENT "{" NL 
	(Elements:StateflowElement NL)*	
  	"}";

SFBlock(StateflowElement):
  	!"Block" "{" NL 
	!"BlockType" (Type:IDENT|Type:STRING) NL
	(Elements:StateflowElement NL)*	
  	"}";

SFList(StateflowElement):
  	!"List" "{" NL 
	!"ListType" Type:IDENT NL
	(Elements:StateflowElement NL)*	
  	"}";

SFDataRecord(StateflowElement):
  	!"DataRecord" "{" NL 
	(Elements:StateflowElement NL)*	
  	"}";

}
  
  	 