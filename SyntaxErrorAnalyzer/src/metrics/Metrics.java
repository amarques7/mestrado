package metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import analysis.Dependency;
import analysis.Link;
import analysis.Variability;
import analysis.core.Call;
import git.AllCommit;
import main.Main;
import mestrado.core.Runner;

public class Metrics {
	
	public static String path;
	public static int numberOfVariabilities;
	public static int numberOfDependencies;
	public static int index = 1;
	
	public static Set<Dependency> allDependencies = new HashSet<Dependency>(500);
	public static Set<Variability> allVariabilities = new HashSet<Variability>(500);

	public static Set<Dependency> previousDependenciesList = new HashSet<Dependency>(500);
	public static Set<Variability> previousVariabilitiesList = new HashSet<Variability>(500);
	
	public static List<Call> calls;
	
	public static void write() throws InterruptedException{
		System.out.println("Writing dependencies.txt...");
		createDependenciesTxt();
	}

	//DEPENDENCIES
	public static void createDependenciesTxt() throws InterruptedException{
		
		if(!Main.analyseThisTime){
			//adding the current dps to previous dps
			previousDependenciesList = new HashSet<Dependency>(allDependencies.size());
			for(Dependency currentDp : allDependencies){
				previousDependenciesList.add(currentDp);
			}
			index = Runner.getIndexOfPastAnalysis() + 1;
			exportFunctionCalls.ExportNumberOfCalls.i = index - 1;
			Runner.analyseThisTime = true;
			return;
		}
		
		try {
			new File(Main.PATH + "\\" + Main.currentProject + "\\results\\dependencies").mkdirs();
			FileWriter arq = null;
			if(AllCommit.currentTag.size() == 0)
				arq = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\dependencies\\"+index+"_dependencies_" + Main.allCommitsThisAnalysis.get(index-1) + ".txt",true);
			else
				arq = new FileWriter(Main.PATH + "\\" + Main.currentProject + "\\results\\dependencies\\"+index+"_dependencies_" + AllCommit.currentTag.get(index-1) + ".txt",true);
			
			PrintWriter writer = new PrintWriter(arq);
			
			writer.println(index + "/" + (AllCommit.commitsIdToAnalyse.size() + Main.numberOfAnalysisOcurred - 1));
			writer.println("	Variabilities: " + allVariabilities.size());
			
			int totalVars = 0, totalFunc = 0, totalPE = 0;
			for(Variability v : allVariabilities){
				writer.println("		"+v.getName() + " vars: " + v.getNumberOfVariables() + " funcs: " + v.getNumberOfFunctions() + " local vars: " + v.getNumberOfLocalVariables());
				totalVars+= v.getNumberOfVariables() + v.getNumberOfLocalVariables();
				totalFunc+= v.getNumberOfFunctions();
			}
			totalPE = totalVars + totalFunc;
			writer.println("		"+"vars: " + totalVars + " funcs: " + totalFunc + " " + "pe: " + totalPE);
			
			writer.println();
			HashSet<Dependency> newDependencies = null, deadDependencies = null;
			if(index > 1){//this analysis occurred once
				newDependencies = getAllNewDependencies();
				writer.println("	New dependencies: " + newDependencies.size());
				for(Dependency dp : newDependencies)
					writer.println(" 		"+dp.getVariabilityA().getName() + " " +dp.getVariabilityB().getName());
				
				deadDependencies = getAllDeadDependencies();
				writer.println("	Dead dependencies: " + deadDependencies.size());
				for(Dependency dp : deadDependencies)
					writer.println(" 		"+dp.getVariabilityA().getName() + " " +dp.getVariabilityB().getName());
			
			}
			else{
				writer.println("	New dependencies: " + allDependencies.size());
				for(Dependency dp : allDependencies)
					writer.println(" 		"+dp.getVariabilityA().getName() + " " +dp.getVariabilityB().getName());
				
				writer.println("	Dead dependencies: " + "0");

			}
			
			writer.println();
			
			writer.println(" 	Number of dependencies: " + allDependencies.size());
			int numberOfChangedDependencies = 0;
			for(Dependency dpCurrentVersion : allDependencies){
				if(index > 1){
					Set<String> newLinks = new HashSet<String>(dpCurrentVersion.getLinks().size());
					Set<String> deadLinks = new HashSet<String>(dpCurrentVersion.getLinks().size());
					boolean in = false;

					for(Dependency dpPrevious : previousDependenciesList){
						boolean sameDependencie = dpPrevious.getVariabilityA().getName().equals(dpCurrentVersion.getVariabilityA().getName()) &&
								dpPrevious.getVariabilityB().getName().equals(dpCurrentVersion.getVariabilityB().getName());
						if(sameDependencie){
							for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
								for(Link linkFromPreviousDp : dpPrevious.getLinks()){
									if(linkFromCurrentDp.equals(linkFromPreviousDp)){
										in = true;
										break;
									}
								}
								if(!in)
									newLinks.add(linkFromCurrentDp.toString());
								in = false;
							}
		
							for(Link linkFromPreviousDp : dpPrevious.getLinks()){
								for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
									if(linkFromPreviousDp.equals(linkFromCurrentDp)){
										in = true;
										break;
									}
								}
								if(!in)
									deadLinks.add(linkFromPreviousDp.toString());
								in = false;
							}
						}
					}
					boolean newDependency = false;
					for(Dependency dp : newDependencies){
						if(dp.getVariabilityA().getName().equals(dpCurrentVersion.getVariabilityA().getName()) &&
							dp.getVariabilityB().getName().equals(dpCurrentVersion.getVariabilityB().getName())) {
							for(Link link : dp.getLinks())
								newLinks.add(link.toString());
							newDependency = true;
						}
						
					}
					int numberOfChanges = deadLinks.size() + newLinks.size();
					boolean changeOccurrence = false;
					if(numberOfChanges > 0){
						changeOccurrence = true;
						numberOfChangedDependencies++;
					}
					else{
						changeOccurrence = false;
					}
					
					if(newDependency){
						writer.print("*");
					}
					if(changeOccurrence && !newDependency){
						writer.println("!# 		"+dpCurrentVersion.getVariabilityA().getName() + " " +dpCurrentVersion.getVariabilityB().getName());
					}
					else{
						writer.println("# 		"+dpCurrentVersion.getVariabilityA().getName() + " " +dpCurrentVersion.getVariabilityB().getName());
					}
					
					writer.println("		links: " + dpCurrentVersion.getLinks().size());
					writer.println("		unique links: " + dpCurrentVersion.numberOfUniqueLinks);
					writer.println("		distinct links: " + dpCurrentVersion.numberOfDistinctLinks);
					writer.println("		program elements: " + dpCurrentVersion.getNumberOfPE());
					writer.println("		variables: " + dpCurrentVersion.getNumberOfVarDeclarated());
					writer.println("		functions: " + dpCurrentVersion.getNumberOfFuncDeclarated());
					
					writer.println("		new links: " + newLinks.size());
					writer.println("		removed links: " + deadLinks.size());
					writer.println("		changes: " + numberOfChanges);
					
					Set<String> links = new HashSet<String>(dpCurrentVersion.getLinks().size());
					for(Link link : dpCurrentVersion.getLinks()){
						links.add(link.toString());
					}
					
					showLinks(links, writer);
					
					writer.println("		+ New Links:");
					showLinks(newLinks, writer);
					
					writer.println("		- Dead Links:");
					showLinks(deadLinks, writer);
					
				}
				else{
					//first version
					
					writer.println("#* 		"+dpCurrentVersion.getVariabilityA().getName() + " " +dpCurrentVersion.getVariabilityB().getName());

					writer.println("		links: " + dpCurrentVersion.getLinks().size());
					writer.println("		unique links: " + dpCurrentVersion.numberOfUniqueLinks);
					writer.println("		distinct links: " + dpCurrentVersion.numberOfDistinctLinks);
					writer.println("		program elements: " + dpCurrentVersion.getNumberOfPE());
					writer.println("		variables: " + dpCurrentVersion.getNumberOfVarDeclarated());
					writer.println("		functions: " + dpCurrentVersion.getNumberOfFuncDeclarated());
					
					writer.println("		new links: " + dpCurrentVersion.getLinks().size());
					writer.println("		removed links: " + 0);
					writer.println("		changes: " + dpCurrentVersion.getLinks().size());
					
					Set<String> links = new HashSet<String>(dpCurrentVersion.getLinks().size());
					for(Link link : dpCurrentVersion.getLinks()){
						links.add(link.toString());
					}
					
					showLinks(links, writer);
					
					writer.println("		+ New Links:");
					showLinks(links, writer);
					writer.println("		- Dead Links:");
				
					numberOfChangedDependencies = allDependencies.size();
				}
				
				writer.println();
				
			}
			writer.println(" 	Total number of changed dependencies: " + numberOfChangedDependencies);
			//adding the current dps to previous dps
			previousDependenciesList = new HashSet<Dependency>(allDependencies.size());
			for(Dependency currentDp : allDependencies){
				previousDependenciesList.add(currentDp);
			}
			index++;
			
			writer.close();
			arq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void showLinks(Set<String> links, PrintWriter writer){
		
		for(String link : links){
			writer.println(" 			" + link);
		}
	}
	
	public static void showDependencies(List<Dependency> dps, PrintWriter gravarArq){
		for(Dependency dp : dps){
			gravarArq.println(" 		"+dp.getVariabilityA().getName() + " " +dp.getVariabilityB().getName());
		}
	}
	
	public static HashSet<Dependency> getAllNewDependencies(){
		boolean sameDependencie = false;
		HashSet<Dependency> newDependencies = new HashSet<Dependency>();
		for(Dependency dependency : allDependencies){
			sameDependencie = false;
			for(Dependency dependencyPrevious : previousDependenciesList) {
				sameDependencie = dependency.getVariabilityA().getName().equals(dependencyPrevious.getVariabilityA().getName()) &&
						dependency.getVariabilityB().getName().equals(dependencyPrevious.getVariabilityB().getName());
				if(sameDependencie)
					break;
			}
			if(!sameDependencie){
				newDependencies.add(dependency);
			}
		}

		return newDependencies;
		
	}
	
	public static HashSet<Dependency> getAllDeadDependencies(){
		HashSet<Dependency> deadDependencies = new HashSet<Dependency>();
		boolean sameDependencie = false;
		for(Dependency dependencyPrevious : previousDependenciesList){
			for(Dependency dependency : allDependencies){
				sameDependencie = dependency.getVariabilityA().getName().equals(dependencyPrevious.getVariabilityA().getName()) &&
						dependency.getVariabilityB().getName().equals(dependencyPrevious.getVariabilityB().getName());
				if(sameDependencie)
					break;
			}
			if(!sameDependencie){
				deadDependencies.add(dependencyPrevious);
			}
		}

		return deadDependencies;
		
	}
	
}
