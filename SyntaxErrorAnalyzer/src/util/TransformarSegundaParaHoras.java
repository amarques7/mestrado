package util;

import java.io.File;
import java.io.IOException;
import analysis.core.AstLogger;


public class TransformarSegundaParaHoras {

	

	public void transforma(long segundos, String projeto) throws IOException {
		StringBuilder data = new StringBuilder();
		StringBuilder directory = new StringBuilder();
		StringBuilder nameFile = new StringBuilder();
		
		double hora = 0.0;
		double minuto = 0.0;
		double seg = 0.0;
		double tempoTotal = 0.0;
		tempoTotal = java.util.concurrent.TimeUnit.SECONDS.toHours(segundos);
		
		hora = (int) (segundos - (tempoTotal * 3600));
		minuto = java.util.concurrent.TimeUnit.SECONDS.toMinutes(segundos);
		segundos = (int) (segundos - (minuto * 60));
		seg = segundos;
		
		String hora1 = Double.toString(minuto);
		String minuto1 = Double.toString(minuto);
		String seg1 = Double.toString(seg);

		data.append(projeto).append(";").append(hora1).append(";").append(minuto1).append(";").append(seg1);
		directory.append( "C://error" + File.separator);
		nameFile.append("Tempo_execução.csv");
	
	
		AstLogger.writeaST(data, directory, nameFile);

	}

}
