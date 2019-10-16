package finegrained;

import java.io.FileWriter;
import java.io.PrintWriter;
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
import mestrado.core.Runner;
import metrics.Metrics;

public class DetailedReports extends Metrics{
	public static int rindex = 1;
	public static Set<Dependency> preservedDependencies = new HashSet<Dependency>(500);
	public static Set<Dependency> changedDependencies = new HashSet<Dependency>(500);
	
	public static void printVariabilties(FileWriter arqvar) {
		PrintWriter gravarArq = new PrintWriter(arqvar);
		//gravarArq.println(rindex + "," + AllCommit.commitsIdToAnalyse.size()+","+allVariabilities.size());
		gravarArq.println(rindex + "," + Runner.projectManager.repo.getNumberofCommits()+","+allVariabilities.size());
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
	
//
	
	public static void reportCoarseGrained(PrintWriter gravarArq) {
		//Evolu��o	Variabilities	DependenciesTotal	DependenciesExcluded	DependenciesNew	  Dependenciespreserved		DependenciesChanged
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
