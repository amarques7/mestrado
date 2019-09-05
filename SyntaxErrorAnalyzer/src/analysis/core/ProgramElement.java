package analysis.core;

import java.util.HashSet;
import java.util.Set;

import analysis.ID;
import de.fosd.typechef.featureexpr.FeatureExpr;
import de.fosd.typechef.parser.Position;
import tree.Opt;

public class ProgramElement{
	public String file;
	private String type, qualifier, specifier, modifier;
	private String name;
	private int weight;
	public ID id;
	public Set<Opt> directives = new HashSet<Opt>();
	private Position positionTo, positionFrom;
	
	private FeatureExpr presenceCondition;
	
	public ProgramElement() {
		this.weight = 1;
	} 
	
	public void setWeight() {
		weight++;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getSpecifier() {
		return specifier;
	}

	public void setSpecifier(String specifier) {
		this.specifier = specifier;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Opt> getDirectives() {
		return directives;
	}

	public void setDirectives(Set<Opt> directives) {
		this.directives = directives;
	}
	
	public void setFile(String file){
		this.file = file;
	}

	public Position getPositionFrom() {
		return positionFrom;
	}

	public void setPositionFrom(Position positionFrom) {
		this.positionFrom = positionFrom;
	}

	public Position getPositionTo() {
		return positionTo;
	}

	public void setPositionTo(Position positionTo) {
		this.positionTo = positionTo;
	}

	public FeatureExpr getPresenceCondition() {
		return presenceCondition;
	}

	public void setPresenceCondition(FeatureExpr presenceCondition) {
		this.presenceCondition = presenceCondition;
	}

}
