package tree;

import tree.visitor.Visitor;

public class NArySubExpr extends Node {

	private String operator;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.run(this);
	}

}
