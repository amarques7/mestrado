package analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import analysis.core.Call;
import analysis.core.Function;
import analysis.core.ProgramElement;
import analysis.core.Variable;
import de.fosd.typechef.featureexpr.FeatureExpr;
//import finegrained.Reports;
import mestrado.core.Runner;
import metrics.Metrics;
import tree.FunctionCall;
import tree.FunctionDef;
import tree.Id;

public class Analyser {

	public List<Variability> variabilities = new ArrayList<Variability>();
	public List<Variability> variabilitiesToAdd = new ArrayList<Variability>();
	public List<Link> links = new ArrayList<Link>();
	public List<Dependency> dependencies = new ArrayList<Dependency>();
	
	private List<Call> calls;
	private List<Variable> globals;
	
	private Map<FunctionDef, Function> functions;
	
	public static int numberOfVariabilities = 0;
	public static int numberOfDependencies = 0;
	
	public List<Dependency> setDps(Map<FunctionDef, Function> functions, List<Variable> globals, List<Variable> useOfGlobals, List<Call> calls) throws InterruptedException{
		System.out.println("-----");
		if(Runner.projectManager.isNoChangesInCFiles()) {//confirmar aqui
			Metrics.write();
		//	Reports.Dependencies(); //comentei 25/11
		}
		else {
			variabilities.add(new Variability("True")); //set first variability as True
			
			this.globals = globals;
			this.calls = calls;
			this.functions = functions;
			
			for (Entry<FunctionDef, Function> functionMap : functions.entrySet()) {
				Function f = functionMap.getValue();	
				setFunctionDeclarations(f);
				for(FunctionCall fc : f.getFunctionCalls())
					setFunctionCallDeclarations(fc, f);
				for(Id localVariable : f.getLocalVariables())
					setLocalVariableDeclarations(localVariable, f);
			}
			for(Variable var : globals){
				setVariableDeclarations(var);
			}
			for(Variable var : useOfGlobals){
				setVariableUses(var);
			}
			
//			variabilitiesToAdd.clear(); // doing this because of the use of the method getVariability in the set of the program elements
			
			setLinks();
			
			System.out.println("Creating dependencies");
			
			setDependencies();
			
			System.out.println("Done!");
			
		//	metrics
			Metrics.allVariabilities = new HashSet<Variability>(variabilities.size());
			for(Variability v : variabilities){
				v.updateNumberOfPE();
				Metrics.allVariabilities.add(v);
			 } 
			
			Metrics.allDependencies = new HashSet<Dependency>(dependencies.size());
			Metrics.allDependencies.addAll(dependencies); 
			
			//Raiza
		//	Reports.preservedDependencies = new HashSet<Dependency>(dependencies.size());
		//	Reports.changedDependencies = new HashSet<Dependency>(dependencies.size());
			
			//Adriano
			Metrics.calls = calls;
			Metrics.write();
	//		Reports.Dependencies();
			//end
		}
		
		
		return dependencies;
	}
		
	public void setDependencies(){
		for(Variability variability : variabilities){
			for(Link link : variability.getLinks()){
				String callerPresenceCondition = variability.getName();
				String calleePresenceCondition = link.getCallee().getPresenceCondition().toString();
				if(!(callerPresenceCondition.equals(calleePresenceCondition))){
					Dependency dependency = getDependency(variability, getVariability(calleePresenceCondition, true));
					dependency.getLinks().add(link);
				}
			}
		}
		
		resolveNewVariabilities();
		
		for(Dependency dependency : dependencies){
			dependency.updateProps();
			dependency.setDistinctAndUniqueLinks();
		}
	}
	
	public Dependency getDependency(Variability var1, Variability var2){
		for(Dependency dependency : dependencies){
			if(dependency.getVariabilityA().getName().equals(var1.getName())){
				if(dependency.getVariabilityB().getName().equals(var2.getName())){
					return dependency;
				}
			}
		}
		
		Dependency dependency = new Dependency(var1, var2);
		dependencies.add(dependency);
		
		return dependency;
	}
	
	public void resolveNewVariabilities() {
		Collections.sort(variabilitiesToAdd, new Comparator<Variability>() {
			public int compare(Variability s1, Variability s2){
				return s1.getName().compareTo(s2.getName());
			}
		});
		
		List<Variability> noRepetition = new ArrayList<Variability>(variabilitiesToAdd.size());
		if(variabilitiesToAdd.size() == 1) {
			variabilities.addAll(variabilitiesToAdd);
		}
		else {
			for(int i = 0 ; i < variabilitiesToAdd.size() - 1 ; i++) {
				if(!(variabilitiesToAdd.get(i).getName().equals(variabilitiesToAdd.get(i+1).getName()))) {
					noRepetition.add(variabilitiesToAdd.get(i));
				}
			}
			variabilities.addAll(noRepetition);
		}
		
		variabilitiesToAdd.clear();
	}
	
	public Variability getVariability(String presenceCondition, boolean toAdd){
		for(Variability variability : variabilities){
			if(variability.getName().equals(presenceCondition)){
				return variability;
			}
		}
		Variability variability = new Variability(presenceCondition);
		if(toAdd)
			variabilitiesToAdd.add(variability);
		else {
			variabilities.add(variability);
		}
		return variability;
	}
	
	public void setFunctionDeclarations(Function f){		
		Variability variability = getVariability(f.getPresenceCondition().toString(), false);
		variability.getDeclaredFunctions().add(f);
	}
	
	public void setFunctionCallDeclarations(FunctionCall fc, Function f) {
		Variability variability = getVariability(f.getPresenceCondition().toString(), false);
		variability.getUsedFunctions().add(f);
	}
	
	public void setVariableUses(Variable var) {
		Variability variability = getVariability(var.getPresenceCondition().toString(), false);
		variability.getUsedGlobalVariables().add(getDeclaredVar(var));
	}
	
	public void setVariableDeclarations(Variable v){		
		Variability variability = getVariability(v.getPresenceCondition().toString(), false);
		variability.getDeclaredGlobalVariables().add(v);
	}
	
	public void setLocalVariableDeclarations(Id localVariable, Function f) {
		for(Variability variability : variabilities){
			if(localVariable.getPresenceCondition().toString().equals(variability.getName())){
				variability.getDeclaredLocalVariables().add(new Variable(localVariable,f));
				return;
			}
		}
		
		Variability variability = new Variability(localVariable.getPresenceCondition().toString());
		variability.getDeclaredLocalVariables().add(new Variable(localVariable,f));
		variabilities.add(variability);
	}
	
	public void setLinks(){
		for(Call call : calls){
			for(Function function : call.getFunctionCalleesDeclaration()){
				createLink(call.getCaller(), function, call.getContext());
			}
			
			for(Variable variable : call.getVariableCalleesDeclaration()){
				createLink(call.getCaller(), variable, call.getContext());
			}
			
			for(ProgramElement useOfCaller : call.getUses()) {
				createDifferLink(useOfCaller.getPresenceCondition(), call.getContext(), call.getCaller().getPresenceCondition(), call.getCaller());
			}
		}
	}
	
	public void createDifferLink(FeatureExpr presenceConditionCaller, ProgramElement caller, FeatureExpr presenceConditionCallee, ProgramElement callee ) {
		caller.setPresenceCondition(presenceConditionCaller);
		callee.setPresenceCondition(presenceConditionCallee);
		Link link = new Link(caller, callee, (Function) caller); 
		links.add(link);
		getVariability(caller.getPresenceCondition().toString(), false).getLinks().add(link);
	}
	
	public void createLink(ProgramElement caller, ProgramElement callee, Function context){
		Link link = new Link(caller, callee, context); 
		if(callee == null || caller == null)
			return;
		links.add(link);
		getVariability(caller.getPresenceCondition().toString(), false).getLinks().add(link);
	}
	
	public Function getFunctionByFunctionDef(FunctionDef fdef){
		for(Entry<FunctionDef, Function> map : functions.entrySet()){
			FunctionDef functionDef = map.getKey();
			if(functionDef.equals(fdef)){
				return map.getValue();
			}
		}
		return null;
	}
	
	public List<Link> getLinks(){
		return links;
	}
	
	public Variable getDeclaredVar(Variable var) {
		for(Variable global : globals) {
			if(global.getName().equals(var.getName())) {
				return global;
			}
		}
		return null;
	}

}
