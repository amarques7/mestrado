package util;

import java.io.File;
import java.io.IOException;

public class CreateFile {

	public static void create(String downloadPath, String nameFileExtension) {
		
		try {
			new File(downloadPath + nameFileExtension).createNewFile();		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	
}
