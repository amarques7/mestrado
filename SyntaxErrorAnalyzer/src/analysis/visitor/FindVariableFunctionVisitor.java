package analysis.visitor;

import java.util.ArrayList;
import java.util.List;

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
import tree.visitor.Visitor;

public class FindVariableFunctionVisitor implements Visitor {
	
	private FunctionDef functionDef;
	private List<CompoundStatement> scope = new ArrayList<CompoundStatement>();

	public FunctionDef getFunctionDef() {
		return functionDef;
	}

	public List<CompoundStatement> getScope() {
		return scope;
	}

	@Override
	public void run(Choice node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(AtomicNamedDeclarator node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ElifStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(CompoundStatement node) {
		this.scope.add(node);
		node.getParent().accept(this);

	}

	@Override
	public void run(DeclIdentifierList node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(TranslationUnit node) {
		
	}

	@Override
	public void run(ExprList node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(DeclParameterDeclList node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ParameterDeclarationD node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(StructDeclaration node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(StructDeclarator node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(AtomicAbstractDeclarator node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(Pointer node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ParameterDeclarationAD node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(FunctionDef node) {
		this.functionDef = node;
	}

	@Override
	public void run(Opt node) {
		node.getParent().accept(this);
	}

	@Override
	public void run(Initializer node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(InitDeclaratorI node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(TypeName node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(One node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(Some node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(SimplePostfixSuffix node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(PostfixExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(AssignExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(IfStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(WhileStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(SizeOfExprT node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(SizeOfExprU node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(NestedNamedDeclarator node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(FunctionCall node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ExprStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(TypeDefTypeSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(DeclArrayAccess node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ForStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(NAryExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(NArySubExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(DoStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(CaseStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(SwitchStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(DefaultStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(DeclarationStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(Declaration node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(Constant node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(Id node) {
		node.getParent().accept(this);
	}

	@Override
	public void run(VoidSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(IntSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(DoubleSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(UnsignedSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(VolatileSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ConstSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ExternSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(TypedefSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(AutoSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(BreakStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(CharSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(VarArgs node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(PointerPostfixSuffix node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(PointerDerefExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(UnaryExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ContinueStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(RegisterSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(StaticSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(FloatSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ReturnStatement node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ShortSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(LongSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(StructOrUnionSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(PointerCreationExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(UnaryOpExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ArrayAccess node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(LcurlyInitializer node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(StringLit node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(ConditionalExpr node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(DefineDirective node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(EnumSpecifier node) {
		node.getParent().accept(this);
		
	}

	@Override
	public void run(Enumerator node) {
		node.getParent().accept(this);
		
	}
	
}
