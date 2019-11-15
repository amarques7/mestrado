package analysis.core;

import java.io.File;
import java.util.ArrayList;

import analysis.visitor.PresenceConditionVisitor;
import core.ASTGenerator;
import de.fosd.typechef.FrontendOptions;
import de.fosd.typechef.FrontendOptionsWithConfigFiles;
import de.fosd.typechef.Lex;
import de.fosd.typechef.lexer.options.OptionException;
import de.fosd.typechef.parser.TokenReader;
import de.fosd.typechef.parser.c.AST;
import de.fosd.typechef.parser.c.CParser;
import de.fosd.typechef.parser.c.CToken;
import de.fosd.typechef.parser.c.CTypeContext;
import de.fosd.typechef.parser.c.ParserMain;
import mestrado.core.Runner;
import tree.Node;
import tree.TranslationUnit;
import tree.visitor.VisitorASTOrganizer;

public class Ast {
	
	private File source;
	private File stubs;
	private Node node;
	private String logAst;

	
	public Ast(File source, File stubs) {
		setSource(source);
		setStubs(stubs);

	}

	public File getSource() {
		return source;
	}

	public void setSource(File source) {
		this.source = source;
	}

	public File getStubs() {
		return stubs;
	}

	public void setStubs(File stubs) {
		this.stubs = stubs;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
	public enum GenerationStatus { OK, OPTIONS_EXCEPTION, BAD_AST }

	public GenerationStatus generate() {
		FrontendOptions myParserOptions = new FrontendOptionsWithConfigFiles();
		ArrayList<String> parameters = new ArrayList<String>();
		//parameters.add("--output=C:\\Users\\bruno\\Documents\\runtime-EclipseApplication\\outputfiles\\");
		//parameters.add("--interface");
		//parameters.add("--debugInterface");
		
		//parameters.add("--parserstatistics");
		//parameters.add("--typecheck");
		//parameters.add("--writePI");
		parameters.add("--lexNoStdout");
		parameters.add("-h");
		parameters.add(this.stubs.getPath());
		parameters.add(this.source.getPath());
		//parameters.add("--serializeAST");
		//parameters.add("--reuseAST");
		

		
		String[] parameterArray = parameters.toArray(new String[parameters.size()]);
		
		try {
			System.out.print("Trying to parse options for file " + getSource().getPath() + "... ");
			myParserOptions.parseOptions(parameterArray);
			for(String info : myParserOptions.getIncludePaths()){
				System.out.println("\n"+info);
				System.out.println("next");
			}
//			logAst =  Runner.projectManager.getLogControl() + getSource().getName() + ";" + Runner.projectManager.getTotalArqPro() + ";"+ " OK_1 "+ ";" + "-" ;
//			AstLogger.writeaST(logAst, Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + File.separator + "results", Runner.projectManager.getCurrentProject()+".csv");
		} catch (OptionException e) {
			System.out.println("ERROR_AST_Trying to parse options for file");
			AstLogger.write(getSource().getAbsolutePath());
			AstLogger.write(e.getMessage());
			//"ERROR_AST_Trying to parse options for file" = 1
			logAst = Runner.projectManager.getLogControl() + ";" + getSource().getName()+";" + "1"+ ";" +"1";
			//logAst = Runner.projectManager.getLogControl() + getSource().getName() + ";" + Runner.projectManager.getTotalArqPro() + ";" + "ERROR_AST_Trying to parse options for file: "+ e.getMessage() + ";"+  "1";
			AstLogger.writeaST(logAst, Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + File.separator + "results", Runner.projectManager.getCurrentProject()+".csv");
			
			e.printStackTrace();
			
			return GenerationStatus.OPTIONS_EXCEPTION;
		}
		System.out.println("OK_1_AST_Trying to parse options for file");

		ParserMain parser = new ParserMain(new CParser(null, false));

		TokenReader<CToken, CTypeContext> in = Lex.lex(myParserOptions);
		System.out.println("Parsing AST...");
		AST ast = parser.parserMain(in, myParserOptions);
		
		Node myAst = new TranslationUnit();
		
		try {
			//System.out.print("Trying to generate AST for file " + getSource().getPath() + "... ");
			System.out.print("Trying to generate AST for file " + getSource().getName() + "... ");
			new ASTGenerator().generate(ast, myAst);
			//OK_AST_generate AST for file = 0
			
			logAst = Runner.projectManager.getLogControl() + ";" + getSource().getName()+";" + "1"+ ";" +"0";
		//	logAst =  Runner.projectManager.getLogControl() + getSource().getName() + ";" + Runner.projectManager.getTotalArqPro() + ";"+ " OK_AST_generate AST for file"+ ";" + "0" ;
			AstLogger.writeaST(logAst, Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + File.separator + "results", Runner.projectManager.getCurrentProject()+".csv");
			
		} catch (Exception e) {
			AstLogger.write(getSource().getAbsolutePath());
			AstLogger.write(e.getMessage());
			//ERROR_AST_generate AST for file = 1 
			System.out.println("ERROR_AST");
			logAst = Runner.projectManager.getLogControl() + ";" + getSource().getName()+";" + "1"+ ";" +"1";
			//logAst =  Runner.projectManager.getLogControl() + getSource().getName() + ";" + Runner.projectManager.getTotalArqPro() + ";" + "ERROR_AST_generate AST for file" + ";" + "1";
			AstLogger.writeaST(logAst, Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + File.separator + "results", Runner.projectManager.getCurrentProject()+".csv");
			
		//	
			e.printStackTrace();
			
			return GenerationStatus.BAD_AST;
		}
		System.out.println("OK_2_AST");
		
		// Optimize AST
		myAst.accept(new VisitorASTOrganizer());
		// Get the presence condition for all nodes of the tree
		myAst.accept(new PresenceConditionVisitor());
		setNode(myAst);
		return GenerationStatus.OK;
		
		
	}

}
