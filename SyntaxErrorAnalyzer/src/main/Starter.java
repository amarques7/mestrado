package main;

import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
//import java.util.List;
import java.util.HashSet;

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
	
		CreateFile.create(downloadPath + File.separator + "include" + File.separator, "stubs.h");
//	new File(downloadPath + "include/stubs.h").createNewFile();
		
		File platform = new File(downloadPath + "platform.h");
		
		if (platform.length() == 0) {
			project = new Project(downloadPath + "analysis", downloadPath + "include"+ File.separator +"stubs.h");
		} else {
			project = new Project(downloadPath + "analysis", downloadPath + "platform.h");
		}
		project.setName(Runner.projectManager.getCurrentProject());
	}
	
	public static Project start(HashSet<String> filesToAnalyze) throws InterruptedException{

		//creating include/stubs.h and platform.h
		if(!noStubs){
			createStubs(filesToAnalyze);
		}
		
		//start the analysis
		return startAnalyser(filesToAnalyze);
	}
	
	public static Project startAnalyser(HashSet<String> filesToAnalyze) throws InterruptedException{

		//start the analyzer
		project.analyze(filesToAnalyze);
		return project;
	}
	//
	public static void createStubs(HashSet<String> files){
		
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
