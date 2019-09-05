package tree;

import java.util.ArrayList;
import java.util.List;

import de.fosd.typechef.featureexpr.FeatureExpr;
import de.fosd.typechef.parser.Position;
import tree.visitor.Visitor;

public abstract class Node {

	
	private List<Node> children = new ArrayList<Node>();
	private Node parent;
	private FeatureExpr presenceCondition;
	private Position positionFrom;
	private Position positionTo;
	
	public Node() {

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
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public void addChild(Node node){
		this.children.add(node);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this.getClass().getCanonicalName().equals(obj.getClass().getCanonicalName())){
			Node objNod = (Node) obj;
			if (objNod.getChildren().size() != this.children.size()){
				return false;
			}
			for (int i = 0; i < this.children.size(); i++){
				if (!this.children.get(i).equals(objNod.getChildren().get(i))){
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}
	
	public abstract void accept (Visitor visitor);
}
