package mestrado.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;

import analysis.core.AstLogger;
import analysis.core.Project;
import cdt.handlers.SampleHandler;
import main.Starter;
import mestrado.git.Commit;
import mestrado.git.CommitManager;
import mestrado.git.DiffFilesGit;
import mestrado.git.Repo;
import mestrado.git.Repo;
import mestrado.utils.ListFilesC;
import mestrado.utils.ManipulationUtils;
import mestrado.utils.MoveFile;
import util.CreatLogs;
import util.LineOfCode;

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
	//private String lastCommitAnalysed = "";
	String repeated = new String(new char[50]).replace("\0", "-*");

	private StringBuilder dataText = new StringBuilder();
	private StringBuilder directory = new StringBuilder();
	private StringBuilder fileName = new StringBuilder();
	private StringBuilder elem = new StringBuilder();

	public HashSet<String> fileValidation;
	public static List<String> arquivo2;
	private ArrayList<Repo> listofRepos;
	private List<String> allCommitsThisAnalysis;
	public HashSet<String> listModFile;
	public HashSet<String> errorFiles;
	private HashSet<String> notModFilesaux;
	private HashSet<String> notModFiles;
	private HashSet<String> modFiles;

	private BufferedReader reader;

	private long startTime;
	private long startTime2;
	private int totalArqPro;
	private int numberOfAnalysisOcurred;
	private int lineOfCode;
	public static int validador;

	private boolean noChangesInCFiles;
	private boolean analyseThisTime;

	private Commit commitAtual;
	public Repo repo;

	// private HashSet<String> arquivo;
	public ArrayList<String> repos;
	private List<CommitManager> listaCommitManager;
	private int numeroProjetosGit;

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
		listaCommitManager = new ArrayList<CommitManager>();

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

		numeroProjetosGit = Integer.parseInt(reader.readLine());
		for (int i = 0; i < numeroProjetosGit; i++) {
			String elementos = reader.readLine();
			String[] listaElementos = elementos.split("/");
			Integer inicio = Integer.parseInt(listaElementos[0]);
			Integer fim = Integer.parseInt(listaElementos[1]);
			this.listaCommitManager.add(new CommitManager(inicio, fim, i));
		}
	}

	public void generateVariabilities() {
		try {
			CreatLogs create = new CreatLogs();
			LineOfCode locTotal = new LineOfCode();
			String lastCommitAnalysed = "";
			repos = ManipulationUtils.loadRepos(repoList);
			int posicaoCommit = 0;
			validador = 0;
			for (String repoURI : repos) {
				try {
					CommitManager commitManager = listaCommitManager.get(posicaoCommit);
					Repo resp = new Repo(repoURI, dirProject, commitManager.getCommitInicial(),
							commitManager.getCommitFinal());
				
					startTime = System.nanoTime();
					numberOfAnalysisOcurred = 0;

					System.out.println(repeated);
					System.out.println("Analyzing....");

					currentProject = resp.getName();
					SampleHandler.PROJECT = resp.getName();

					create.CreateLog();
					directory.append(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject()
							+ File.separator + "results");
					fileName.append(Runner.projectManager.getCurrentProject() + ".csv");

					// essa funçao cria os arquivos platform.h e stubs.h
					Starter analyser = new Starter(dirPlugin + currentProject + File.separator, false);

					if (listaProjetos.equals("a")) {
						listaProjetos = resp.getName();
					} else {
						listaProjetos = listaProjetos + resp.getName();
					}
					System.out.println();
					System.gc();
					for (int i = commitManager.getCommitInicial(); i <= commitManager.getCommitFinal(); i++) {
						resp = new Repo(repoURI, dirProject, commitManager.getCommitInicial(),
								commitManager.getCommitFinal());
						HashMap<String, Commit> retorno = resp.retornaCommitByIndex(i);
						validador++;
						if (!resp.getCommitList().isEmpty()) {
							numberOfAnalysisOcurred = i;
							System.out.println("Quantidade de commits: " + resp.getTotalCommit());
							// traz os commit
							//lastCommitAnalysed = "";
							for (Commit c : resp.getCommitList()) {
								if (c == null)
									continue;
								startTime2 = System.nanoTime();
								deleteAllFromAnalysisFolder();

								commitAtual = c;
								currentCommit = c.getId();
						
								resp.checkoutCommit(c.getId());

								System.out.println("Análise do commit: " + numberOfAnalysisOcurred);

								ListFilesC contArq = new ListFilesC();
								File fileI = new File(dirProject + currentProject);

								int QtdArqOfCommit = contArq.findFiles(fileI, ".*\\.c").size();
								System.out.println("A quantidade de arquivo no commit " + numberOfAnalysisOcurred
										+ ", é: " + QtdArqOfCommit);

								if (!lastCommitAnalysed.equals("")) {
									System.out.println("entrei..");
									List<String> filesModifiedRightPath = new ArrayList<String>();
									// get the difference between the last commit and the new
					
									try {
										
										filesModifiedRightPath.clear();
										filesModifiedRightPath = DiffFilesGit.diffFilesInCommits(lastCommitAnalysed,
												currentCommit);

										List<File> file = contArq.findFiles(fileI, ".*\\.c");
										System.out.println("File: " + file);
										if (!filesModifiedRightPath.isEmpty()) {
											for (File k : file) {
												for (String x : filesModifiedRightPath) {
													File y = new File(x);

													if (y.getName().equals(k.getName()) && !modFiles
															.contains(dirPlugin + currentProject + File.separator
																	+ "analysis" + File.separator + y.getName())) {
														this.noChangesInCFiles = true;

														modFiles.add(dirPlugin + currentProject + File.separator
																+ "analysis" + File.separator + y.getName());

														MoveFile.copyFileUsingChannel(k,
																(new File(dirPlugin + currentProject + File.separator
																		+ "analysis" + File.separator + y.getName())));
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

									// this.noChangesInCFiles = true; *
									lastCommitAnalysed = currentCommit;
								}
								logControl = numberOfAnalysisOcurred + ";" + c.getId();

								// trata quando nenhum arquivo foi alterado, no primeiro commit
								if (modFiles.size() == 0) {
									if (numberOfAnalysisOcurred == 1) {
										dataText.delete(0, dataText.length());
										dataText.append(numberOfAnalysisOcurred + ";" + c.getId() + ";" + " " + ";"
												+ " " + ";" + " ");
										AstLogger.writeaST(dataText, directory, fileName);
									}
								}
								// pega do os arquivos em disco para depois retirar os repetidos.
								List<File> file = contArq.findFiles(fileI, ".*\\.c");
								for (File f : file) {
									String aux = dirPlugin + currentProject + File.separator + "analysis"
											+ File.separator + f.getName();
									if (!modFiles.contains(aux))
										notModFiles.add(f.getName());
								}
								System.out.println("qtd modFiles: " + modFiles.size());
								System.out.println("qtd notModFiles: " + notModFiles.size());

								lineOfCode = locTotal.Loc(modFiles) + lineOfCode;
								// COMENTAR ATE O FINAL FOR PARA ADQUIR O LOC
								
								HashSet<String> arquivo = new HashSet<String>();
								StringBuilder directory = new StringBuilder();
								StringBuilder fileName = new StringBuilder();
								HashSet<String> arquivosCompilados = new HashSet<String>();
								int cont = 0;
								directory.append("C:\\Users\\amarq\\runtime-EclipseApplication\\"
										+ Runner.projectManager.getCurrentProject());
								fileName.append("log_arquivos.txt");
								String caminho = "C:\\Users\\amarq\\runtime-EclipseApplication\\"
										+ Runner.projectManager.getCurrentProject() + "\\analysis\\";
								String filee = "C:\\Users\\amarq\\runtime-EclipseApplication\\"
										+ Runner.projectManager.getCurrentProject() + "\\log_arquivos.txt";

								File fi = new File(filee);
								if (!fi.exists()) {
									fi.createNewFile();
								}
								try {
									Scanner scan = new Scanner(new File(filee));

									while (scan.hasNextLine()) {
										String line = scan.nextLine();
										arquivosCompilados.add(line);
										System.out.println("Arquivo sendo processado:" + line);
									}

								} catch (Exception e) {
									System.out.println("Arquivo vazio");

								}
//						      
								for (String elementos : modFiles) {
								
									if (arquivosCompilados.contains(elementos)) {
				
										continue;
									}
									arquivo.add(elementos);
									elem.append(elementos.toString());
									AstLogger.writeaST(elem, directory, fileName);
									elem.delete(0, elem.length());

									System.out.println("Quantidade de Arquivos: " + modFiles.size());
									System.out.println("Processados: " + (arquivosCompilados.size()+cont));

									System.out.println("Ainda falta: " + (modFiles.size() - (cont + arquivosCompilados.size()))
											+ "/" + modFiles.size());

									Project project = analyser.start(arquivo);
									arquivo.clear();
									cont += 1;
									System.gc();
								}

								for (String f : notModFiles) {

									if (errorFiles.contains(f)) {
										dataText.delete(0, dataText.length());
										dataText.append(numberOfAnalysisOcurred + ";" + c.getId() + ";" + f + ";" + "0"
												+ ";" + "1");
										AstLogger.writeaST(dataText, directory, fileName);

									} else {
										dataText.delete(0, dataText.length());
										dataText.append(numberOfAnalysisOcurred + ";" + c.getId() + ";" + f + ";" + "0"
												+ ";" + "0");
										AstLogger.writeaST(dataText, directory, fileName);
										fileValidation.add(f);
									}
								}

								modFiles.clear();
								notModFilesaux.clear();
								notModFiles.clear();
								listModFile.clear();
								// COMENTAR O TRY PARA ADQUIR O LOC
								try {
									Files.delete(new File(dirPlugin + currentProject + "\\temp2.c").toPath());

								} catch (Exception e) {
									// in case of the file doesnt exist
									System.out.println("O arquivo não existe: " + e.getMessage());
								}
								try {
									Files.delete(new File(dirPlugin + currentProject + File.separator + "platform.h")
											.toPath());
								} catch (Exception e) {
									// in case of the file doesnt exist
									System.out.println("O arquivo não existe: " + e.getMessage());
								}
								try {
									Files.delete(new File(dirPlugin + currentProject + File.separator + "include"
											+ File.separator + "stubs.h").toPath());
								} catch (Exception e) {
									// in case of the file doesnt exist
									System.out.println("O arquivo não existe: " + e.getMessage());
								}
								try {
									Files.delete(
											new File(dirPlugin + currentProject + File.separator + "log_arquivos.txt")
													.toPath());
											System.out.println("log_arquivo.tx!!! apagado.." );
								} catch (Exception e) {
									// in case of the file doesnt exist
									System.out.println("O arquivo não existe: " + e.getMessage());
								}
							}
						}
						numberOfAnalysisOcurred++;
						long elapsedTimes2 = System.nanoTime() - startTime2;
						long seg = TimeUnit.SECONDS.convert(elapsedTimes2, TimeUnit.NANOSECONDS);

						System.out.println("Tempo do commit "
								+ TimeUnit.SECONDS.convert(elapsedTimes2, TimeUnit.NANOSECONDS) + " seconds.");
						System.out.println("terminei o Commit");
						System.out.println(repeated);
						System.out.println("\n\n");
						System.gc();

					}

				} catch (Exception e) {
					// listofRepos.add(new Repo(repoURI, Runner.projectManager.dirProject));
				}
				posicaoCommit++;
			}
		} catch (Exception e) {
			System.out.println("Erro" + e.getMessage());
		}
	}

	public static void deleteAllFromAnalysisFolder() {
		try {
			FileUtils.cleanDirectory(
					new File(Runner.projectManager.dirPlugin + Runner.projectManager.currentProject + "/analysis"));
			// System.out.println("deleteDir: "+ Runner.projectManager.dirPlugin +
			// Runner.projectManager.currentProject + "/analysis");

		} catch (IOException e) {
			System.out.println("Analysis folder not found to delete!");
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

	public int getValidador() {
		return validador;
	}

	public void setValidador(int validador) {
		this.validador = validador;
	}

}
