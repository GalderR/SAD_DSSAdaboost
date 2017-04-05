package preprocesamiento.fssInfoGain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

public class InfoGainOut {

	private static InfoGainOut myInfoGain = new InfoGainOut();
	
	public static InfoGainOut instance(){
		return myInfoGain;
	}
	
	public void generateArff(String outputFile, Instances misInstances) throws IOException {
		outputFile = quitarExtensionArchivo(outputFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile + "_FSS_InfoGain.arff"));
		writer.write(misInstances.toString());
		writer.flush();
		writer.close();
	}

	private String quitarExtensionArchivo(String archivo) {
		String rdo = archivo.substring(0, archivo.length() - 5);
		return rdo;
	}
}
