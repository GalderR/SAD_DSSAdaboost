package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class GetARFF {
	private static GetARFF mArchivo = new GetARFF();
	private File archivo;
	private File archi;
	private FileReader fr;
	private BufferedReader br;

	private BufferedWriter bw;

	private GetARFF() {
		archivo = null;
		archi=null;
		fr = null;
		br = null;
		bw=null;
	}

	public static GetARFF getFichero() {
		return mArchivo;
	}

	public void parser(String pArchivo,String pSalida) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		//leer
		archivo = new File(pArchivo);
		fr = new FileReader(archivo);
		br = new BufferedReader(fr);
		Scanner entrada = new Scanner(archivo);
		//write
		String rute=pSalida+"/resultados.arff";
		archi = new File(rute);
		bw = new BufferedWriter(new FileWriter(archi));
		PrintWriter pw = new PrintWriter(bw);
		
		int contador = 0;		
		String linea = "", inicio="", resto="";		
		while (entrada.hasNext()) {
			linea = entrada.nextLine();
			if (contador == 0) {
				pw.println("@relation SMS");
				pw.println("@attribute class {ham, spam}");
				pw.println("@attribute Text string");
				pw.println("@data");
				contador++;
			} else {
				if (linea.length() > 4) {
					inicio = linea.substring(0, 3);
					inicio = inicio.replace("\t", "");
					if(inicio.equals("ham")||inicio.equals("spam")){
					resto = linea.substring(4, linea.length());
					resto = resto.replace(":", "");
					resto = resto.replace("-)", "");
					resto = resto.replace(")", "");
					resto = resto.replace(".", "");
					resto = resto.replace("(", "");
					resto = resto.replace("/", "");
					resto = resto.replace("''", "");
					resto = resto.replace("-", "");
					pw.print(inicio + " " + resto + " ");
					pw.println(" ");
					pw.flush();
					}
					else{
						resto = linea;
						resto = resto.replace(":", "");
						resto = resto.replace("-)", "");
						resto = resto.replace(")", "");
						resto = resto.replace(".", "");
						resto = resto.replace("(", "");
						resto = resto.replace("/", "");
						resto = resto.replace("''", "");
						resto = resto.replace("-", "");
						pw.print(resto + " ");
						pw.println(" ");
						pw.flush();
					}
				}
			}				
		}		
		pw.close();
		entrada.close();
		System.out.println("Terminado!!");
	}
}
