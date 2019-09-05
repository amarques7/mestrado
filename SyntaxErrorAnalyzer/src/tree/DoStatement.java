package tree;

import tree.visitor.Visitor;

public class DoStatement extends Node {

	private String expression;
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.run(this);
	}

}
