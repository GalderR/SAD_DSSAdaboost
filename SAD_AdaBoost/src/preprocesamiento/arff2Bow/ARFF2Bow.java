package preprocesamiento.arff2Bow;

import code.Data;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ARFF2Bow {

	public static void main(String[] args) throws Exception {

		// Cargar los datos
		Instances train = Data.getData().cargarDatos(args[0]);
		Instances dev = Data.getData().cargarDatos(args[1]);
		Instances test = Data.getData().cargarDatos(args[2]);

		/**
		 * BOW
		 */
		// Inicio Timer (cambiar por clase Timer)
		Long tInicio = System.currentTimeMillis();
		System.out.println("Aplicando BOW...");

		train.setClassIndex(0);
		dev.setClassIndex(0);
		test.setClassIndex(0);

		StringToWordVector filter = new StringToWordVector();
		filter.setIDFTransform(false);
		filter.setTFTransform(false);
		filter.setLowerCaseTokens(true);
		filter.setOutputWordCounts(true);

		filter.setInputFormat(train);
		Instances bowTrain = Filter.useFilter(train, filter);

		filter.setInputFormat(dev);
		Instances bowDev = Filter.useFilter(dev, filter);

		filter.setInputFormat(test);
		Instances bowTest = Filter.useFilter(test, filter);

		// Print timer
		Long tFin = System.currentTimeMillis();
		System.out.println("Tiempo BOW : " + (tFin - tInicio) + " milisegundos");

		// Exportar a ARFF
		Data.getData().generateArff(Data.getData().formatearPath(args[0]) + "\\train_bowed.arff", bowTrain);
		Data.getData().generateArff(Data.getData().formatearPath(args[1]) + "\\dev_bowed.arff", bowDev);
		Data.getData().generateArff(Data.getData().formatearPath(args[2]) + "\\test_bowed.arff", bowTest);
	}
}
