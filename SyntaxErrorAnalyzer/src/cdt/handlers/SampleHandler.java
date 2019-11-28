package cdt.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.CCorePlugin;
//import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.index.IIndex;
//import org.eclipse.cdt.core.index.IIndexManager;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
//import org.eclipse.cdt.core.settings.model.CSourceEntry;
//import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
//import org.eclipse.cdt.core.settings.model.ICProjectDescription;
//import org.eclipse.cdt.core.settings.model.ICSettingEntry;
//import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypedefNameSpecifier;
//import org.eclipse.cdt.managedbuilder.core.BuildException;
//import org.eclipse.cdt.managedbuilder.core.IConfiguration;
//import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
//import org.eclipse.cdt.managedbuilder.core.IManagedProject;
//import org.eclipse.cdt.managedbuilder.core.IToolChain;
//import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
//import org.eclipse.core.resources.IFolder;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import analysis.core.AstLogger;
import mestrado.core.Runner;
import mestrado.utils.MoveFile;
/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
@SuppressWarnings("restriction")
public class SampleHandler extends AbstractHandler {
	
	public static String PROJECT = "";

	/* 
	 * NOTES:

	 */
	
	// What is the Runtime Workspace path?
	public static final String RUNTIME_WORKSPACE_PATH = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + File.separator;
	
	// It keeps the C types.
	private static List<String> types = new ArrayList<String>();
	
	//It keeps the directive macros
	private static List<String> macrosNotToInclude = new ArrayList<String>();
	
	// It keeps the macros defined.
	private static List<String> macros = new ArrayList<String>();
	
	// It keeps of files in the SRC folder.
	private static List<String> filesInSrc;
	
	private static int ERROR = 0, ANALYSISOK = 1;
	
	private static boolean toTheNextFile = false;
	
	public SampleHandler() {
		
	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		//start();
		try {
			startdiff();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test();
		
		MessageDialog.openInformation(
				window.getShell(),
				"Syntax Error Analyzer",
				"Done! Check plaftorm.h and include/stubs.h");
		return null;
	}
	
	private void startdiff() throws Exception {
	//	Main.start(RUNTIME_WORKSPACE_PATH);
		Runner.start(RUNTIME_WORKSPACE_PATH);
	} 
	

	public static void analyzeFilesInSrc(HashSet<String> files) throws Exception{
	//public static void analyzeFilesInSrc(ArrayList<String> files) throws Exception{
		ICProject project = CoreModel.getDefault().getCModel().getCProject(SampleHandler.PROJECT);//trocar por Runner.projectManager.getCurrentProject()
		project.getProject().refreshLocal(IResource.DEPTH_ZERO, null);
		String thePath = project.getPath().toString();
		System.out.println(thePath);
		
		IIndex index = CCorePlugin.getIndexManager().getIndex(project);
		

		// It gets all C files from the ANALYSIS path to analyze.
		List<File> filesInSrcArray = new ArrayList<File>(files.size());
		for(String file : files){
			filesInSrcArray.add(new File(file));
		}

		filesInSrc = new ArrayList<String>();
		for(File file : filesInSrcArray){
			filesInSrc.add(file.getAbsolutePath().replace("\\", "/"));
		}
		
		// For each C file in the ANALYSIS folder..
		for (String file : filesInSrc){
			String completeFilePath = file.replace(SampleHandler.RUNTIME_WORKSPACE_PATH.replace("\\", "/"), "");
			System.out.println(completeFilePath);
			//editDirectives(file);
			
			IPath iFilePath = new Path(completeFilePath);
			IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(iFilePath);
			iFile.refreshLocal(IResource.DEPTH_ZERO, null);
			
			ITranslationUnit tu = (ITranslationUnit) CoreModel.getDefault().create(iFile);
			
			IASTTranslationUnit ast = null;
			
			int condition = -1;
			
			do{
				condition = analyze(index, tu, ast, file);
			}while(condition == ERROR);
			
			toTheNextFile = false;
		}
		//Preenche as plataform.h e stub.h
		writeTypesToPlatformHeader();
		
		macros = new ArrayList<String>(macros.size());
		macrosNotToInclude = new ArrayList<String>(macrosNotToInclude.size());
		types = new ArrayList<String>(types.size());

		//this.removeAnalysisFiles(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "analysis");
		
	}
	
	private static int analyze(IIndex index, ITranslationUnit tu, IASTTranslationUnit ast, String file){
		try {
			// We need a read-lock on the index.
			index.acquireReadLock();
		
			// The AST is ready for use..
			ast = tu.getAST(index, ITranslationUnit.AST_PARSE_INACTIVE_CODE);
			
			setTypes(ast);
			
			if(!toTheNextFile)
				setMacros(ast);
			
			//System.out.println("Getting directives of file: " + new File(file).getAbsolutePath());
			getDirectives(new File(file));
			
			removeDirectives(file);
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("ANALYSIS ERROR. TRYING AGAIN : " + e.getMessage());
			index.releaseReadLock();
			return ERROR;
		}finally {
			// Do not use the AST after release the lock.
			index.releaseReadLock();
			ast = null;
		}
		return ANALYSISOK;
	}
	
	public static void removeDirectives(String file) throws IOException{
		FileWriter fstreamout = new FileWriter(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "temp2.c");
		BufferedWriter out = new BufferedWriter(fstreamout);
		  
	//	out.write("#include \"stubs.h\"\n");
		
		FileInputStream fstream = new FileInputStream(file);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
  		BufferedReader br = new BufferedReader(new InputStreamReader(in));
  		String strLine;
  		//Read File Line By Line
	  	while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console
	  		if(strLine.contains("define") && strLine.contains("#")) {
	  			out.write("//" + strLine + "\n");
	  		}
	  		else if ((strLine.contains("include") || strLine.contains("error") || strLine.contains("warning")) && strLine.contains("#") || strLine.contains("error:") ){
		  		out.write("//" + strLine + "\n");
		  	} else {
		  		out.write(strLine + "\n");
		  	}
		  
	  	}
	  	
		in.close();
		br.close();
		fstream.close();
	  	out.close();
	  	fstreamout.close();
	  	
	  	File original = new File(file);
	  	
	  	File temp2 = new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "temp2.c");
	  	
	  	MoveFile.copyFileUsingChannel(temp2, original);
	  	
//	  	Files.copy(temp2.toPath(),original.toPath(),StandardCopyOption.REPLACE_EXISTING);
		  
  		//new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "temp2.c").renameTo(new File(file));
		  
	}
	
	public static void editDirectives(String file) throws IOException{
		FileWriter fstreamout = new FileWriter(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "temp2.c");
		BufferedWriter out = new BufferedWriter(fstreamout);
		  
		out.write("#define A\n\n");
		
		
		FileInputStream fstream = new FileInputStream(file);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
  		BufferedReader br = new BufferedReader(new InputStreamReader(in));
  		String strLine;
  		//Read File Line By Line
  		
	  	while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console
	  
		  	if (strLine.contains("#if")){
		  		out.write("\n#ifdef A\n");
		  	} else if (strLine.contains("#el")) {
		  		out.write("\n#endif\n#ifdef A\n");
		  	} else if (strLine.contains("#endif")) {
		  		out.write("\n#endif\n");
		  	} else {
		  		out.write(strLine + "\n");
		  	}
		  
	  	}
	  	
	  	in.close();
	  	out.close();
	  
	}

	// It finds probable macros in the node.
	public static void setMacros(IASTNode node){
		IASTPreprocessorMacroDefinition[] definitions = node.getTranslationUnit().getMacroDefinitions();
		IASTPreprocessorMacroDefinition[] definitionsBuiltIn = node.getTranslationUnit().getBuiltinMacroDefinitions();
		
		for(IASTPreprocessorMacroDefinition definition : definitionsBuiltIn){
			String macro = definition.getRawSignature();
			if (!macros.contains(macro)){
				macros.add(macro);
			}
		}
		
		for (IASTPreprocessorMacroDefinition definition : definitions){
			String macro = definition.getRawSignature();
			if (!macros.contains(macro)){
				macros.add(macro);
			}
		}
	}
	
	// It finds probable types in the node.
	public static void setTypes(IASTNode node){
		IASTNode[] nodes;
		try{
			nodes = node.getChildren();
		}catch(Exception e){
			AstLogger.write("ERROR");
			e.printStackTrace();
			toTheNextFile = true;
			return;
		}
		if (node.getClass().getCanonicalName().equals("org.eclipse.cdt.internal.core.dom.parser.c.CASTTypedefNameSpecifier")){
			CASTTypedefNameSpecifier s = (CASTTypedefNameSpecifier) node;

			String type = s.getRawSignature().replace("extern", "").replace("static", "").replace("const", "").trim();
			if (!types.contains(type) && isValidJavaIdentifier(type)){
				types.add(type);
			}
		}
		for (int i = 0; i < nodes.length; i++){
			setTypes(nodes[i]);
		}
	}
	
	// All types found are defined in the platform.h header file.
	public static void writeTypesToPlatformHeader(){
		File platform = new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "platform.h");
		try {
			platform.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("erro ao criar platmor.h em samplehander: " + e1.getMessage());
		}
		//comentar essa linha a pasta include ja existe
		new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "include").mkdir();
		File header = new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "include" + File.separator + "stubs.h");
		File headerPlatform = new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + File.separator + "platform.h");
		try {
			FileWriter writer = new FileWriter(header);
			FileWriter writerPlatform = new FileWriter(headerPlatform);
			String content = "";
			for (Iterator<String> i = types.iterator(); i.hasNext();){
				content = i.next();
				writer.write("typedef struct {} " + content + ";\n");
				writerPlatform.write("typedef struct {} " + content + ";\n");
			}
			
			for (Iterator<String> i = macros.iterator(); i.hasNext();){
				boolean include = true;
				
				String next = i.next();
				String strToInclude = next.trim().replaceAll("\\s+", " ");
				//System.out.println(strToInclue);
				
				for (String test : macrosNotToInclude){
					if (strToInclude.startsWith("#define " + test) || strToInclude.startsWith("# define " + test)){
						//System.out.println("DO NOT INCLUDE IT!");
						include = true; 
						break;
					}
				}
				if (include){
					writer.write(next + "\n");
					writerPlatform.write(next + "\n");
				}
			}
			
			writer.flush();
			writer.close();
			writerPlatform.flush();
			writerPlatform.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("erro ao escrever em platmor.h em samplehander: " + e.getMessage());
		}
		
	}
	
	// It returns a set with all different directives.
	public static void getDirectives(File file) throws Exception{
		
		
		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;

		while ((strLine = br.readLine()) != null)   {
			
			strLine = strLine.trim();
			
			if (strLine.trim().startsWith("#if") || strLine.trim().startsWith("#elif")){
				
				strLine = strLine.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)","");
				
				String directive = strLine.replace("#ifdef", "").replace("#ifndef", "").replace("#if", "");
				directive = directive.replace("defined", "").replace("(", "").replace(")", "");
				directive = directive.replace("||", "").replace("&&", "").replace("!", "").replace("<", "").replace(">", "").replace("=", "");
				
				String[] directivesStr = directive.split(" ");
				
				for (int i = 0; i < directivesStr.length; i++){
					if (!macrosNotToInclude.contains(directivesStr[i].trim()) && !directivesStr[i].trim().equals("") && isValidJavaIdentifier(directivesStr[i].trim())){
						macrosNotToInclude.add(directivesStr[i].trim());
					}
				}
			}
		}
		in.close();
		br.close();
		fstream.close();
	}
	
	public static boolean isValidJavaIdentifier(String s) {
		// An empty or null string cannot be a valid identifier
	    if (s == null || s.length() == 0){
	    	return false;
	   	}

	    char[] c = s.toCharArray();
	    if (!Character.isJavaIdentifierStart(c[0])){
	    	return false;
	    }

	    for (int i = 1; i < c.length; i++){
	    	
	        if (!Character.isJavaIdentifierPart(c[i])){
	           return false;
	        }
	    }

	    return true;
	}

}
