package mestrado.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayList;

import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.Paths;

class Teste {

	static File fl = null;
	public static ArrayList<String> modFiles = new ArrayList<String>();

	static String destino = "C:\\Users\\amarq\\runtime-EclipseApplication\\analyse\\";

	public static void main(String a[]) throws IOException {
//		String texto = "Projeto\\BCC-2s13-PI2-Codigo-de-Honra\\game/desafios.c";
//		String[] array = texto.split("[/]");
//		System.out.println("tamanho do arry-> " + array.length);
//		int x = array.length;
//
//		System.out.println("0 ->" + array[0]);
//		System.out.println("1 -> " + array[1]);
//		System.out.println("2-> " + array[x]);

		modFiles.add("C:/Projeto/game/arena.c");
		modFiles.add("C:/Projeto/gx/arena.c");
		modFiles.add("C:/Projeto/ga/arena.c");
		modFiles.add("C:/Projeto/ge/arena.c");
		modFiles.add("C:/Projeto/gm/arena.c");
		modFiles.add("C:/Projeto/gme/arena.c");

		copyFile(modFiles, destino);

	}

	@SuppressWarnings("resource")
	private static void copyFile(ArrayList<String> modFiles, String destino)
	        throws IOException {
		for (String arqMod : modFiles) {

			// Arquivo a ser copiado
		File sourceFile = new File(arqMod);
		File destFile = new File(destino);
	    if (!sourceFile.exists()) {
	        return;
	    }
	    if (!destFile.exists()) {
	        destFile.createNewFile();
	    }
	    FileChannel source = null;
	    FileChannel destination = null;
	    source = new FileInputStream(sourceFile).getChannel();
	    destination = new FileOutputStream(destFile).getChannel();
	    if (destination != null && source != null) {
	        destination.transferFrom(source, 0, source.size());
	    }
	    if (source != null) {
	        source.close();
	    }
	    if (destination != null) {
	        destination.close();
	    }

	}
	}

}
