package mestrado.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import mestrado.git.Commit;
import mestrado.git.Repo;
import mestrado.git.RepoFile;
import mestrado.utils.ManipulationUtils;


//import br.ufms.facom.cafeo.utils.ProgressBar;

public class TestCommit {

	static Repo repository;
	static int commitNumber;
	
	static String arquivo;
	static int linha;
	
	public static void main(String[] args) {
		
		repository = new Repo("https://github.com/tgl-dogg/BCC-2s13-PI2-Codigo-de-Honra.git", null);
		
		commitNumber = 2;
		
		
		generateVariabilities();
	}
	

	
	private static void generateVariabilities() {
		
		Commit c = repository.getCommit(repository.getCommitNumber(commitNumber).getId());
				
		repository.checkoutCommit(c.getId());
		
		
													
		for (RepoFile f : c.getTouchedFiles()){								
			if (f.getExtension().equals("c") || f.getExtension().equals("cpp")  || f.getExtension().equals("h") ){
				//File temp = new File((System.getProperty("user.dir") + System.getProperty("file.separator") + "analysis" + System.getProperty("file.separator") + r.getName() +  System.getProperty("file.separator") + c.getId() + System.getProperty("file.separator") + f.getName() + "." + f.getExtension() + ".var"));
				File temp = new File(("C:" + System.getProperty("file.separator") + "analysis" + System.getProperty("file.separator") + repository.getName() +  System.getProperty("file.separator") + c.getId() + System.getProperty("file.separator") + f.getName() + "." + f.getExtension() + ".var"));
				temp.getParentFile().mkdirs();
				
				arquivo = temp.getName();				
				
				try {
					temp.createNewFile();
					
					ManipulationUtils.writeinFile(temp, foo(new File(f.getPath())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	
	}
	
	private static ArrayList<String> foo(File f) {
		
		Scanner input=null;
		
		ArrayList<String> varLine = new ArrayList<String>();
		ArrayList<String> fileLines = new ArrayList<String>();
		Stack<String> stackVar = new Stack<String>();
		
		
		
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		
		while(input.hasNext()) 	
			fileLines.add(input.nextLine());
	
		
		if (!fileLines.isEmpty())
			try {
				foo_helper(fileLines,1,stackVar, varLine);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//System.err.println(f.getAbsolutePath());
				//System.err.println(e.getMessage());
				System.err.println("Deu ruim!  --> " + arquivo + linha);
			}
						
		
		input.close();	
		
		return varLine;
	}
	
	
	private static void foo_helper(ArrayList<String> fileLines, int lineNumber, Stack<String> stackVar, ArrayList<String> varLine) throws Exception{
		
		String line="";
		String var="";
		String[] tokens=null;
		
		if (lineNumber<=fileLines.size()){
			line = fileLines.get(lineNumber-1);
			

			if (arquivo.equals("secular-newton.c.var") && linha==174)
				System.out.println();
			
	
			linha = lineNumber;
	
			tokens = line.split(" ");
		
			
			int tpos=0;
			
			if (!(tokens.length==0 || (tokens.length==1 && (tokens[tpos].isEmpty() || tokens[tpos].trim().isEmpty()))))
				while(tpos<tokens.length && tokens[tpos].trim().isEmpty())
					tpos++;
			
			if (tpos==tokens.length)
				tpos--;
			
			
			if (tokens.length==0 || (tokens.length==1 && (tokens[tpos].isEmpty() || tokens[tpos].trim().isEmpty()))){
				varLine.add("NULL");
				
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
			}
			else if (tokens.length>=2 && tokens[tpos].contains("#if") && tokens[tpos+1].contains("defined")){			
				
				for (int i=tpos+2;i<tokens.length;i++)				
					if (!tokens[i].equals("defined"))
						var = var + tokens[i] + " ";
				
				
				stackVar.push(var);
				varLine.add("NULL");
				
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
				
			}
			else if (tokens[tpos].contains("#ifdef") || tokens[tpos].contains("#if")){
							
				for (int i=tpos+1;i<tokens.length;i++)
					var = var + tokens[i] + " ";
				
				stackVar.push(var);
				varLine.add("NULL");
				
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
				
			}
			else if (tokens[tpos].contains("#elif")){
				
				stackVar.pop();
				
				for (int i=tpos+1;i<tokens.length;i++)
					var = var + tokens[i] + " ";
				
				
				stackVar.push(var);
				
				varLine.add("NULL");			
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
				
			}
			else if (tokens.length>=2 && tokens[tpos].contains("#else") && tokens[tpos].contains("if")){
				
				stackVar.pop();
				
				for (int i=tpos+2;i<tokens.length;i++)
					var = var + tokens[i] + " ";
				
				stackVar.push(var);
				
				varLine.add("NULL");			
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
				
			}		
			else if (tokens[tpos].contains("#else")){	
				
				stackVar.push("!(" + stackVar.pop() + ")");
				
				varLine.add("NULL");			
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
				
			}
			else if (tokens[tpos].contains("#ifndef")){	
							
				for (int i=tpos+1;i<tokens.length;i++)
					var = var + tokens[i] + " ";
				
				stackVar.push("!(" + var + ")");
	
				varLine.add("NULL");			
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
				
			}
			else if (tokens[tpos].contains("#endif")){		
				
				if (!stackVar.isEmpty())
					stackVar.pop();
				else{
					throw new Exception(lineNumber + "\n" + 
										Arrays.toString(stackVar.toArray())
										.replace("[", "")  //remove the right bracket
										.replace("]", "")  //remove the left bracket
										.replace(" )", ")")
										.trim() + "\n");					
				}
				
				varLine.add("NULL");			
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
			}
			else{
				
				if (stackVar.isEmpty())
					varLine.add("TRUE");
				else				
					varLine.add(Arrays.toString(stackVar.toArray())
							.replace("[", "")  //remove the right bracket
						    .replace("]", "")  //remove the left bracket
						    .replace(" )", ")")
						    .trim());           //remove trailing spaces from partially initialized arrays;
				
				foo_helper(fileLines, lineNumber + 1, stackVar, varLine);
				
			}
		}
		
	}

}