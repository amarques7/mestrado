package analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import analysis.core.Function;
import analysis.core.ProgramElement;
import analysis.core.Variable;

public class Dependency {
	
	private Variability variability1, variability2;
	private List<Link> links = new ArrayList<Link>();
	public List<Link> distinctLinks = new ArrayList<Link>();
	public List<Link> uniqueLinks = new ArrayList<Link>();
	
	public int numberOfTimesChanged;
	public int numberOfUniqueLinks;
	public int numberOfDistinctLinks;
	
	private List<Variable> variablesDeclarated;
	private List<Function> functionsDeclarated;
	
	private List<Variable> variablesUsed;
	private List<Function> functionsUsed;
	
	private Set<ProgramElement> functionsChanged;
	private Set<ProgramElement> variablesChanged;
	
	public List<ProgramElement> callers;
	public List<ProgramElement> callees;

	public Dependency(Variability var1, Variability var2){

		variablesDeclarated = new ArrayList<Variable>();
		functionsDeclarated = new ArrayList<Function>();
		
		variablesUsed = new ArrayList<Variable>();
		functionsUsed = new ArrayList<Function>();
		
		functionsChanged = new HashSet<ProgramElement>(functionsDeclarated.size());
		variablesChanged = new HashSet<ProgramElement>(variablesDeclarated.size());
		
		
		callers = new ArrayList<ProgramElement>();
		callees = new ArrayList<ProgramElement>();
		
		this.setVariabilityA(var1);
		this.setVariabilityB(var2);
		
	}
	
	public void updateProps(){
		for(Link link : links){
			this.callers.add(link.getCaller());
			this.callees.add(link.getCallee());
			
			if(link.getCaller() instanceof Variable)
				this.variablesUsed.add((Variable)link.getCaller());
			else
				this.functionsUsed.add((Function) link.getCaller());
			
			if(link.getCallee() instanceof Variable)
				this.variablesDeclarated.add((Variable) link.getCallee());
			else
				this.functionsDeclarated.add((Function) link.getCallee());
		}
	}
	
	public List<Link> getLinksDistintos(){
		for(Link link: this.links) {
			if(!distinctLinks.contains(link)) {
				distinctLinks.add(link);
			}
		}
		return distinctLinks;
	}
	
	public void setDistinctAndUniqueLinks(){
		for(Link link : links){
			if(!distinctLinks.contains(link)){
				distinctLinks.add(link);
			}
			int weight = 0;
			for(Link linkToCompare : links){
				if(linkToCompare.equals(link)){
					weight++;
				}
			}
			link.setWeight(weight);
			
			if(link.getWeight() == 1){
				uniqueLinks.add(link);
				link.setUnique(true);
			}
		}
		
		numberOfUniqueLinks = uniqueLinks.size();
		numberOfDistinctLinks = distinctLinks.size();
	}
	
	public int getNumberOfPE(){
		return variablesDeclarated.size() + functionsDeclarated.size();
	}
	
	public int getNumberOfVarDeclarated(){
		int count = 0;
		Set<ProgramElement> counted = new HashSet<ProgramElement>(variablesDeclarated.size());
		for (Variable v : variablesDeclarated) {
			if (!isContainPE(counted, v)) {
				count++;
				counted.add(v);
			}
		}
		return count;
	}
	
	public int getNumberOfFuncDeclarated(){
		int count = 0;
		Set<ProgramElement> counted = new HashSet<ProgramElement>(functionsDeclarated.size());
		for (Function f :functionsDeclarated) {
			if (!isContainPE(counted, f)) {
				count++;
				counted.add(f);
			}
		}
		return count;
	}
	
	public Set<ProgramElement> getFuncDeclarated(){
		Set<ProgramElement> counted = new HashSet<ProgramElement>(functionsDeclarated.size());
		for (Function f :functionsDeclarated) {
			if (!isContainPE(counted, f)) {
				counted.add(f);
			}
		}
		return counted;
	}

	
	
	public int getNumberOfVarUsed(){
		int count = 0;
		Set<ProgramElement> counted = new HashSet<ProgramElement>(variablesUsed.size());
		for (Variable v : variablesUsed) {
			if (!isContainPE(counted, v)) {
				count++;
				counted.add(v);
			}
		}
		return count;
	}
	
	public int getNumberOfFuncUsed(){
		int count = 0;
		Set<ProgramElement> counted = new HashSet<ProgramElement>(functionsUsed.size());
		for (Function f :functionsUsed) {
			if (!isContainPE(counted, f)) {
				count++;
				counted.add(f);
			}
		}
		return count;
	}
	public Variability getVariabilityA() {
		return variability1;
	}

	public void setVariabilityA(Variability variability1) {
		this.variability1 = variability1;
	}

	public Variability getVariabilityB() {
		return variability2;
	}

	public void setVariabilityB(Variability variability2) {
		this.variability2 = variability2;
	}
	
	public List<Link> getLinks(){
		return this.links;
	}
	
	public Set<ProgramElement> calc_pe_weight() {
		Set<ProgramElement> programElement = new HashSet<ProgramElement>(1000);
			for (Link link: getLinksDistintos()) {
				if (isContainPE(programElement,link.getCallee())) {
					link.getCallee().setWeight();
				}
				if (!isContainPE(programElement,link.getCallee())) {
					programElement.add(link.getCallee());
				}
			}
			return programElement;
	}
	
	public void addPeChanged(ProgramElement pe){
		if (pe.id == ID.Function) {
			if (!isContainPE(functionsChanged, pe)) {
				functionsChanged.add(pe);
			}
		}
				
		if (pe.id == ID.Variable) {
			if (!isContainPE(variablesChanged, pe)) {
				variablesChanged.add(pe);
			}
		}
	}
	
	
	public Set<ProgramElement> getFuncChanged() {
		return functionsChanged;
	}
	
	public Set<ProgramElement> getVarChanged() {
		return variablesChanged;
	}
	
	
	
	
	
	public boolean isContainPE(Set<ProgramElement> list, ProgramElement pe) {
		for(ProgramElement p: list) {
			if (p.getName().equals(pe.getName()))
				return true;
		}
		return false;
	}

}
