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

public class Ast extends Thread{
	private int [] array;
	private int i;
	private File source;
	private File stubs;
	private Node node;
	private StringBuilder logAst = new StringBuilder();
	private StringBuilder diretorio = new StringBuilder();
	private StringBuilder nomeArquivo = new StringBuilder();

	public Ast(File source, File stubs, int []list, int index) {
		array =list;
		i = index;
		setSource(source);
		setStubs(stubs);
		diretorio.append(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + File.separator + "results");
		nomeArquivo.append(Runner.projectManager.getCurrentProject() +".csv");
	}
	//                               0           1             2
	//public enum GenerationStatus { OK, OPTIONS_EXCEPTION, BAD_AST }
	 
	//public GenerationStatus generate() {
	public void run() {
		FrontendOptions myParserOptions = new FrontendOptionsWithConfigFiles();
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("--lexNoStdout");
		parameters.add("-h");
		parameters.add(this.stubs.getPath());
		parameters.add(this.source.getPath());
		
		String[] parameterArray = parameters.toArray(new String[parameters.size()]);
		
		try {
			System.out.print("Trying to parse options for file " + getSource().getPath() + "... ");
			myParserOptions.parseOptions(parameterArray);
			for(String info : myParserOptions.getIncludePaths()){
				System.out.println("\n"+info);
				System.out.println("next");
			}

		} catch (OptionException e) {
			System.out.println("ERROR_AST_Trying to parse options for file");
			AstLogger.write(getSource().getAbsolutePath());
			AstLogger.write(e.getMessage());
			//"ERROR_AST_Trying to parse options for file" = 1
			logAst.delete(0, logAst.length());
			logAst.append( Runner.projectManager.getLogControl() + ";" + getSource().getName()+";" + "1"+ ";" +"1");
			AstLogger.writeaST(logAst, diretorio, nomeArquivo);
			Runner.projectManager.errorFiles.add(getSource().getName());
			e.printStackTrace();
			
			//return GenerationStatus.OPTIONS_EXCEPTION;
			array[i] = 1;
			return;
		}
				
		System.out.println("OK_1_AST_Trying to parse options for file");
		Node myAst = null;
		
		ParserMain parser = new ParserMain(new CParser(null, false));
		TokenReader<CToken, CTypeContext> in = null; 
		
		
		in = Lex.lex(myParserOptions);
		
		System.out.println("Parsing AST...");
		
						
		
		try {
			AST ast = parser.parserMain(in, myParserOptions);
			myAst = new TranslationUnit();
			
			System.out.print("Trying to generate AST for file " + getSource().getName() + "... ");
			
			new ASTGenerator().generate(ast, myAst);
			//OK_AST_generate AST for file = 0
			logAst.delete(0, logAst.length());
			logAst.append(Runner.projectManager.getLogControl() + ";" + getSource().getName()+";" + "1"+ ";" +"0");
			AstLogger.writeaST(logAst, diretorio, nomeArquivo);
			Runner.projectManager.errorFiles.remove(getSource().getName());
			Runner.projectManager.fileValidation.add(getSource().getName());
			
		} catch (Exception e) {
			AstLogger.write(getSource().getAbsolutePath());
			AstLogger.write(e.getMessage());
			//ERROR_AST_generate AST for file = 1 
			System.out.println("ERROR_AST");
			logAst.delete(0, logAst.length());
			logAst.append( Runner.projectManager.getLogControl() + ";" + getSource().getName()+";" + "1"+ ";" +"1");
			AstLogger.writeaST(logAst, diretorio, nomeArquivo);
	//*		AstLogger.writeaST(logAst, Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + File.separator + "results", Runner.projectManager.getCurrentProject()+".csv");
			Runner.projectManager.errorFiles.add(getSource().getName());
			e.printStackTrace();
			
			//return GenerationStatus.BAD_AST;
			array[i]= 2;
			return;
		}
		System.out.println("OK_2_AST");
		
		// Optimize AST
		myAst.accept(new VisitorASTOrganizer());
		// Get the presence condition for all nodes of the tree
		myAst.accept(new PresenceConditionVisitor());
		setNode(myAst);
		//return GenerationStatus.OK;
		array[i] = 0;
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

}
