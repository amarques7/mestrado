package tree;

import tree.visitor.Visitor;

public class UnaryOpExpr extends Node{

	private String kind;
	
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}



	@Override
	public void accept(Visitor visitor) {
		visitor.run(this);
	}

}
