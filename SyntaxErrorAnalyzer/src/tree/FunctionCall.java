package tree;

import analysis.core.Function;
import tree.visitor.Visitor;

public class FunctionCall extends Node {
	
	private FunctionDef context;

	public FunctionDef getContext() {
		return context;
	}

	public void setContext(FunctionDef context) {
		this.context = context;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.run(this);
	}
	
}
