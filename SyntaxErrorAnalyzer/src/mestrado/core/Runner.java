package mestrado.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.stream.events.StartElement;

import org.apache.poi.xslf.model.geom.Path;
import org.apache.tools.ant.taskdefs.optional.Rpm;

import cdt.handlers.SampleHandler;
import main.Starter;
import mestrado.git.Commit;
import mestrado.git.Repo;
import mestrado.git.RepoFile;
import mestrado.utils.CreateDirectory;
import mestrado.utils.ManipulationUtils;
import mestrado.utils.MoveFile;

public class Runner {

	static File TEMP;
	static int TEMP2;
	static ArrayList<Repo> listofRepos = new ArrayList<Repo>();
//	public static ArrayList<String> modFiles = new ArrayList<String>();

	static String repoList = "";
	static String dir_projeto;
	static String dir_result;
	public static String path =  " ";
	static int i;
	static public String listaProjetos = "a";
	static public String dir_plugin;
	public static String currentProject;
	public static FileReader arquivoLeitura;
	public static boolean noChangesInCFiles = false;
	public static boolean analyseThisTime = true;
	private static IniciarDiretorio iniciarDiretorio;
	
	

	public static BufferedReader setReader() {
		// Carrega o buffer com os arquivos do info.txt
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:/Users/amarq/git/mestrado/SyntaxErrorAnalyzer/diretorios.txt"));
			return reader;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void start(String runTimeWorkspacePath) throws IOException, InterruptedException {
		path = runTimeWorkspacePath;
		
		iniciarDiretorio = new IniciarDiretorio("C:/Users/amarq/git/mestrado/SyntaxErrorAnalyzer/diretorios.txt");
		
//		BufferedReader reader = setReader();
		
		BufferedReader reader = iniciarDiretorio.getReader();
		try {
			dir_projeto = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dir_result = reader.readLine();
		dir_plugin = reader.readLine();
		repoList = reader.readLine();
		System.out.println(" dir_result" + dir_result);
		System.out.println("dir_plugin:" + dir_plugin);
		System.out.println("repoList: " + repoList);
//		if (args.length == 0)
//			repoList = leitorArquivo.readLine();
//		else
//			repoList = args[0];
		loadRepos(ManipulationUtils.loadRepos(repoList));
		generateVariabilities();

	}

	private static void generateVariabilities() throws IOException, InterruptedException {

		int count = 0;
		currentProject = null;
		// CreateDirectory.setWriter(dir_plugin + "analysis");
		if (!listofRepos.isEmpty()) {
			for (Repo r : listofRepos) {

				count = 0;
				System.out.println();
				System.out.println("Analyzing " + r.getName() + "... ");
				currentProject = r.getName();
				SampleHandler.PROJECT = currentProject;
				// CreateDirectory.setWriter(dir_plugin + currentProject);
				CreateDirectory.setWriter(dir_plugin + currentProject + "\\analysis");
				//essa função cria os arquivos platform.h e stubs.h 
				Starter analyser = new Starter(dir_plugin + currentProject +"\\", false);
	
				
				if (listaProjetos.equals("a")) {
					listaProjetos = r.getName();
				} else {
					listaProjetos = listaProjetos + r.getName();
				}
				System.out.println();

				if (!r.getCommitList().isEmpty()) {

					File diretorio = new File((dir_result + System.getProperty("file.separator") + r.getName()
							+ System.getProperty("file.separator")));
					diretorio.mkdirs();

					// traz os commit
					for (Commit c : r.getCommitList()) {
						count++;
						r.checkoutCommit(c.getId());
						System.out.println("Análise commit: " + count);
						String arquivoMod = null;
						ArrayList<String> modFiles = new ArrayList<String>();

						// traz os arquivo modificados
						for (RepoFile f : c.getTouchedFiles()) {
							if (f.getExtension().equals("c")) {

								arquivoMod = f.getPath().replace("/", "\\");
								
								File file = new File(f.getPath().replace("/","\\"));
								System.out.println("file:" + file);
						//		modFiles.add(f.getPath().replace("/","\\"));
								modFiles.add(dir_plugin + currentProject + "\\" + "analysis" + "\\" + f.getName() + ".c");
								System.out.println("arqui mod: " + arquivoMod);
								
								noChangesInCFiles = true;
								MoveFile.copyFileUsingChannel(file, (new File(
										dir_plugin + currentProject + "\\" + "analysis" + "\\" + f.getName() + ".c")));
								// chamar o cproje

							}

						}
						// verificar o pq a copia de arquivo ta dando ruim.

						if (noChangesInCFiles) {
							System.out.println("qtd modFiles: " + modFiles.size());
							System.out.println("Copiado os arquivos do commit: " + count);
							// MoveFile.copy(modFiles, dir_plugin + "analysis");
							// ClearDirectory.remover(new File(dir_projeto + r.getName()));
							
							Starter.start(modFiles);
							noChangesInCFiles = false;
						} else {
							System.out.println("não há arquivos alterado  no commit: " + count);
						}
						// ClearDirectory.remover(new File(dir_plugin +"analysis"));
//						if (count == 5) {
//							System.exit(0);
//						}

					}
				}

			}
		}
		System.out.println();
		System.out.println("Analise dos projetos: " + listaProjetos + ", foi concluido com sucesso!!!");
//		for(String x : modFiles)
		// System.out.println(x);

		System.out.println("acabei");

	}

	private static void loadRepos(ArrayList<String> repos) {
		for (String repoURI : repos)
			listofRepos.add(new Repo(repoURI, dir_projeto));
	}
}
