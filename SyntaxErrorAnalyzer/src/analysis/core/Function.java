package analysis.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tree.AtomicNamedDeclarator;
import tree.FunctionCall;
import tree.FunctionDef;
import tree.Id;
import tree.Opt;

public class Function extends ProgramElement{
	private FunctionDef functionDef;
	private Set<Id> localVariables = new HashSet<Id>();
	private List<Variable> parameters = new ArrayList<Variable>();;
	private List<FunctionCall> functionCalls = new ArrayList<FunctionCall>();
	private List<Variable> globalReferences = new ArrayList<Variable>();
	private List<ProgramElement> callForSingleElements = new ArrayList<ProgramElement>();

	private Set<Opt> opts = new HashSet<Opt>();

	public Function(Id function){
		setName(function.getName());
		setModifier(function.getModifier());
		setQualifier(function.getQualifier());
		setSpecifier(function.getSpecifier());
		setType(function.getType());
		setPresenceCondition(function.getPresenceCondition());
		setPositionTo(function.getPositionTo());
		setPositionFrom(function.getPositionFrom());
		setFile(getPositionFrom().toString().split(" ")[1]);
	}
	
	public Function(FunctionDef functionDef, List<Id> parameters) {
		this.functionDef = functionDef;
		
		if(parameters != null)
			for(Id id : parameters){
				this.parameters.add(new Variable(id));
			}
		
		for (int i = 0; i < functionDef.getChildren().size(); i++) {
			if (functionDef.getChildren().get(i) instanceof AtomicNamedDeclarator) {
				for (int j = 0; j < functionDef.getChildren().get(i).getChildren().size(); j++) {
					if (functionDef.getChildren().get(i).getChildren().get(j) instanceof Id) {
						setName(((Id) functionDef.getChildren().get(i).getChildren().get(j)).getName());
						setType(((Id) functionDef.getChildren().get(i).getChildren().get(j)).getType());
						setModifier(((Id) functionDef.getChildren().get(i).getChildren().get(j)).getModifier());
						setSpecifier(((Id) functionDef.getChildren().get(i).getChildren().get(j)).getSpecifier());
						setQualifier(((Id) functionDef.getChildren().get(i).getChildren().get(j)).getQualifier());
						setPresenceCondition(functionDef.getPresenceCondition());
						setPositionTo(functionDef.getPositionTo());
						setPositionFrom(functionDef.getPositionFrom());
						setFile(getPositionFrom().toString().split(" ")[1].split(":")[1]);
					}
				}
			}
		}
	}
	

	
	public Function(FunctionDef functionDef) {
		this.functionDef = functionDef;
		
		for (int i = 0; i < functionDef.getChildren().size(); i++) {
			if (functionDef.getChildren().get(i) instanceof AtomicNamedDeclarator) {
				for (int j = 0; j < functionDef.getChildren().get(i).getChildren().size(); j++) {
					if (functionDef.getChildren().get(i).getChildren().get(j) instanceof Id) {
						setName( ((Id) functionDef.getChildren().get(i).getChildren().get(j)).getName());
					}
				}
			}
		}
	}

	public List<FunctionCall> getFunctionCalls() {
		return functionCalls;
	}
	
	public FunctionDef getFunctionDef() {
		return functionDef;
	}

	public void setFunctionDef(FunctionDef functionDef) {
		this.functionDef = functionDef;
	}

	public List<Variable> getGlobalReferences(){
		return this.globalReferences;
	}
	
	public Set<Id> getLocalVariables() {
		return localVariables;
	}

	public void setLocalVariables(Set<Id> localVariables) {
		this.localVariables = localVariables;
	}
	
	public void setLocalVariables(List<Id> localVariables) {
		this.localVariables = new HashSet<Id>(localVariables);
	}

	public Set<Opt> getDirectives() {
		return directives;
	}

	public void setDirectives(Set<Opt> directives) {
		this.directives = directives;
	}
	
	public Set<Opt> getOpts() {
		return opts;
	}

	public void setOpts(Set<Opt> opts) {
		this.opts = opts;
	}
	
	public List<Variable> getParameters() {
		return parameters;
	}

	public void setParameters(List<Variable> parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public void setModifier(String modifier) {
		super.setModifier(modifier);
	}

	public List<ProgramElement> getCallForSingleElements() {
		return callForSingleElements;
	}

	public void setCallForSingleElements(List<ProgramElement> callForSingleElements) {
		this.callForSingleElements = callForSingleElements;
	}
	
	public void getNameWithParameters() {
		
	}


}
