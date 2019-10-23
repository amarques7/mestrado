package mestrado.utils;

import java.io.File;
import java.io.IOException;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class ListFilesC {

	public static void main(String[] args) {
		new ListFilesC();
	}

	public ListFilesC() {
		File f = new File("C:/Projeto/BCC-2s13-PI2-Codigo-de-Honra");
//		System.out.println(contarArquivos("C:/Projeto/" +dirproj , "c"));
	}

	public int contarArquivos(File path, String extensao) {
		int quantidade = 0;
		

		if (path == null)
			throw new RuntimeException("Deve ser informado um diret?io!");
		if (!path.exists())
			throw new RuntimeException("Diret?io n? existe. [" + path.getAbsolutePath() + "]");
		if (!path.isDirectory())
			throw new RuntimeException("Deve ser informado um diret?io. [" + path.getAbsolutePath() + "]");
		if (!path.canRead())
			throw new RuntimeException("Sem permiss? no diret?io. [" + path.getAbsolutePath() + "]");

		quantidade += path.list(new FiltroArquivo(Pattern.compile(".*" + extensao))).length;

		File pastasDentro[] = path.listFiles(new FiltroDiretorio(Pattern.compile(".*")));

		for (File pastaDentro : pastasDentro)
			quantidade += contarArquivos(pastaDentro, extensao);

		return quantidade;
	}

	private class FiltroArquivo implements FilenameFilter {
		private final Pattern jexl;

		public FiltroArquivo(Pattern expressaoRegular) {
			this.jexl = expressaoRegular;
		}

		public boolean accept(File path, String nome) {
			return jexl.matcher(nome).matches();
		}
	}

	private class FiltroDiretorio implements FilenameFilter {
		private final Pattern jexl;

		public FiltroDiretorio(Pattern expressaoRegular) {
			this.jexl = expressaoRegular;
		}

		public boolean accept(File path, String nome) {
			return new File(path.getAbsolutePath() + File.separator + nome).isDirectory()
					&& jexl.matcher(nome).matches();
		}
	}

}
