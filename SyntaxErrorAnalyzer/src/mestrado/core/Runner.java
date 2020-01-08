package mestrado.core;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;


public class Runner {

	public static ProjectManager projectManager;

	public static void start(String runTimeWorkspacePath) throws IOException, InterruptedException, NoFilepatternException, GitAPIException {

		projectManager = new ProjectManager("C:/Users/mac-01/git/mestrado/SyntaxErrorAnalyzer/diretorios.txt",
				runTimeWorkspacePath);
		projectManager.loadRepos();
		projectManager.generateVariabilities();
		
	}

}
