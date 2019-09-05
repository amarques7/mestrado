package tree;

import tree.visitor.Visitor;

public class Pointer extends Node {

	@Override
	public void accept(Visitor visitor) {
		visitor.run(this);
	}

}
