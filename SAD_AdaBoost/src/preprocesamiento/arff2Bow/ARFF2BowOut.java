package preprocesamiento.arff2Bow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

public class ARFF2BowOut {

	private static ARFF2BowOut mySelf = new ARFF2BowOut();

	public static ARFF2BowOut instance() {
		return mySelf;
	}

	public void generateArff(String outputFile, Instances misInstances) throws IOException {
		outputFile = quitarExtensionArchivo(outputFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile + "_bowed.arff"));
		writer.write(misInstances.toString());
		writer.flush();
		writer.close();
	}

	private String quitarExtensionArchivo(String archivo) {
		String rdo = archivo.substring(0, archivo.length() - 5);
		return rdo;
	}
}
