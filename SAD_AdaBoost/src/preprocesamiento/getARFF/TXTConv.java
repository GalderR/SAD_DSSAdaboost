package preprocesamiento.getARFF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TXTConv {

	private static TXTConv mySelf = new TXTConv();
	
	public static TXTConv instance(){
		return mySelf;
	}

	public ArrayList<Instance> getTXTIns(String ruta, ArrayList<String> clases){

		System.out.println("Procesando " + ruta);

		ArrayList<Instance> misInstancias = new ArrayList<Instance>();
		String linea = "";
		BufferedReader miReader;
		try {
			miReader = new BufferedReader(new FileReader(new File(ruta)));
			while((linea = miReader.readLine()) != null){
				Instance miInstancia;

				if(linea.split("\t").length >1){
					miInstancia = new Instance(linea.split("\t")[0], linea.split("\t")[1].replace("'", " ").replace("\"", " "));
					if(!clases.contains(linea.split("\t")[0].toLowerCase())){
						clases.add(linea.split("\t")[0].toLowerCase());
					}
				}else{
					miInstancia = new Instance("?", linea.replace("'", " ").replace("\"", " "));
				}
				misInstancias.add(miInstancia);
			}
			miReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return misInstancias;


	}

}
