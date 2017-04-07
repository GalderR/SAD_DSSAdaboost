package code;

import preprocesamiento.getARFF.ARFFConverter;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.NonSparseToSparse;

public class MainPruebas {
	private static String path = "D:\\dss\\sms_spam\\";
	//private static String path = "D:\\dss\\movies_reviews\\";
	//private static String path = "D:\\dss\\tweet_sentiment_eGela\\";

	public static void main(String[] args) throws Exception {
		//Carga datos en bruto
		String[] arg=new String[4];
		arg[0]="-txt";
		arg[1]=path+"train.txt";
		arg[2]=path+"dev.txt";
		arg[3]=path+"test.txt";
		ARFFConverter.main(arg);
		
		//Carga de datos en arff
		Instances train = Data.getData().cargarDatos(path+"train.arff");
		Instances dev = Data.getData().cargarDatos(path+"dev.arff");
		Instances test = Data.getData().cargarDatos(path+"test.arff");
		
		//Paso a bow
		System.out.println("Aplicando BOW...");
		StringToWordVector filter;
		filter = stringToWordVector(train);
		Instances bowTrain = Filter.useFilter(train, filter);
		//filter = stringToWordVector(dev);
		Instances bowDev = Filter.useFilter(dev, filter);
		//filter = stringToWordVector(test);
		Instances bowTest = Filter.useFilter(test, filter);
		System.out.println("FIN del BOW...");
		//Sparse
		System.out.println("Aplicando Sparse...");
		NonSparseToSparse filter2 = nonSparseToSparse(bowTrain);
		Instances sparsedTrain = Filter.useFilter(bowTrain, filter2);
		Instances sparsedDev = Filter.useFilter(bowDev, filter2);
		Instances sparsedTest = Filter.useFilter(bowTest, filter2);
		System.out.println("FIN de Sparse...");
		/*//Numeric to nominal
		System.out.println("Aplicando Numeric to nominal...");
		NumericToNominal ntn = numericToNominal(sparsedTrain);
		Instances ntnTrain = Filter.useFilter(sparsedTrain, ntn);
		Instances ntnDev = Filter.useFilter(sparsedDev, ntn);
		Instances ntnTest = Filter.useFilter(sparsedTest, ntn);
		System.out.println("FIN del Numeric to nominal...");*/
		
		/*Instances ntnTrain = sparsedTrain;
		Instances ntnDev = sparsedDev;
		Instances ntnTest = sparsedTest;*/
		
		//Genera arff con bow
		//Data.getData().generateArff(path+"trainBowed.arff", ntnTrain);
		//Data.getData().generateArff(path+"devBowed.arff", ntnDev);
		//Data.getData().generateArff(path+"testBowed.arff", ntnTest);
		
		/*//InfoGain
		System.out.println("");
		System.out.println("");
		System.out.println("Pruebas infogain");
		System.out.println(ntnTrain.get(1).toString());
		AttributeSelection as = infoGain(ntnTrain);
		System.out.println(ntnTrain.get(1).toString());
		Instances infoGainedTrain = as.reduceDimensionality(ntnTrain);
		System.out.println(infoGainedTrain.get(1).toString());
		
		double rank;
		for (int i = 0; i < infoGainedTrain.numAttributes() - 1; i++) {
			rank = as.rankedAttributes()[i][1];
			if (rank < 0.006) {
				infoGainedTrain.deleteAttributeAt(i);
				//ntnDev.deleteAttributeAt(i);
				//ntnTest.deleteAttributeAt(i);
				i--;
			}
		}
		//Genera arff con infogain
		Data.getData().generateArff(path+"trainInfoGained.arff", infoGainedTrain);
		Data.getData().generateArff(path+"devInfoGained.arff", ntnDev);
		//Data.getData().generateArff(path+"testInfoGained.arff", ntnTest);*/
		
		//TDIDF
		
		
		//Llamo a header fixing
		//Instances fixedDev = Preprocess.headerFixing(infoGainedTrain, ntnDev);
		//Instances fixedTest = Preprocess.headerFixing(infoGainedTrain, ntnTest);
		//Genera arff despues de header fixing
		//Data.getData().generateArff(path+"devHeaderFixed.arff", fixedDev);
		//Data.getData().generateArff(path+"testHeaderFixed.arff", fixedTest);
		
		
		/*for (int i = 0; i < 10; i++) {
			System.out.println();
			System.out.println("Train "+i+": "+train.instance(i).toString());
			System.out.println("BowTrain "+i+": "+ntnTrain.instance(i).toString());
			System.out.println("InfoGainedTrain "+i+": "+infoGainedTrain.instance(i).toString());
			//System.out.println("TfIdfTrain "+i+": "+tfIdfTrain.instance(i).toString());
			System.out.println();
			System.out.println();
			System.out.println("Dev "+i+": "+dev.instance(i).toString());
			System.out.println("BowDev "+i+": "+ntnDev.instance(i).toString());
			System.out.println();
			System.out.println();
			System.out.println("Test "+i+": "+test.instance(i).toString());
			System.out.println("BowTest "+i+": "+ntnTest.instance(i).toString());
		}*/
		
		
		
	}
	
	private static StringToWordVector stringToWordVector(Instances data) throws Exception {
		StringToWordVector filter = new StringToWordVector();
		
		data.setClass(data.attribute("clase"));
		filter.setIDFTransform(false);
		filter.setTFTransform(false);
		filter.setAttributeIndices("1");
		filter.setLowerCaseTokens(true);
		filter.setOutputWordCounts(true);
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
	
	private static NonSparseToSparse nonSparseToSparse(Instances data) throws Exception{
		NonSparseToSparse filter = new NonSparseToSparse();
		filter.setInputFormat(data);
		return filter;
	}

	private static NumericToNominal numericToNominal(Instances data) throws Exception {
		NumericToNominal filter = new NumericToNominal();
		filter.setAttributeIndices("2-last-1");
		filter.setInputFormat(data);
		return filter;
	}
	
	private static AttributeSelection infoGain(Instances data) throws Exception {
		AttributeSelection as = new AttributeSelection();
		Ranker r = new Ranker();
		r.setNumToSelect(-1); //Mantener todos los atributos
		r.setThreshold(Long.MIN_VALUE);//Por defecto se usa este valor
		//r.
		r.setGenerateRanking(true);
		as.setEvaluator(new InfoGainAttributeEval());// InfoGain te da la
														// informacion de las
														// instancias
		as.setSearch(r);// Ranker las ordena segun los datos sacados de InfoGain
		as.SelectAttributes(data);
		System.out.println("dentro de atselect"+data.get(1));
		return as;
	}
}
