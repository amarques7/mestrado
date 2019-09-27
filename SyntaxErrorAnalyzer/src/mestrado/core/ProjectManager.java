package mestrado.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import analysis.core.AstLogger;
import analysis.core.Project;
import cdt.handlers.SampleHandler;
import main.Starter;
import mestrado.git.Commit;
import mestrado.git.Repo;
import mestrado.git.RepoFile;
import mestrado.utils.CreateDirectory;
import mestrado.utils.ManipulationUtils;
import mestrado.utils.MoveFile;

public class ProjectManager {

	private String dirProjetct;
	private String dirResult;
	private String dirPlugin;
	private String repoList;
	private ArrayList<Repo> listofRepos;
	private String currentProject;
	private String listaProjetos;
	private BufferedReader reader;
	private String currentCommit;
	private boolean noChangesInCFiles;
	private String path;
	private boolean analyseThisTime;
	private Commit commitAtual;
	private int numberOfAnalysisOcurred;
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	private List<String> allCommitsThisAnalysis;
	public Repo repo;
	private long startTime;
	
	private List<String> commitsIdToAnalyse;
	
	public Commit getCommitAtual() {
		return commitAtual;
	}

	public void setCommitAtual(Commit commitAtual) {
		this.commitAtual = commitAtual;
	}

	public ArrayList<Repo> getListofRepos() {
		return listofRepos;
	}

	public void setListofRepos(ArrayList<Repo> listofRepos) {
		this.listofRepos = listofRepos;
	}

	public String getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(String currentProject) {
		this.currentProject = currentProject;
	}

	public String getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(String listaProjetos) {
		this.listaProjetos = listaProjetos;
	}

	public String getCurrentCommit() {
		return currentCommit;
	}

	public void setCurrentCommit(String currentCommit) {
		this.currentCommit = currentCommit;
	}

	public boolean isNoChangesInCFiles() {
		return noChangesInCFiles;
	}

	public void setNoChangesInCFiles(boolean noChangesInCFiles) {
		this.noChangesInCFiles = noChangesInCFiles;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ProjectManager(String pathFrom, String runTimeWorkspacePath) {
		super();

		listofRepos = new ArrayList<Repo>();
		currentProject = null;
		listaProjetos = "a";
		currentCommit = "";
		noChangesInCFiles = false;
		analyseThisTime = true;
		path = runTimeWorkspacePath;
		numberOfAnalysisOcurred = 0;

		try {
			generateReader(pathFrom);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void generateReader(String pathFrom) throws IOException {
		try {
			reader = new BufferedReader(new FileReader(pathFrom));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		dirProjetct = reader.readLine();
		dirResult = reader.readLine();
		dirPlugin = reader.readLine();
		repoList = reader.readLine();
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public String getDirProjetct() {
		return dirProjetct;
	}

	public void setDirProjetct(String dirProjetct) {
		this.dirProjetct = dirProjetct;
	}

	public String getDirResult() {
		return dirResult;
	}

	public void setDirResult(String dirResult) {
		this.dirResult = dirResult;
	}

	public String getDirPlugin() {
		return dirPlugin;
	}

	public void setDirPlugin(String dirPlugin) {
		this.dirPlugin = dirPlugin;
	}

	public String getRepoList() {
		return repoList;
	}

	public void setRepoList(String repoList) {
		this.repoList = repoList;
	}
	
	public boolean isAnalyseThisTime() {
		return analyseThisTime;
	}

	public void setAnalyseThisTime(boolean analyseThisTime) {
		this.analyseThisTime = analyseThisTime;
	}

	public void loadRepos() {
		ArrayList<String> repos = ManipulationUtils.loadRepos(repoList);

		for (String repoURI : repos) {
			try {
				Repo a = new Repo(repoURI, dirProjetct);
				listofRepos.add(a);
			} catch (Exception e) {
				// TODO: handle exception
			}
//			listofRepos.add(new Repo(repoURI, dirProjetct));	
		}
	}

	public boolean listaRepositorioVazia() {
		return listofRepos.isEmpty();
	}

	public ArrayList<Repo> getListaRepos() {
		// TODO Auto-generated method stub
		return listofRepos;
	}

	public void generateVariabilities() throws IOException, InterruptedException {
		
		//int count = 0;
		commitsIdToAnalyse = new ArrayList<String>();
		
		if (!listaRepositorioVazia()) {
			
			
			for (Repo r : listofRepos) {
				startTime = System.nanoTime();
				
				numberOfAnalysisOcurred = 0;
				System.out.println();
				System.out.println("Analyzing " + r.getName() + "... "); 
				//System.out.println("-1 Number of commits: "+ repo.getNumberofCommits());
				currentProject = r.getName();
				SampleHandler.PROJECT = r.getName();
				// CreateDirectory.setWriter(dir_plugin + currentProject);
				CreateDirectory.setWriter(dirPlugin + currentProject + "\\analysis");
				CreateDirectory.setWriter(dirPlugin + currentProject + "\\results");
				CreateDirectory.setWriter(dirPlugin + currentProject + "\\results\\errorPath");
				// essa função cria os arquivos platform.h e stubs.h
				Starter analyser = new Starter(dirPlugin + currentProject + "\\", false);
				//new Starter(dirPlugin + currentProject + "\\", false);
		//		System.out.println("-1 Number of commits: "+ repo.getNumberofCommits());
				
				if (listaProjetos.equals("a")) {
					listaProjetos = r.getName();
				} else {
					listaProjetos = listaProjetos + r.getName();
				}
				System.out.println();

				if (!r.getCommitList().isEmpty()) {
					
					File diretorio = new File((dirResult + System.getProperty("file.separator") + r.getName()
							+ System.getProperty("file.separator")));
					diretorio.mkdirs();
//					for (Commit c : r.getCommitList()) {
//						commitsIdToAnalyse.add(c.getId());					
//						System.out.println("ciommi: " + commitsIdToAnalyse);
//					}

					// traz os commit
					for (Commit c : r.getCommitList()) {
						numberOfAnalysisOcurred ++;
						commitAtual = c;
						AstLogger.write("\nAnalysis number:" + numberOfAnalysisOcurred );
						currentCommit = c.getId();
						r.checkoutCommit(c.getId());
						System.out.println("teste na projectMa id:" + c.getId());
						System.out.println("Análise commit: " + numberOfAnalysisOcurred);
						String arquivoMod = null;
						ArrayList<String> modFiles = new ArrayList<String>();
						
						try {
							// this file needs to be deleted for the next analysis
							System.out.println(dirPlugin + currentProject + "\\temp2.c");
							Files.delete(new File(dirPlugin + currentCommit + "\\temp2.c")
									.toPath());
						} catch (Exception e) {
							// in case of the file doesnt exist
						}
						
						for (RepoFile f : c.getTouchedFiles()) {
							if (f.getExtension().equals("c")) {

								arquivoMod = f.getPath().replace("/", "\\");

								File file = new File(f.getPath().replace("/", "\\"));
								System.out.println("file:" + file);
								// modFiles.add(f.getPath().replace("/","\\"));
								modFiles.add(
										dirPlugin + currentProject + "\\" + "analysis" + "\\" + f.getName() + ".c");
								System.out.println("arqui mod: " + arquivoMod);

								this.noChangesInCFiles = true;// por causa dessa variavel nao estou entrando no anlayser
								
								MoveFile.copyFileUsingChannel(file, (new File(
										dirPlugin + currentProject + "\\" + "analysis" + "\\" + f.getName() + ".c")));
								// chamar o cproje

							}

						}
						// verificar o pq a copia de arquivo ta dando ruim.

//						if (noChangesInCFiles) {
							System.out.println("qtd modFiles: " + modFiles.size());
							System.out.println("Copiado os arquivos do commit: " + numberOfAnalysisOcurred);
							// MoveFile.copy(modFiles, dir_plugin + "analysis");
							// ClearDirectory.remover(new File(dir_projeto + r.getName()));
							
							Project project = null;
							project = analyser.start(modFiles);// esta assim no codigo velho, será que o project é um objeto??
							//Starter.start(modFiles);
							noChangesInCFiles = false;
							
							deleteAllFromAnalysisFolder();
//						} else {
//							System.out.println("não há arquivos alterado  no commit: " + numberOfAnalysisOcurred);
//						}
						// ClearDirectory.remover(new File(dir_plugin +"analysis"));
//						if (count == 5) {
//							System.exit(0);
//						}

					}
				}

			}
			long elapsedTime = System.nanoTime() - startTime;
			System.out.println(
					"Analysis ended in " + TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " seconds.");
		}
		System.out.println();
		System.out.println("Analise dos projetos: " + listaProjetos + ", foi concluido com sucesso!!!");
//		for(String x : modFiles)
		// System.out.println(x);

		System.out.println("acabei");

	}

	public int getIndexOfPastAnalysis() throws InterruptedException {
		File pathToErro = new File(path + "/results/csv/Variabilities/ProgramWeight");
		pathToErro.mkdirs();
		File[] allAnalysis = pathToErro.listFiles();

		System.out.println("AAL :" + allAnalysis);

		if (allAnalysis == null) {
			return -1;
		} else {
			return allAnalysis.length;
		}
	}
	public String getReturnCurrentCommit() {
		String data = "";
	
		try {
			data =DATE_FORMAT.format(Runner.projectManager.getCommitAtual().getTimestamp());
		} catch (Exception e) {
			data = e.getMessage();
		} 
		
		return data;
		
	}
		
	private static void deleteAllFromAnalysisFolder() {
		try {
			String x = Runner.projectManager.dirPlugin + Runner.projectManager.currentProject + "/analysis";
			System.out.println(x); 
			
			FileUtils.cleanDirectory(new File(Runner.projectManager.dirPlugin + Runner.projectManager.currentProject + "/analysis"));
		} catch (IOException e) {
			System.out.println("Analysis folder not found to delete!");
		}
	}



	public List<String> getAllCommitsThisAnalysis() {
		return allCommitsThisAnalysis;
	}

	public void setAllCommitsThisAnalysis(List<String> allCommitsThisAnalysis) {
		this.allCommitsThisAnalysis = allCommitsThisAnalysis;
	}

	public int getNumberOfAnalysisOcurred() {
		return numberOfAnalysisOcurred;
	}

	public void setNumberOfAnalysisOcurred(int numberOfAnalysisOcurred) {
		this.numberOfAnalysisOcurred = numberOfAnalysisOcurred;
	}
}
