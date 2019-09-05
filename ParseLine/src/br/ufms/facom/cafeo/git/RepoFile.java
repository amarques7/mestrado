package br.ufms.facom.cafeo.git;

import java.util.ArrayList;
import java.util.Date;

public class RepoFile {
	
	String path;
	String name;
	String extension;
	
	Date dateofBirth;
	Date dateofDeath = new Date(Long.MAX_VALUE);
	
	ArrayList<String> dateofChanges = new ArrayList<String>();

	public RepoFile(String path, String name, String extension) {
		
		this.path = path;
		this.name = name;
		this.extension = extension;
	}
		
	public void birth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	
	
	public Date getDateofBirth() {
		return dateofBirth;
	}


	public Date getDateofDeath() {
		return dateofDeath;
	}

	public void die(Date dateofDeath) {
		this.dateofDeath = dateofDeath;
	}
	
	public boolean isDead(){
		return getDateofDeath().equals(new Date(Long.MAX_VALUE)) ? false : true; 
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
	public String toString(){
		return getName() + " - " + getExtension() + " - " + getPath();
	}

}
