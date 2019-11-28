package mestrado.git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mestrado.core.Runner;
import mestrado.git.Repo;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class DiffFilesGit {

	public static Date commitDate;
	
	//this method will only return the .c files modified
	public static ArrayList<String> diffFilesInCommits(String oldCommitId, String newCommitId) throws GitAPIException, IOException{
		List<DiffEntry> diffs = Repo.getGit().diff()
                .setOldTree(prepareTreeParser(Repo.getGit().getRepository(), oldCommitId))
                .setNewTree(prepareTreeParser(Repo.getGit().getRepository(), newCommitId))
                .call();

        System.out.println("Found: " + diffs.size() + " differences");
        List<String> modifiedFiles = new ArrayList<String>(diffs.size());
   //   	filesToDeleteFromAnalysisFolder = new ArrayList<String>(diffs.size());
        for (DiffEntry diff : diffs) {
        	Runner.projectManager.setNoChangesInCFiles(true);
    		if(diff.getChangeType().toString().equals("DELETE")){
        		if(diff.getOldPath().endsWith(".c")){
        			Runner.projectManager.setNoChangesInCFiles(false);
        			System.out.println(new File(diff.getOldPath()).getName());
            		
            	}
        	}
        		
            //checking for .c file
    		else if(diff.getNewPath().endsWith(".c")){
    			Runner.projectManager.setNoChangesInCFiles(false);
    			//this method will only get the name of file
            	modifiedFiles.add(diff.getNewPath());
            }
        }

//        for(String fileToDelete : filesToDeleteFromAnalysisFolder){
//        	try{
//        		Files.delete(new File(downloadPath + "/analysis/" + fileToDelete).toPath());
//        	}
//        	catch(Exception e){
//        		//do nothing
//        	}
        	      
        
        return (ArrayList<String>) modifiedFiles;
	}
	
	private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
	
		
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(repository.resolve(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());
            PersonIdent authorIdent = commit.getAuthorIdent();
    		commitDate = authorIdent.getWhen();
    		System.out.println("Commit DATE --"+ commitDate);

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }
	
	
}
