package br.ufms.facom.cafeo.utils;

public class FilenameUtils {

	public FilenameUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getExtension(String path) {
        if (path == null) {
            return null;
        }
        int extensionPos = path.lastIndexOf(".");
        int lastUnixPos = path.lastIndexOf("/");
        int lastWindowsPos = path.lastIndexOf("\\");
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return path.substring(index + 1);
        }
    }
	
	public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        
        int lastUnixPos = path.lastIndexOf("/");
        int lastWindowsPos = path.lastIndexOf("\\");
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        return path.substring(lastSeparator + 1).replace("." + getExtension(path),"");
        
    }
	
	

}
