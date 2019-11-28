package util;

import java.io.File;

import analysis.core.AstLogger;


public class TransformarSegundaParaHoras {

	public void transforma(long segundos, String projeto) {
		StringBuilder data = null;
		StringBuilder directory = null;
		StringBuilder nameFile = null;
		double hora = 0.0;
		double minuto = 0.0;
		double seg = 0.0;
		hora = java.util.concurrent.TimeUnit.SECONDS.toHours(segundos);
		segundos = (int) (segundos - (hora * 3600));
		minuto = java.util.concurrent.TimeUnit.SECONDS.toMinutes(segundos);
		segundos = (int) (segundos - (minuto * 60));
		seg = segundos;

		data.append(projeto + ";" + hora + ";" + minuto +" ;" + seg);
		directory.append( "C://error" + File.separator);
		nameFile.append("Tempo_execução" + ".csv");
		AstLogger.writeaST(data, directory, nameFile);

	}

}
