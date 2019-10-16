package main;

import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
//import java.util.List;

import analysis.core.Project;
//import analysis.core.ResultsLogger;
import cdt.handlers.SampleHandler;
//import git_Deletar.ModifiedFileList;
import mestrado.core.ProjectManager;
import mestrado.core.Runner;
import util.CProject;
import util.CreateDirectory;
import util.CreateFile;

public class Starter {

	String downloadPath = "";
	static boolean noStubs = false;
	static Project project;
	public static ProjectManager projectManager;
	//just to register
	public static int numberOfCFiles = 0;
	
	public Starter(String downloadPath, boolean noStubs){
		Starter.noStubs = noStubs;
		this.downloadPath = downloadPath;
		//defining arrays for the Metrics
		
		//file name with extension 
		CreateFile.create(downloadPath, "platform.h");
		CreateDirectory.setWriter(downloadPath + "include");
		//new File(downloadPath + "include").mkdirs();
		CreateFile.create(downloadPath + "/include/", "stubs.h");
//	new File(downloadPath + "include/stubs.h").createNewFile();
		
		File platform = new File(downloadPath + "platform.h");
		
		if (platform.length() == 0) {
			project = new Project(downloadPath + "analysis", downloadPath + "include\\stubs.h");
		} else {
			project = new Project(downloadPath + "analysis", downloadPath + "platform.h");
		}
		project.setName(Runner.projectManager.getCurrentProject()); // project.Manager.getCurrentProject()
	}
	
//	public String[] defineFiles(String[] filesToAnalyze){ posso apagar
//		File path = new File(downloadPath + "analysis");
//		File[] files = path.listFiles();
//		numberOfCFiles = files.length;
//		List<String> allFilesInAnalysisFolder = new ArrayList<String>(numberOfCFiles);
//		
//		ResultsLogger.write("	number of .c files: " + numberOfCFiles);
//		if(filesToAnalyze == null){
//			for(File file : files){
//				allFilesInAnalysisFolder.add(file.getAbsolutePath());
//			}
//		}
//		else{
//			for(String file : filesToAnalyze){
//				File fileWithCompletePath = new File(downloadPath + "analysis/" +file);
//				allFilesInAnalysisFolder.add(fileWithCompletePath.getAbsolutePath());
//			}
//		}
//		
//		filesToAnalyze = new String[allFilesInAnalysisFolder.size()];
//		filesToAnalyze = allFilesInAnalysisFolder.toArray(filesToAnalyze);
//		return filesToAnalyze;
//	}
	
//	
	public static Project start(ArrayList<String> filesToAnalyze) throws InterruptedException{
	//public  Project start(ArrayList<String> filesToAnalyze) throws InterruptedException{
		//String[] files = filesToAnalyze;
		
		//creating include/stubs.h and platform.h
		if(!noStubs){
			createStubs(filesToAnalyze);
		}
		
		//start the analysis
		return startAnalyser(filesToAnalyze);
	}
	
	public static Project startAnalyser(ArrayList<String> filesToAnalyze) throws InterruptedException{
//	public  Project startAnalyser(ArrayList<String> filesToAnalyze) throws InterruptedException{
		//start the analyzer
		project.analyze(filesToAnalyze);
		return project;
	}
	//
	public static void createStubs(ArrayList<String> files){
		
		//creating CProject to create include/stubs.h and platform.h
		CProject.createCProject(Runner.projectManager.getCurrentProject()); // projectManager.getCurrentProject()
		
		try {
			//creating platform.h and include/stubs.h // CIRAR UM MODULO SEPARADO.
			SampleHandler.analyzeFilesInSrc(files);
		} catch (Exception e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	
}
