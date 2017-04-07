package preprocesamiento.fssTfIdf;

import code.Data;
import code.Preprocess;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.NumericToNominal;
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
		//Numeric To Nominal
		System.out.println("Aplicando Numeric To Nominal...");
		NumericToNominal filter3 = numericToNominal(sparsedTrain);
		Instances nominalTrain = Filter.useFilter(bowTrain, filter3);
		Instances nominalDev = Filter.useFilter(bowDev, filter3);
		Instances nominalTest = Filter.useFilter(bowTest, filter3);
		System.out.println("FIN de Numeric To Nominal...");
		
		double idf;
		int cont = 0;
		for (int i = 1; i < nominalTrain.numAttributes()-1; i++) {
			idf = Double.parseDouble(nominalTrain.attribute(i).value(1));
			if (idf > 3) {// Observando los resultados vemos que cuánto más
							// pequeño es el número idf más común es la palabra
							// en las 3 clases.
				nominalTrain.deleteAttributeAt(i);
				i--;
				cont++;
			}
		}

		// Print timer
		Long tFin = System.currentTimeMillis();
		System.out.println("Tiempo BOW : " + (tFin - tInicio) + " milisegundos");
		
		Instances fixedDev = Preprocess.headerFixing(nominalTrain, nominalDev);
		Instances fixedTest = Preprocess.headerFixing(nominalTrain, nominalTest);

		// Exportar a ARFF
		Data.getData().generateArff(Data.getData().formatearPath(args[0]) + "\\trainBOW_FSS_TFIDF.arff", nominalTrain);
		Data.getData().generateArff(Data.getData().formatearPath(args[1]) + "\\devBOW_FSS_TFIDF.arff", fixedDev);
		Data.getData().generateArff(Data.getData().formatearPath(args[2]) + "\\testBOW_FSS_TFIDF.arff", fixedTest);
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
	
	public static NumericToNominal numericToNominal(Instances data) throws Exception {
		NumericToNominal filter = new NumericToNominal();
		filter.setAttributeIndices("2-last-1");
		filter.setInputFormat(data);
		return filter;
	}

}
