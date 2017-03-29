package code;

import weka.core.Instances;

public class DataMiningExample {

	static Instances train;
	static Instances dev;
	static Instances test;

	public static void main(String[] args) throws Exception {

		////////////////////
		// Carga de datos //
		////////////////////
		if (args.length < 1) {
			System.out.println("Lanzar el programa con la direccion al archivo .arff");
			System.out.println("java -jar DataMinigExample.jar /home/alguien/archivo.arff");
		} else {
			train = Data.getData().cargarDatos(args[0]);
			dev = Data.getData().cargarDatos(args[1]);
			test = Data.getData().cargarDatos(args[2]);
		}
		//////////////////
		// PREPROCESADO //
		//////////////////

		// Use getARFF

		// Apply arff2Bow

		// Seleccion de atributos
		Preprocess.AttributeSelect(train);
		Preprocess.Randomize(train, 42);

		///////////////
		// ESTIMADOR //
		///////////////

		////////////////
		// EVALUACION //
		////////////////

		Evaluacion.crossValidateModel(Estimator.IBk(), train);
		Evaluacion.getResults();
	}
}