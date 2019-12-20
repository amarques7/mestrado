package util;


import java.io.File;


import analysis.core.AstLogger;
import mestrado.core.Runner;
import mestrado.utils.CreateDirectory;

public class CreatLogs {
	StringBuilder data = new StringBuilder();
	StringBuilder directory = new StringBuilder();
	StringBuilder fileName = new StringBuilder();

	public void CreateLog() {

	
		CreateDirectory.setWriter(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject()
				+ File.separator + "analysis");
		CreateDirectory.setWriter(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject()
				+ File.separator + "results");
		CreateDirectory.setWriter(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject()
				+ File.separator + "results" + File.separator + "errorPath");
		
		data.delete(0, data.length());
		directory.delete(0, directory.length());
		fileName.delete(0,fileName.length());
		
		data.append("COMMIT" + ";" + "HASH " + ";" + "ARQUIVOS.C" + ";" + "ARQUIVO.C ALTERADO " + ";" + "ERRO");
		directory.append(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject()
				+ File.separator + "results" + File.separator);
		fileName.append(Runner.projectManager.getCurrentProject() + ".csv");

		AstLogger.writeaST(data, directory, fileName);
		
		data.delete(0, data.length());
		directory.delete(0, directory.length());
		fileName.delete(0,fileName.length());
		 
		data.append("PROJETO" + ";" + "HORAS" + ";" + "MINUTOS" + ";" + "SEGUNDOS");
		directory.append("C://error" + File.separator);
		fileName.append("Tempo_execução" + ".csv");

		AstLogger.writeaST(data, directory, fileName);
		
		data.delete(0, data.length());
		directory.delete(0, directory.length());
		fileName.delete(0,fileName.length());
	}

	public void createCSV() {

		data.delete(0, data.length());
		directory.delete(0, directory.length());
		fileName.delete(0,fileName.length());
		
		data.append("File" + ";" + "Chamador" + ";" + "Chamado" + ";" + "Variabilidade");
		directory.append(Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
				+ "/results/functionCalls/");
		fileName.append(Runner.projectManager.getNumberOfAnalysisOcurred() + "_"
				+ Runner.projectManager.getCurrentCommit() + ".csv");

		AstLogger.writeaST(data, directory, fileName);
		
		data.delete(0, data.length());
		directory.delete(0, directory.length());
		fileName.delete(0,fileName.length());
	}

}
