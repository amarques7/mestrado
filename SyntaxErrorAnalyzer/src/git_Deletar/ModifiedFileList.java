//package git_Deletar;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import analysis.core.ResultsLogger;
//
//public class ModifiedFileList {
//
//	public static int numberOfCFiles = 0;
//
//	// cria lista de arquivos modificado do commit
//	public static String[] defineFiles(String[] filesToAnalyze) {
//		File path = new File(main.Main.downloadPath + "analysis");
//		File[] files = path.listFiles();
//		numberOfCFiles = files.length;
//		List<String> allFilesInAnalysisFolder = new ArrayList<String>(numberOfCFiles);
//
//		ResultsLogger.write("	number of .c files: " + numberOfCFiles);
//		if (filesToAnalyze == null) {
//			for (File file : files) {
//				allFilesInAnalysisFolder.add(file.getAbsolutePath());
//			}
//		} else {
//			for (String file : filesToAnalyze) {
//				File fileWithCompletePath = new File(main.Main.downloadPath + "analysis/" + file);
//				allFilesInAnalysisFolder.add(fileWithCompletePath.getAbsolutePath());
//			}
//		}
//
//		filesToAnalyze = new String[allFilesInAnalysisFolder.size()];
//		filesToAnalyze = allFilesInAnalysisFolder.toArray(filesToAnalyze);
//		return filesToAnalyze;
//	}
//
//}
