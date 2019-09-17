package mestrado.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ManipulationUtils {
	
	private static ArrayList<String> reposURI = new ArrayList<String>();
	
	public static ArrayList<String> loadRepos(String file){
		
		File reposFile = new File(file);
		Scanner input = null;
		
		try {
			input = new Scanner(reposFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(input.hasNext()) {		    
			String repoURI = input.nextLine();
			reposURI.add(repoURI);
		}
		input.close();
		
		return reposURI;
	}
	
	public static void writeinFile(File f, ArrayList<String> content) throws IOException{
				
		FileWriter fileWriter = new FileWriter(f.getAbsolutePath());
		
		for (String line:content)			
			fileWriter.write(line + "\n");
				
	    fileWriter.close();	
	}
}
