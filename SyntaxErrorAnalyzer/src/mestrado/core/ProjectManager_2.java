package mestrado.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import analysis.core.AstLogger;
import analysis.core.Project;
import analysis.core.ResultsLogger;
import cdt.handlers.SampleHandler;
import main.Starter;
import mestrado.git.Commit;
import mestrado.git.Repo;
import mestrado.git.RepoFile;
import mestrado.utils.CreateDirectory;
import mestrado.utils.ListFilesC;
import mestrado.utils.ManipulationUtils;
import mestrado.utils.MoveFile;
import scala.collection.parallel.ParSeqLike.Corresponds;

public class ProjectManager_2 {

	private String dirProject;
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
	private List<String> listModFile;
	public Repo repo;
	private long startTime;
	private String logControl;
	private int totalArqPro;
	private String currentFile;

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

	public ProjectManager_2(String pathFrom, String runTimeWorkspacePath) {
		super();

		listofRepos = new ArrayList<Repo>();
		currentProject = null;
		listaProjetos = "a";
		currentCommit = "";
		noChangesInCFiles = false;
		analyseThisTime = true;
		path = runTimeWorkspacePath;
		numberOfAnalysisOcurred = 0;
		listModFile = new ArrayList<String>();
		totalArqPro = 0;
		currentFile = null;

		try {
			generateReader(pathFrom);
		} catch (Exception e) {
			System.out.println("error em generateReader" + e.getMessage());
		}
	}

	public List<String> getListModFile() {
		return listModFile;
	}

	public void setListModFile(List<String> listModFile) {
		this.listModFile = listModFile;
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

	public void generateVariabilities() throws IOException, InterruptedException {

		numberOfAnalysisOcurred = 0;
		System.out.println();

		// System.out.println("-1 Number of commits: "+ repo.getNumberofCommits());
		currentProject = "libusb"; // colocar o nome do projeto;
				SampleHandler.PROJECT = currentProject;
		// CreateDirectory.setWriter(dir_plugin + currentProject);

		// essa função cria os arquivos platform.h e stubs.h
		Starter analyser = new Starter(dirPlugin + currentProject + File.separator, false);

//		if (listaProjetos.equals("a")) {
//			listaProjetos = currentProject;
//		} else {
//			listaProjetos = listaProjetos + currentProject;
//		}
		System.out.println();

		numberOfAnalysisOcurred = 1;
		AstLogger.write("\nAnalysis number:" + numberOfAnalysisOcurred);
		currentCommit = "852bba4754ec57679c823f33e8feba6e4a564cbe"; // passar commit
		ResultsLogger.write("Commit: " + currentCommit);
	
		System.out.println("Análise commit: " + numberOfAnalysisOcurred);
		String arquivoMod = null;
		ArrayList<String> modFiles = new ArrayList<String>();
		ListFilesC contArq = new ListFilesC();
		File fileI = new File(dirProject + currentProject);
		int QtdArq = contArq.contarArquivos(fileI, "c");

		System.out.println("a quantidade de arquivo no commit " + numberOfAnalysisOcurred + ", é: " + QtdArq);

		logControl = numberOfAnalysisOcurred + "_" + currentCommit + " , " + QtdArq + "," + " ";
		totalArqPro = totalArqPro + QtdArq;
//////////////////////////////////
		currentFile ="io.c" ;// nome do arquivo
		String ArqError = "libfpusb/io.c";  //path relativo do arquivo
////////////////////////////////////
		
		arquivoMod = dirProject + currentProject + File.separator + ArqError;
		listModFile.add(arquivoMod);

		File file = new File(arquivoMod);
		// System.out.println("file:" + file);
		// modFiles.add(f.getPath().replace("/","\\"));

		modFiles.add(dirPlugin + currentProject + System.getProperty("file.separator") + "analysis"
				+ System.getProperty("file.separator") + currentFile);
		System.out.println("arqui mod: " + arquivoMod);
		this.noChangesInCFiles = true;// por causa dessa variavel nao estou entrando no anlayser

		MoveFile.copyFileUsingChannel(file, (new File(
				dirPlugin + currentProject + File.separator + "analysis" + File.separator + currentFile)));

	

	Project project = null;
	project=analyser.start(modFiles);
	// Starter.start(modFiles);
	noChangesInCFiles=false;


	listModFile.clear();try
	{

		Files.delete(new File(dirPlugin + currentProject + "\\temp2.c").toPath());
		System.out.println("//------------------------------//");
	}catch(
	Exception e)
	{
		// in case of the file doesnt exist
		System.out.println("O arquivo não existe: " + e.getMessage());
	}

	if(numberOfAnalysisOcurred==1)
	{
		System.out.println("fim.. morreuu");
		System.exit(0);
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
		String data = "";

		try {
			data = DATE_FORMAT.format(Runner.projectManager.getCommitAtual().getTimestamp());
		} catch (Exception e) {
			data = e.getMessage();
		}

		return data;

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
