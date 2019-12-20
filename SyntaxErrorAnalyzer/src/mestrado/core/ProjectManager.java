package mestrado.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;

import analysis.core.AstLogger;
import analysis.core.Project;
import cdt.handlers.SampleHandler;
import main.Starter;
import mestrado.git.Commit;
import mestrado.git.DiffFilesGit;
import mestrado.git.Repo;
import mestrado.utils.ListFilesC;
import mestrado.utils.ManipulationUtils;
import mestrado.utils.MoveFile;
import util.CreatLogs;
import util.TransformarSegundaParaHoras;

public class ProjectManager {

	private String dirProject;
	private String dirPlugin;
	private String repoList;
	private String currentCommit;
	private String path;
	private String currentProject;
	private String listaProjetos;
	private String currentFile;
	private String data = "";
	private String logControl;
	
	private String lastCommitAnalysed = "";

	private StringBuilder dataText = new StringBuilder();
	private StringBuilder directory = new StringBuilder();
	private StringBuilder fileName = new StringBuilder();
	
	public HashSet<String> fileValidation;
	public static  List<String> arquivo2;
	private ArrayList<Repo> listofRepos;
	private List<String> allCommitsThisAnalysis;
	private BufferedReader reader;

	private boolean noChangesInCFiles;
	private boolean analyseThisTime;

	private Commit commitAtual;
	public Repo repo;
	private long startTime;
	private long startTime2;
	private int totalArqPro;
	private int numberOfAnalysisOcurred;
	public HashSet<String> listModFile;
	public HashSet<String> errorFiles;
	private HashSet<String> notModFilesaux;
	private HashSet<String> notModFiles;
	private HashSet<String> modFiles;

	
	
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

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
		totalArqPro = 0;
		currentFile = null;

		
		 dataText = new StringBuilder();
		 directory = new StringBuilder();
		 fileName = new StringBuilder();
		

		listModFile = new HashSet<String>();
		errorFiles = new HashSet<String>();
		notModFilesaux = new HashSet<String>();
		modFiles = new HashSet<String>();
		notModFiles = new HashSet<String>();
		fileValidation = new HashSet<String>();
		arquivo2 = new ArrayList<String>();
		
		try {
			generateReader(pathFrom);
		} catch (Exception e) {
			System.out.println("error em generateReader" + e.getMessage());
		}
	}

	private void generateReader(String pathFrom) throws IOException {
		try {
			reader = new BufferedReader(new FileReader(pathFrom));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dirProject = reader.readLine();
		dirPlugin = reader.readLine();
		repoList = reader.readLine();
	}

	public void loadRepos() throws NoFilepatternException, GitAPIException {
		ArrayList<String> repos = ManipulationUtils.loadRepos(repoList);

		for (String repoURI : repos) {
			try {
				Repo a = new Repo(repoURI, dirProject);
				listofRepos.add(a);
			} catch (Exception e) {
				listofRepos.add(new Repo(repoURI, Runner.projectManager.dirProject));
			}
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

//		commitsIdToAnalyse = new HashSet<String>();
		CreatLogs create = new CreatLogs();
				

		if (!listaRepositorioVazia()) {
			for (Repo r : listofRepos) {

				startTime = System.nanoTime();
				numberOfAnalysisOcurred = 0;
				System.out.println();
				System.out.println("Analyzing " + r.getName() + "... ");

				currentProject = r.getName();
				SampleHandler.PROJECT = r.getName();

				create.CreateLog();
				directory.append(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject()+ File.separator + "results");
				fileName.append(Runner.projectManager.getCurrentProject() + ".csv");
				
				// essa função cria os arquivos platform.h e stubs.h
				Starter analyser = new Starter(dirPlugin + currentProject + File.separator, false);

				if (listaProjetos.equals("a")) {
					listaProjetos = r.getName();
				} else {
					listaProjetos = listaProjetos + r.getName();
				}
				System.out.println();

				if (!r.getCommitList().isEmpty()) {

					numberOfAnalysisOcurred = 0;
					// traz os commit
					lastCommitAnalysed = "";
					for (Commit c : r.getCommitList()) {
						
						startTime2 = System.nanoTime();
						deleteAllFromAnalysisFolder();
						numberOfAnalysisOcurred++;
						commitAtual = c;

						currentCommit = c.getId();
						r.checkoutCommit(c.getId());

						System.out.println("Análise commit: " + numberOfAnalysisOcurred);

						ListFilesC contArq = new ListFilesC();
						File fileI = new File(dirProject + currentProject);

						int QtdArqOfCommit = contArq.findFiles(fileI, ".*\\.c").size();
						System.out.println("a quantidade de arquivo no commit " + numberOfAnalysisOcurred + ", é: "
								+ QtdArqOfCommit);

						if (!lastCommitAnalysed.equals("")) {
							List<String> filesModifiedRightPath = new ArrayList<String>();
							// get the difference between the last commit and the new
							try {
								filesModifiedRightPath.clear();
								filesModifiedRightPath = DiffFilesGit.diffFilesInCommits("c:\\Projeto"+ File.separator+Runner.projectManager.getCurrentProject());
						
								List<File> file = contArq.findFiles(fileI, ".*\\.c");

								if (!filesModifiedRightPath.isEmpty()) {
									for (File k : file) {
										for (String x : filesModifiedRightPath) {
											File y = new File(x);

											if (y.getName().equals(k.getName())
													&& !modFiles.contains(dirPlugin + currentProject + File.separator
															+ "analysis" + File.separator + y.getName())) {
												this.noChangesInCFiles = true;

												modFiles.add(dirPlugin + currentProject + File.separator + "analysis"
														+ File.separator + y.getName());

												MoveFile.copyFileUsingChannel(k, (new File(dirPlugin + currentProject
														+ File.separator + "analysis" + File.separator + y.getName())));
												currentFile = y.getName();
												listModFile.add(y.getName());
												break;

											}
										}
									}
								}

							} catch (GitAPIException e) {
								e.printStackTrace();
							}

							lastCommitAnalysed = currentCommit;
						}
						/// trata o primeiro commit
						else {
							List<File> file = contArq.findFiles(fileI, ".*\\.c");
							modFiles.clear();
							notModFiles.clear();
							errorFiles.clear();

							// adiciona os arquivos modificados do primeiro commit para o modFiles
							if (!file.isEmpty()) {
								for (File x : file) {
									modFiles.add(dirPlugin + currentProject + File.separator + "analysis"
											+ File.separator + x.getName());
									MoveFile.copyFileUsingChannel(x, (new File(dirPlugin + currentProject
											+ File.separator + "analysis" + File.separator + x.getName())));
									currentFile = x.getName();
									listModFile.add(x.getName());
									
								}
							
							}
							
							this.noChangesInCFiles = true;
							lastCommitAnalysed = currentCommit;
						}
						logControl = numberOfAnalysisOcurred + ";" + c.getId();
						
		
						// trata quando nenhum arquivo foi alterado, no primeiro commit
						if (modFiles.size() == 0) {
							if (numberOfAnalysisOcurred == 1) {
								dataText.delete(0,dataText.length());
								dataText.append(numberOfAnalysisOcurred + ";" + c.getId() + ";" + " " + ";" + " " + ";" + " ");
								AstLogger.writeaST(dataText, directory, fileName);
							}
						}
						// pega do os arquivos em disco para depois retirar os repetidos.
						List<File> file = contArq.findFiles(fileI, ".*\\.c");
						for (File f : file) {
							String aux = dirPlugin + currentProject+ File.separator + "analysis"+ File.separator + f.getName();
							if (!modFiles.contains(aux))
								notModFiles.add(f.getName());
						}
						System.out.println("qtd modFiles: " + modFiles.size());
						System.out.println("qtd notModFiles: " + notModFiles.size());

						if(modFiles.isEmpty())
							this.noChangesInCFiles = false;
//						if(numberOfAnalysisOcurred == 162)
//							System.out.println("cheguei: " + numberOfAnalysisOcurred);
						
						Project project = analyser.start(modFiles);

						for (String f : notModFiles) {
							
							if(errorFiles.contains(f)) {
								dataText.delete(0,dataText.length());
								dataText.append(numberOfAnalysisOcurred + ";" + c.getId() + ";" + f + ";" + "0" + ";"
										+ "1");
								AstLogger.writeaST(dataText, directory, fileName);
								
								
						}else {
									dataText.delete(0,dataText.length());
									dataText.append(numberOfAnalysisOcurred + ";" + c.getId() + ";" + f + ";" + "0" + ";" + "0");
									AstLogger.writeaST(dataText, directory, fileName);
									fileValidation.add(f);
								}
							}
						
						noChangesInCFiles = false;
						modFiles.clear();
						notModFilesaux.clear();
						notModFiles.clear();
						listModFile.clear();
						try {
							Files.delete(new File(dirPlugin + currentProject + "\\temp2.c").toPath());
							System.out.println("//------------------------------//");
						} catch (Exception e) {
							// in case of the file doesnt exist
							System.out.println("O arquivo não existe: " + e.getMessage());
						}
//						if (numberOfAnalysisOcurred == 25) {
//							System.exit(0);
//						}
						Files.delete(new File(dirPlugin + currentProject + File.separator +"platform.h").toPath());
						Files.delete(new File(dirPlugin + currentProject + File.separator +"include" + File.separator+"stubs.h").toPath());
						
						long elapsedTimes2 = System.nanoTime() - startTime2;
						long seg = TimeUnit.SECONDS.convert(elapsedTimes2, TimeUnit.NANOSECONDS);
						
						System.out.println("Tempo do commit " + TimeUnit.SECONDS.convert(elapsedTimes2, TimeUnit.NANOSECONDS)
								+ " seconds.");
						

						
						System.gc();
					}
					
				}
				long elapsedTime = System.nanoTime() - startTime;
				System.out.println("Analysis ended in " + TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)
						+ " seconds.");
				long segundos = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
				TransformarSegundaParaHoras x = new TransformarSegundaParaHoras();
				x.transforma(segundos, Runner.projectManager.getCurrentProject());
			}
			System.out.println();
			System.out.println("Analise dos projetos: " + listaProjetos + ", foi concluido com sucesso!!!");
			System.out.println("acabei");
		}
	}

	public int getIndexOfPastAnalysis() throws InterruptedException {
		File pathToErro = new File(path + "/results/csv/Variabilities/ProgramWeight");
		pathToErro.mkdirs();
		File[] allAnalysis = pathToErro.listFiles();

		if (allAnalysis == null) {
			return -1;
		} else {
			return allAnalysis.length;
		}
	}

	public String getReturnCurrentCommit() {
		data = "";

		try {
			data = DATE_FORMAT.format(Runner.projectManager.getCommitAtual().getTimestamp());
		} catch (Exception e) {
			data = e.getMessage();
		}

		return data;
	}

	public static void deleteAllFromAnalysisFolder() {
		try {
			FileUtils.cleanDirectory(
					new File(Runner.projectManager.dirPlugin + Runner.projectManager.currentProject + "/analysis"));
		} catch (IOException e) {
			System.out.println("Analysis folder not found to delete!");
		}
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public String getDirProjetct() {
		return dirProject;
	}

	public void setDirProjetct(String dirProjetct) {
		this.dirProject = dirProjetct;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
	}

	public int getTotalArqPro() {
		return totalArqPro;
	}

	public void setTotalArqPro(int totalArqPro) {
		this.totalArqPro = totalArqPro;
	}

	public String getLogControl() {
		return logControl;
	}

	public void setLogControl(String logControl) {
		this.logControl = logControl;
	}

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

	public HashSet<String> getlistModFile() {
		return listModFile;
	}

	public void setlistModFile(HashSet<String> listModFile) {
		this.listModFile = listModFile;
	}

	public HashSet<String> getNotModFilesaux() {
		return notModFilesaux;
	}

	public HashSet<String> getModFiles() {
		return modFiles;
	}

	public void setModFiles(HashSet<String> modFiles) {
		this.modFiles = modFiles;
	}

	public static List<String> getArquivo2() {
		return arquivo2;
	}

	public static void setArquivo2(List<String> arquivo2) {
		ProjectManager.arquivo2 = arquivo2;
	}

	public HashSet<String> getFileValidation() {
		return fileValidation;
	}

	public void setFileValidation(HashSet<String> fileValidation) {
		this.fileValidation = fileValidation;
	}

	public HashSet<String> getErrorFiles() {
		return errorFiles;
	}

	public void setErrorFiles(HashSet<String> errorFiles) {
		this.errorFiles = errorFiles;
	}

	
}
