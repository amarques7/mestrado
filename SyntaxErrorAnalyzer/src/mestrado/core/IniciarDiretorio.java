package mestrado.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IniciarDiretorio {
	
	public IniciarDiretorio( String pathFrom) {
		super();
		generateReader(pathFrom);	
	}				

	private BufferedReader reader;


	private void generateReader(String pathFrom){
		try {
			reader = new BufferedReader(new FileReader(pathFrom));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public String RetornaDirResult() throws IOException {
		return reader.readLine();
	}
	
	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}
}
