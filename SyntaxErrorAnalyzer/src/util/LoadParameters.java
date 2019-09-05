package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import git.AllCommit;

public class LoadParameters {

	// this array is set later
	public static String[] versionsToAnalyseOfEachProject = null;
	// paths define from info.txt
	public static String[] pathsToResults = null;
	// parameters, , colocado no loadParamters
	public static String[] parameters = null;

	
	
	public static void setInfos() {
	
			BufferedReader reader = setReader();
		// carrega o buffer com os parametro do info.txt conforme a quantidade de
		// projeto.
		int numberOfProjects = 0;
		try {
			numberOfProjects = Integer.parseInt(reader.readLine());
		    versionsToAnalyseOfEachProject = new String[numberOfProjects];
			pathsToResults = new String[numberOfProjects];
			parameters = new String[numberOfProjects];

			for (int i = 0; i < numberOfProjects; i++) {
				String projectString[] = reader.readLine().split(" ");
				main.Main.projectPaths.add(projectString[0]);// recebe o caminho web do git
				versionsToAnalyseOfEachProject[i] = projectString[1];// para baixar todos os commits
				pathsToResults[i] = projectString[2];// nome do projeto
				AllCommit.branchToAnalyze = projectString[4]; // branch
				try {
					parameters[i] = projectString[3];// ação
				} catch (Exception e) {
					System.out.println("No arguments on project number " + i);
				}
			}

			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static BufferedReader setReader() {
		// Carrega o buffer com os arquivos do info.txt
		try {
			BufferedReader reader = new BufferedReader(new FileReader(main.Main.PATH + "/info.txt"));
			return reader;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}

	
	public static boolean[] settingsFromParameters() {

		boolean settings[] = { true, true, false, false, false, true };
		// defining arguments
		for (String arguments : parameters) {
			if (arguments == null) {
				System.out.println("Specify -v or -allC");
				return settings;
			}

			for (String argument : arguments.split("-")) {
				if (argument.contains("nodl")) {
					settings[0] = false;
				} else if (argument.contains("allC")) {
					settings[1] = false;
					settings[2] = true;
				} else if (argument.contains("stubs")) {
					settings[3] = true;
				} else if (argument.contains("allR")) {
					settings[1] = false;
					settings[4] = true;
				} else if (argument.contains("nocommit")) {
					settings[5] = false;
				}
			}
		}
		return settings;
	}
}
