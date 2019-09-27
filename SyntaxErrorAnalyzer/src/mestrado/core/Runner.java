package mestrado.core;

import java.io.IOException;


public class Runner {

	public static ProjectManager projectManager;


	public static void start(String runTimeWorkspacePath) throws IOException, InterruptedException {

		projectManager = new ProjectManager("C:/Users/amarq/git/mestrado/SyntaxErrorAnalyzer/diretorios.txt",
				runTimeWorkspacePath);
		projectManager.loadRepos();
		projectManager.generateVariabilities();

	}

}
