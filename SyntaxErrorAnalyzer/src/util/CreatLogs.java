package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVWriter;

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

//		AstLogger.writeaST("File" + ";" + "Chamador" + ";" + "Chamado" + ";" + "Variabilidade", Runner.projectManager.getPath() + Runner.projectManager.getCurrentProject()
//		+ "/results/functionCalls/",Runner.projectManager.getNumberOfAnalysisOcurred() + "_" + Runner.projectManager.getCurrentCommit() + ".csv");

	}

	public void createCSVLinhas(String file, String chamador, String chamado, String variabilidade) {
			
		data.delete(0, data.length());
		directory.delete(0, directory.length());
		fileName.delete(0,fileName.length());
		
		data.append(file + ";" + chamador + ";" + chamado + ";" + variabilidade);
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
