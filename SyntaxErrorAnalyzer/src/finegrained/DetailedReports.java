package finegrained;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import analysis.Dependency;
import analysis.ID;
import analysis.Link;
import analysis.Variability;
import analysis.core.Function;
import analysis.core.ProgramElement;
import analysis.core.Variable;
import git.AllCommit;
import main.Main;
import metrics.Metrics;
import util.LoadParameters;


public class DetailedReports extends Metrics{
	public static int rindex = 1;
	public static Set<Dependency> preservedDependencies = new HashSet<Dependency>(500);
	public static Set<Dependency> changedDependencies = new HashSet<Dependency>(500);
	
	public static void printVariabilties(FileWriter arqvar) {
		PrintWriter gravarArq = new PrintWriter(arqvar);
		gravarArq.println(rindex + "," + AllCommit.commitsIdToAnalyse.size()+","+allVariabilities.size());
		//escrevendo dados
		for(Variability var : allVariabilities){
			System.out.println(var.getName());
			//gravarArq.println(var.getName());
			//(categoria, qualificador, especificador, modificador, tipo, nome);
			for(Function decfunc: var.getDeclaredFunctions()) {
				gravarArq.println(var.getName()+",function,declarated,"+decfunc.getQualifier()+","+decfunc.getSpecifier()+","+decfunc.getModifier()+","+decfunc.getType()+","+decfunc.getName()+","+decfunc.getPositionTo());
			}
			for(Variable decvariable: var.getDeclaredGlobalVariables()) {
				gravarArq.println(var.getName()+",variable,declarated,"+decvariable.getQualifier()+","+decvariable.getSpecifier()+","+decvariable.getModifier()+","+decvariable.getType()+","+decvariable.getName()+","+decvariable.getPositionTo());
			}
			for(Function usedfunc: var.getUsedFunctions()) {
				gravarArq.println(var.getName()+",function,used,"+usedfunc.getQualifier()+","+usedfunc.getSpecifier()+","+usedfunc.getModifier()+","+usedfunc.getType()+","+usedfunc.getName()+","+usedfunc.getPositionFrom());
			}
			for(Variable usedvariable: var.getUsedGlobalVariables()) {
				gravarArq.println(var.getName()+",variable,used,"+usedvariable.getQualifier()+","+usedvariable.getSpecifier()+","+usedvariable.getModifier()+","+usedvariable.getType()+","+usedvariable.getName()+","+usedvariable.getPositionFrom());
			}
			
			
		}
		gravarArq.close();
	}
	
	
	//DEPENDENCIES
	
	public static void printNewDependencies(Set<Dependency> allDependencies, PrintWriter gravarArqn) {
		gravarArqn.println(rindex + "," +allDependencies.size()+","+allVariabilities.size());
		for(Dependency dp : allDependencies) {
			//Dependencias novas
			gravarArqn.println("Dependency,"+dp.getVariabilityA().getName() + "," +dp.getVariabilityB().getName());
			showLinks(1,dp.getLinks(), gravarArqn);
		}
	}
	
	public static void printDeadDependencies(HashSet<Dependency> hashSet, PrintWriter gravarArqd) {
		gravarArqd.println(rindex + "," + hashSet.size()+","+allVariabilities.size());
		for(Dependency dp : hashSet) {
			//Dependencias Mortas
			gravarArqd.println("Dependency,"+dp.getVariabilityA().getName() + "," +dp.getVariabilityB().getName());
			showLinks(2,dp.getLinks(), gravarArqd);
		}
	}
	
	public static void Dependencies() throws InterruptedException{
		
		if(!Main.analyseThisTime){
			//adding the current dps to previous dps
			previousDependenciesList = new HashSet<Dependency>(allDependencies.size());
			for(Dependency currentDp : allDependencies){
				previousDependenciesList.add(currentDp);
			}
			rindex = Main.getIndexOfPastAnalysis() + 1;
			Main.analyseThisTime = true;
			return;
		}
		
		try {
			new File(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\PermanentsDependencies").mkdirs();
			new File(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\Variabilities").mkdirs();
			new File(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\DeadDependencies").mkdirs();
			new File(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\NewDependencies").mkdirs();
			FileWriter arq = null; FileWriter arqd = null; FileWriter arqn = null; FileWriter arqvar = null; FileWriter coarsearq = null;
			if(AllCommit.currentTag.size() == 0){
				System.out.println("rindex  "+rindex);
				coarsearq = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\PermanentsDependencies\\"+rindex+"_CoarseGrained_" + AllCommit.commitsIdToAnalyse.get(rindex-1) + ".csv",true);
				arq = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\PermanentsDependencies\\"+rindex+"_Permanents_" + AllCommit.commitsIdToAnalyse.get(rindex-1) + ".csv",true);
				arqd = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\DeadDependencies\\"+rindex+"_Dead_" + AllCommit.commitsIdToAnalyse.get(rindex-1) + ".csv",true);
				arqn = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\NewDependencies\\"+rindex+"_New_" + AllCommit.commitsIdToAnalyse.get(rindex-1) + ".csv",true);
				arqvar = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\Variabilities\\"+rindex+"_variabilities_" + AllCommit.commitsIdToAnalyse.get(rindex-1) + ".csv",true);
			}else {
				
				coarsearq = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\PermanentsDependencies\\"+rindex+"_CoarseGrained_" +  AllCommit.currentTag.get(rindex-1) + ".csv",true);
				arq = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\PermanentsDependencies\\"+rindex+"_Permanents_" +  AllCommit.currentTag.get(rindex-1) + ".csv",true);
				arqd = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\DeadDependencies\\"+rindex+"_Dead_" + AllCommit.currentTag.get(rindex-1) + ".csv",true);
				arqn = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\NewDependencies\\"+rindex+"_New_" + AllCommit.currentTag.get(rindex-1) + ".csv",true);
				arqvar = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\csv\\Variabilities\\"+rindex+"_variabilities_" + AllCommit.currentTag.get(rindex-1) + ".csv",true);
			}
			PrintWriter gravarArq = new PrintWriter(arq); PrintWriter gravarArqd = new PrintWriter(arqd); PrintWriter gravarArqn = new PrintWriter(arqn);
			PrintWriter gravarCoarse = new PrintWriter(coarsearq);
			printVariabilties(arqvar);
			if(rindex > 1){//this analysis occurred once
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-mm-yyyyy hh:mm:ss");
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				System.out.println(DATE_FORMAT.format(Main.commitDate));
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				printNewDependencies((Set<Dependency>) getAllNewDependencies(),gravarArqn);
				printDeadDependencies(getAllDeadDependencies(),gravarArqd);
			}
			if(rindex <= 1){
				//Primeira vez todas as dependencias são novas
				printNewDependencies(allDependencies,gravarArqn);
			}
			gravarArq.println(rindex + "," + AllCommit.commitsIdToAnalyse.size()+","+allVariabilities.size());
			for(Dependency dpCurrentVersion : allDependencies){
				if(rindex > 1){
					List<Link> newLinks = new ArrayList<Link>(dpCurrentVersion.getLinks().size());
					List<Link> deadLinks = new ArrayList<Link>(dpCurrentVersion.getLinks().size());
					List<Link> permanentLinks = new ArrayList<Link>(dpCurrentVersion.getLinks().size());
					boolean entrou = false;
					for(Dependency dpPrevious : previousDependenciesList){
						boolean sameDependencie = dpPrevious.getVariabilityA().getName().equals(dpCurrentVersion.getVariabilityA().getName()) &&
								dpPrevious.getVariabilityB().getName().equals(dpCurrentVersion.getVariabilityB().getName());
						if(sameDependencie){
							if (!containsDep(preservedDependencies, dpCurrentVersion)) {
								preservedDependencies.add(dpCurrentVersion);
							}
							for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
								for(Link linkFromPreviousDp : dpPrevious.getLinks()){
									if((linkFromPreviousDp.callee.getName().equals(linkFromCurrentDp.callee.getName()) && 
											linkFromPreviousDp.caller.getName().equals(linkFromCurrentDp.caller.getName()))){
										entrou = true;
										break;
									}
								}
								if(!entrou)
									newLinks.add(linkFromCurrentDp);
								entrou = false;
							}
		
							for(Link linkFromPreviousDp : dpPrevious.getLinks()){
								for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
									if((linkFromPreviousDp.callee.getName().equals(linkFromCurrentDp.callee.getName()) && 
											linkFromPreviousDp.caller.getName().equals(linkFromCurrentDp.caller.getName()))){
										entrou = true;
										FoundChanges atest = new FoundChanges();
										if(atest.differ(linkFromPreviousDp, linkFromCurrentDp, dpCurrentVersion.getVariabilityA())) {
											changedDependencies.add(dpCurrentVersion);
											System.out.println("mudanca em "+dpCurrentVersion.getVariabilityA());
										}
										//links iguais encontrar mudancas
										break;
										
									}
								}
								if(!entrou)
									deadLinks.add(linkFromPreviousDp);
								entrou = false;
							}
							
							for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
								if(!newLinks.contains(linkFromCurrentDp)) {
									if(!permanentLinks.contains(linkFromCurrentDp)) {
										permanentLinks.add(linkFromCurrentDp);
									}
								}
							}	
						}
					}

					for(Dependency dp : getAllNewDependencies()){
						if(dp.getVariabilityA().getName().equals(dpCurrentVersion.getVariabilityA().getName()) &&
							dp.getVariabilityB().getName().equals(dpCurrentVersion.getVariabilityB().getName()))
							for(Link link : dp.getLinks())
								newLinks.add(link);
						
					}
					gravarArq.println("Dependency,"+dpCurrentVersion.getVariabilityA().getName() + "," +dpCurrentVersion.getVariabilityB().getName()+","+dpCurrentVersion.getLinks().size());					
					showLinks(0,dpCurrentVersion.getLinks(), gravarArq);
					showLinks(1,newLinks, gravarArq);
					showLinks(2,deadLinks, gravarArq);
					reportCoarseGrained(gravarCoarse);
					
					
				}
			}
			//adding the current dps to previous dps
			previousDependenciesList =  new HashSet<Dependency>(allDependencies.size());
			for(Dependency currentDp : allDependencies){
				previousDependenciesList.add(currentDp);
			}
			rindex++;
			
			gravarArq.close();
			arq.close();
			gravarArqn.close();
			arqn.close();
			gravarArqd.close();
			arqn.close();
			arqvar.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void reportCoarseGrained(PrintWriter gravarArq) {
		//Evolução	Variabilities	DependenciesTotal	DependenciesExcluded	DependenciesNew	  Dependenciespreserved		DependenciesChanged
		//csvcaller ="Evolution, Variabilities, TotalDependencies, DependenciesDeleted, DependenciesAdditions, DependenciesPreserved, DependenciesChanged";
		gravarArq.println("Evolution, Variabilities, TotalDependencies, DependenciesDeleted, DependenciesAdditions, DependenciesPreserved, DependenciesChanged");
		gravarArq.println("Evolution"+","+allVariabilities.size()+","+allDependencies.size()+","+getAllDeadDependencies().size()+","+getAllNewDependencies().size()+","+preservedDependencies.size()+","+changedDependencies.size());
	}
	
	public static void showLinks(int status,List<Link> links, PrintWriter gravarArq){
		for(int i = 0; i < links.size(); i++){
			Link link = links.get(i);
			String csvcaller = ""; 
			String	csvcallee = "";
			ProgramElement caller, callee;
			caller = link.getCaller();
			callee = link.getCallee();
			if(caller.id == ID.Variable) {
				csvcaller ="variable,"+caller.getQualifier()+","+caller.getSpecifier()+","+caller.getModifier()+","+caller.getType()+","+caller.getName();
				//(categoria, qualificador, especificador, modificador, tipo, nome);
			}
			if( caller.id == ID.Function) {
				csvcaller ="function,"+caller.getQualifier()+","+caller.getSpecifier()+","+caller.getModifier()+","+caller.getType()+","+caller.getName();
				Function f = (Function) caller;
				String pars=","+f.getParameters().size()+',';
				for(Variable parameter: f.getParameters()) {
					pars =pars+","+parameter.getQualifier()+","+parameter.getSpecifier()+","+parameter.getModifier()+","+parameter.getType()+","+parameter.getName() ;
					 
				}
				csvcaller = csvcaller+pars;
				//(categoria, qualificador, especificador, modificador, tipo, nome, quantidade de parametros);
			}
			
			if(callee.id == ID.Variable) {
				csvcallee =",variable,"+callee.getQualifier()+","+callee.getSpecifier()+","+callee.getModifier()+","+callee.getType()+","+callee.getName();
				//(categoria, qualificador, especificador, modificador, tipo, nome);
			}
			if(callee.id == ID.Function) {
				csvcallee =",function,"+callee.getQualifier()+","+callee.getSpecifier()+","+callee.getModifier()+","+callee.getType()+","+callee.getName();
				//(categoria, qualificador, especificador, modificador, tipo, nome, quantidade de parametros);
				Function f = (Function) callee;
				String pars=","+f.getParameters().size()+',';
				for(Variable parameter: f.getParameters()) {
					pars =pars+","+parameter.getQualifier()+","+parameter.getSpecifier()+","+parameter.getModifier()+","+parameter.getType()+","+parameter.getName() ;
					 
				}
				csvcallee = csvcallee+pars;
			}
			
			
			gravarArq.println(status+","+csvcaller+csvcallee);
		}
	}
	
	


	
	
	
	
	public static boolean compare_variabilities(Dependency dep1, Dependency dep2) {
		if(dep1.getVariabilityA().getName().compareTo(dep2.getVariabilityA().getName()) == 0) {
			if(dep1.getVariabilityB().getName().compareTo(dep2.getVariabilityB().getName()) == 0) 
				return true;
		}
		
		return false;			
	}
	
	public static boolean containsDep(Set<Dependency> Dependencies, Dependency dependency) {
		for (Dependency dp: Dependencies) {
				if(compare_variabilities(dp, dependency)) {
					return true;
				}
		}
		return false;
		
	}
	


	

}
