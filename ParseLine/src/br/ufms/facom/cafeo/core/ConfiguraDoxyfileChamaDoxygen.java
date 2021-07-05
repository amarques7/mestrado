package br.ufms.facom.cafeo.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfiguraDoxyfileChamaDoxygen {

	public static void main(String [] args ) throws IOException, InterruptedException {

		String localpath = "C:\\Codigos\\Engenharia_Software\\";	
		//String dir_projeto = "C:\\Projeto\\";
		String dir_result = "C:\\Analise\\";
		String dir_Doxyfile = "C:\\Users\\amarq\\eclipse-workspace\\ParseLine\\";
		String nome_projeto = "BCC-2s13-PI2-Codigo-de-Honra";

		DoxyfileDoxygen("C:\\Codigos\\Engenharia_Software\\", dir_result, dir_Doxyfile,nome_projeto);
	}
	public static void DoxyfileDoxygen( String localpath, String dir_result, String dir_Doxyfile, String nome_projeto) throws IOException, InterruptedException{
		String line;
	
		ProcessBuilder processBuilder = new ProcessBuilder();
		ProcessBuilder processBuilder2 = new ProcessBuilder();

		processBuilder.command("python", localpath + "Doxyfile.py", dir_result, dir_Doxyfile, nome_projeto);
		processBuilder.redirectOutput(new File (dir_result + nome_projeto +"1.txt"));
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
		//BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		int i = process.waitFor();
		System.out.println(i);
		if(i == 0) {
	
		processBuilder2.command("Doxygen", dir_Doxyfile + "Doxyfile");
		processBuilder2.redirectOutput(new File (dir_result + nome_projeto +".txt"));
		processBuilder2.redirectErrorStream(true);
		
		Process process2 = processBuilder2.start();
		int x = process2.waitFor();
		System.out.println( x);
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();}
		else {
		System.out.println("fudeu!!1");
		System.exit(0);
		}
		
	}
}	