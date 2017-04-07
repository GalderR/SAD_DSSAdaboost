package code;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instance;
import weka.core.Instances;

public class Data {
	// Attributes
	private static Data miData = new Data();

	// Constructor
	private Data() {
	}

	// Static getter
	public static Data getData() {
		if (miData == null) {
			miData = new Data();
		}
		return miData;
	}

	// Methods
	public Instances cargarDatos(String path) throws Exception {
		// Open the file
		FileReader fi = null;
		try {
			fi = new FileReader(path);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos: " + path);
		}
		// Load the instances
		Instances data = null;
		try {
			data = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: " + path);
		}
		// Close the file
		try {
			fi.close();
		} catch (IOException e) {
			System.out.println("Error al cerrar el archivo.");
		}
		// Specify which attribute will be used as the class: 
		data.setClass(data.attribute("clase"));
		return data;
	}
	
	public void generateArff(String outputFile, Instances misInstances) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		writer.write(misInstances.toString());
		writer.flush();
		writer.close();
	}

	public String formatearPath(String path) {
		int pos = path.lastIndexOf("\\");
		path = path.substring(0,pos) ;
		return path;
	}
	
	public Instances append(Instances train, Instances dev){
		// Juntar los dos archivos en uno
		Instances trainDev = new Instances(train);
		for (int i = 0; i < dev.numInstances(); i++) {
			trainDev.add(dev.instance(i));
		}
		return trainDev;
	}
}
