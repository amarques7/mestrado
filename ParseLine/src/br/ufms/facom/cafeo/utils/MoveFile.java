package br.ufms.facom.cafeo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MoveFile {
	static File fl = null;
	public static ArrayList<String> modFiles = new ArrayList<String>();

	static String destino = "C:\\Users\\amarq\\runtime-EclipseApplication\\analyse\\";

	public static void main(String arg[]) throws IOException {

//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/arena.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/desafios/desafios.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/descricao/descricao.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/fase1.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/fase2.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/fase3.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/fases.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/fases/intro.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/game.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/geral/cards.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/geral/dialog.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/game/src/geral/file_loader.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/rascunhos/codigos/caixa_texto.c");
//		modFiles.add("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra/rascunhos/codigos/game.c");
//
//		copy(modFiles, destino);
	}

	public static void copy(ArrayList<String> modFiles, String destino) throws IOException {
		int count = 0;
		for (String arqMod : modFiles) {

			// Arquivo a ser copiado
			File arquivo = new File(arqMod);
			if (!arquivo.exists()) {
				System.out.println("Arquivo não encontrado");
			}
			else {
				count ++;
				arqMod = arqMod.replace("\\", "/");
			//	System.out.println("arqui" + arqMod);
				String[] nameToFile = arqMod.split("[/]");
				int i = nameToFile.length;
				System.out.println("nameToFile: " + nameToFile[i - 1]);
				
				
				InputStream in = new FileInputStream(arquivo);
				OutputStream out = new FileOutputStream(destino + "\\" + nameToFile[i - 1]);
				
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();

			}

		}
		System.out.println("count: "+ count);
	}
	
	public static void copyFileUsingChannel(File source, File dest) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}
	}
}