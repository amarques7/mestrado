package tree;

import tree.visitor.Visitor;

public class StructOrUnionSpecifier extends Node {

	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.run(this);
	}

}
