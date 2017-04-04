package preprocesamiento.arff2Bow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;

public class ARFF2BowOut {

	private static ARFF2BowOut mySelf;

	public static ARFF2BowOut instance() {
		return mySelf;
	}

	public void generateArff(String outputFile, Instances misInstances) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile + ".arff"));
		writer.write(misInstances.toString());
		writer.flush();
		writer.close();
	}
}
