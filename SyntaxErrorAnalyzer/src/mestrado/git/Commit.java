package mestrado.git;

import java.util.ArrayList;
import java.util.Date;

public class Commit {
	
	String id;
	String author;
	Date timestamp;
	ArrayList<RepoFile> touchedFiles;

	public Commit(String commitId, String commiter, Date timestamp) {
		this.id = commitId;
		this.author = commiter;
		this.timestamp = timestamp;
		
		this.touchedFiles = new ArrayList<RepoFile>();
		
		for (RepoFile f : getTouchedFiles()) {
			System.out.println("f.getName():  "+ f.getName());
			
		}
}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void associateFile(RepoFile file){
		touchedFiles.add(file);
	}
	
	public ArrayList<RepoFile> getTouchedFiles(){
		return touchedFiles;
	}
	
	public String toString(){
		return getId() + " - " + getAuthor() + " - " + getTimestamp().toString();
	}
	
}
