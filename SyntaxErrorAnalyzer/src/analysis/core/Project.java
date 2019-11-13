package analysis.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import analysis.Analyser;
import analysis.Dependency;
import analysis.core.Ast.GenerationStatus;
import analysis.visitor.FindFunctionCallsVisitor;
import analysis.visitor.FindFunctionDefVisitor;
import analysis.visitor.FindFunctionDirectivesVisitor;
import analysis.visitor.FindFunctionParametersVisitor;
import analysis.visitor.FindFunctionsVisitor;
import analysis.visitor.FindGlobalVariableUsesVisitor;
import analysis.visitor.FindGlobalVariablesDeclarationsVisitor;
import analysis.visitor.FindVariableDeclarationInsideFunctionVisitor;
import analysis.visitor.FindVariableDeclarationVisitor;
import analysis.visitor.FindVariableDefinitionVisitor;
import analysis.visitor.FindVariableFunctionVisitor;
import analysis.visitor.PresenceConditionVisitor;
import main.Starter;
import mestrado.core.Runner;
import tree.AssignExpr;
import tree.CompoundStatement;
import tree.FunctionCall;
import tree.FunctionDef;
import tree.Id;
import tree.Node;
import tree.Opt;
import tree.PostfixExpr;

public class Project {

	public String name, version;
	private String sourcePath;
	private String stubsPath;
	private int totalFiles = 0;
	private int totalSuccessfulFiles = 0;
	public List<Ast> asts = new ArrayList<Ast>();
	public int numberOfVariabilities = 0;

	private Map<FunctionDef, Function> functions = new HashMap<FunctionDef, Function>();
	private List<Variable> globals = new ArrayList<Variable>();
	private List<Variable> useOfGlobalVariables = new ArrayList<Variable>();
	private List<Call> calls = new ArrayList<Call>();

	private List<Dependency> dependencies = new ArrayList<Dependency>();

	// lists used to set calls
	private List<Id> ids = new ArrayList<Id>();
	private List<Id> allCallers = new ArrayList<Id>();

	public Project(String sourcePath, String stubsPath) {
		setSourcePath(sourcePath);
		setStubsPath(stubsPath);
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getStubsPath() {
		return stubsPath;
	}

	public void setStubsPath(String stubsPath) {
		this.stubsPath = stubsPath;
	}

	public int getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(int totalFiles) {
		this.totalFiles = totalFiles;
	}

	public int getTotalSuccessfulFiles() {
		return totalSuccessfulFiles;
	}

	public void setTotalSuccessfulFiles(int totalSuccessfulFiles) {
		this.totalSuccessfulFiles = totalSuccessfulFiles;
	}

	public List<Ast> getAsts() {
		return asts;
	}

	public Map<FunctionDef, Function> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<FunctionDef, Function> functions) {
		this.functions = functions;
	}

	public List<Variable> getGlobals() {
		return globals;
	}

	public void setGlobals(List<Variable> globals) {
		this.globals = globals;
	}

	public void createAst(ArrayList<String> filesToAnalyze) {

		for (String file : filesToAnalyze) {
			Ast ast = new Ast(new File(file), new File(getStubsPath()));
			GenerationStatus generationStatus = null;
	
		//	do {
				generationStatus = ast.generate();
		//	} while (generationStatus == GenerationStatus.OPTIONS_EXCEPTION);
			
			if (generationStatus == GenerationStatus.OK) {
				setTotalSuccessfulFiles(getTotalSuccessfulFiles() + 1);

				// replacing duplicates ASTS git didnt tell was deleted
				// duplicating asts to modifify the list
				List<Ast> astsDuplicated = new ArrayList<Ast>(asts.size());
				for (Ast astFromAsts : asts)
					astsDuplicated.add(astFromAsts);

				boolean replaced = false;
				int indexToRemove = 0;
				for (Ast astFromAstsDuplicated : astsDuplicated) {
					if (astFromAstsDuplicated.getSource().getName().equals(ast.getSource().getName())) {
						asts.set(indexToRemove, ast);
						replaced = true;
						break;
					}
					indexToRemove++;
				}
				if (!replaced)
					getAsts().add(ast);
			}
		}
	}

	public void cleanAst(ArrayList<String> filesToRemove) {
		// removing all the deleted files from ast list
		int indexToRemove = 0;
		List<Integer> allIndexToRemove = new ArrayList<Integer>(filesToRemove.size());
		//for (String fileToClean : main.Main.filesToDeleteFromAnalysisFolder) {
		for (String fileToClean : Runner.projectManager.getListModFile()) {
			indexToRemove = 0;
			for (Ast ast : getAsts()) {
				String fileFromAST = ast.getSource().getName();
				if (fileToClean.equals(fileFromAST)) {
					allIndexToRemove.add(indexToRemove);
					break;
				}
				indexToRemove++;
			}

		}

		List<Ast> newAsts = new ArrayList<Ast>(getAsts().size());
		for (int j = 0; j < getAsts().size(); j++) {
			if (!allIndexToRemove.contains(j)) {
				newAsts.add(getAsts().get(j));
			}
		}
		asts = newAsts;

	}

	public void findVariables(Ast ast) {

		// Get global variables
		Node nodeAst = ast.getNode();
		FindGlobalVariablesDeclarationsVisitor findGlobalVariablesDeclarationsVisitor = new FindGlobalVariablesDeclarationsVisitor();
		nodeAst.accept(findGlobalVariablesDeclarationsVisitor);

		// Get all global variables on this AST
		List<Id> globalVariablesDeclarations = findGlobalVariablesDeclarationsVisitor.getGlobalVariablesDeclarations();

		for (Id globalVariable : globalVariablesDeclarations) {
			Variable v = new Variable(globalVariable);
			this.globals.add(v);
		}

		FindVariableDefinitionVisitor findVariableDefinitionVisitor = new FindVariableDefinitionVisitor();
		nodeAst.accept(findVariableDefinitionVisitor);

		for (Id globalVariableDeclaration : globalVariablesDeclarations) {

			FindGlobalVariableUsesVisitor findGlobalVariableUsesVisitor = new FindGlobalVariableUsesVisitor(
					globalVariableDeclaration);
			nodeAst.accept(findGlobalVariableUsesVisitor);
			// Get all uses for this global variable
			List<Id> globalVariableUses = findGlobalVariableUsesVisitor.getGlobalVariableUses();

			for (Id globalVariableUse : globalVariableUses) {
				FindVariableFunctionVisitor findVariableFunctionVisitor = new FindVariableFunctionVisitor();
				globalVariableUse.accept(findVariableFunctionVisitor);

				FunctionDef functionDef = findVariableFunctionVisitor.getFunctionDef();

				// If global variable use is outside a function, then continue
				if (functionDef == null) {
					continue;
				}

				Function function = getFunctions().get(functionDef);

				List<CompoundStatement> scope = findVariableFunctionVisitor.getScope();

				// Looks for a homonymous variable declared within the scope
				FindVariableDeclarationVisitor findVariableDeclarationVisitor = new FindVariableDeclarationVisitor(
						globalVariableUse, scope, true);
				functionDef.accept(findVariableDeclarationVisitor);

				// No homonymous variable found
				if (!(findVariableDeclarationVisitor.isFound())) {
					// Keep record of global variables accesses
					function.getGlobalReferences().add(new Variable(globalVariableUse));
				} else {
					if ((!(findVariableDeclarationVisitor.getPresenceCondition()
							.equivalentTo(globalVariableUse.getPresenceCondition())))
							&& (!(globalVariableDeclaration.getPresenceCondition()
									.and(globalVariableUse.getPresenceCondition())
									.and(findVariableDeclarationVisitor.getPresenceCondition())).isContradiction())) {
						function.getGlobalReferences().add(new Variable(globalVariableUse));
					}
				}

				allCallers = new ArrayList<Id>();
				if (globalVariableUse.getParent() instanceof AssignExpr) {
					if (globalVariableUse.getParent().getChildren().get(1) instanceof Id) {
						Id idToCompare = (Id) globalVariableUse.getParent().getChildren().get(1);
						if (!idToCompare.getName().equals(globalVariableUse.getName())) {
							createCallToAdd(globalVariableUse, function);
						}
					} else {
						createCallToAdd(globalVariableUse, function);
					}
				}
				Variable v = new Variable(globalVariableUse);
				useOfGlobalVariables.add(v);
			}

		}

	}

	public ProgramElement createCallToAdd(Id globalVariableUse, Function function) {
		ProgramElement declaration = null;
		findAllCallers(globalVariableUse.getParent());
		Node node = globalVariableUse.getParent().getChildren().get(0);
		while (!(node instanceof Id)) {
			node = node.getChildren().get(0);
		}
		Id callerId = (Id) node;
		Variable caller = findVariableDeclaration(callerId, function);
		Call call = new Call(caller);
		call.setContext(function);
		call.getUses().add(new Variable(globalVariableUse));

		ids = new ArrayList<Id>();
		Node parent = callerId;
		while (!(parent instanceof AssignExpr)) {
			parent = parent.getParent();
		}
		runTree(parent.getChildren().get(1));

		for (Id id : ids) {
			declaration = findFunctionDeclaration(id);
			if (declaration != null) {
				call.getFunctionCalleesDeclaration().add(new Function(id));
				call.getFunctionCalleesDeclaration().add((Function) declaration);
			} else {
				call.getVariableCalleesUses().add(verifyGlobalOrLocalVariable(id, function));
				declaration = findVariableDeclaration(id, function);
				call.getVariableCalleesDeclaration().add(((Variable) declaration));
			}
		}
		calls.add(call);
		return declaration;
	}

	public Variable findVariableDeclaration(Id id, Function function) {
		for (Variable globalVar : globals) {
			if (globalVar.getName().equals(id.getName()))
				return globalVar;
		}

		for (Id local : function.getLocalVariables()) {
			if (local.getName().equals(id.getName())) {
				Variable var = new Variable(local);
				var.setContext(function);
				return var;
			}
		}
		for (Variable parameter : function.getParameters()) {
			if (parameter.getName().equals(id.getName()))
				return parameter;
		}
		return null;
	}

	public Function findFunctionDeclaration(Id id) {
		for (Entry<FunctionDef, Function> functionMap : getFunctions().entrySet()) {
			Function functionDeclared = functionMap.getValue();
			if (id.getName().contains(functionDeclared.getName())) {
				return functionDeclared;
			}
		}

		return null;
	}

	public void findAllCallers(Node node) {
		if (node instanceof PostfixExpr || node instanceof AssignExpr) {
			while (node.getChildren().size() > 0) {
				node = node.getChildren().get(0);
				if (node instanceof Id) {
					allCallers.add((Id) node);
					return;
				}
			}
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			findAllCallers(node.getChildren().get(i));
		}
	}

	public void findAllCallersOnlyAssignExpr(Node node) {
		if (node instanceof AssignExpr) {
			while (node.getChildren().size() > 0) {
				node = node.getChildren().get(0);
				if (node instanceof Id) {
					allCallers.add((Id) node);
					return;
				}
			}
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			findAllCallersOnlyAssignExpr(node.getChildren().get(i));
		}
	}

	private void runTree(Node node) {
		if (node instanceof Id) {
			ids.add((Id) node);
			return;
		}
		if (node instanceof PostfixExpr) {
			while (node.getChildren().size() > 0) {
				node = node.getChildren().get(0);
				if (node instanceof Id) {
					ids.add((Id) node);
					return;
				}
			}
		}
		for (Node children : node.getChildren())
			runTree(children);
	}

	public void analyze(ArrayList<String> filesToAnalyze) throws InterruptedException {

		if (!Runner.projectManager.isNoChangesInCFiles()) {
			exportDirectives();
			return;
		}

		// resetting
		this.ids = new ArrayList<Id>();
		this.allCallers = new ArrayList<Id>();
		this.calls = new ArrayList<Call>(calls.size());
		this.globals = new ArrayList<Variable>(globals.size());
		this.useOfGlobalVariables = new ArrayList<Variable>(useOfGlobalVariables.size());
		this.functions = new HashMap<FunctionDef, Function>(this.functions.size());

		cleanAst(filesToAnalyze);
		createAst(filesToAnalyze);

//		FileWriter writer;
//		try {
//			writer = new FileWriter(new File("C:/results/" + "Ast.txt"));
//			for (String commit : getAsts()) {
//				writer.write(commit + "\n");
//			}
//			writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("Tamanho ASTs: " + asts.size());
	//	System.exit(0);
		this.allPairs = new ArrayList<Pair>();
		for (Ast ast : this.asts) {
			ast.getNode().accept(new PresenceConditionVisitor());

			findFunctions(ast);
			findAllFunctionCalls(ast);

			findVariables(ast);
		}

		findFunctionCalls();
		findCallsForFunctionsCalls();
		findCallsForLocalVariables(getFunctions());

		// at this point, in the calls list, it is listed all the calls for local
		// variables, global variables and functions

		System.out.println(asts.size() + " " + Starter.numberOfCFiles);

		float percentage = (((float) asts.size() / (float) Starter.numberOfCFiles) * 100);

		ResultsLogger.write("	ASTs: " + asts.size() + "/" + Starter.numberOfCFiles + " " + percentage + "%");

		System.out.println("Exporting directives");
		exportDirectives();

	}

	public void findAllFunctionCalls(Ast ast) {
		try {
			findAllCallersOnlyPostfixExpr(ast.getNode());
		} catch (Exception e) {

		}

	}

	public void findAllCallersOnlyPostfixExpr(Node node) {
		if (node instanceof PostfixExpr) {
			PostfixExpr postfix = (PostfixExpr) node;
			boolean fc = false;
			for (Node childrens : postfix.getChildren()) {
				if (childrens instanceof FunctionCall) {
					fc = true;
					break;
				}
			}
			if (!fc)
				return;
			while (node.getChildren().size() > 0) {
				node = node.getChildren().get(0);
				if (node instanceof Id) {
					FunctionDef fdef = findFunctionDef(postfix);
					resolvePair((Id) node, this.functions.get(fdef));
					break;
				}
			}
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			findAllCallersOnlyPostfixExpr(node.getChildren().get(i));
		}
	}

	public FunctionDef findFunctionDef(Node node) {
		while (!(node instanceof FunctionDef)) {
			node = node.getParent();
		}
		return (FunctionDef) node;
	}

	public void resolvePair(Id node, Function fdef) {
		for (Pair pair : allPairs) {
			if (pair.getFunction().getName().equals(node.getName())) {
				pair.getAllFunctionDefs().add(fdef);
				return;
			}
		}

		allPairs.add(new Pair(fdef, node));
	}

	public void findCallsForFunctionsCalls() {
		for (Entry<FunctionDef, Function> functions : getFunctions().entrySet()) {
			Function function = functions.getValue();
			for (FunctionCall fc : function.getFunctionCalls()) {
				ids = new ArrayList<Id>();
				runTree(fc);
				Id callerFunction = findId(fc.getParent());
				Call call = new Call(findFunctionDeclaration(callerFunction));
				call.setContext(getFunctions().get(fc.getContext()));
				call.getUses().add(new Function(callerFunction));
				ProgramElement declaration = null;
				for (Id id : ids) {
					declaration = findFunctionDeclaration(id);
					if (declaration != null) {
						call.getFunctionCalleesDeclaration().add(new Function(id));
						call.getFunctionCalleesDeclaration().add((Function) declaration);
					} else {
						call.getVariableCalleesUses().add(verifyGlobalOrLocalVariable(id, function));
						declaration = findVariableDeclaration(id, function);
						function.getCallForSingleElements().add(declaration);
						call.getVariableCalleesDeclaration().add((Variable) declaration);
					}
				}
				calls.add(call);
			}

		}
	}

	public Id findId(Node node) {
		while (!(node instanceof Id)) {
			node = node.getChildren().get(0);
		}
		return (Id) node;

	}

	public Variable verifyGlobalOrLocalVariable(Id id, Function function) {
		for (Variable global : globals) {
			if (global.getName().equals(id.getName())) {
				return global;
			}
		}
		for (Id local : function.getLocalVariables()) {
			if (id.getName().equals(local.getName()))
				return new Variable(local, function);
		}
		return null;
	}

	public void findCallsForLocalVariables(Map<FunctionDef, Function> functions) {
		for (Entry<FunctionDef, Function> map : functions.entrySet()) {
			Function function = map.getValue();
			allCallers = new ArrayList<Id>(allCallers.size());
			findAllCallersOnlyAssignExpr(function.getFunctionDef());

			for (Id localVariable : function.getLocalVariables()) {
				for (Id caller : allCallers) {
					if (caller.getName().equals(localVariable.getName())) {
						Call call = new Call(new Variable(localVariable, function));
						call.setContext(function);

						// set uses
						ids = new ArrayList<Id>(ids.size());
						Node parent = caller;
						while (!(parent instanceof AssignExpr)) {
							try {
								parent = parent.getParent();
							} catch (Exception e) {
								System.out.println("A");
							}
						}
						runTree(parent.getChildren().get(1));

						ProgramElement declaration;
						for (Id id : ids) {
							declaration = findFunctionDeclaration(id);
							if (declaration != null) {
								call.getFunctionCalleesDeclaration().add(new Function(id));
								call.getFunctionCalleesDeclaration().add((Function) declaration);
							} else {
								call.getVariableCalleesUses().add(verifyGlobalOrLocalVariable(id, function));
								declaration = findVariableDeclaration(id, function);
								call.getVariableCalleesDeclaration().add((Variable) declaration);
							}
						}
						calls.add(call);
					}
				}
			}

		}
	}

	public boolean verifyIfFunctionInGetFunctionsByName(String name) {
		for (Entry<FunctionDef, Function> functionMap : getFunctions().entrySet()) {
			Function function = functionMap.getValue();
			if (function.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void exportDirectives() throws InterruptedException {
		

		setDependencies(new Analyser().setDps(getFunctions(), globals, useOfGlobalVariables, calls));
		//esse é o meu
		exportFunctionCalls.ExportNumberOfCalls.receive(allPairs);
	}

	public void findFunctions(Ast ast) {
		Map<FunctionDef, Function> functions;

		Node nodeAst = ast.getNode();

		FindFunctionsVisitor findFunctionsVisitor = new FindFunctionsVisitor();
		nodeAst.accept(findFunctionsVisitor);
		// Get all function definitions on this AST
		List<FunctionDef> functionDefs = findFunctionsVisitor.getFunctions();
		functions = new HashMap<FunctionDef, Function>();
		// Create a function object for each function definition
		for (FunctionDef functionDef : functionDefs) {
			FindFunctionDirectivesVisitor findFunctionDirectivesVisitor = new FindFunctionDirectivesVisitor(
					functionDef);
			functionDef.accept(findFunctionDirectivesVisitor);
			// Get all function directives
			Set<Opt> functionDirectives = findFunctionDirectivesVisitor.getFunctionDirectives();
			// Get function inner directives (ifdefs inside function)
			Set<Opt> functionOpts = findFunctionDirectivesVisitor.getFunctionOpts();

			// setting parameters
			FindFunctionParametersVisitor findFunctionParametersVisitor = new FindFunctionParametersVisitor();
			functionDef.accept(findFunctionParametersVisitor);
			List<Id> functionParameters = findFunctionParametersVisitor.getFunctionParameters();
			Function function = new Function(functionDef, functionParameters);
			function.setDirectives(functionDirectives);
			function.setOpts(functionOpts);

			Set<Id> variables = findVariableDefinitionsInsideFunction(function);
			function.setLocalVariables(variables);
			functions.put(functionDef, function);
		}

		this.functions.putAll(functions);
	}

	public Set<Id> findVariableDeclarationsInsideFunction(Function function) {
		FindVariableDeclarationInsideFunctionVisitor findVariableDeclarationVisitor = new FindVariableDeclarationInsideFunctionVisitor();
		function.getFunctionDef().accept(findVariableDeclarationVisitor);
		Set<Id> localVariables = findVariableDeclarationVisitor.getLocalVariables();
		function.setLocalVariables(localVariables);
		return localVariables;
	}

	public Set<Id> findVariableDefinitionsInsideFunction(Function function) {
		FindVariableDefinitionVisitor findVariableDefinitionVisitor = new FindVariableDefinitionVisitor();
		function.getFunctionDef().accept(findVariableDefinitionVisitor);
		Set<Id> localVariablesDefinitions = findVariableDefinitionVisitor.getLocalVariables();

		FindVariableDeclarationInsideFunctionVisitor findVariableDeclarationVisitor = new FindVariableDeclarationInsideFunctionVisitor();
		function.getFunctionDef().accept(findVariableDeclarationVisitor);
		Set<String> localVariablesDeclarations = findVariableDeclarationVisitor.getLocalVariablesNames();

		for (Id variable : localVariablesDefinitions) {
			if (localVariablesDeclarations.contains(variable.getName())) {
				localVariablesDefinitions.add(variable);
			}
		}

		function.setLocalVariables(localVariablesDefinitions);

		return localVariablesDefinitions;
	}

	public void findFunctionCalls() {
		// int i = 1;
		System.out.println("Looking for function calls on ASTs (" + getAsts().size() + ")");
		for (Ast ast : getAsts()) {
			// System.out.println("AST " + i + "/" + getAsts().size());
			Node nodeAst = ast.getNode();
			for (Entry<FunctionDef, Function> functionMap : getFunctions().entrySet()) {
				Function function = functionMap.getValue();
				FindFunctionCallsVisitor findFunctionCallsVisitor = new FindFunctionCallsVisitor(
						function.getFunctionDef());
				nodeAst.accept(findFunctionCallsVisitor);

				// Get all calls for this function
				List<FunctionCall> functionCalls = findFunctionCallsVisitor.getFunctionCalls();
				for (FunctionCall cs : functionCalls) {
					FindFunctionDefVisitor findFunctionDefVisitor = new FindFunctionDefVisitor();
					cs.accept(new PresenceConditionVisitor());
					cs.accept(findFunctionDefVisitor);
					FunctionDef caller = findFunctionDefVisitor.getFunctionDef();
					cs.setContext(caller);
				}
				function.getFunctionCalls().addAll(functionCalls);

			}

		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(Date commitDate) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		this.version = DATE_FORMAT.format(commitDate);
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	private List<Pair> allPairs;

	public class Pair {
		private List<Function> allFunctionDefs;
		private Id function;

		public Pair(Function fdef, Id function) {
			this.allFunctionDefs = new ArrayList<Function>();
			this.allFunctionDefs.add(fdef);
			this.setFunction(function);
		}

		public Id getFunction() {
			return function;
		}

		public void setFunction(Id function) {
			this.function = function;
		}

		public List<Function> getAllFunctionDefs() {
			return this.allFunctionDefs;
		}

	}

}
