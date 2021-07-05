package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;

public class LineOfCode {

	public int Loc(HashSet<String> filesToAnalyze) throws IOException {
		int total = 0;
		for (String file : filesToAnalyze) {

			LineNumberReader lnr = new LineNumberReader(new FileReader(file));
			lnr.skip(Long.MAX_VALUE);
			total = lnr.getLineNumber() + total + 1;

		}
		return total;
	}
}
