package mestrado.parser;

import java.util.Arrays;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Scanner;
import java.util.Stack;

//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.internal.storage.file.FileRepository;
//import org.eclipse.jgit.lib.Repository;

//import br.ufms.facom.cafeo.git.Commit;
//import br.ufms.facom.cafeo.git.Repo;
//import br.ufms.facom.cafeo.git.RepoFile;


public class LineParser {
	
	Stack<String> variabilities = new Stack<String>();
	
	String varIf="";

	public String findVariability(String nextLine) throws Exception {
		
		String[] tokens = nextLine.split(" ");
		String var="";
		
		
		if (tokens.length==0){
			return "NULL";
		}
			
				
		if (tokens[0].equals("#ifdef") || tokens[0].equals("#if")){
			
			varIf = "";
			
			for (int i=1;i<tokens.length;i++)
				var = var + tokens[i] + " ";
			
			variabilities.push(var);
			
			return "NULL";
			
		}
		else if (tokens[0].equals("#elif")){
			
			varIf = variabilities.pop();
			
			for (int i=1;i<tokens.length;i++)
				var = var + tokens[i] + " ";
			
			
			variabilities.push(var);
			
			return "NULL";
			
		}
		else if (tokens.length>=2 && tokens[0].equals("#else") && tokens[1].equals("if")){
			
			varIf = variabilities.pop();
			
			for (int i=2;i<tokens.length;i++)
				var = var + tokens[i] + " ";
			
			variabilities.push(var);
			
			return "NULL";
			
		}
		else if (tokens.length>=2 && tokens[0].equals("#if") && tokens[1].equals("defined")){
			//variabilities.pop();
			
			varIf = "";
			
			for (int i=2;i<tokens.length;i++)				
				if (!tokens[i].equals("defined"))
					var = var + tokens[i] + " ";
			
			
			variabilities.push(var);
			
			return "NULL";
			
		}
		else if (tokens[0].equals("#else")){	
			
			varIf = variabilities.pop();
			
			variabilities.push("!(" + varIf + ")");
			
			return "NULL";
			
		}
		else if (tokens[0].equals("#ifndef")){	
			
			varIf = "";
			
			for (int i=1;i<tokens.length;i++)
				var = var + tokens[i] + " ";
			
			variabilities.push("!(" + var + ")");
			
			return "NULL";
			
		}
		else if (tokens[0].equals("#endif")){		
			
			if (!varIf.isEmpty())
				variabilities.push(varIf);
			
			varIf = "";
			
			if (!variabilities.isEmpty())
				variabilities.pop();
			else
				throw new Exception();
			
			return "NULL";
			
		}
		else{
			
			if (variabilities.empty())
				return "TRUE";
			else
				return Arrays.toString(variabilities.toArray())
						.replace("[", "")  //remove the right bracket
					    .replace("]", "")  //remove the left bracket
					    .replace(" )", ")")
					    .trim();           //remove trailing spaces from partially initialized arrays;
			
		}
		
		
	}

}
