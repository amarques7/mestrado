package git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

public class AllCommit {
	
	public static int count = 0;
	
	//all the commit ids to analyse
	public static List<String> commitsIdToAnalyse = new ArrayList<String>();
	// branch to analyze
	public static String branchToAnalyze;
	// keep record of the current tag analysing
	public static List<String> currentTag;
	
	public static void defineAllCommitsId(boolean[] settings, String versions) {
		String versionsDefined[] = versions.split(",");
		commitsIdToAnalyse = new ArrayList<String>();
		currentTag = new ArrayList<String>(versionsDefined.length);
		try {
			DownloadGit.git.checkout().setName(branchToAnalyze).call();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (settings[1]) {

			System.out.println("ANALISE RELEASES");
			// get all the commits id from versions
			try {
				List<Ref> tagRefs = DownloadGit.git.tagList().call();
				List<String> allCommitsId = new ArrayList<String>(versionsDefined.length);
				List<String> allVersionNames = new ArrayList<String>(allCommitsId.size());
				for (Ref ref : tagRefs) {
					String versionNumber = ref.getName().split("/")[2];
					allVersionNames.add(versionNumber);

					String commitId = ref.getObjectId().getName();
					allCommitsId.add(commitId);
				}
				for (String version : versionsDefined) {
					for (int i = 0; i < allVersionNames.size(); i++) {
						if (version.equals(allVersionNames.get(i))) {
							commitsIdToAnalyse.add(allCommitsId.get(i));
							break;
						}
					}
				}

			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (settings[2]) {
			System.out.println("ANALISE COMMIT");
			// get all the commits id between the versions//pega todos os commit entre as
			// versoes
			if (versionsDefined[0].equals("0")) {
				try {
					Iterable<RevCommit> logs = DownloadGit.git.log().call();
				 
					for (RevCommit rev : logs) {
						commitsIdToAnalyse.add(rev.getName());
						count++;
					//	System.out.println("Count " + count + ", name: " + rev.getName() + ", id: " + rev.getId().getName());

					}
					System.out.println("Number of commits: " + count);
				} catch (GitAPIException e) {
					e.printStackTrace();
				}
				Collections.reverse(commitsIdToAnalyse);
				return;
			}
			try {
				List<Ref> tagRefs = DownloadGit.git.tagList().call();
				List<String> commitsToAdd = new ArrayList<String>();
				for (Ref ref : tagRefs) {
					String versionNumber = ref.getName().split("/")[2];
					String commitId = ref.getObjectId().getName();

					for (String version : versionsDefined) {
						if (versionNumber.contains(version)) {
							commitsToAdd.add(commitId);
						}
					}
				}
				DownloadGit.git.reset().call();

				Iterable<RevCommit> logs;

				// next 4 lines are about to transform the tag id into the commit id from the
				// moment above the creation of tag
				ObjectId tagIdFrom = DownloadGit.git.getRepository().resolve(commitsToAdd.get(0));
				ObjectId tagIdTo = DownloadGit.git.getRepository().resolve(commitsToAdd.get(1));
				RevCommit commitIdFrom = DownloadGit.git.getRepository().parseCommit(tagIdFrom);
				RevCommit commitIdTo = DownloadGit.git.getRepository().parseCommit(tagIdTo);

				logs = DownloadGit.git.log().addRange(commitIdFrom.getId(), commitIdTo.getId()).call();

				for (RevCommit rev : logs) {
					commitsIdToAnalyse.add(rev.getName());
				}

				Collections.reverse(commitsIdToAnalyse);

			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RevisionSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MissingObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IncorrectObjectTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AmbiguousObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (settings[4]) {
			System.out.println("Get TAGS");
			try {
				List<Ref> tagRefs = DownloadGit.git.tagList().call();
				for (Ref ref : tagRefs) {

					String versionNumber = ref.getName().split("/")[2];
					currentTag.add(versionNumber);
				//	String commitId = ref.getObjectId().getName();
				//	System.out.println("ref.getObjectId().getName(, main, linha 368: )"+ ref.getObjectId().getName());
					commitsIdToAnalyse.add(versionNumber);

					System.out.println("TAG " + versionNumber);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
