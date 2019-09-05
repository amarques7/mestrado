package mestrado.utils;

import java.io.File;

public class ClearDirectory {

	public static void remover(File f) {
		// Se o arquivo passado for um diretório
		if (f.isDirectory()) {
			/*
			 * Lista todos os arquivos do diretório em um array de objetos File
			 */
			File[] files = f.listFiles();
			// Identa a lista (foreach) e deleta um por um
			for (File file : files) {
				file.delete();
			}
		}
	}

}