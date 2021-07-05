package analysis.visitor;

import tree.ArrayAccess;
import tree.AssignExpr;
import tree.AtomicAbstractDeclarator;
import tree.AtomicNamedDeclarator;
import tree.AutoSpecifier;
import tree.BreakStatement;
import tree.CaseStatement;
import tree.CharSpecifier;
import tree.Choice;
import tree.CompoundStatement;
import tree.ConditionalExpr;
import tree.ConstSpecifier;
import tree.Constant;
import tree.ContinueStatement;
import tree.DeclArrayAccess;
import tree.DeclIdentifierList;
import tree.DeclParameterDeclList;
import tree.Declaration;
import tree.DeclarationStatement;
import tree.DefaultStatement;
import tree.DefineDirective;
import tree.DoStatement;
import tree.DoubleSpecifier;
import tree.ElifStatement;
import tree.EnumSpecifier;
import tree.Enumerator;
import tree.ExprList;
import tree.ExprStatement;
import tree.ExternSpecifier;
import tree.FloatSpecifier;
import tree.ForStatement;
import tree.FunctionCall;
import tree.FunctionDef;
import tree.Id;
import tree.IfStatement;
import tree.InitDeclaratorI;
import tree.Initializer;
import tree.IntSpecifier;
import tree.LcurlyInitializer;
import tree.LongSpecifier;
import tree.NAryExpr;
import tree.NArySubExpr;
import tree.NestedNamedDeclarator;
import tree.Node;
import tree.One;
import tree.Opt;
import tree.ParameterDeclarationAD;
import tree.ParameterDeclarationD;
import tree.Pointer;
import tree.PointerCreationExpr;
import tree.PointerDerefExpr;
import tree.PointerPostfixSuffix;
import tree.PostfixExpr;
import tree.RegisterSpecifier;
import tree.ReturnStatement;
import tree.ShortSpecifier;
import tree.SimplePostfixSuffix;
import tree.SizeOfExprT;
import tree.SizeOfExprU;
import tree.Some;
import tree.StaticSpecifier;
import tree.StringLit;
import tree.StructDeclaration;
import tree.StructDeclarator;
import tree.StructOrUnionSpecifier;
import tree.SwitchStatement;
import tree.TranslationUnit;
import tree.TypeDefTypeSpecifier;
import tree.TypeName;
import tree.TypedefSpecifier;
import tree.UnaryExpr;
import tree.UnaryOpExpr;
import tree.UnsignedSpecifier;
import tree.VarArgs;
import tree.VoidSpecifier;
import tree.VolatileSpecifier;
import tree.WhileStatement;
import tree.visitor.Visitor;
import de.fosd.typechef.featureexpr.FeatureExpr;

public class PresenceConditionVisitor implements Visitor {

	@Override
	public void run(Choice node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}
	}

	@Override
	public void run(AtomicNamedDeclarator node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ElifStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(CompoundStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DeclIdentifierList node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();

				} else {
					fexpr = fexpr.and(opt.getConditional());

				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(TranslationUnit node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ExprList node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DeclParameterDeclList node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ParameterDeclarationD node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(StructDeclaration node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(StructDeclarator node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(AtomicAbstractDeclarator node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Pointer node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ParameterDeclarationAD node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(FunctionDef node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Opt node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
			
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Initializer node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(InitDeclaratorI node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(TypeName node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(One node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Some node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(SimplePostfixSuffix node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(PostfixExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(AssignExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(IfStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(WhileStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(SizeOfExprT node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(SizeOfExprU node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(NestedNamedDeclarator node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(FunctionCall node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();

				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ExprStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);

		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(TypeDefTypeSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DeclArrayAccess node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ForStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(NAryExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(NArySubExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DoStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(CaseStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(SwitchStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DefaultStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DeclarationStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Declaration node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Constant node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Id node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(VoidSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(IntSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DoubleSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(UnsignedSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(VolatileSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ConstSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ExternSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(TypedefSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(AutoSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(BreakStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(CharSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(VarArgs node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(PointerPostfixSuffix node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(PointerDerefExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(UnaryExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ContinueStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(RegisterSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(StaticSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(FloatSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ReturnStatement node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ShortSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(LongSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(StructOrUnionSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(PointerCreationExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(UnaryOpExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ArrayAccess node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(LcurlyInitializer node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(StringLit node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(ConditionalExpr node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(DefineDirective node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(EnumSpecifier node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

	@Override
	public void run(Enumerator node) {
		FeatureExpr fexpr = node.getPresenceCondition();
		Node parent = node.getParent();
		while (parent != null) {
			if (parent instanceof Opt) {
				Opt opt = (Opt) parent;

				if (fexpr == null) {
					fexpr = opt.getConditional();
				} else {
					fexpr = fexpr.and(opt.getConditional());
				}

			}
			parent = parent.getParent();
		}
		node.setPresenceCondition(fexpr);
		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().get(i).accept(this);
		}

	}

}
