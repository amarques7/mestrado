package mestrado.utils;

import java.util.*;
import java.io.*;

public class ListFilesC {
	
	
	public List<File> findFiles (final File startingDirectory, final String pattern) {
        List<File> files = new ArrayList<File>();
        
        if (startingDirectory.isDirectory()) {
            File[] sub = startingDirectory.listFiles(new FileFilter() {
                public boolean accept (File pathname) {
                    return pathname.isDirectory() || pathname.getName().matches (pattern);
                }
            });
            for (File fileDir: sub) {
                if (fileDir.isDirectory()) {
                    files.addAll (findFiles (fileDir, pattern));
                } else {
                    files.add (fileDir); // 
                }
            }
        }
        
        return files;
    }

}
