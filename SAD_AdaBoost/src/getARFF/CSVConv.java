package getARFF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVConv {

	private static CSVConv mySelf = new CSVConv();

	public static CSVConv instance(){
		return mySelf;
	}

	public ArrayList<Instance> getCSVIns(String ruta, ArrayList<String> clases, String clase, String texto){
		System.out.println("Procesando " + ruta);
		ArrayList<Instance> misInstancias = new ArrayList<Instance>();
		String linea = "";
		BufferedReader miReader;
		int colClase = 0;
		int colTexto = 0;

		try {
			miReader = new BufferedReader(new FileReader(new File(ruta)));

			linea = miReader.readLine();
			String[] columnas = linea.split("\",\"");

			//Obtenemos las columnas de la clase y el texto
			for (int i = 0; i < columnas.length; i++) {
				if(columnas[i].toLowerCase().contains(clase.toLowerCase())){
					colClase = i;
				}
				if(columnas[i].toLowerCase().contains(texto.toLowerCase())){
					colTexto = i;
				}
			}

			while((linea = miReader.readLine()) != null){
				Instance miInstancia;
				if((linea.length() > 0) && (columnas.length == linea.split("\",\"").length)){
					String miClase = ARFFConverter.filtrarCaracteres(linea.split("\",\"")[colClase].toLowerCase());
					String miTexto = ARFFConverter.filtrarCaracteres(linea.split("\",\"")[colTexto].toLowerCase());

					if(!miClase.toLowerCase().contains("unknown")){
						miInstancia = new Instance(miClase, miTexto);
						if(!clases.contains(miClase)){
							clases.add(miClase);
						}
					}else{
						miInstancia = new Instance("?", miTexto);
					}

					misInstancias.add(miInstancia);
				}
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
