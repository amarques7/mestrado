package tree;

import tree.visitor.Visitor;

public class StringLit extends Node{

	private String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.run(this);
	}

}
