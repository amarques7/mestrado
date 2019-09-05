package tree;

import tree.visitor.Visitor;

public class Id extends Node {

	private String name;
	private String type, qualifier, specifier, modifier;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualificator) {
		this.qualifier = qualificator;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public void accept(Visitor visitor) {
        visitor.run(this);    
    }
	
	@Override
	public boolean equals(Object obj) {
		if (this.getClass().getCanonicalName().equals(obj.getClass().getCanonicalName())){
			Node objNod = (Node) obj;
			
			if (objNod instanceof Id){
				if (this instanceof Id){
					if ( !((Id)objNod).getName().equals(this.getName()) ){
						return false;
					}
				}
			}
			
		} else {
			return false;
		}
		return true;
	}
}
