package preprocesamiento.fssTfIdf;

import code.Data;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.NonSparseToSparse;

public class TfIdf {

	public static void main(String[] args) throws Exception {
		
		// Cargar los datos
		Instances train = Data.getData().cargarDatos(args[0]);
		Instances dev = Data.getData().cargarDatos(args[1]);
		Instances test = Data.getData().cargarDatos(args[2]);

		/**
		 * BOW
		 */
		//Inicio Timer (cambiar por clase Timer)
		Long tInicio = System.currentTimeMillis();
		
		//Bow
		System.out.println("Aplicando BOW...");
		StringToWordVector filter = stringToWordVectorTFIDF(train);
		Instances bowTrain = Filter.useFilter(train, filter);
		Instances bowDev = Filter.useFilter(dev, filter);
		Instances bowTest = Filter.useFilter(test, filter);
		System.out.println("FIN del BOW...");
		//Sparse
		System.out.println("Aplicando Sparse...");
		NonSparseToSparse filter2 = nonSparseToSparse(bowTrain);
		Instances sparsedTrain = Filter.useFilter(bowTrain, filter2);
		Instances sparsedDev = Filter.useFilter(bowDev, filter2);
		Instances sparsedTest = Filter.useFilter(bowTest, filter2);
		System.out.println("FIN de Sparse...");

		// Print timer
		Long tFin = System.currentTimeMillis();
		System.out.println("Tiempo BOW : " + (tFin - tInicio) + " milisegundos");

		// Exportar a ARFF
		Data.getData().generateArff(Data.getData().formatearPath(args[0]) + "\\trainBOW_FSS_TFIDF.arff", sparsedTrain);
		Data.getData().generateArff(Data.getData().formatearPath(args[1]) + "\\devBOW_FSS_TFIDF.arff", sparsedDev);
		Data.getData().generateArff(Data.getData().formatearPath(args[2]) + "\\testBOW_FSS_TFIDF.arff", sparsedTest);
	}
	
	private static NonSparseToSparse nonSparseToSparse(Instances data) throws Exception{
		NonSparseToSparse filter = new NonSparseToSparse();
		filter.setInputFormat(data);
		return filter;
	}

	private static StringToWordVector stringToWordVectorTFIDF(Instances data) throws Exception {
		StringToWordVector filter = new StringToWordVector();
		
		data.setClass(data.attribute("clase"));
		filter.setIDFTransform(true);
		filter.setTFTransform(true);
		filter.setAttributeIndices("1");
		filter.setLowerCaseTokens(true);
		filter.setOutputWordCounts(true);
		filter.setInputFormat(data);

		return filter;
	}

}
