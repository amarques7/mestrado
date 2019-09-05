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

public interface Visitor {

	public void run (Choice node);
	
	public void run (AtomicNamedDeclarator node);
	
	public void run (ElifStatement node);
	
	public void run (CompoundStatement node);

	public void run (DeclIdentifierList node);
	
	public void run (TranslationUnit node);
	
	public void run (ExprList node);
	
	public void run (DeclParameterDeclList node);
	
	public void run (ParameterDeclarationD node);
	
	public void run (StructDeclaration node);
	
	public void run (StructDeclarator node);
	
	public void run (AtomicAbstractDeclarator node);
	
	public void run (Pointer node);
	
	public void run (ParameterDeclarationAD node);
	
	public void run (FunctionDef node);
	
	public void run (Opt node);
	
	public void run (Initializer node);
	
	public void run (InitDeclaratorI node);
	
	public void run (TypeName node);
	
	public void run (One node);
	
	public void run (Some node);
	
	public void run (SimplePostfixSuffix node);
	
	public void run (PostfixExpr node);
	
	public void run (AssignExpr node);
	
	public void run (IfStatement node);
	
	public void run (WhileStatement node);
	
	public void run (SizeOfExprT node);
	
	public void run (SizeOfExprU node);
	
	public void run (NestedNamedDeclarator node);
	
	public void run (FunctionCall node);
	
	public void run (ExprStatement node);
	
	public void run (TypeDefTypeSpecifier node);
	
	public void run (DeclArrayAccess node);
	
	public void run (ForStatement node);
	
	public void run (NAryExpr node);
	
	public void run (NArySubExpr node);
	
	public void run (DoStatement node);
	
	public void run (CaseStatement node);
	
	public void run (SwitchStatement node);
	
	public void run (DefaultStatement node);
	
	public void run (DeclarationStatement node);
	
	public void run (Declaration node);
	
	public void run (Constant node);
	
	public void run (Id node);
	
	public void run (VoidSpecifier node);
	
	public void run (IntSpecifier node);
	
	public void run (DoubleSpecifier node);
	
	public void run (UnsignedSpecifier node);
	
	public void run (VolatileSpecifier node);
	
	public void run (ConstSpecifier node);
	
	public void run (ExternSpecifier node);
	
	public void run (TypedefSpecifier node);
	
	public void run (AutoSpecifier node);
	
	public void run (BreakStatement node);
	
	public void run (CharSpecifier node);
	
	public void run (VarArgs node);
	
	public void run (PointerPostfixSuffix node);
	
	public void run (PointerDerefExpr node);
	
	public void run (UnaryExpr node);
	
	public void run (ContinueStatement node);
	
	public void run (RegisterSpecifier node);
	
	public void run (StaticSpecifier node);
	
	public void run (FloatSpecifier node);
	
	public void run (ReturnStatement node);
	
	public void run (ShortSpecifier node);
	
	public void run (LongSpecifier node);
	
	public void run (StructOrUnionSpecifier node);
	
	public void run (PointerCreationExpr node);
	
	public void run (UnaryOpExpr node);
	
	public void run (ArrayAccess node);
	
	public void run (LcurlyInitializer node);
	
	public void run (StringLit node);
	
	public void run (ConditionalExpr node);
	
	public void run (DefineDirective node);
	
	public void run (EnumSpecifier node);
	
	public void run (Enumerator node);
	
}
