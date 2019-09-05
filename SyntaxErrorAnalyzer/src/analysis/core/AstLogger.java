package analysis.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cdt.handlers.SampleHandler;

public class AstLogger {
	
	public static void setWriter(){
		File pathToError = new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + "/results/errorPath");
	//	File pathtoError2 =  new File(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT+ "/results/csv/Variabilities/ProgramWeight");
		pathToError.mkdirs();
		//pathtoError2.mkdirs();
	}
	
	public static void write(String toWrite){
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(SampleHandler.RUNTIME_WORKSPACE_PATH + SampleHandler.PROJECT + "/results/errorPath/errorTxt.txt", true));
			writer.println(toWrite);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
