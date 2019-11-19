package mestrado.git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
//import org.eclipse.jgit.util.FileUtils;

//import mestrado.core.Runner;
import mestrado.utils.*;

public class Repo {

	private String URI;
	private File localPath;
	private String name;

	private Repository repositorio;
	private static Git git;

	private HashMap<String, Commit> commitList;
	ArrayList<Commit> chronologicalorderCommits;// = new ArrayList<Commit>();

	private int numberofCommits;

	public Repo(String repoURI, String dir_projeto) throws NoFilepatternException, GitAPIException {

		this.URI = repoURI;
		this.name = repoURI.substring(repoURI.lastIndexOf("/") + 1).replace(".git", "");
//		 this.localPath = new File(System.getProperty("C:Projeto/") +
//		 System.getProperty("file.separator") + "repo" +
//		 System.getProperty("file.separator") + name);

		this.localPath = new File(dir_projeto + System.getProperty("file.separator") + name);
//		 this.localPath = new File("C:" + System.getProperty("file.separator") +
//		 dir_projeto + System.getProperty("file.separator") + name);

		this.commitList = new HashMap<String, Commit>();
		this.numberofCommits = 0;

		if (!(getLocalPath().exists()))
			try {
				cloneRepo();
			} catch (IOException | GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			try {
				openRepo();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// retrieveCommits();

		createCommitsHistory();

	}

	private void openRepo() throws IOException, NoFilepatternException, GitAPIException {

		System.out.print("Opening " + getName() + "...");
		Git g = Git.open(getLocalPath());
		setGit(g);
		g.close();
		setRepo(getGit().getRepository());
		
		System.out.println(" OK2!");

	}

	public void cloneRepo() throws IOException, NoFilepatternException, GitAPIException {

		System.out.println("Cloning " + getName() + "...");
		Git g = Git.cloneRepository().setURI(getURI()).setDirectory(getLocalPath()).call();
		setGit(g);
		g.close();
		setRepo(getGit().getRepository());

		System.out.println(" OK!");

	}

	public void checkoutCommit(String commitID) {

		try {
			getGit().checkout().setName(commitID).call();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	private void createCommitsHistory() {

		RevWalk walk = null;
		Ref head = null;
		RevCommit commit = null;

		try {
			head = getRepo().exactRef("refs/heads/master");

			walk = new RevWalk(getRepo());

			commit = walk.parseCommit(head.getObjectId());

			walk.markStart(commit);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		walk.sort(RevSort.REVERSE);

		for (RevCommit rev : walk) {
			Commit tempCommit = new Commit(rev.name(), rev.getCommitterIdent().getName(),
					rev.getCommitterIdent().getWhen());

			RevTree tree = rev.getTree();

			TreeWalk treeWalk = new TreeWalk(getRepo());

			try {
				treeWalk.addTree(tree);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			treeWalk.setRecursive(true);

			try {
				while (treeWalk.next()) {

					String fileFullPath = getLocalPath() + System.getProperty("file.separator")
							+ treeWalk.getPathString();
					String fileName = FilenameUtils.getFilename(fileFullPath);
					String fileExtension = FilenameUtils.getExtension(fileFullPath);

					tempCommit.associateFile(new RepoFile(fileFullPath, fileName, fileExtension));

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			addCommit(rev.name(), tempCommit);
		}
		walk = null;

	}

	public ArrayList<Commit> getCommitList() {

		RevWalk walk = null;
		Ref head = null;
		RevCommit commit = null;

		// ArrayList<Commit> chronologicalorderCommits = new ArrayList<Commit>();
		chronologicalorderCommits = new ArrayList<Commit>();
		try {
			head = getRepo().exactRef("refs/heads/master");

			walk = new RevWalk(getRepo());

			commit = walk.parseCommit(head.getObjectId());

			walk.markStart(commit);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		walk.sort(RevSort.REVERSE);

		for (RevCommit rev : walk) {
			chronologicalorderCommits.add(getCommit(rev.getName()));

		}

		setNumberofCommits(chronologicalorderCommits.size());

		return chronologicalorderCommits;

	}

	public ArrayList<Commit> getChronologicalorderCommits() {
		return chronologicalorderCommits;
	}

	public void setChronologicalorderCommits(ArrayList<Commit> chronologicalorderCommits) {
		this.chronologicalorderCommits = chronologicalorderCommits;
	}

	public Commit getCommit(String commitID) {
		return commitList.get(commitID);
	}

	public Commit getCommitNumber(int commitNumber) {
		return getCommitList().get(commitNumber);
	}

	public void addCommit(String commitId, Commit commit) {
		commitList.put(commitId, commit);
	}

	public int getNumberofCommits() {
		return numberofCommits;
	}

	public void setNumberofCommits(int numberofCommits) {
		this.numberofCommits = numberofCommits;
	}

	public static Git getGit() {
		return git;
	}

	public void setGit(Git git) {
		this.git = git;
	}

	public Repository getRepo() {
		return repositorio;
	}

	public void setRepo(Repository repositorio) {
		this.repositorio = repositorio;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String URI) {
		this.URI = URI;
	}

	public File getLocalPath() {
		return localPath;
	}

	public void setLocalPath(File localPath) {
		this.localPath = localPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return getName() + " - " + getURI() + " - " + getLocalPath();
	}

}
