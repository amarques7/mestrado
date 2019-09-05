package git;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;



public class DownloadGit {

	public static Git git;
	public static void downloadProject(String URL) {


		System.out.println("Downloading project");
		// git clone
		try {
			git = Git.cloneRepository().setURI(URL).setDirectory(new File(main.Main.downloadPath)).call();
			System.out.println("Donwnload done!");

		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
