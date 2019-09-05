package finegrained;

import java.util.HashSet;
import java.util.Set;

import analysis.Dependency;
import analysis.Variability;
import analysis.core.ProgramElement;
import de.fosd.typechef.parser.Position;

public class Impact {
	
	private ProgramElement programElement;
	private ProgramElement fromProgramElement;
	private String categoryOfChange;
	private Variability variability;
	
	
	public Impact() {}
	
	public void setProgramElement(ProgramElement pe) {
		this.programElement = pe;
	}
	
	public void setChangeCategory(String cat) {
		this.categoryOfChange = cat;
	}
	
	public void setVariability(Variability var) {
		this.variability = var;
	}
	
	public void setFromProgramElement(ProgramElement pe) {
		this.fromProgramElement = pe;
	}
	
	public Variability getVariability() {
		return variability;
	}
	
	public ProgramElement getProgramElement() {
		return programElement;
	}
	
	public String getChangeCategory() {
		return categoryOfChange;
	}
	
	public ProgramElement getFromProgramElement() {
		return fromProgramElement;
	}

}
