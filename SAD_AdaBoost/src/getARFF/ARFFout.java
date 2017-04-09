package getARFF;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ARFFout {
	
	private static ARFFout mySelf = new ARFFout();
	
	public static ARFFout instance(){
		return mySelf;
	}
	
	public void generateArff(String outputFile, ArrayList<Instance> misInstancias, ArrayList<String> p_classes){
		try {
			PrintWriter outFile = new PrintWriter(outputFile + ".arff", "UTF-8");
			outFile.println("%ARFF File generated automatically%");
			outFile.println("@relation no-idea");
			outFile.println("@attribute text string");

			String clases = "@attribute clase {";
			
			for (String miClase : p_classes) {
				clases = clases + miClase + ",";
			}
			clases = clases.substring(0, clases.length() - 1);
			outFile.println(clases + "}");
			

			outFile.println("@data");
			for (Instance miInstancia : misInstancias) {
				outFile.println("\""+ miInstancia.getText() + "\", " + miInstancia.getClase());
			}
			
			outFile.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
