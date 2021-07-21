package exportFunctionCalls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOExceptionWithCause;

import analysis.core.Function;
import analysis.core.Project;
import mestrado.core.ProjectManager;
import mestrado.core.Runner;
import util.CreatLogs;
import util.CreateFile;

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
			boolean f = Files.exists(path);
			if (!f) {
				arquivo.add("File" + ";" + "Chamador" + ";" + "Chamado" + ";" + "Variabilidade");
				Files.write(path, arquivo, Charset.forName("UTF-8"));
				arquivo.clear();
			}
			for (Project.Pair pair : allPairs) {
				for (Function fdef : pair.getAllFunctionDefs()) {
					String file = fdef.file.substring(fdef.file.toString().lastIndexOf("\\") + 1);
					arquivo.add(file + ";" + fdef.getName() + "@" + fdef.getPresenceCondition().toString() + ";"
							+ pair.getFunction().getName() + ";"
							+ pair.getFunction().getPresenceCondition().toString());
				}
			}
			if (arquivo.isEmpty() && Runner.projectManager.getNumberOfAnalysisOcurred() != 1) {
				arquivo.addAll(Runner.projectManager.getArquivo2());
			} else {
				for (String fileVal : Runner.projectManager.getFileValidation()) {
					if (!Runner.projectManager.getlistModFile().contains(fileVal)) {
						for (String x : Runner.projectManager.getArquivo2()) {
							if (x.indexOf(fileVal) == 0) {
								arquivo.add(x);
							}
						}
					}
				}
			}

			Files.write(path, arquivo, Charset.forName("UTF-8"), StandardOpenOption.APPEND);

			if (arquivo.size() != 0) {
				ProjectManager.getArquivo2().clear();
				ProjectManager.getArquivo2().addAll(arquivo);
			}
			arquivo.clear();	
		}
		
	}
}
