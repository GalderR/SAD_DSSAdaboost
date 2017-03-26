package code;

import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class Evaluacion {

	private static Evaluation myEval;
	
	private Evaluacion(){}
	
	public static void crossValidateModel(Classifier myClassifier, Instances myData, int myFolds, Random rnd) throws Exception{
		myEval = new Evaluation(myData);
		myEval.crossValidateModel(myClassifier, myData, myFolds, rnd);
	}
	
	public static void crossValidateModel(Classifier myClassifier, Instances myData) throws Exception{
		crossValidateModel(myClassifier, myData,10,new Random(1));
	}
	
	public static double getWeightedFMeasure(){
		
		return myEval.weightedFMeasure();
	}
	
	public static String getResults() throws Exception{
		return myEval.toSummaryString() + myEval.toClassDetailsString() + myEval.toMatrixString();
	}
}
