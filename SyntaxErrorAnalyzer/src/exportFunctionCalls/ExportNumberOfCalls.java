package exportFunctionCalls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import analysis.core.AstLogger;
import analysis.core.Function;
import analysis.core.Project;
import mestrado.core.ProjectManager;
import mestrado.core.Runner;
import util.CreatLogs;
import util.CreateFile;

public class ExportNumberOfCalls {
	public static int i = 1;
 
//metodo que salvas as functionsCalls - alterar ele...
	public static void receive(List<Project.Pair> allPairs) throws IOException {
		new File(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject() + "/results/functionCalls")
				.mkdirs();

		// esse é o meu cara
//			FileWriter writer = new FileWriter(
//					new File(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
//							+ "/results/functionCalls/" + i + "_" + Runner.projectManager.getCurrentCommit() + ".txt"));

//			CreatLogs createcsv = new CreatLogs();
//			createcsv.createCSV();
//			
//			if (allPairs != null) {
//				for (Project.Pair pair : allPairs) {
//					String toWrite = pair.getFunction().getName() + ";";
//					boolean in = false;
//					for (Function fdef : pair.getAllFunctionDefs()) {
//						toWrite = toWrite + fdef.getPresenceCondition().toString() + ";";
//						in = true;
//					}
//					if (!in)
//						continue;
//					toWrite = toWrite + " @ ";
//					for (Function fdef : pair.getAllFunctionDefs()) {
//						toWrite = toWrite + fdef.getName() + ";";
//					}
//					writer.write(toWrite);
//					writer.write("\n");
//				}
//			}

		if (allPairs != null) {
			CreatLogs createcsv = new CreatLogs();
			createcsv.createCSV();
			for (Project.Pair pair : allPairs) {
				//String toWrite = pair.getFunction().getName() + ";";
				boolean in = false;
				for (Function fdef : pair.getAllFunctionDefs()) {
					// toWrite = toWrite + fdef.getPresenceCondition().toString() + ";";
					in = true;

					String file = fdef.file.substring(fdef.file.toString().lastIndexOf("\\") + 1);

					createcsv.createCSVLinhas(file, pair.getFunction().getName(), fdef.getName(),
							fdef.getPresenceCondition().toString());
				}
//				if (!in)
//					continue;
				// toWrite = toWrite + " @ ";
//				for (Function fdef : pair.getAllFunctionDefs()) {
//					// toWrite = toWrite + fdef.getName() + ";";
//				}

				// writer.write(toWrite);
				// writer.write("\n");
			}
		}

		
//		writer.close();

	}

}
