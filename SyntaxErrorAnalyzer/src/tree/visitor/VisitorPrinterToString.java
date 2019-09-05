package tree.visitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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

public class VisitorPrinterToString implements Visitor{

	private ByteArrayOutputStream baos;
	private PrintStream defaultStdio;
	
	public VisitorPrinterToString() {
		defaultStdio = System.out;
		
		baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
	}
	
	public String getSourceCode(){
		try {
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String temp = baos.toString();
		
		System.setOut(defaultStdio);
		
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
	public void run(Choice node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (AtomicNamedDeclarator node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (ElifStatement node){
		System.out.print("else if ");
		for (int i = 0; i < node.getChildren().size(); i++){
			if (i == 1){
				if (node.getChildren().get(i) instanceof One){
					if (node.getChildren().get(i).getChildren().size() > 0){
						if (!(node.getChildren().get(i).getChildren().get(0) instanceof CompoundStatement)){
							System.out.println();
						}
					}
				}
			}node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (CompoundStatement node){
		System.out.println("{\n");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
		System.out.println("}");
		
	}

	public void run (DeclIdentifierList node){
		System.out.print("(");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print(") ");
	}
	
	public void run (TranslationUnit node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (ExprList node){
		boolean optionalNodes = false;
		for (int i = 0; i < node.getChildren().size(); i++){
			if (node.getChildren().get(i) instanceof Opt){
				optionalNodes = true;
				break;
			}
		}
		
		if (!optionalNodes){
			for (int i = 0; i < node.getChildren().size(); i++){
				node.getChildren().get(i).accept(this);
				if (i < node.getChildren().size()-1){
					System.out.print(", ");
				}
			}
		} else {
			for (int i = 0; i < node.getChildren().size(); i++){
				if (node.getChildren().get(i) instanceof Opt){
					if (i >= 1 && node.getChildren().get(i).getChildren().size() > 0){
						Node id = node.getChildren().get(i).getChildren().get(0);
						if (id instanceof Id){
							((Id) id).setName(", " + ((Id) id).getName());
						}
					}
				} else {
					if (i >= 1){
						System.out.print(", ");
					}
				}
				node.getChildren().get(i).accept(this);
			}
		}
		
		/*int index = 1;
		List<FeatureExpr> conditions = new ArrayList<FeatureExpr>();
		
		for (int i = 0; i < node.getChildren().size(); i++){
			if (node.getChildren().get(i) instanceof Opt){
				FeatureExpr expr = ((Opt)node.getChildren().get(i)).getConditional();
				boolean add = true;
				for (FeatureExpr fexpr : conditions){
					if (fexpr.or(expr).isTautology()){
						add = false;
						break;
					}
				}
				if (add){
					conditions.add(expr);
				}
			}
		}
		
		index += conditions.size();*/
		
		
	}
	
	public void run (DeclParameterDeclList node){
		if (!(node.getParent() instanceof DefineDirective)){
			System.out.print("(");
		}
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
			if (i < node.getChildren().size()-1){
				System.out.print(", ");
			}
		}
		if (!(node.getParent() instanceof DefineDirective)){
			System.out.print(")");
		}
	}
	
	public void run (ParameterDeclarationD node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (StructDeclaration node){
		System.out.println("{");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.println("}");
	}
	
	public void run (StructDeclarator node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.println(";");
	}
	
	public void run (AtomicAbstractDeclarator node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (Pointer node){
		System.out.print("*");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (ParameterDeclarationAD node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (FunctionDef node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (Opt node){

		if (node.getChildren().size() > 0 && !node.getConditional().toString().trim().equals("False")){
			if (!node.getConditional().toString().trim().equals("True")){
				System.out.println("\n#if " + node.getConditional().toTextExpr().replace("definedEx", "defined"));
			}
			for (int i = 0; i < node.getChildren().size(); i++){
				node.getChildren().get(i).accept(this);
			}
			
			if (!node.getConditional().toString().trim().equals("True")){
				System.out.println("\n#endif\n");
			}
		}
	}
	
	public void run (Initializer node){
		Node parent = node.getParent();
		boolean arrayInit = false;
		while (parent != null){
			if (parent instanceof LcurlyInitializer){
				arrayInit = true;
				break;
			}
			parent = parent.getParent();
		}
		if (!arrayInit){
			if (!(node.getParent() instanceof DefineDirective)){
				System.out.print(" = ");
			}
		}
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		if (arrayInit){
			System.out.print(", ");
		}
	}
	
	public void run (InitDeclaratorI node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (TypeName node){
		System.out.print("(");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print(") ");
	}
	
	public void run (One node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (Some node){
		if (node.getParent() instanceof IfStatement){
			System.out.print("else ");
		}
		
		if (node.getChildren().size() > 0){
			if (node.getChildren().get(0) instanceof One){
				if (node.getChildren().get(0).getChildren().size() > 0){
					if (!(node.getChildren().get(0).getChildren().get(0) instanceof CompoundStatement)){
						System.out.println();
					}
				}
			}
		}
			
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (SimplePostfixSuffix node){
		System.out.print("++");
	}
	
	public void run (PostfixExpr node){
		if (node.getParent().getParent() instanceof IfStatement){
			System.out.print("(");
		}
		
		// This code is for the FOR command.
		boolean hasParentFor = false;
		
		Node parent = node.getParent();
		while (parent != null){
			if (parent instanceof ForStatement){
				hasParentFor = true;
				break;
			}
			parent = parent.getParent();
		}
		
		
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		// Closing the for bracket.
		if (hasParentFor){
			System.out.print(")");
		}
		
		if (node.getParent().getParent() instanceof IfStatement){
			System.out.print(")");
		}
	}
	
	public void run (AssignExpr node){
		// This code avoids repeated (;)
		boolean hasParentStmt = false;
		
		Node parent = node.getParent();
		while (parent != null){
			if (parent instanceof ReturnStatement || parent instanceof ExprStatement){
				hasParentStmt = true;
				break;
			}
			parent = parent.getParent();
		}
				
		//boolean firstID = true;
		
		for (int i = 0; i < node.getChildren().size(); i++){
			/*if (node.getChildren().get(i) instanceof Id && firstID){
				System.out.print("=");
				firstID = false;
			}*/
			if (i == 1){
				System.out.print("=");
			}
			node.getChildren().get(i).accept(this);
		}
		
		if (!hasParentStmt){
			System.out.print("; ");
		}
	}
	
	public void run (IfStatement node){
		System.out.print("if ");
		
		for (int i = 0; i < node.getChildren().size(); i++){
			if (i == 1){
				if (node.getChildren().get(i) instanceof One){
					if (node.getChildren().get(i).getChildren().size() > 0){
						if (!(node.getChildren().get(i).getChildren().get(0) instanceof CompoundStatement)){
							System.out.println();
						}
					}
				}
			}
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (WhileStatement node){
		System.out.print("while ");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (SizeOfExprT node){
		System.out.print("sizeof(");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print(")");
	}
	
	public void run (SizeOfExprU node){
		System.out.print("sizeof ");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (NestedNamedDeclarator node){
		System.out.print("(");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
			if(node.getChildren().get(i) instanceof AtomicNamedDeclarator){
				System.out.print(")");
			}
		}
	}
	
	public void run (FunctionCall node){
		System.out.print("(");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print(")");
	}
	
	public void run (ExprStatement node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.println(";");
	}
	
	public void run (TypeDefTypeSpecifier node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (DeclArrayAccess node){
		System.out.print("[");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print("]");
	}
	
	public void run (ForStatement node){
		System.out.print("for(");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (NAryExpr node){
		// Prints different in a for loop.
		boolean inAForStatement = false;
		
		Node parent = node.getParent();
		while (parent != null){
			if (parent instanceof ForStatement){
				inAForStatement = true;
				break;
			}
			parent = parent.getParent();
		}
		
		// Subexpressions do not need brackets.
		boolean inAnotherExpr = false;
		
		parent = node.getParent();
		while (parent != null){
			if (parent instanceof NAryExpr){
				inAnotherExpr = true;
				break;
			}
			parent = parent.getParent();
		}
		
		if (!inAForStatement && !inAnotherExpr){
			System.out.print("(");
		}
		
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
		if (!inAForStatement && !inAnotherExpr){
			System.out.print(")");
		} else if (inAForStatement) {
			System.out.print("; ");
		}
	}
	
	public void run (NArySubExpr node){
		System.out.print(node.getOperator() + " ");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (DoStatement node){
		System.out.print("do ");
		
		Node nAryExpr = null;
		
		for (int i = 0; i < node.getChildren().size(); i++){
			
			if (node.getChildren().get(i) instanceof NAryExpr){
				nAryExpr = node.getChildren().get(i);
			} else {
				node.getChildren().get(i).accept(this);
			}
		}
		
		System.out.print("while ");
		this.run( (NAryExpr) nAryExpr);
		System.out.println(";");
	}
	
	public void run (CaseStatement node){
		System.out.print("case ");
		for (int i = 0; i < node.getChildren().size(); i++){
			if (i == 1){
				System.out.println(":");
			}
			node.getChildren().get(i).accept(this);
			//if (node.getChildren().get(i) instanceof Constant){
				//System.out.println(":");
			//}
			
		}
	}
	
	public void run (SwitchStatement node){
		System.out.print("switch (");
		for (int i = 0; i < node.getChildren().size(); i++){
			if (i == 1){
				System.out.print(")");
			}
			node.getChildren().get(i).accept(this);
//			if (node.getChildren().get(i) instanceof Id){
//				System.out.print(")");
//			}
		}
		if (node.getParent() instanceof DefineDirective){
			System.out.print(")");
		}
	}
	
	public void run (DefaultStatement node){
		System.out.println("default:");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (DeclarationStatement node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.println(";");
	}
	
	public void run (Declaration node){
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		if ( !(node.getParent() instanceof DeclarationStatement) && !(node.getParent() instanceof DefineDirective)){
			System.out.println(";");
		}
	}
	
	public void run (Constant node){
		System.out.print(node.getValue() + " ");
	}
	
	public void run (Id node){
		if (node.getParent().getParent() instanceof IfStatement){
			System.out.print("(");
		}
		
		System.out.print(node.getName() + " ");
		
		if (node.getParent().getParent() instanceof IfStatement){
			System.out.print(")");
		}
	}
	
	public void run (VoidSpecifier node){
		System.out.print("void ");
	}
	
	public void run (IntSpecifier node){
		System.out.print("int ");
	}
	
	public void run (DoubleSpecifier node){
		System.out.print("double ");
	}
	
	public void run (UnsignedSpecifier node){
		System.out.print("unsigned ");
	}
	
	public void run (VolatileSpecifier node){
		System.out.print("volatile ");
	}
	
	public void run (ConstSpecifier node){
		System.out.print("const ");
	}
	
	public void run (ExternSpecifier node){
		System.out.print("extern ");
	}
	
	public void run (TypedefSpecifier node){
		System.out.print("typedef ");
	}
	
	public void run (AutoSpecifier node){
		System.out.print("auto ");
	}
	
	public void run (BreakStatement node){
		System.out.println("break;");
	}
	
	public void run (CharSpecifier node){
		System.out.print("char ");
	}
	
	public void run (VarArgs node){
		System.out.print("... ");
	}
	
	public void run (PointerPostfixSuffix node){
		System.out.print(node.getType());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (PointerDerefExpr node){
		System.out.print("*");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (UnaryExpr node){
		System.out.print(node.getKind());
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	public void run (ContinueStatement node){
		System.out.println("continue;");
	}
	
	public void run (RegisterSpecifier node){
		System.out.print("register ");
	}
	
	public void run (StaticSpecifier node){
		System.out.print("static ");
	}
	
	public void run (FloatSpecifier node){
		System.out.print("float ");
	}
	
	public void run (ReturnStatement node){
		System.out.print("\nreturn ");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.println(";");
	}
	
	public void run (ShortSpecifier node){
		System.out.print("short ");
	}
	
	public void run (LongSpecifier node){
		System.out.print("long ");
	}
	
	public void run (StructOrUnionSpecifier node){
		System.out.print(node.getName() + " ");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
		/*boolean inAnotherStruct = false;
		
		Node parent = node.getParent();
		while (parent != null){
			if (parent instanceof StructDeclaration){
				inAnotherStruct = true;
				break;
			}
			parent = parent.getParent();
		}
		
		
		// For the special case of union or structure with the name at the end.
		
		boolean addCurlyBracket = false;
		
		for (int i = 0; i < node.getChildren().size(); i++){
			if (node.getChildren().get(i) instanceof Some){
				addCurlyBracket = true;
				break;
			}
			
		}
		
		if (!addCurlyBracket && !inAnotherStruct){
			System.out.println(node.getName() + " {");
		} else {
			System.out.print(node.getName() + " ");
		}
		
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
			if (node.getChildren().get(i) instanceof Some && !inAnotherStruct){
				System.out.println("{");
			}
			
		}
		if (!addCurlyBracket && !inAnotherStruct){
			System.out.print("} ");
		} else if (!inAnotherStruct){
			System.out.print("}");
		}*/
		
	}
	
	@Override
	public void run(PointerCreationExpr node) {
		System.out.print("&");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(UnaryOpExpr node) {
		// Correct..
		System.out.print("(" + node.getKind() + "(");
		//System.out.print("(!(");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print("))");
	}
	
	@Override
	public void run(ArrayAccess node) {
		System.out.print("[");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print("]");
	}
	
	@Override
	public void run(LcurlyInitializer node) {
		if (node.getParent() instanceof Initializer){
			System.out.println();
		}
		System.out.print("{");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.print("}");
	}
	
	@Override
	public void run(StringLit node) {
		if (node.getChildren().size() == 0){
			if (node.getText().trim().equals("")){
				System.out.print("\"\"");
			} else {
				System.out.print(node.getText());
			}
		} else {
			for (int i = 0; i < node.getChildren().size(); i++){
				node.getChildren().get(i).accept(this);
			}
		}
	}
	
	@Override
	public void run(ConditionalExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			if (i == 1){
				System.out.print("?");
			}
			if (i == 2){
				System.out.print(":");
			}
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(DefineDirective node) {
		System.out.print("#define " + node.getName() + " ");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(EnumSpecifier node) {
		System.out.print("enum {");
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		System.out.println("}");
	}
	
	@Override
	public void run(Enumerator node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		if (!(node.getParent() instanceof DefineDirective)){
			System.out.print(", ");
		}
	}
	
}
