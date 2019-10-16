package mestrado.core;

import java.io.IOException;
import java.io.PrintStream;


public class Runner {

	public static ProjectManager projectManager;


	public static void start(String runTimeWorkspacePath) throws IOException, InterruptedException {

		projectManager = new ProjectManager("C:/Users/amarq/git/mst/SyntaxErrorAnalyzer/diretorios.txt",
				runTimeWorkspacePath);
		projectManager.loadRepos();
		projectManager.generateVariabilities();
		
	}

}
