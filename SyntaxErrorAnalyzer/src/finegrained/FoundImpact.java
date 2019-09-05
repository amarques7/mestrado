package finegrained;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import analysis.Dependency;
import analysis.ID;
import analysis.Link;
import analysis.Variability;
import analysis.core.Function;
import analysis.core.ProgramElement;
import analysis.core.Variable;
import metrics.Metrics;
import tree.Id;
import tree.Opt;

public class FoundImpact extends FoundChanges {
	
	private Set<Impact> impactSetVariability = new HashSet<Impact>(Metrics.numberOfVariabilities);
	private Set<ProgramElement> impactSetProgramElement = new HashSet<ProgramElement>(Metrics.numberOfVariabilities);
	private Set<List<Impact>> impactsLists = new HashSet<List<Impact>>(Metrics.numberOfVariabilities);
	private Set<Variability> impactPossibleSetVariability  = new HashSet<Variability>(Metrics.numberOfVariabilities);
	
	
	private Set<Variability> generalImpactVariability = new HashSet<Variability>(Metrics.numberOfVariabilities);
	private Set<ProgramElement> elementsAnalysed = new HashSet<ProgramElement>(10000);
	
	
	private int impactPoints; 
	private int impactFunctions;
	private int impactVariables;
	
	
	
	
	FoundImpact(){
		this.impactPoints = 0;
		this.impactFunctions = 0;
		this.impactVariables = 0;
		
		
	}
	

	public void addImpact(List<Impact> impactList) {
		this.impactsLists.add(impactList);
		
	}
	
	public Set<List<Impact>> getAllImpact() {
		return impactsLists;
	}
	
	public void addPossibleImpactVariability(Variability variability) {
		this.impactPossibleSetVariability.add(variability);
	}
	
	public void addImpactVariability(Impact variability) {
		this.impactSetVariability.add(variability);
	}
	
	public void addImpactProgramElement(ProgramElement pe) {
		this.impactSetProgramElement.add(pe);
	}
	
	
	public Set<Impact> getImpactVariability() {
		return impactSetVariability;
	}
	
	public Set<ProgramElement> getImpactProgramElement() {
		return impactSetProgramElement;
	}
	

	
	public void addImpactPoints(ProgramElement pe) {
		this.impactPoints += 1;
	}
	
	public void addGeneralVariabilityImpact(Variability variability) {
		this.generalImpactVariability.add(variability);
		
	}
	
	public Set<Variability> getGeneralVariability(){
		return generalImpactVariability;
	}
	

	
	public Boolean compareVariabilities(Variability var, Set<Variability> variabilitiesPrevious ) {
		for(Variability varPrevious: variabilitiesPrevious) {
			if(varPrevious.getName().equals(var.getName())) {
				return isVariabilityChanged(varPrevious, var);
			}
			
		}
		return false;
	}
	
	public Boolean compareProgramElement(ProgramElement pe, Variability var, Set<Variability> variabilitiesPrevious) {
		Boolean impact = false;
		for(Variability varPrevious: variabilitiesPrevious) {
			if(varPrevious.getName().equals(var.getName())) {
				
				if (!verifiedElement(pe)) {
					this.elementsAnalysed.add(pe);
					this.addImpactProgramElement(pe);
					if(pe.id == ID.Variable) {
						impact = verifyChangeinVariable((Variable)pe, varPrevious);
					}	
					
					if(pe.id == ID.Function) {
						impact = verifyChangeinFunction((Function)pe, varPrevious);
					}
					
				}
				
			}
			
		}
		return impact;
	} 
	
	public Boolean isVariabilityChanged(Variability variabilityPrevious, Variability variabilityCurrent) {
		Boolean impact = false;
		if(variabilityPrevious.getDeclaredFunctions().size() != variabilityCurrent.getDeclaredFunctions().size() ) {
			impact = true;
			
		}
		
		if(variabilityPrevious.getNumberOfProgramElements() != variabilityCurrent.getNumberOfProgramElements() ) {
			impact = true;
			
		}
		
		if(variabilityPrevious.getUsedFunctions().size() != variabilityCurrent.getUsedFunctions().size() ) {
			impact = true;
		}
		
		if(variabilityPrevious.getNumberOfLocalVariables() != variabilityCurrent.getNumberOfLocalVariables() ) {
			impact = true;
			
		}
		
		for(Function func: variabilityCurrent.getDeclaredFunctions()) {
			if( verifyChangeinFunction(func,variabilityPrevious))
				impact = true;
		}
		
		for(Variable var: variabilityCurrent.getDeclaredGlobalVariables()) {
			if( verifyChangeinVariable(var,variabilityPrevious))
				impact = true;
		}
		
		return impact;
	
	}

	private Boolean verifyChangeinFunction(Function function,Variability variabilityPrevious) {
		Boolean impact = false;

		for(Function func: variabilityPrevious.getDeclaredFunctions()) {
			if(func.getName().equals(function.getName())) {
				if(function.getType() != func.getType())
					return true;
				if(function.getModifier() != func.getModifier())
					return true;
				if(function.getQualifier() != func.getQualifier())
					return true;
				if(function.getSpecifier() != func.getSpecifier())
					return true;
				if(function.getParameters().size() != func.getParameters().size())
					return true;
				
				if(function.getLocalVariables().size() != func.getLocalVariables().size())
					return true;
				
				for(Id localVariable: function.getLocalVariables()) {
					for(Id localVarPrevious: func.getLocalVariables()) {
						if(localVariable.getType() != localVarPrevious.getType())
							return true;
						if(localVariable.getModifier() != localVarPrevious.getModifier())
							return true;
						if(localVariable.getQualifier() != localVarPrevious.getQualifier())
							return true;
						if(localVariable.getSpecifier() != localVarPrevious.getSpecifier())
							return true;
					}
				} 

			}
		}
		
		return impact;
		
	}

	private Boolean verifyChangeinVariable(Variable variable,Variability previousVariability) {
		Boolean impact = false;
		
		for(Variable var: previousVariability.getDeclaredGlobalVariables()) {
			if(var.getName().equals(variable.getName())) {
				if(variable.getType() != var.getType())
					return true;
				if(variable.getModifier() != var.getModifier())
					return true;
				if(variable.getQualifier() != var.getQualifier())
					return true;
				if(variable.getSpecifier() != var.getSpecifier())
					return true;
				
			}
		}
		
		
		
		
		return impact;
		
	}

	public void addImpactFunctions() {
		this.impactFunctions++;
	}

	public void addImpactVariables() {
		this.impactVariables++;
	}
	
	
	
	public Set<Variability> getPossibleImpactVariability() {
		return impactPossibleSetVariability;
	}
	
	
	public int getImpactVariables() {
		return impactVariables;
	}
	
	public int getImpactFunctions() {
		return impactFunctions;
	}
	
	public int getImpactPoints() {
		return impactPoints;
	}
	
	public Boolean verifiedElement(ProgramElement pe) {
		boolean r = false;
		if (this.elementsAnalysed.contains(pe)) 
			r = true;
		
		return r;
	}


}
