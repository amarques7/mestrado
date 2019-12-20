package exportFunctionCalls;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import analysis.core.Function;
import analysis.core.Project;
import mestrado.core.ProjectManager;
import mestrado.core.Runner;
import util.CreatLogs;

public class ExportNumberOfCalls {
	public static int i = 1;
	public static List<String> arquivo;

//metodo que salvas as functionsCalls - alterar ele...
	public static void receive(List<Project.Pair> allPairs) throws IOException {
		new File(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject() + "/results/functionCalls")
				.mkdirs();

		Path path = Paths.get(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
				+ "/results/functionCalls/" + Runner.projectManager.getNumberOfAnalysisOcurred() + "_"
				+ Runner.projectManager.getCurrentCommit() + ".csv");

		arquivo = new ArrayList<>();

		if (allPairs != null) {
			CreatLogs createcsv = new CreatLogs();
			 createcsv.createCSV();
			arquivo.add("File" + ";" + "Chamador" + ";" + "Chamado" + ";" + "Variabilidade");
			for (Project.Pair pair : allPairs) {
				// String toWrite = pair.getFunction().getName() + ";";
				// boolean in = false;
				for (Function fdef : pair.getAllFunctionDefs()) {
					// toWrite = toWrite + fdef.getPresenceCondition().toString() + ";";

					String file = fdef.file.substring(fdef.file.toString().lastIndexOf("\\") + 1);

//					createcsv.createCSVLinhas(file, pair.getFunction().getName(), fdef.getName(),
//							fdef.getPresenceCondition().toString());

					arquivo.add(file + ";" + fdef.getName() + ";" + pair.getFunction().getName() + ";"
							+ fdef.getPresenceCondition().toString());

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

//		if(Runner.projectManager.getNumberOfAnalysisOcurred() == 2) {

			if (arquivo.isEmpty() && Runner.projectManager.getNumberOfAnalysisOcurred() != 1) {
//				for (String x : Runner.projectManager.getArquivo2()) {
				arquivo.addAll(Runner.projectManager.getArquivo2()); // createcsv.createCSVLinhas(x);
//				}
			} else {
							
				for (String fileVal : Runner.projectManager.getFileValidation()) {
					if (!Runner.projectManager.getlistModFile().contains(fileVal)) {
						// Runner.projectManager.getFileValidation().remove(fileVal);

						for (String x : Runner.projectManager.getArquivo2()) {
							if (x.indexOf(fileVal) == 0) {
								// System.out.println("x: " + x);
								arquivo.add(x);

							}
						}
					}
				}

			}

			//System.out.println(arquivo);
			Files.write(path, arquivo, Charset.forName("UTF-8"));
			// createcsv.createCSVLinhas((ArrayList<String>) arquivo);

			if (arquivo.size() != 0) {
				ProjectManager.getArquivo2().clear();
				ProjectManager.getArquivo2().addAll(arquivo);
			}
			arquivo.clear();

		}
	}
}
