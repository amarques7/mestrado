package analysis.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cdt.handlers.SampleHandler;
import mestrado.core.Runner;

public class AstLogger {
	
	public static void setWriter(){
		File pathToError = new File(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + "/results/errorPath");
		pathToError.mkdirs();

	}
	
	public static void write(String toWrite){
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(Runner.projectManager.getDirPlugin() + Runner.projectManager.getCurrentProject() + "/results/errorPath/errorTxt.txt", true));
			writer.println(toWrite);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public static void writeaST(StringBuilder toWrite, StringBuilder path, StringBuilder nameFile){
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(path + File.separator + nameFile, true));
			writer.println(toWrite);
			writer.close();
			System.gc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
