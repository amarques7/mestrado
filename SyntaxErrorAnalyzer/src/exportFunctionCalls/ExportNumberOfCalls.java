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

//					arquivo.add(file + ";" + fdef.getName()+ "@"+ fdef.getPresenceCondition().toTextExpr() +";" + pair.getFunction().getName() + ";"
//						+ pair.getFunction().getPresenceCondition().toTextExpr());
					arquivo.add(file + ";" + fdef.getName() + "@" + fdef.getPresenceCondition().toString() + ";"
							+ pair.getFunction().getName() + ";"
							+ pair.getFunction().getPresenceCondition().toString());

				}

			}
			/* NOVO CODIGO */
//			List<String> arquivoNovo = new ArrayList<>();
//
//			Path path2 = Paths.get(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
//					+ "/results/functionCalls/" + Runner.projectManager.getNumberOfAnalysisOcurred() + "_"
//					+ Runner.projectManager.getCurrentCommit() + "2.csv");
//
//			boolean f2 = Files.exists(path2);
//			if (!f2) {
//
//				arquivoNovo.add("File" + ";" + "Chamador" + ";" + "Chamado" + ";" + "Variabilidade");
//				Files.write(path2, arquivoNovo, Charset.forName("UTF-8"));
//				arquivoNovo.clear();
//
//			}
//			
//			for (Project.Pair pair : allPairs) {
////				String toWrite = pair.getFunction().getName() + ";";
//				for(int i = 0; i< pair.getAllFunctionDefs().size(); i++ )
//				{
//					Function fdef = pair.getFunctionByIndex(i);
//					//System.out.println(pair.getFunction().getName() +"-"+ pair.getAllFunctionDefs().size() +"-"+ pair.getFunction().getPresenceCondition());
//					String file = fdef.file.substring(fdef.file.toString().lastIndexOf("\\") + 1);
//					arquivoNovo.add(file + ";" + fdef.getName() + "@" + fdef.getPresenceCondition().toString() + ";"	
//							+ pair.getFunction().getName() + ";" + pair.getFunction().getPresenceCondition().toString());	
//				}					
//										
//			}
//			
//			Files.write(path2, arquivoNovo, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
//
//			if (arquivoNovo.size() != 0) {
//				ProjectManager.getArquivo2().clear();
//				ProjectManager.getArquivo2().addAll(arquivoNovo);
//			}
//			arquivoNovo.clear();
			//FIM NOVO CODIGO


			
			// ********************
//			try {
//				// new File(main.Main.downloadPath + "results/functionCalls").mkdirs();
//				System.out.println(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject());
//				// CreateFile.create(Runner.projectManager.getPath()+Runner.projectManager.getCurrentProject()
//				// + "results/functionCalls" );
//
//				FileWriter writer = new FileWriter(
//						new File(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
//								+ "/results/functionCalls/" + Runner.projectManager.getNumberOfAnalysisOcurred() + "_"
//								+ Runner.projectManager.getCurrentCommit() + ".txt"));
//
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
//				i++;
//				writer.close();
//			} catch (IOException e) {
//
//			}

			// ************



			if (arquivo.isEmpty() && Runner.projectManager.getNumberOfAnalysisOcurred() != 1) {
//				for (String x : Runner.projectManager.getArquivo2()) {
				arquivo.addAll(Runner.projectManager.getArquivo2());

				// Files.write(path, arquivo, Charset.forName("UTF-8"),
				// StandardOpenOption.APPEND );

//				}
			} else {

				for (String fileVal : Runner.projectManager.getFileValidation()) {
					// Files.write(path, arquivo, Charset.forName("UTF-8"),
					// StandardOpenOption.APPEND);

					if (!Runner.projectManager.getlistModFile().contains(fileVal)) {
						// Runner.projectManager.getFileValidation().remove(fileVal);

						for (String x : Runner.projectManager.getArquivo2()) {
							if (x.indexOf(fileVal) == 0) {
								System.out.println("x: " + x);
								arquivo.add(x);

							}
						}
						// Files.write(path, arquivo, Charset.forName("UTF-8"),
						// StandardOpenOption.APPEND );

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
