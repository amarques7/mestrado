package analysis;

import java.util.ArrayList;
import java.util.List;

import analysis.core.Function;
import analysis.core.Variable;

public class Variability {
	private String name;
	
	private List<Function> declaredFunctions = new ArrayList<Function>();
	private List<Function> usedFunctions = new ArrayList<Function>(); //empty
	
	private List<Variable> declaredGlobalVariables = new ArrayList<Variable>();
	private List<Variable> usedGlobalVariables = new ArrayList<Variable>(); // empty
	
	private List<Variable> declaredLocalVariables = new ArrayList<Variable>();
	
	private List<Link> links = new ArrayList<Link>();
	
	//metrics
	private int numberOfFunctions, numberOfVariables, numberOfChanges, numberOfProgramElements, numberOfLocalVariables;
	public boolean changed = false;
	
	public Variability(String name){
		this.name = name;
	}

	public void updateNumberOfPE(){
		this.numberOfFunctions = declaredFunctions.size();
		this.numberOfVariables = declaredGlobalVariables.size();
		this.numberOfLocalVariables = declaredLocalVariables.size();
		this.numberOfProgramElements = numberOfFunctions + numberOfVariables + numberOfLocalVariables;
	}
	
	public List<Function> getDeclaredFunctions() {
		return declaredFunctions;
	}

	public void setDeclaredFunctions(List<Function> declaredFunctions) {
		this.declaredFunctions = declaredFunctions;
	}

	public List<Function> getUsedFunctions() {
		return usedFunctions;
	}

	public void setUsedFunctions(List<Function> usedFunctions) {
		this.usedFunctions = usedFunctions;
	}

	public List<Variable> getDeclaredGlobalVariables() {
		return declaredGlobalVariables;
	}

	public void setDeclaredGlobalVariables(List<Variable> declaredGlobalVariables) {
		this.declaredGlobalVariables = declaredGlobalVariables;
	}

	public List<Variable> getUsedGlobalVariables() {
		return usedGlobalVariables;
	}

	public void setUsedGlobalVariables(List<Variable> usedGlobalVariables) {
		this.usedGlobalVariables = usedGlobalVariables;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfFunctions() {
		return numberOfFunctions;
	}

	public void setNumberOfFunctions(int numberOfFunctions) {
		this.numberOfFunctions = numberOfFunctions;
	}

	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	
	public int getNumberOfLocalVariables() {
		return numberOfLocalVariables;
	}

	public void setNumberOfVariables(int numberOfVariables) {
		this.numberOfVariables = numberOfVariables;
	}

	public int getNumberOfChanges() {
		return numberOfChanges;
	}

	public void setNumberOfChanges(int numberOfChanges) {
		this.numberOfChanges = numberOfChanges;
	}

	public int getNumberOfProgramElements() {
		return numberOfProgramElements;
	}

	public void setNumberOfProgramElements(int numberOfProgramElements) {
		this.numberOfProgramElements = numberOfProgramElements;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public List<Variable> getDeclaredLocalVariables() {
		return declaredLocalVariables;
	}

	public void setDeclaredLocalVariables(List<Variable> declaredLocalVariables) {
		this.declaredLocalVariables = declaredLocalVariables;
	}
	
}
