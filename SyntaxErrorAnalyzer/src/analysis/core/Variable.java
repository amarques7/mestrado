package analysis.core;

import tree.Id;

public class Variable extends ProgramElement{
	
	// if this context is null in run time it means it is a global variable
	public Function context;
	
	public Variable(Id variable){
		setName(variable.getName());
		setModifier(variable.getModifier());
		setQualifier(variable.getQualifier());
		setSpecifier(variable.getSpecifier());
		setType(variable.getType());
		setPresenceCondition(variable.getPresenceCondition());
		setPositionTo(variable.getPositionTo());
		setPositionFrom(variable.getPositionFrom());
		try {
			setFile(getPositionFrom().toString().split(" ")[1].split(":")[1]);
		} catch (Exception e) {
			// TODO: handle exception
			setFile(getPositionFrom().toString().split(" ")[1].split(":")[0]);
		}
		
	}
	
	public Variable(Id variable, Function function){
		setContext(function);
		setName(variable.getName());
		setModifier(variable.getModifier());
		setQualifier(variable.getQualifier());
		setSpecifier(variable.getSpecifier());
		setType(variable.getType());
		setPresenceCondition(variable.getPresenceCondition());
		setPositionTo(variable.getPositionTo());
		setPositionFrom(variable.getPositionFrom());
		try {
			setFile(getPositionFrom().toString().split(" ")[1].split(":")[1]);
		} catch (Exception e) {
			// TODO: handle exception
			setFile(getPositionFrom().toString().split(" ")[1].split(":")[0]);
		}
		
	}
	
	public void setContext(Function context){
		this.context = context;
	}
	
	public Function getContext(){
		return context;
	}
}
