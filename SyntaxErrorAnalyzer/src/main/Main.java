package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import analysis.core.AstLogger;
import analysis.core.Project;
import analysis.core.ResultsLogger;
import cdt.handlers.SampleHandler;
import finegrained.Reports;
import git.AllCommit;
import git.DownloadGit;
import metrics.Metrics;
import util.CreateDirectory;
import util.LoadParameters;

public class Main {

	// the path for all files
	public static String PATH = "";
	public static int count = 0;

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Main.count = count;
	}

	public static List<String> projectPaths = new ArrayList<String>();

	public static String downloadPath = "";

	// git refer
	//static Git git;

	// all the commit ids to analyse, AllCommit
	//public static List<String> commitsIdToAnalyse = new ArrayList<String>();

	// all the commit ids to analyse
	public static List<String> tagsIdToAnalyse = new ArrayList<String>();

	// keep record of the last commit to compare
	static String lastCommitAnalysed = "";

	// keep record of the last project been analyzing
	public static String currentProject = "";

	// keep record to clean ast list
	public static List<String> filesToDeleteFromAnalysisFolder = new ArrayList<String>();

	// keep record of the current tag analysing, AllCommit
	//public static List<String> currentTag;

	// keep record of the current commit id
	public static String currentCommit;

	// branch to analyze, AllCommit
	//public static String branchToAnalyze;

	// keeping track of project
	public static Project project;

	// decide to not analyse the first version
	public static boolean analyseThisTime = true;

	// the number of past anaylsis
	public static int numberOfAnalysisOcurred = 0;

	// decide if Analyser.java will run this time
	public static boolean noChangesInCFiles = false;

	public static Date commitDate;

	public static List<String> allCommitsThisAnalysis;

	public static void start(String runTimeWorkspacePath) throws IOException, InterruptedException {
		long startTime = System.nanoTime();

		PATH = runTimeWorkspacePath;
		Metrics.path = PATH;
		System.out.println("STARTTTTTTT");
		LoadParameters.setInfos();

		// registering the number of times
		int i = 0;

		// checking if paths are github or directory
		for (String projectPath : projectPaths) {

			// resetting variables
			currentCommit = "";
			lastCommitAnalysed = "";
			Metrics.index = 1;
			Reports.rindex = 1;
			exportFunctionCalls.ExportNumberOfCalls.i = 1;
			AllCommit.commitsIdToAnalyse = new ArrayList<String>();
			filesToDeleteFromAnalysisFolder = new ArrayList<String>();

			noChangesInCFiles = false;

			// 0 = download, 1 = analyseVersions, 2 = analyseCommits, 3 = noStubs, 4 = allR
			boolean settingsFromParameters[] = LoadParameters.settingsFromParameters();

			// if download is enable
			downloadPath = PATH + LoadParameters.pathsToResults[i] + "\\";
			currentProject = LoadParameters.pathsToResults[i];
			SampleHandler.PROJECT = currentProject;
			System.out.println(downloadPath);
			if (settingsFromParameters[0]) {
				new File(downloadPath).mkdirs();
				DownloadGit.downloadProject(projectPath);
			}

			else {
				DownloadGit.git = Git.open(new File(projectPath));
			}

			AllCommit.defineAllCommitsId(settingsFromParameters, LoadParameters.versionsToAnalyseOfEachProject[i]);
			
			// set AstLogger and  set ResultsLogger
			CreateDirectory.setWriter(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + "/results/errorPath");
			
			

			Starter analyser = new Starter(downloadPath, settingsFromParameters[3]);

			int numberOfAnalysis = 1; //
			// analyse only versions

			int indexOfPastAnalysis = getIndexOfPastAnalysis();

			// writing all the commitsId to analyze
			FileWriter writer = new FileWriter(new File(downloadPath + "results/" + "0_allCommitsId.txt"));
			for (String commit : AllCommit.commitsIdToAnalyse) {
				writer.write(commit + "\n");
			}
			writer.close();

			ResultsLogger.write("Number of commitsId: " +  AllCommit.commitsIdToAnalyse.size());
			if (indexOfPastAnalysis != -1) {
				deleteAllFromAnalysisFolder();
				numberOfAnalysisOcurred = indexOfPastAnalysis;
				analyseThisTime = false;
				allCommitsThisAnalysis = new ArrayList<String>( AllCommit.commitsIdToAnalyse.size());
				allCommitsThisAnalysis.addAll( AllCommit.commitsIdToAnalyse);
				System.out.println(indexOfPastAnalysis + "   " +  AllCommit.commitsIdToAnalyse.size());
				 AllCommit.commitsIdToAnalyse =  AllCommit.commitsIdToAnalyse.subList(indexOfPastAnalysis - 1,  AllCommit.commitsIdToAnalyse.size());
			} else {

				allCommitsThisAnalysis = new ArrayList<String>( AllCommit.commitsIdToAnalyse.size());
				allCommitsThisAnalysis.addAll( AllCommit.commitsIdToAnalyse);
			}
			System.out.println("Selected commitsId size: " +  AllCommit.commitsIdToAnalyse.size());

			// writing all the commitsId to analyze after the selection
			writer = new FileWriter(new File(downloadPath + "results/" + "1_allCommitsAfterSelection.txt"));
			for (String commit :  AllCommit.commitsIdToAnalyse) {
				writer.write(commit + "\n");
			}
			writer.close();
			
			for (String commit :  AllCommit.commitsIdToAnalyse) {

				currentCommit = commit;
				// writing to results/errorPath/errorTxt.txt
				AstLogger.write("\nAnalysis number: " + numberOfAnalysis);
				numberOfAnalysis++;
				try {
					// this file needs to be deleted for the next analysis
					Files.delete(new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + "/temp2.c")
							.toPath());
				} catch (Exception e) {
					// in case of the file doesnt exist
				}

				changeToVersion(commit);
				System.out.println(commit);

				List<String> modifiedFilesArrayList = null;
//	recebe a lista de arquivos modificados
				modifiedFilesArrayList = settingUp(commit);
				String modifiedFiles[] = null;
				if (modifiedFilesArrayList != null) {
					modifiedFiles = new String[modifiedFilesArrayList.size()];
					modifiedFiles = modifiedFilesArrayList.toArray(modifiedFiles);
				}

				// writing to results/specs.txt
				ResultsLogger.write("Commit " + commit);

				// this method will create the stubs file, generate all ASTs and create
				// results/dependencies.txt
				project = analyser.start(modifiedFiles);

			}

			DownloadGit.git.getRepository().close();
			i++;
			System.out.println("235 - i "+ i);
		}

		long elapsedTime = System.nanoTime() - startTime;
		System.out.println(
				"Analysis ended in " + TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " seconds.");
	}

	@SuppressWarnings("resource")

	public static void filesInAnalysis(String sourceFolder) throws IOException {
		File[] files = new File(sourceFolder).listFiles();
		for (File file : files) {
			//verifica se o file é um arquivo ou diretório
			if (file.isDirectory()) {
				filesInAnalysis(file.getAbsolutePath());
			} else {
				// || file.getName().trim().endsWith(".h")
				if (file.getName().trim().endsWith(".c")) {
					copyFileUsingChannel(file, (new File(downloadPath + "\\analysis" + "\\" + file.getName())));
//                	FileUtils.copyFile(file, (new File(downloadPath + "\\analysis" + "\\" + file.getName())));
//                	Files.copy(file.toPath(), (new File(downloadPath + "\\analysis" + "\\" + file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			} 
		}
	}
//tirar daqui
	@SuppressWarnings("resource")
	public static void copyFileUsingChannel(File source, File dest) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}
	}

	public static void changeToVersion(String commitId) throws InterruptedException {
		System.out.println("Changing version...");
		
		try {
			DownloadGit.git.checkout().setCreateBranch(true).setName(commitId).setStartPoint(commitId).call();
		} catch (GitAPIException e) {
			try {
				DownloadGit.git.checkout().setCreateBranch(false).setName(commitId).call();
			} catch (GitAPIException e1) {
				e1.printStackTrace();
			}
			// do nothing
		}
		System.out.println("Changed");
	}

	public static List<String> settingUp(String commitId) throws IOException {
		List<String> filesModified = null;

		if (!lastCommitAnalysed.equals("")) {
			List<String> filesModifiedRightPath = new ArrayList<String>();
			// get the difference between the last commit and the new
			try {
				filesModified = diffFilesInCommits(lastCommitAnalysed, commitId);

				// adding only the new files to the analysis directory
				for (String fileModified : filesModified) {
					String file = new File(fileModified).getName();
					filesModifiedRightPath.add(file);
				}

			} catch (GitAPIException e) {
				e.printStackTrace();
			}
			lastCommitAnalysed = commitId;
			filesInAnalysis(downloadPath);
			return filesModifiedRightPath;
		}//chamar meu metodo para criar pastas
		new File(downloadPath + "\\analysis").mkdir();
		filesInAnalysis(downloadPath);

		lastCommitAnalysed = commitId;
		return filesModified;
	}

	public static int tryToCopy(String fileModified, String file) {
		try {
			Files.copy(new File(downloadPath + "" + fileModified).toPath(),
					new File(downloadPath + "analysis\\" + file).toPath(), StandardCopyOption.REPLACE_EXISTING);
			return 1;
		} catch (IOException e) {
			// do nothing
			e.printStackTrace();
			return 0;
		}
	}

	// this method will only return the .c files modified
	public static List<String> diffFilesInCommits(String oldCommitId, String newCommitId)
			throws GitAPIException, IOException {
		List<DiffEntry> diffs = DownloadGit.git.diff().setOldTree(prepareTreeParser(DownloadGit.git.getRepository(), oldCommitId))
				.setNewTree(prepareTreeParser(DownloadGit.git.getRepository(), newCommitId)).call();

		System.out.println("Found: " + diffs.size() + " differences");
		List<String> modifiedFiles = new ArrayList<String>(diffs.size());
		filesToDeleteFromAnalysisFolder = new ArrayList<String>(diffs.size());
		for (DiffEntry diff : diffs) {
			noChangesInCFiles = true;
			if (diff.getChangeType().toString().equals("DELETE")) {
				if (diff.getOldPath().endsWith(".c")) {
					noChangesInCFiles = false;
					System.out.println(new File(diff.getOldPath()).getName());
					filesToDeleteFromAnalysisFolder.add(new File(diff.getOldPath()).getName());
				}
			}

			// checking for .c file
			else if (diff.getNewPath().endsWith(".c")) {
				noChangesInCFiles = false;
				// this method will only get the name of file
				modifiedFiles.add(diff.getNewPath());
			}
		}

		for (String fileToDelete : filesToDeleteFromAnalysisFolder) {
			try {
				Files.delete(new File(downloadPath + "/analysis/" + fileToDelete).toPath());
			} catch (Exception e) {
				// do nothing
			}

		}

		return modifiedFiles;
	}

	// this method receives a path and return the .c file if exists
	public static String getFileFromPath(String path) {
		String fileToReturn = "";
		for (int length = path.length() - 1; length >= 0; length--) {
			if (path.charAt(length) != '/' && path.charAt(length) != '\\') {
				fileToReturn = fileToReturn + path.charAt(length);
			} else {
				break;
			}
		}
		// reverse the string
		return new StringBuilder(fileToReturn).reverse().toString();

	}

	private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
		// from the commit we can build the tree which allows us to construct the
		// TreeParser
		// noinspection Duplicates

		try (RevWalk walk = new RevWalk(repository)) {
			RevCommit commit = walk.parseCommit(repository.resolve(objectId));
			RevTree tree = walk.parseTree(commit.getTree().getId());
			PersonIdent authorIdent = commit.getAuthorIdent();
			commitDate = authorIdent.getWhen();
			System.out.println("Commit DATE " + commitDate);

			CanonicalTreeParser treeParser = new CanonicalTreeParser();
			try (ObjectReader reader = repository.newObjectReader()) {
				treeParser.reset(reader, tree.getId());
			}

			walk.dispose();

			return treeParser;
		}
	}

//	public static boolean[] settingsFromParameters() { posso deletar
//
//		boolean settings[] = { true, true, false, false, false, true };
//		// defining arguments
//		for (String arguments : LoadParameters.parameters) {
//			if (arguments == null) {
//				System.out.println("Specify -v or -allC");
//				return settings;
//			}
//
//			for (String argument : arguments.split("-")) {
//				if (argument.contains("nodl")) {
//					settings[0] = false;
//				} else if (argument.contains("allC")) {
//					settings[1] = false;
//					settings[2] = true;
//				} else if (argument.contains("stubs")) {
//					settings[3] = true;
//				} else if (argument.contains("allR")) {
//					settings[1] = false;
//					settings[4] = true;
//				} else if (argument.contains("nocommit")) {
//					settings[5] = false;
//				}
//			}
//		}
//		return settings;
//	}

	

	private static void deleteAllFromAnalysisFolder() {
		try {
			FileUtils.cleanDirectory(new File(downloadPath + "analysis"));
		} catch (IOException e) {
			System.out.println("Analysis folder not found to delete!");
		}
	}

}
