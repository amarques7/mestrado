package mestrado.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Comentário Classe para um diretório vazio.
 */
public class CreateDirectory {

	public static void setWriter(String dir_result) {
		File newDirectory = new File(dir_result);
		newDirectory.mkdirs();

	}

}