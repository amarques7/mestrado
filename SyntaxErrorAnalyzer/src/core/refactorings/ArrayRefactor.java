package core.refactorings;

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
import tree.visitor.VisitorConditionalChecker;
import core.Refactor;

public class ArrayRefactor implements Visitor{
	
	@Override
	public void run(Choice node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(AtomicNamedDeclarator node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ElifStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}

	@Override
	public void run(CompoundStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DeclIdentifierList node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TranslationUnit node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ExprList node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(DeclParameterDeclList node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ParameterDeclarationD node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StructDeclaration node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StructDeclarator node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(AtomicAbstractDeclarator node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Pointer node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ParameterDeclarationAD node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(FunctionDef node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Opt node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Initializer node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(InitDeclaratorI node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TypeName node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(One node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Some node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(SimplePostfixSuffix node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(PostfixExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(AssignExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(IfStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(WhileStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
				node.getChildren().get(i).accept(this);
		}
	}

	@Override
	public void run(SizeOfExprT node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(SizeOfExprU node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(NestedNamedDeclarator node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(FunctionCall node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	
	@Override
	public void run(ExprStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TypeDefTypeSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DeclArrayAccess node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ForStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(NAryExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(NArySubExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DoStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(CaseStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(SwitchStatement node) {
for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DefaultStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(DeclarationStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(Declaration node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Constant node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(Id node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(VoidSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(IntSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(DoubleSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(UnsignedSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(VolatileSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ConstSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ExternSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(TypedefSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(AutoSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(BreakStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(CharSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(VarArgs node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(PointerPostfixSuffix node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(PointerDerefExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(UnaryExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ContinueStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(RegisterSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StaticSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(FloatSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ReturnStatement node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(ShortSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(LongSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}

	@Override
	public void run(StructOrUnionSpecifier node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(PointerCreationExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(UnaryOpExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
		
	}
	
	@Override
	public void run(ArrayAccess node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(LcurlyInitializer node) {
		VisitorConditionalChecker conditionalChecker = new VisitorConditionalChecker();
		node.accept(conditionalChecker);
		
		if (conditionalChecker.containConditional()){
			
			Refactor refactor = new Refactor();
			//Node clone = refactor.cloneNode(node);
			
			List<Opt> conditionals = refactor.getConditionalNodes(node);
			
			for (int i = 0; i < conditionals.size(); i++){
				DefineDirective define = new DefineDirective();
				define.setName("ELEMS" + (i+1));
				
				List<Node> children = conditionals.get(i).getChildren();
				for (int j = 0; j < children.size(); j++){
					define.addChild(children.get(j));
					children.get(j).setParent(define);
				}
				Constant comma = new Constant();
				comma.setValue(",");
				define.addChild(comma);
				comma.setParent(define);
				
				conditionals.get(i).setChildren(new ArrayList<Node>());
				
				
				// Adding the ELEMS macro in the array..
				int indexOpt = conditionals.get(i).getParent().getChildren().indexOf(conditionals.get(i));
				
				Constant constant = new Constant();
				constant.setValue("ELEMS" + (i+1));
			
				conditionals.get(i).getParent().getChildren().add(indexOpt, constant);
				constant.setParent(conditionals.get(i).getParent());
				
				conditionals.get(i).getParent().getChildren().remove((indexOpt+1));
				
				// Adding the #define directive..
				conditionals.get(i).addChild(define);
				define.setParent(conditionals.get(i));
				
				Node declStmt = node.getParent();
				while (declStmt != null && !(declStmt instanceof Declaration)){
					declStmt = declStmt.getParent();
				}
				
				if (declStmt instanceof Declaration){
					int declIndex = declStmt.getParent().getChildren().indexOf(declStmt);
					declStmt.getParent().getChildren().add(declIndex, conditionals.get(i));
					conditionals.get(i).setParent(declStmt.getParent());
					
					
					// Adding the macro with an empty String..
					declIndex++;
					Opt optClone = (Opt) refactor.cloneNode(conditionals.get(i));
					optClone.setChildren(new ArrayList<Node>());
					
					DefineDirective define2 = new DefineDirective();
					define2.setName("ELEMS" + (i+1));
					StringLit emptyString = new StringLit();
					emptyString.setText("");
					
					define2.addChild(emptyString);
					emptyString.setParent(define2);
					
					optClone.addChild(define2);
					define2.setParent(optClone);
					
					optClone.setConditional(optClone.getConditional().not());
					
					declStmt.getParent().getChildren().add(declIndex, optClone);
					optClone.setParent(declStmt.getParent());
					
				}
			}
			
			
			/*refactor.removeConditionalsKeepingChildren(clone);
			
			int maxSize = 0;
			
			for (int i = 0; i < clone.getChildren().size(); i++){
				if (clone.getChildren().get(i) instanceof Initializer){
					maxSize++;
				}
			}
			
			Node initDeclarator = node.getParent();
			while (initDeclarator != null && !(initDeclarator instanceof InitDeclaratorI)){
				initDeclarator = initDeclarator.getParent();
			}
			
			if (initDeclarator != null){
				Node id = initDeclarator.getChildren().get(0).getChildren().get(0);
				if (id instanceof Id){
					
					Node declStmt = initDeclarator.getParent().getParent();
					
					while (declStmt != null && !(declStmt instanceof DeclarationStatement)){
						declStmt = declStmt.getParent();
					}
					
					/*if (!(declStmt instanceof DeclarationStatement)){
						declStmt = initDeclarator.getParent();
						while (declStmt != null && !(declStmt instanceof Declaration)){
							declStmt = declStmt.getParent();
						}
					}
					
					System.out.println(declStmt.getClass().getCanonicalName());*/
					
					/*if (declStmt instanceof DeclarationStatement /*|| declStmt instanceof Declaration*//*){
						
						// We need to get the real type..
						Node type = new IntSpecifier();
						
						if (declStmt.getChildren().get(0) instanceof Declaration){
							
							if (declStmt.getChildren().size() > 0){
								if (declStmt.getChildren().get(0).getChildren().size() > 0){
									if (declStmt.getChildren().get(0).getChildren().get(0).getChildren().size() > 0){
										if (declStmt.getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().size() > 0){
											if (declStmt.getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0) instanceof Id){
												type = declStmt.getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0);
											}
										}
									}
								}
							}
							
						}
						
						
						// Array new declaration..
						Node decl = refactor.createArrayDeclarationStatement( ((Id)id).getName() , type, maxSize);
						
						// Initializing the array..
						int index = declStmt.getParent().getChildren().indexOf(declStmt);
						declStmt.getParent().getChildren().remove(declStmt);
						
						//if (declStmt.getChildren().get(0).getChildren().get(0).getChildren().get(1) instanceof StructDeclaration){
							// We need to add the struct declaration..
							
							//Node declClone = refactor.cloneNode(declStmt);
							//declClone.getChildren().get(0).getChildren().remove(1);
							//declClone.getChildren().get(0).accept(new VisitorPrinter(false));
							
							//System.out.println(declClone.getChildren().size());
							//declClone.getChildren().remove(1);
							//declStmt.getParent().getChildren().add((index), declClone);
							//index++;
						//}
						
						declStmt.getParent().getChildren().add(index, decl);
						decl.setParent(declStmt.getParent());
					
						
						
						// Initialize variable index..
						Constant c = new Constant();
						c.setValue("0");
						Node declIndex = refactor.createDeclarationStatement("index", new IntSpecifier(), c);
						
						index++;
						declStmt.getParent().getChildren().add(index, declIndex);
						declIndex.setParent(declStmt.getParent());
						
						for (int i = 0; i < node.getChildren().size(); i++){
							if (node.getChildren().get(i) instanceof Initializer){
								
								Node constant = node.getChildren().get(i).getChildren().get(0);
								Node exprStmt = refactor.createArrayExprStatement(((Id)id).getName(), "index++", constant);
								index++;
								declStmt.getParent().getChildren().add(index, exprStmt);
								exprStmt.setParent(declStmt.getParent());
							} else if (node.getChildren().get(i) instanceof Opt){
								Opt opt = (Opt) node.getChildren().get(i);
								Opt optClone = (Opt) refactor.cloneNode(opt);
								optClone.setChildren(new ArrayList<Node>());
								
								for (int j = 0; j < opt.getChildren().size(); j++){
									if (opt.getChildren().get(j) instanceof Initializer){
										Node constant = opt.getChildren().get(j).getChildren().get(0);
										Node exprStmt = refactor.createArrayExprStatement(((Id)id).getName(), "index++", constant);
										optClone.addChild(exprStmt);
									}
								}
								index++;
								declStmt.getParent().getChildren().add(index, optClone);
								optClone.setParent(declStmt.getParent());
							}
						}
					
					}
				}
			}*/
			
		}
		
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(StringLit node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(ConditionalExpr node) {
		for (int i = 0; i < node.getChildren().size(); i++){
			node.getChildren().get(i).accept(this);
		}
	}
	
	@Override
	public void run(DefineDirective node) {
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
