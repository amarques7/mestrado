package br.ufms.facom.cafeo.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import br.ufms.facom.cafeo.git.Commit;
import br.ufms.facom.cafeo.git.Repo;
import br.ufms.facom.cafeo.git.RepoFile;
import br.ufms.facom.cafeo.utils.ClearDirectory;
import br.ufms.facom.cafeo.utils.CreateDirectory;
import br.ufms.facom.cafeo.utils.ManipulationUtils;
import br.ufms.facom.cafeo.utils.MoveFile;

public class Runner {

	static File TEMP;
	static int TEMP2;
	static ArrayList<Repo> listofRepos = new ArrayList<Repo>();
//	public static ArrayList<String> modFiles = new ArrayList<String>();

	static String repoList = "";
	static String dir_projeto;
	static String dir_result;
	static int i;
	static public String listaProjetos = "a";
	static public String dir_plugin;
	public static String projeto;
	public static boolean arqAlterado = false;

	public static void main(String[] args) throws IOException, InterruptedException {

		FileReader arquivoLeitura = new FileReader("diretorios.txt");

		BufferedReader leitorArquivo = new BufferedReader(arquivoLeitura);
		dir_projeto = leitorArquivo.readLine();
		dir_result = leitorArquivo.readLine();
		dir_plugin = leitorArquivo.readLine();
		repoList = leitorArquivo.readLine();
//		if (args.length == 0)
//			repoList = leitorArquivo.readLine();
//		else
//			repoList = args[0];
		loadRepos(ManipulationUtils.loadRepos(repoList));
		generateVariabilities();

	}

	private static void generateVariabilities() throws IOException, InterruptedException {

		int count = 0;
		projeto = null;
		CreateDirectory.setWriter(dir_plugin + "analysis");
		if (!listofRepos.isEmpty()) {
			for (Repo r : listofRepos) {

				count = 0;
				System.out.println();
				System.out.println("Analyzing " + r.getName() + "... ");
				projeto = r.getName();

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

					for (Commit c : r.getCommitList()) {
						count++;
						r.checkoutCommit(c.getId());
						System.out.println("Análise commit: " + count);
						String arquivoMod = null;
						ArrayList<String> modFiles = new ArrayList<String>();
						

						
						// traz os arquivo modificados
						for (RepoFile f : c.getTouchedFiles()) {
							if (f.getExtension().equals("c")) {
								
								
								
								arquivoMod = f.getPath().replace("C:\\", " ");
								File file = new File(f.getPath());
								
								modFiles.add(f.getPath());
								System.out.println("arqui mod: " + arquivoMod);
								arqAlterado = true;
								
								
								MoveFile.copyFileUsingChannel(file, (new File(dir_plugin +"\\"+"analysis"+"\\"+f.getName() + ".c")));
							}
							

						}
						// verificar o pq a copia de arquivo ta dando ruim.
						
						if (arqAlterado) {
							System.out.println("qtd modFiles: " + modFiles.size());
							System.out.println("Copiado os arquivos do commit: " + count);
						//	MoveFile.copy(modFiles, dir_plugin + "analysis");
							// ClearDirectory.remover(new File(dir_projeto + r.getName()));
							arqAlterado = false;
						}else {
							System.out.println("não há arquivos alterado  no commit: " + count );
						}
					//	ClearDirectory.remover(new File(dir_plugin +"analysis"));
						if(count == 15) {
							System.exit(0);
						}

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
