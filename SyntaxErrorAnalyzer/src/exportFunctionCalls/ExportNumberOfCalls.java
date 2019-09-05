package exportFunctionCalls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import analysis.core.Function;
import analysis.core.Project;
import util.CreateFile;

public class ExportNumberOfCalls {
	public static int i = 1;
	//main.Main.currentCommitId
	
	public static void receive(List<Project.Pair> allPairs){
		try {
			//new File(main.Main.downloadPath + "results/functionCalls").mkdirs();
			CreateFile.create(main.Main.downloadPath, "results/functionCalls" );
			
			FileWriter writer = new FileWriter(new File(main.Main.downloadPath + "results/functionCalls/" + i + "_"+ main.Main.currentCommit + ".txt"));
			
			for(Project.Pair pair : allPairs) {
				String toWrite = pair.getFunction().getName() + ";";
				boolean in = false;
				for(Function fdef : pair.getAllFunctionDefs()) {
					toWrite = toWrite + fdef.getPresenceCondition().toString() + ";";
					in = true;
				}
				if(!in)
					continue;
				toWrite = toWrite + " @ ";
				for(Function fdef : pair.getAllFunctionDefs()) {
					toWrite = toWrite + fdef.getName() + ";";
				}
				writer.write(toWrite);
				writer.write("\n");
			}
			i++;
			writer.close();
		}
		catch(IOException e) {
			
		}
		
	}
}
