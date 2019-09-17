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
import main.Main;
import mestrado.core.Runner;
import metrics.Metrics;
import xtc.lang.blink.agent.GenerateJNIFunctionProxy;

public class Reports extends Metrics {
	public static int rindex = 1;
	public static Set<Dependency> preservedDependencies = new HashSet<Dependency>(500);
	public static Set<Dependency> changedDependencies = new HashSet<Dependency>(500);
	public static Set<Variability> variabilityDependencyComposer = new HashSet<Variability>(allVariabilities);
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	
	

	
	public static void Dependencies() throws InterruptedException{
		
		if(!Runner.analyseThisTime){//verificar o valor no outro codigo
			//adding the current dps to previous dps
			previousDependenciesList = new HashSet<Dependency>(allDependencies.size());
			previousVariabilitiesList = new HashSet<Variability>(allVariabilities.size());
			variabilityDependencyComposer = new HashSet<Variability>(allVariabilities);
			
			for(Variability currentVar : allVariabilities) {
				previousVariabilitiesList.add(currentVar);
			}
			for(Dependency currentDp : allDependencies){
				previousDependenciesList.add(currentDp);
			}
			rindex = Runner.getIndexOfPastAnalysis() + 1;
			Main.analyseThisTime = true;
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> RINDEX     "+ rindex);
			return;
		}
		
		try {
			PrintWriter printCoarse = null;
			PrintWriter printFine = null;
			PrintWriter printImpact = null;
			PrintWriter printVar = null;
			FileWriter arqvar = null; FileWriter coarsearq = null; FileWriter fineFile = null; FileWriter impactFile = null;
			new File(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\PermanentsDependencies").mkdirs();
			new File(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities").mkdirs();
			new File(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\AllVariabilities").mkdirs();
			new File(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\ImpactVariabilities").mkdirs();
			new File(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\AllDependencies").mkdirs();
			new File(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\AllDependenciesChanged").mkdirs();
			new File(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\ProgramWeight").mkdirs();
			
			
			impactFile = new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\PermanentsDependencies\\"+"_Impact_" + ".csv",true);
			fineFile = new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\PermanentsDependencies\\"+"_FineGrained_" + ".csv",true);
			coarsearq = new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\PermanentsDependencies\\"+"_CoarseGrained_"+ ".csv",true);
			arqvar = new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\"+"variabilities"+ ".csv",true);
			
			printCoarse = new PrintWriter(coarsearq);
			printFine = new PrintWriter(fineFile);
			printImpact = new PrintWriter(impactFile);
			printVar = new PrintWriter(arqvar);
			
			FoundImpact foundImpact = new FoundImpact();
			FoundChanges foundChanges = new FoundChanges();
			
			if(rindex <= 1){
				//Primeira vez todas as dependencias s�o novas
				//printNewDependencies(allDependencies,gravarArqn);
				printCoarse.println("Date, Evolution, Variabilities, TotalDependencies, DependenciesDeleted, DependenciesAdditions, DependenciesPreserved, DependenciesChanged");
				printCoarse.println("--"+","+rindex+","+ allVariabilities.size()+","+allDependencies.size()+","+getAllDeadDependencies().size()+","+getAllNewDependencies().size()+","+preservedDependencies.size()+","+changedDependencies.size());
			
				printFine.println("Date, Evolution,Number of Functions Change,Number of Variables Change,Function(+),Variable(+),Function(-),Variable(-),Modifier Change(Var.),Especifier Change(Var.),Qualifier Change(Var.),Type Change(Var.),Function Return Change,	Modifier Change (Func.), Specifier Change (Func.),Qualifier Change (Func.),Parameters Change (Func.)");
				printVariabilities(printVar);
			} 
			
			//Found Preserved Dependencies
			if(rindex > 1){//this analysis occurred once
				
				System.out.println(DATE_FORMAT.format(Main.commitDate));
				foundPreservedDependencies(foundChanges, foundImpact);
				printVariabilities(printVar);
				reportCoarseGrained(printCoarse);
				reportFineGrained(printFine, foundChanges);
				compareImpactChanges(foundImpact, foundChanges);
				reportPointImpact(printImpact, foundImpact);
				reportPE_wheight();
				
			}
			
			variabilityDependencyComposer = new HashSet<Variability>(allVariabilities);
			previousVariabilitiesList = new HashSet<Variability>(allVariabilities.size());
			for(Variability currentVar : allVariabilities) {
				previousVariabilitiesList.add(currentVar);
			}

			//adding the current dps to previous dps
			previousDependenciesList =  new HashSet<Dependency>(allDependencies.size());
			for(Dependency currentDp : allDependencies){
				previousDependenciesList.add(currentDp);
			}
			
			changedDependencies = new HashSet<Dependency>(500);
			rindex++;
			
			fineFile.close();
			printFine.close();
			printCoarse.close();
			coarsearq.close();
			impactFile.close();
			printImpact.close();
			arqvar.close();
			printVar.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void foundPreservedDependencies(FoundChanges atest, FoundImpact foundImpact) {
		
		for(Dependency dpCurrentVersion : allDependencies){
			List<Link> newLinks = new ArrayList<Link>(dpCurrentVersion.getLinks().size());
			List<Link> deadLinks = new ArrayList<Link>(dpCurrentVersion.getLinks().size());
			List<Link> permanentLinks = new ArrayList<Link>(dpCurrentVersion.getLinks().size());
			boolean isPreserved = false;
			
			if(!variabilityDependencyComposer.contains(dpCurrentVersion.getVariabilityA())) {
				variabilityDependencyComposer.add(dpCurrentVersion.getVariabilityA());
			}
			if(!variabilityDependencyComposer.contains(dpCurrentVersion.getVariabilityB())) {
				variabilityDependencyComposer.add(dpCurrentVersion.getVariabilityB());
			}
			
			for(Dependency dpPrevious : previousDependenciesList){
				boolean sameDependency = isSameDependency(dpPrevious, dpCurrentVersion);
				if(sameDependency){
					if (!containsDep(preservedDependencies, dpCurrentVersion)) {
						preservedDependencies.add(dpCurrentVersion);	
					}
					
					//Looking for new links
					for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
						for(Link linkFromPreviousDp : dpPrevious.getLinks()){
							if(isSameLink(linkFromPreviousDp, linkFromCurrentDp)){
								isPreserved = true;
								break;
							}
						}
						if(!isPreserved) {
							newLinks.add(linkFromCurrentDp);
							dpCurrentVersion.addPeChanged(linkFromCurrentDp.getCallee());
							atest.addLink(linkFromCurrentDp, dpCurrentVersion.getVariabilityB());
							atest.getImpactList();
							foundImpact.addImpact(atest.getImpactList());
							if (!containsDep(changedDependencies, dpCurrentVersion)) {
								changedDependencies.add(dpCurrentVersion);
							}
						}
						isPreserved = false;
					}
					
					//Looking for dead links
					for(Link linkFromPreviousDp : dpPrevious.getLinks()){
						for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
							if(isSameLink(linkFromPreviousDp, linkFromCurrentDp)){
								isPreserved = true;
								break;
							}
						}
						if(!isPreserved) {
							deadLinks.add(linkFromPreviousDp);
							dpCurrentVersion.addPeChanged(linkFromPreviousDp.getCallee());
							atest.delLink(linkFromPreviousDp, dpCurrentVersion.getVariabilityB());
							atest.getImpactList();
							foundImpact.addImpact(atest.getImpactList());
							if (!containsDep(changedDependencies, dpCurrentVersion)) {
								changedDependencies.add(dpCurrentVersion);
							}
						}
						isPreserved = false;
					}
						
					//Looking for preserved links
					for(Link linkFromCurrentDp : dpCurrentVersion.getLinks()){
						if(!newLinks.contains(linkFromCurrentDp)) {
							if(!permanentLinks.contains(linkFromCurrentDp)) {
								permanentLinks.add(linkFromCurrentDp);
							}
						}
					}
					
					//Looking for fine grained changes in preserved links
					for(Link linkFromPreviousDp: dpPrevious.getLinks()) {
						for(Link preservedLink: permanentLinks) {
							if (isSameLink(linkFromPreviousDp, preservedLink)) {
								if(atest.differ(linkFromPreviousDp, preservedLink, dpCurrentVersion.getVariabilityB())) {
									dpCurrentVersion.addPeChanged(preservedLink.getCallee());
									if (!containsDep(changedDependencies, dpCurrentVersion)) {
										changedDependencies.add(dpCurrentVersion);
										foundImpact.addImpact(atest.getImpactList());
									}
								}	
							}	
						}
					}					
				}
			}
		}
	}
	
	
	public static void compareImpactChanges(FoundImpact foundImpact,  FoundChanges foundChanges) {
		
		Set<Variability> variabilityAnalyzedAll = new HashSet<Variability>(allVariabilities.size());
		try {
			PrintWriter printVaRALL = new PrintWriter(new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\AllVariabilities\\"+rindex+"_All_Variabilities"+ ".csv",true));
			for (Variability var: allVariabilities) {
				printVaRALL.println(var.getName()+",");
				if(!containsVariability(variabilityAnalyzedAll, var)) {
					if(foundImpact.compareVariabilities(var, previousVariabilitiesList)) {	
						foundImpact.addGeneralVariabilityImpact(var);
					}
				}
				
			}
			printVaRALL.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Set<Impact> variabilityAnalyzed = new HashSet<Impact>(foundChanges.getImpactList().size());
		for (Impact imp: foundChanges.getImpactList()) {
			if(!containsImpact(variabilityAnalyzed, imp)) {
				variabilityAnalyzed.add(imp);
				foundImpact.addPossibleImpactVariability(imp.getVariability());
				if(foundImpact.compareVariabilities(imp.getVariability(), previousVariabilitiesList)) {	
					foundImpact.addImpactPoints(imp.getFromProgramElement());
					foundImpact.addImpactVariability(imp);
				}
				
				/*for (Function func: imp.getVariability().getDeclaredFunctions()) {
					if(foundImpact.compareProgramElement((ProgramElement)func, imp.getVariability(), previousVariabilitiesList)) {
						foundImpact.addImpactFunctions();
					}
					
				}
				
				for (Variable variable: imp.getVariability().getDeclaredGlobalVariables()) {
					if(foundImpact.compareProgramElement((ProgramElement)variable, imp.getVariability(), previousVariabilitiesList)) {
						foundImpact.addImpactVariables();
					}
				}*/
			}
		}
	}

	public static void printVariabilities(PrintWriter gravarArq) {
		int nfunction = 0;
		int nvariable = 0;
		
		for (Dependency dep: allDependencies) {
			nfunction += dep.getNumberOfFuncDeclarated();
			nvariable += dep.getNumberOfVarDeclarated();
		}
		
		gravarArq.println(rindex+","+allVariabilities.size()+","+allDependencies.size()+","+nfunction+","+nvariable);
		nfunction = 0;
		nvariable = 0;
	}
	
	public static void reportCoarseGrained(PrintWriter gravarArq) {

		try {
			if(preservedDependencies.size() > 0) {
				PrintWriter printDepALL = new PrintWriter(new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\AllDependencies\\"+rindex+"_All_Dependencies"+ ".csv",true));
				for(Dependency dep: preservedDependencies) {			
					printDepALL.println(dep.getVariabilityA().getName()+","+dep.getVariabilityB().getName());	
				}
				printDepALL.close();
			}

			if(changedDependencies.size() > 0) {
				PrintWriter printDepCh = new PrintWriter(new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\AllDependenciesChanged\\"+rindex+"_All_Dependencies_Changed"+ ".csv",true));
				for(Dependency dep: changedDependencies) {			
					printDepCh.println(dep.getVariabilityA().getName()+","+dep.getVariabilityB().getName());	
				}
				printDepCh.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gravarArq.println(DATE_FORMAT.format(Main.commitDate)+","+rindex+","+allVariabilities.size()+","+allDependencies.size()+","+getAllDeadDependencies().size()+","+getAllNewDependencies().size()+","+preservedDependencies.size()+","+changedDependencies.size());
	}
	
	public static void reportFineGrained(PrintWriter printFile, FoundChanges changes) {
		
		int nfunctionCh = 0;
		int nvariableCh = 0;
		for (Dependency dep: allDependencies) {
			nfunctionCh += dep.getFuncChanged().size();
			nvariableCh += dep.getVarChanged().size();
		}
		printFile.println(DATE_FORMAT.format(Main.commitDate)+","+rindex+","+nfunctionCh+","+nvariableCh+","+changes.funch.get_Insert()+","+changes.varch.get_Insert()+","+changes.funch.get_Removal()+","+changes.varch.get_Removal()+","+changes.varch.modifierchanges.get_total()+","+changes.varch.specifierchanges.get_total()+","+changes.varch.qualifierchanges.get_total()+","+changes.varch.typeschanges.get_total()+","+changes.funch.typeschanges.get_total()+","+changes.funch.modifierchanges.get_total()+","+changes.funch.specifierchanges.get_total()+","+changes.funch.qualifierchanges.get_total()+","+changes.cont_change_parameter);
		
		nfunctionCh = 0;
		nvariableCh = 0;
	}


	public static Set<ProgramElement> howmany() {
		Set<ProgramElement> pes = new HashSet<ProgramElement>(10000);
		Set<Variability> ves = new HashSet<Variability>(allVariabilities);
		for (Dependency dep: allDependencies) {
			for (Link link: dep.getLinksDistintos()) {
				
				if(! dep.isContainPE(pes, link.getCallee())) {
					pes.add(link.getCallee());
					ves.add(dep.getVariabilityB());
				}else {	
					if(! containsVariability(ves, dep.getVariabilityB())) {
						link.getCallee().setWeight();
						ves.add(dep.getVariabilityB());
					}	
				}		
			}
		}
		return pes;
	}

	public static void reportPE_wheight() {
		String category = null;
		Set<ProgramElement> pes = howmany();
		try {
			PrintWriter printPeW = new PrintWriter(new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\ProgramWeight\\"+rindex+"pe"+ ".csv",true));
			for(ProgramElement pe: pes) {
					if (pe.id == ID.Function) {
						category = "function";
					}
					if (pe.id == ID.Variable) {
						category = "variable";
					}
					printPeW.println(pe.getName()+","+pe.getWeight()+","+category);
				}
			printPeW.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void reportPointImpact(PrintWriter printFile, FoundImpact fi) {
		
		printFile.println(DATE_FORMAT.format(Main.commitDate)+","+rindex+","+changedDependencies.size()+","+fi.getPossibleImpactVariability().size()+","+fi.getImpactVariability().size()+","+fi.getImpactPoints()+","+fi.getGeneralVariability().size()+","+fi.getImpactProgramElement().size()+","+fi.getImpactFunctions()+","+fi.getImpactVariables()); 		
		
		try {
			if(fi.getGeneralVariability().size() > 0) {
				PrintWriter printVar = new PrintWriter(new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\ImpactVariabilities\\"+rindex+"_impact_variabilities"+ ".csv",true));
				for (Variability var: fi.getGeneralVariability()) {
					printVar.println(var.getName()+",");
				}
				printVar.close();
			}
			
			if(fi.getPossibleImpactVariability().size() > 0) {
				PrintWriter printVarDep = new PrintWriter(new FileWriter(Runner.path + "\\" + Runner.currentProject + "\\results\\csv\\Variabilities\\"+rindex+"_impact_variabilities_dependee"+ ".csv",true));
				for (Impact imp: fi.getImpactVariability()) {	
					if(imp.getFromProgramElement().id == null)
						System.out.println("393 >>>> "+imp.getFromProgramElement().getName()+"   is   "+imp.getFromProgramElement().id);
					
					
					if (imp.getFromProgramElement().id == ID.Function)
						printVarDep.println(imp.getVariability().getName()+","+imp.getFromProgramElement().getName()+","+imp.getChangeCategory()+",Function,"+fi.getPossibleImpactVariability().size());
					if (imp.getFromProgramElement().id == ID.Variable)
						printVarDep.println(imp.getVariability().getName()+","+imp.getFromProgramElement().getName()+","+imp.getChangeCategory()+",Variable,"+fi.getPossibleImpactVariability().size());
					if ((imp.getFromProgramElement().id != ID.Variable) && (imp.getFromProgramElement().id != ID.Function))
						printVarDep.println(imp.getVariability().getName()+","+imp.getFromProgramElement().getName()+","+imp.getChangeCategory()+","+imp.getFromProgramElement().id+","+fi.getPossibleImpactVariability().size());
				}
				printVarDep.close();	
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public static boolean isSameDependency(Dependency dep1, Dependency dep2) {
		return (dep1.getVariabilityA().getName().equals(dep2.getVariabilityA().getName()) && 
				dep1.getVariabilityB().getName().equals(dep2.getVariabilityB().getName()));			
	}
	
	public static boolean isSameLink(Link lk1, Link lk2) {
		return (lk1.callee.getName().equals(lk2.callee.getName()) && 
				lk1.caller.getName().equals(lk2.caller.getName()));
	}
	
	public static boolean containsDep(Set<Dependency> Dependencies, Dependency dependency) {
		for (Dependency dp: Dependencies) {
				if(isSameDependency(dp, dependency)) {
					return true;
				}
		}
		return false;
		
	}
	
	public static boolean containsImpact(Set<Impact> impacts, Impact impact) {
		for (Impact imp:  impacts) {
			if(imp.getVariability().getName().equals(impact.getVariability().getName())) {
				if(imp.getFromProgramElement().getName().equals(impact.getFromProgramElement().getName())) {
					return true;
				}
				
			}		
		}
		return false;
	}
	
	public static boolean containsVariability(Set<Variability> variabilities, Variability variability) {
		for (Variability var:  variabilities) {
			if(var.getName().equals(variability.getName())) {
				return true;
			}		
		}
		return false;
	}
}
