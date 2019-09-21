package mestrado.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

	
	public ProjectManager( String pathFrom) {
		super();
		
		listofRepos = new ArrayList<Repo>();
		currentProject = null;
		listaProjetos = "a";
		currentCommit = "";
		noChangesInCFiles = false;
		
		try {
			generateReader(pathFrom);	
		}catch (Exception e) {
			// TODO: handle exception
		}
	}				

	private void generateReader(String pathFrom) throws IOException{
		try {
			reader = new BufferedReader(new FileReader(pathFrom));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		
		dirProjetct = reader.readLine();
		dirResult  = reader.readLine();
		dirPlugin = reader.readLine();
		repoList  = reader.readLine();
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

	public void loadRepos() {
		ArrayList<String> repos = ManipulationUtils.loadRepos(repoList);
		
		for (String repoURI : repos)
		{
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

		int count = 0;
		
		if (!listaRepositorioVazia()){
			for (Repo r : listofRepos) {

				count = 0;
				System.out.println();
				System.out.println("Analyzing " + r.getName() + "... ");
				currentProject = r.getName();
				SampleHandler.PROJECT = r.getName();
				// CreateDirectory.setWriter(dir_plugin + currentProject);
				CreateDirectory.setWriter(dirPlugin + currentProject + "\\analysis");
				CreateDirectory.setWriter(dirPlugin + currentProject +"\\results");
				// essa função cria os arquivos platform.h e stubs.h
				Starter analyser = new Starter(dirPlugin + currentProject + "\\", false);

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

					// traz os commit
					for (Commit c : r.getCommitList()) {
						count++;
						currentCommit = c.getId();
						r.checkoutCommit(c.getId());
						System.out.println("Análise commit: " + count);
						String arquivoMod = null;
						ArrayList<String> modFiles = new ArrayList<String>();

						// traz os arquivo modificados
						for (RepoFile f : c.getTouchedFiles()) {
							if (f.getExtension().equals("c")) {

								arquivoMod = f.getPath().replace("/", "\\");

								File file = new File(f.getPath().replace("/", "\\"));
								System.out.println("file:" + file);
								// modFiles.add(f.getPath().replace("/","\\"));
								modFiles.add(dirPlugin + currentProject + "\\" + "analysis" + "\\" + f.getName() + ".c");
								System.out.println("arqui mod: " + arquivoMod);

								noChangesInCFiles = true;
								MoveFile.copyFileUsingChannel(file, (new File(dirPlugin + currentProject + "\\" + "analysis" + "\\" + f.getName() + ".c")));
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
}
