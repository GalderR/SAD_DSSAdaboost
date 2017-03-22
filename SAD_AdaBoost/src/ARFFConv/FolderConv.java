package ARFFConv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FolderConv {

	private static FolderConv mySelf = new FolderConv();
	
	private ArrayList<String> diccionarioExclusiones;
	private boolean existeDicc = false;

	private FolderConv(){
		//Comprobamos si existe un diccionario
		System.out.println("Comprobando la existencia de un diccionario de exclusiones");

		try {
			String palabra  = "";
			int contPal = 0;
			BufferedReader listaDicc = new BufferedReader(new FileReader(new File("./dicc.txt")));
			existeDicc = true;
			diccionarioExclusiones = new ArrayList<String>();
			while ((palabra = listaDicc.readLine()) != null) {
				//System.out.println(palabra);
				diccionarioExclusiones.add(palabra);
				contPal++;
			}
			System.out.println("Encontradas " + contPal + " palabras a excluir.");
			listaDicc.close();

		} catch (FileNotFoundException e1) {
			System.out.println("No se encontró un fichero de palabras dicc.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FolderConv instance(){
		return mySelf;
	}

	public ArrayList<Instance> getFolderIns(String ruta, ArrayList<String> clases){
		System.out.println("Procesando " + ruta);
		ArrayList<Instance> misInstancias = new ArrayList<Instance>();
		File mainFolder = new File(ruta);
		ArrayList<File> carpetas = new ArrayList<File>(Arrays.asList(mainFolder.listFiles()));

		for (File file : carpetas) {
			String miClase = file.getName();

			if(!clases.contains(miClase)){
				clases.add(miClase);
			}

			misInstancias.addAll(procesarArchivos(file, miClase));
		}

		return misInstancias;

	}

	public ArrayList<Instance> getFolderInsTest(String ruta, ArrayList<String> clases){
		System.out.println("Procesando " + ruta);
		ArrayList<Instance> misInstancias = new ArrayList<Instance>();
		File mainFolder = new File(ruta);

		String miClase = "?";

		misInstancias = procesarArchivos(mainFolder, miClase);
		return misInstancias;

	}

	private ArrayList<Instance> procesarArchivos(File file, String miClase){

		ArrayList<Instance> misInstancias = new ArrayList<Instance>();
		String linea = "";
		BufferedReader miReader = null;

		ArrayList<File> archivos = new ArrayList<File>(Arrays.asList(file.listFiles()));

		//Procesamos los archivos de la carpeta
		for (File archivo : archivos) {
			Instance miInstancia;
			String miTexto = "";

			try {
				miReader = new BufferedReader(new FileReader(archivo));
				while((linea = miReader.readLine()) != null){	
					if(linea.length() > 0){

						//Convertimos a minusculas
						linea = linea.toLowerCase();

						//Filtramos caracteres raros
						linea = filtrarCaracteres(linea);

						//Si existe el diccionario lo usamos y eliminamos las palabras
						if (existeDicc)
							linea = filtrarPorDiccionario(linea); 

						miTexto += linea;
						//miTexto = miTexto + linea.toLowerCase().replace("'", "").replace("\"", "").replace("\\", "");
					}
				}
				miInstancia = new Instance(miClase, miTexto);					
				misInstancias.add(miInstancia);


				miReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return misInstancias;
	}

	private String filtrarPorDiccionario(String frase){

		String res  = frase;

		for (String palabraAExcluir : diccionarioExclusiones) {
			res = res.replaceAll("\\b"+ palabraAExcluir +"\\b", "");

		}

		return res;

	}

	private String filtrarCaracteres(String frase){

		String res = frase;

		ArrayList<String> charToRemove = new ArrayList<String>();

		//charToRemove.add("'");
		//charToRemove.add("`");
		//charToRemove.add("'");
		charToRemove.add(",");
		charToRemove.add(".");
		charToRemove.add("/");
		charToRemove.add("\"");
		charToRemove.add("*");
		charToRemove.add("-");
		charToRemove.add("_");
		charToRemove.add("\\");
		charToRemove.add("&");
		charToRemove.add("%");
		charToRemove.add("$");
		charToRemove.add("#");

		for (String string : charToRemove) {
			res = res.replace(string, "");
		}

		return res;
	}
}
