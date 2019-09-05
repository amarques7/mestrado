package analysis;

import java.io.File;

import analysis.core.Function;
import analysis.core.ProgramElement;
import analysis.core.Variable;

public class Link {
	public ProgramElement caller;
	public ProgramElement callee;
	
	private Function context;
	
	private int weight = 0;
	private boolean unique = false;
	
	public Link(ProgramElement caller, ProgramElement callee, Function context){
		this.caller = caller;
		this.callee = callee;
		this.context = context;
		if (this.caller instanceof Function)
			this.caller.id = ID.Function;
		
		if (this.caller instanceof Variable)
			this.caller.id = ID.Variable;
		
		if (this.callee instanceof Function)
			this.callee.id = ID.Function;
		
		if (callee instanceof Variable)
			this.callee.id = ID.Variable;
	}
	
	public ProgramElement getCaller() {
		return caller;	
	}
	
	public ProgramElement getCallee() {
		return callee;
	}
	
	public boolean isUnique(){
		return unique;
	}
	
	public void setUnique(boolean value){
		this.unique = value;
	}
	
  @Override
   public boolean equals(Object o) {
      boolean result = false;
      if ((this.getCaller().getName()).equals(((Link)o).getCaller().getName())) {
    	  if((this.getCallee()).getName().equals(((Link)o).getCallee().getName())) {
    		  result = true;
    	  }
      }
      return result;
   }

	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Function getContext() {
		return context;
	}

	public void setContext(Function context) {
		this.context = context;
	}   
	
	@Override
	public String toString(){
		String toReturn = "";
		toReturn = printingFirsPart(this.caller);
		
		if(callee instanceof Variable){
			String typeVar = "";
			if(((Variable) callee).getContext() != null)
				typeVar = "(local) ";
			
			String varName = getElements(caller) + callee.getName();
			
			toReturn = toReturn + varName + " " + typeVar + callee.getPresenceCondition() + " | weight: " + this.weight
					+ " | " + new File(context.file).getName() + ":" + context.getName();
		}
		else{
			Function function = ((Function)callee);
			String functionName = getElements(callee) +  function.getName();
			String params = "";
			
			boolean in = false;
			
			for(Variable param : function.getParameters()) {
				params = params + getElements(param) + param.getName() + ",";
				in = true;
			}
			if(in)
				params = params.substring(0, params.length()-1);
			toReturn = toReturn + functionName + "(" + params + ")" + " " + callee.getPresenceCondition() + " | weight: " + this.weight
					+ " | " + new File(context.file).getName() + ":" + context.getName();
		}
		return toReturn;
	}
	
	private String printingFirsPart(ProgramElement caller){

		String toReturn = "";
		if(caller instanceof Variable){
			
			String varName = getElements(caller) +  caller.getName();
			
			String typeVar = "";
			if(((Variable) caller).getContext() != null){
				typeVar = "(local) ";
			}
			toReturn = varName + " " + typeVar + caller.getPresenceCondition().toString() + " and ";
		}
		else{
			
			toReturn = getElements(caller) + caller.getName() + "(";
			
			boolean in = false;
			
			for(Variable parameter : ((Function) caller).getParameters()){
				in = true;
				toReturn = toReturn + getElements(parameter) + parameter.getName() + ",";
			}
			if(in)
				toReturn = toReturn.substring(0, toReturn.length()-1);
			toReturn = toReturn + ") " + caller.getPresenceCondition() + " and ";
		}
		return toReturn;
	}
	
	private String getElements(ProgramElement pe) {
		String qualifier = "-", specifier = "-", modifier = "-", type = "-";
		
		if(pe.getQualifier() != "")
			qualifier = caller.getQualifier();
		if(pe.getSpecifier() != "")
			specifier = caller.getSpecifier();
		if(pe.getModifier() != "")
			modifier = caller.getModifier();
		if(pe.getType() != "")
			type = caller.getType();
		
		String toReturn = qualifier + " " + specifier + " " + modifier + " " + type + " ";
		return toReturn;
	}
}
