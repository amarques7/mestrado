package exportFunctionCalls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import analysis.core.Function;
import analysis.core.Project;
import mestrado.core.ProjectManager;
import mestrado.core.Runner;
import util.CreateFile;

public class ExportNumberOfCalls {
	public static int i = 1;

	// main.Main.currentCommitId
//	public static ProjectManager projectManager;
	public static void receive(List<Project.Pair> allPairs) {
		try {
		//	System.out.println(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
	//				+ "/results/functionCalls");

			new File(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
					+ "/results/functionCalls").mkdirs();
			// esse é o meu cara

			FileWriter writer = new FileWriter(
					new File(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
							+ "/results/functionCalls/" + i + "_" + Runner.projectManager.getCurrentCommit() + ".txt"));

			if (allPairs != null) {
				for (Project.Pair pair : allPairs) {
					String toWrite = pair.getFunction().getName() + ";";
					boolean in = false;
					for (Function fdef : pair.getAllFunctionDefs()) {
						toWrite = toWrite + fdef.getPresenceCondition().toString() + ";";
						in = true;
					}
					if (!in)
						continue;
					toWrite = toWrite + " @ ";
					for (Function fdef : pair.getAllFunctionDefs()) {
						toWrite = toWrite + fdef.getName() + ";";
					}
					writer.write(toWrite);
					writer.write("\n");
				}
			}

			i++;
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
