package core;

import de.fosd.typechef.conditional.Choice;
import de.fosd.typechef.conditional.One;
import de.fosd.typechef.conditional.Opt;
import de.fosd.typechef.parser.c.ArrayAccess;
import de.fosd.typechef.parser.c.ConditionalExpr;
import de.fosd.typechef.parser.c.LcurlyInitializer;
import de.fosd.typechef.parser.c.UnaryOpExpr;
import de.fosd.typechef.parser.c.VoidSpecifier;
import scala.Product;
import scala.Some;
import tree.AssignExpr;
import tree.AtomicAbstractDeclarator;
import tree.AtomicNamedDeclarator;
import tree.AutoSpecifier;
import tree.BreakStatement;
import tree.CaseStatement;
import tree.CharSpecifier;
import tree.CompoundStatement;
import tree.ConstSpecifier;
import tree.Constant;
import tree.ContinueStatement;
import tree.DeclArrayAccess;
import tree.DeclIdentifierList;
import tree.DeclParameterDeclList;
import tree.Declaration;
import tree.DeclarationStatement;
import tree.DefaultStatement;
import tree.DoStatement;
import tree.DoubleSpecifier;
import tree.ElifStatement;
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
import tree.LongSpecifier;
import tree.NAryExpr;
import tree.NArySubExpr;
import tree.NestedNamedDeclarator;
import tree.Node;
import tree.ParameterDeclarationAD;
import tree.ParameterDeclarationD;
import tree.Pointer;
import tree.PointerDerefExpr;
import tree.PointerPostfixSuffix;
import tree.PostfixExpr;
import tree.RegisterSpecifier;
import tree.ReturnStatement;
import tree.ShortSpecifier;
import tree.SimplePostfixSuffix;
import tree.SizeOfExprT;
import tree.SizeOfExprU;
import tree.StaticSpecifier;
import tree.StringLit;
import tree.StructDeclaration;
import tree.StructDeclarator;
import tree.StructOrUnionSpecifier;
import tree.SwitchStatement;
import tree.TypeDefTypeSpecifier;
import tree.TypeName;
import tree.TypedefSpecifier;
import tree.UnaryExpr;
import tree.UnsignedSpecifier;
import tree.VarArgs;
import tree.VolatileSpecifier;
import tree.WhileStatement;

public class ASTGenerator {
	
	private String specifier = "", qualifier = "", modifier = "", type = "";
	
	// This method receives the translation units from TypeChef and my Translation Unit (tree.TranslationUnit).
	public void generate(Product node, Node parent){
		//try {
			for (int i = 0; i < node.productArity(); i++){
				if (node.productElement(i) instanceof Product){
					Node myNode = this.getNode( (Product) node.productElement(i));
					if (myNode != null && parent != null){
						myNode.setParent(parent);
						parent.addChild(myNode);
						this.generate( (Product) node.productElement(i), myNode);
					} else {
						this.generate( (Product) node.productElement(i), parent);
					}		
				} 
			}
		
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
	@SuppressWarnings("rawtypes")
	public Node getNode(Product node){
		
		if (node instanceof de.fosd.typechef.parser.c.CompoundStatement){
			CompoundStatement myNode = new CompoundStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.CompoundStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.CompoundStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.FunctionDef){
			FunctionDef myNode = new FunctionDef();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.FunctionDef) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.FunctionDef) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.Id){
			Id id = new Id(); 
			
			id.setName(((de.fosd.typechef.parser.c.Id) node).name());
			id.setModifier(modifier);
			id.setQualifier(qualifier);
			id.setSpecifier(specifier);
			id.setType(type);
			modifier = qualifier = specifier = type = "";
			id.setPositionFrom(((de.fosd.typechef.parser.c.Id) node).getPositionFrom());
			id.setPositionTo(((de.fosd.typechef.parser.c.Id) node).getPositionTo());
			return id;
		} else if (node instanceof de.fosd.typechef.parser.c.AtomicNamedDeclarator){
			AtomicNamedDeclarator myNode = new AtomicNamedDeclarator();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.AtomicNamedDeclarator) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.AtomicNamedDeclarator) node).getPositionTo());
			return myNode;
		} else if (node instanceof Opt<?>){
			tree.Opt opt = new tree.Opt();
			opt.setConditional( ( (Opt) node).feature() );
			//TODO
			// ADD POSITIONS
			return opt;
		} else if (node instanceof VoidSpecifier){
			type = "void";
			tree.VoidSpecifier myNode = new tree.VoidSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.VoidSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.VoidSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.DeclIdentifierList){
			DeclIdentifierList myNode = new DeclIdentifierList();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.DeclIdentifierList) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.DeclIdentifierList) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.IntSpecifier){
			type = "int";
			IntSpecifier myNode = new IntSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.IntSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.IntSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.DoubleSpecifier){
			if(modifier.contains("long")){
				modifier = modifier + " double";
			}
			type =  "double";
			DoubleSpecifier myNode = new DoubleSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.DoubleSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.DoubleSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.UnsignedSpecifier){
			modifier = "unsigned";
			UnsignedSpecifier myNode = new UnsignedSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.UnsignedSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.UnsignedSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.VolatileSpecifier){
			qualifier = "volatile";
			VolatileSpecifier myNode = new VolatileSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.VolatileSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.VolatileSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ConstSpecifier){
			qualifier = "const";
			ConstSpecifier myNode = new ConstSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ConstSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ConstSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ExternSpecifier){
			specifier = "extern";
			ExternSpecifier myNode = new ExternSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ExternSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ExternSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.DeclarationStatement){
			DeclarationStatement myNode = new DeclarationStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.DeclarationStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.DeclarationStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.Declaration){
			
			Declaration myNode = new Declaration();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.Declaration) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.Declaration) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.InitDeclaratorI){
			InitDeclaratorI myNode = new InitDeclaratorI();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.InitDeclaratorI) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.InitDeclaratorI) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.Initializer){
			Initializer myNode = new Initializer();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.Initializer) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.Initializer) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.Constant){
			Constant constant = new Constant(); 
			constant.setValue(((de.fosd.typechef.parser.c.Constant) node).value());
			constant.setPositionFrom(((de.fosd.typechef.parser.c.Constant) node).getPositionFrom());
			constant.setPositionTo(((de.fosd.typechef.parser.c.Constant) node).getPositionTo());
			return constant;
		} else if (node instanceof de.fosd.typechef.parser.c.IfStatement){
			IfStatement myNode = new IfStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.IfStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.IfStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.NAryExpr){
			NAryExpr myNode = new NAryExpr();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.NAryExpr) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.NAryExpr) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.NArySubExpr){
			NArySubExpr nArySubExpr = new NArySubExpr();
			nArySubExpr.setOperator( ((de.fosd.typechef.parser.c.NArySubExpr) node).op() );
			nArySubExpr.setPositionFrom(((de.fosd.typechef.parser.c.NArySubExpr) node).getPositionFrom());
			nArySubExpr.setPositionTo(((de.fosd.typechef.parser.c.NArySubExpr) node).getPositionTo());
			return nArySubExpr;
		} else if (node instanceof de.fosd.typechef.parser.c.WhileStatement){
			WhileStatement myNode = new WhileStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.WhileStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.WhileStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ForStatement){
			ForStatement myNode = new ForStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ForStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ForStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.AssignExpr){
			AssignExpr myNode = new AssignExpr();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.AssignExpr) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.AssignExpr) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.PostfixExpr){
			PostfixExpr myNode = new PostfixExpr();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.PostfixExpr) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.PostfixExpr) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.SimplePostfixSuffix){
			SimplePostfixSuffix myNode = new SimplePostfixSuffix();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.SimplePostfixSuffix) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.SimplePostfixSuffix) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.TypedefSpecifier){
			type = "typedef";
			TypedefSpecifier myNode = new TypedefSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.TypedefSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.TypedefSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.AutoSpecifier){
			specifier = "auto";
			AutoSpecifier myNode = new AutoSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.AutoSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.AutoSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.BreakStatement){
			BreakStatement myNode = new BreakStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.BreakStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.BreakStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.CharSpecifier){
			type = "char";
			CharSpecifier myNode = new CharSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.CharSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.CharSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ContinueStatement){
			ContinueStatement myNode = new ContinueStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ContinueStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ContinueStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof One<?>){
			tree.One myNode = new tree.One();
			//TODO
			// ADD POSITIONS
			return myNode;
		} else if (node instanceof Some<?>){
			tree.Some myNode = new tree.Some();
			//TODO
			// ADD POSITIONS
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ElifStatement){
			ElifStatement myNode = new ElifStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ElifStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ElifStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.RegisterSpecifier){
			specifier = "register";
			RegisterSpecifier myNode = new RegisterSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.RegisterSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.RegisterSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.StaticSpecifier){
			specifier = "static";
			StaticSpecifier myNode = new StaticSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.StaticSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.StaticSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ReturnStatement){
			ReturnStatement myNode = new ReturnStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ReturnStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ReturnStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ShortSpecifier){
			modifier = "short";
			ShortSpecifier myNode = new ShortSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ShortSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ShortSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.StructOrUnionSpecifier){
			StructOrUnionSpecifier s = new StructOrUnionSpecifier();
			if ( ((de.fosd.typechef.parser.c.StructOrUnionSpecifier)node).isUnion() ){
				s.setName("union");
			} else {
				s.setName("struct");
			}
			s.setPositionFrom(((de.fosd.typechef.parser.c.StructOrUnionSpecifier) node).getPositionFrom());
			s.setPositionTo(((de.fosd.typechef.parser.c.StructOrUnionSpecifier) node).getPositionTo());
			return s;
		} else if (node instanceof de.fosd.typechef.parser.c.CaseStatement){
			CaseStatement myNode = new CaseStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.CaseStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.CaseStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.DefaultStatement){
			DefaultStatement myNode = new DefaultStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.DefaultStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.DefaultStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.LongSpecifier){
			modifier = "long";
			LongSpecifier myNode = new LongSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.LongSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.LongSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.SwitchStatement){
			SwitchStatement myNode = new SwitchStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.SwitchStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.SwitchStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.DoStatement){
			DoStatement myNode = new DoStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.DoStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.DoStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ExprStatement){
			ExprStatement myNode = new ExprStatement();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ExprStatement) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ExprStatement) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.TypeDefTypeSpecifier){
			type = "typedeftype";
			TypeDefTypeSpecifier myNode = new TypeDefTypeSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.TypeDefTypeSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.TypeDefTypeSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.SizeOfExprT){
			SizeOfExprT myNode = new SizeOfExprT();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.SizeOfExprT) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.SizeOfExprT) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.SizeOfExprU){
			SizeOfExprU myNode = new SizeOfExprU();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.SizeOfExprU) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.SizeOfExprU) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.FunctionCall){
			FunctionCall myNode = new FunctionCall();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.FunctionCall) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.FunctionCall) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ExprList){
			ExprList myNode = new ExprList();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ExprList) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ExprList) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.DeclParameterDeclList){
			
			DeclParameterDeclList myNode = new DeclParameterDeclList();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.DeclParameterDeclList) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.DeclParameterDeclList) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ParameterDeclarationD){
			ParameterDeclarationD myNode = new ParameterDeclarationD();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ParameterDeclarationD) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ParameterDeclarationD) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.ParameterDeclarationAD){
			ParameterDeclarationAD myNode = new ParameterDeclarationAD();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ParameterDeclarationAD) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ParameterDeclarationAD) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.AtomicAbstractDeclarator){
			
			AtomicAbstractDeclarator myNode = new AtomicAbstractDeclarator();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.AtomicAbstractDeclarator) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.AtomicAbstractDeclarator) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.Pointer){
			Pointer myNode = new Pointer();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.Pointer) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.Pointer) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.StructDeclaration){
			StructDeclaration myNode = new StructDeclaration();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.StructDeclaration) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.StructDeclaration) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.StructDeclarator){
			StructDeclarator myNode = new StructDeclarator();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.StructDeclarator) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.StructDeclarator) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.DeclArrayAccess){
			DeclArrayAccess myNode = new DeclArrayAccess();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.DeclArrayAccess) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.DeclArrayAccess) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.NestedNamedDeclarator){
			NestedNamedDeclarator myNode = new NestedNamedDeclarator();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.NestedNamedDeclarator) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.NestedNamedDeclarator) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.FloatSpecifier){
			type = "float";
			FloatSpecifier myNode = new FloatSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.FloatSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.FloatSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.VarArgs){
			VarArgs myNode = new VarArgs();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.VarArgs) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.VarArgs) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.PointerPostfixSuffix){
			de.fosd.typechef.parser.c.PointerPostfixSuffix p = (de.fosd.typechef.parser.c.PointerPostfixSuffix) node;
			
			PointerPostfixSuffix myP = new PointerPostfixSuffix();
			myP.setType(p.kind());
			
			myP.setPositionFrom(((de.fosd.typechef.parser.c.PointerPostfixSuffix) node).getPositionFrom());
			myP.setPositionTo(((de.fosd.typechef.parser.c.PointerPostfixSuffix) node).getPositionTo());
			
			return myP;
		} else if (node instanceof de.fosd.typechef.parser.c.UnaryExpr){
			UnaryExpr u = new UnaryExpr();
			u.setKind(((de.fosd.typechef.parser.c.UnaryExpr) node).kind());
			u.setPositionFrom(((de.fosd.typechef.parser.c.UnaryExpr) node).getPositionFrom());
			u.setPositionTo(((de.fosd.typechef.parser.c.UnaryExpr) node).getPositionTo());
			return u;
		} else if (node instanceof de.fosd.typechef.parser.c.TypeName){
			TypeName myNode = new TypeName();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.TypeName) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.TypeName) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.PointerDerefExpr){
			PointerDerefExpr myNode = new PointerDerefExpr();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.PointerDerefExpr) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.PointerDerefExpr) node).getPositionTo());
			return myNode;
		} else if (node instanceof Choice<?>){
			tree.Choice myNode = new tree.Choice();
			//TODO
			// ADD POSITIONS
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.PointerCreationExpr){
			tree.PointerCreationExpr myNode = new tree.PointerCreationExpr();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.PointerCreationExpr) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.PointerCreationExpr) node).getPositionTo());
			return myNode;
		} else if (node instanceof ArrayAccess){
			tree.ArrayAccess myNode = new tree.ArrayAccess();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ArrayAccess) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ArrayAccess) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.EnumSpecifier){
			tree.EnumSpecifier myNode = new tree.EnumSpecifier();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.EnumSpecifier) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.EnumSpecifier) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.Enumerator){
			tree.Enumerator myNode = new tree.Enumerator();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.Enumerator) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.Enumerator) node).getPositionTo());
			return myNode;
		} else if (node instanceof UnaryOpExpr){
			UnaryOpExpr unary = (UnaryOpExpr) node;
			
			UnaryExpr myUnary = new UnaryExpr();
			myUnary.setKind(unary.kind());
			myUnary.setPositionFrom(((de.fosd.typechef.parser.c.UnaryOpExpr) node).getPositionFrom());
			myUnary.setPositionTo(((de.fosd.typechef.parser.c.UnaryOpExpr) node).getPositionTo());
			
			return myUnary;
		} else if (node instanceof LcurlyInitializer){
			tree.LcurlyInitializer myNode = new tree.LcurlyInitializer();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.LcurlyInitializer) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.LcurlyInitializer) node).getPositionTo());
			return myNode;
		} else if (node instanceof de.fosd.typechef.parser.c.StringLit){
			StringLit mainStringLit = new StringLit();
			mainStringLit.setPositionFrom(((de.fosd.typechef.parser.c.StringLit) node).getPositionFrom());
			mainStringLit.setPositionTo(((de.fosd.typechef.parser.c.StringLit) node).getPositionTo());
			try {
				//First Opt
				StringLit lit = new StringLit();
				Opt<?> opt = (Opt<?>)   ((Product) node.productElement(0)).productElement(0);
				lit.setText(  opt.productElement(1).toString()   );
				
				tree.Opt opt1 = new tree.Opt();
				opt1.setConditional(opt.feature());
				opt1.addChild(lit);
				lit.setParent(opt1);
				
				mainStringLit.addChild(opt1);
				opt1.setParent(mainStringLit);
			} catch (Exception e) {
				// Do nothing..
			}
			
			try {
				//Second Opt
				StringLit lit = new StringLit();
				Opt<?> opt = (Opt<?>)   ((Product)((Product) node.productElement(0)).productElement(1)).productElement(0);
				lit.setText(  opt.productElement(1).toString()   );
				
				tree.Opt opt1 = new tree.Opt();
				opt1.setConditional(opt.feature());
				opt1.addChild(lit);
				lit.setParent(opt1);
				
				mainStringLit.addChild(opt1);
				opt1.setParent(mainStringLit);
			} catch (Exception e) {
				// Do nothing..
			}
			
			try {
				//Third Opt
				StringLit lit = new StringLit();
				Opt<?> opt = (Opt<?>)  ((Product)((Product)((Product) node.productElement(0)).productElement(1)).productElement(1)).productElement(0);
				lit.setText(  opt.productElement(1).toString()   );
				
				tree.Opt opt1 = new tree.Opt();
				opt1.setConditional(opt.feature());
				opt1.addChild(lit);
				lit.setParent(opt1);
				
				mainStringLit.addChild(opt1);
				opt1.setParent(mainStringLit);
			} catch (Exception e) {
				// Do nothing..
			}
			
			try {
				//Fourth Opt
				StringLit lit = new StringLit();
				Opt<?> opt = (Opt<?>)    ((Product)((Product)((Product)((Product) node.productElement(0)).productElement(1)).productElement(1)).productElement(1)).productElement(0);
				lit.setText(  opt.productElement(1).toString()   );
				
				tree.Opt opt1 = new tree.Opt();
				opt1.setConditional(opt.feature());
				opt1.addChild(lit);
				lit.setParent(opt1);
				
				mainStringLit.addChild(opt1);
				opt1.setParent(mainStringLit);
			} catch (Exception e) {
				// Do nothing..
			}
			
			try {
				//Fifth Opt
				StringLit lit = new StringLit();
				Opt<?> opt = (Opt<?>)    ((Product)((Product)((Product)((Product)((Product) node.productElement(0)).productElement(1)).productElement(1)).productElement(1)).productElement(1)).productElement(0);
				lit.setText(  opt.productElement(1).toString()   );
				
				tree.Opt opt1 = new tree.Opt();
				opt1.setConditional(opt.feature());
				opt1.addChild(lit);
				lit.setParent(opt1);
				
				mainStringLit.addChild(opt1);
				opt1.setParent(mainStringLit);
			} catch (Exception e) {
				// Do nothing..
			}
			
			
			try {
				//Sixth Opt
				StringLit lit = new StringLit();
				Opt<?> opt = (Opt<?>)   ((Product)((Product)((Product)((Product)((Product)((Product) node.productElement(0)).productElement(1)).productElement(1)).productElement(1)).productElement(1)).productElement(1)).productElement(0);
				lit.setText(  opt.productElement(1).toString()   );
				
				tree.Opt opt1 = new tree.Opt();
				opt1.setConditional(opt.feature());
				opt1.addChild(lit);
				lit.setParent(opt1);
				
				mainStringLit.addChild(opt1);
				opt1.setParent(mainStringLit);
			} catch (Exception e) {
				// Do nothing..
			}
			
			try {
				//Seventh Opt
				StringLit lit = new StringLit();
				Opt<?> opt = (Opt<?>)   ((Product)((Product)((Product)((Product)((Product)((Product)((Product) node.productElement(0)).productElement(1)).productElement(1)).productElement(1)).productElement(1)).productElement(1)).productElement(1)).productElement(0);
				lit.setText(  opt.productElement(1).toString()   );
				
				tree.Opt opt1 = new tree.Opt();
				opt1.setConditional(opt.feature());
				opt1.addChild(lit);
				lit.setParent(opt1);
				
				mainStringLit.addChild(opt1);
				opt1.setParent(mainStringLit);
			} catch (Exception e) {
				// Do nothing..
			}
			
			
			return mainStringLit;
		} else if (node instanceof ConditionalExpr){
			tree.ConditionalExpr myNode = new tree.ConditionalExpr();
			myNode.setPositionFrom(((de.fosd.typechef.parser.c.ConditionalExpr) node).getPositionFrom());
			myNode.setPositionTo(((de.fosd.typechef.parser.c.ConditionalExpr) node).getPositionTo());
			return myNode;
		}
		
		
		return null;
	}
	
}
