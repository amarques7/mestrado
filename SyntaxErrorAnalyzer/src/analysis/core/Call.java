package analysis.core;

import java.util.ArrayList;
import java.util.List;

import analysis.ID;

public class Call {
	private ProgramElement caller;
	private List<Variable> variableCalleesDeclaration;
	private List<Variable> variableCalleesUses;
	private List<Function> functionCalleesDeclaration;
	private List<Function> functionCalleesUses;
	private Function context;
	
	private List<ProgramElement> uses;
	
	public Call(ProgramElement caller){
		if(caller instanceof Variable)
			caller.id = ID.Variable;
		else if(caller instanceof Function)
			caller.id = ID.Function;
		this.setCaller(caller);
		
		variableCalleesDeclaration = new ArrayList<Variable>();
		variableCalleesUses = new ArrayList<Variable>();
		functionCalleesDeclaration = new ArrayList<Function>();
		functionCalleesUses = new ArrayList<Function>();
		
		uses = new ArrayList<ProgramElement>();
	}

	public ProgramElement getCaller() {
		return caller;
	}

	public void setCaller(ProgramElement caller) {
		this.caller = caller;
	}

	public Function getContext() {
		return context;
	}

	public void setContext(Function context) {
		this.context = context;
	}

	public List<ProgramElement> getUses() {
		return uses;
	}

	public void setUses(List<ProgramElement> uses) {
		this.uses = uses;
	}

	public List<Variable> getVariableCalleesDeclaration() {
		return variableCalleesDeclaration;
	}

	public void setVariableCalleesDefinitions(List<Variable> variableCalleesDefinitions) {
		this.variableCalleesDeclaration = variableCalleesDefinitions;
	}

	public List<Variable> getVariableCalleesUses() {
		return variableCalleesUses;
	}

	public void setVariableCalleesUses(ArrayList<Variable> arrayList) {
		this.variableCalleesUses = arrayList;
	}

	public List<Function> getFunctionCalleesDeclaration() {
		return functionCalleesDeclaration;
	}

	public void setFunctionCalleesDefinitions(List<Function> functionCalleesDefinitions) {
		this.functionCalleesDeclaration = functionCalleesDefinitions;
	}

	public List<Function> getFunctionCalleesUses() {
		return functionCalleesUses;
	}

	public void setFunctionCalleesUses(List<Function> functionCalleesUses) {
		this.functionCalleesUses = functionCalleesUses;
	}
	
	
}
