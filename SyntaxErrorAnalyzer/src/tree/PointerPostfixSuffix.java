package tree;

import tree.visitor.Visitor;

public class PointerPostfixSuffix extends Node {

	private String type;
	
	
	
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

}
