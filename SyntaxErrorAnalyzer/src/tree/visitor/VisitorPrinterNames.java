package tree.visitor;

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

public class VisitorPrinterNames implements Visitor{

	@Override
	public void run(Choice node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}

	@Override
	public void run(AtomicNamedDeclarator node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ElifStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(CompoundStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DeclIdentifierList node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TranslationUnit node) {
		System.out.println(node.getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ExprList node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DeclParameterDeclList node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ParameterDeclarationD node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StructDeclaration node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StructDeclarator node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(AtomicAbstractDeclarator node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Pointer node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ParameterDeclarationAD node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(FunctionDef node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Opt node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName() + " : " + node.getConditional());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Initializer node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(InitDeclaratorI node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TypeName node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(One node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Some node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(SimplePostfixSuffix node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(PostfixExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(AssignExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(IfStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(WhileStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(SizeOfExprT node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(SizeOfExprU node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(NestedNamedDeclarator node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(FunctionCall node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ExprStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TypeDefTypeSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DeclArrayAccess node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ForStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(NAryExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(NArySubExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DoStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(CaseStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(SwitchStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DefaultStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DeclarationStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Declaration node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Constant node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Id node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName() + " : " + node.getName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(VoidSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(IntSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DoubleSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(UnsignedSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(VolatileSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ConstSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ExternSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TypedefSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(AutoSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(BreakStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(CharSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(VarArgs node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(PointerPostfixSuffix node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(PointerDerefExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(UnaryExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ContinueStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(RegisterSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StaticSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(FloatSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ReturnStatement node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ShortSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(LongSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StructOrUnionSpecifier node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(PointerCreationExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(UnaryOpExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(ArrayAccess node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(LcurlyInitializer node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}

	@Override
	public void run(StringLit node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(ConditionalExpr node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(DefineDirective node) {
		System.out.println(node.getClass().getCanonicalName() + " - " + node.getParent().getClass().getCanonicalName());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(EnumSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(Enumerator node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
}
