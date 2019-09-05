package br.ufms.facom.cafeo.core;
import java.io.*; 
import javax.swing.JOptionPane;

public class LerArquivo {
	public static void main (String[]args){
		try{

			String nome;
			//nome = JOptionPane.showInputDialog(null,"Entre com o nome do arquivo");
			nome = "Doxyfile";
			BufferedReader br = new BufferedReader(new FileReader(nome));
			while(br.ready()){
				String linha = br.readLine();
				System.out.println(linha);
			}
			br.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		} 
	} 
}

