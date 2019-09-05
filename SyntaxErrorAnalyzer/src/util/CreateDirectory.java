package util;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CreateDirectory{
	
	//diretório mais o nome da pasta
	
	public static void setWriter(String dir_result){
		File pathToError = new File(dir_result);
		pathToError.mkdirs();
	}
	
	public static void write(String dir_result, String nameLog, String commit ,ArrayList<String> toWrite){
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(dir_result + nameLog, true));
			writer.println(commit);
			for (int i = 0; i < toWrite.size(); i++) {
				writer.println(toWrite.get(i));
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}