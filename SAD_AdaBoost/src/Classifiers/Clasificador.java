package Classifiers;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;

public class Clasificador {

	private static Classifier myClassifier = null;
	private static Evaluation myEval;
	
	private Clasificador(){}
	
	public static void setClassifier(Classifier myCls){
		myClassifier = myCls;
	}
	
	public static Classifier getClassifier(){
		return myClassifier;
	}
	
	public static void build(Instances myIns){
		try {
			myClassifier.buildClassifier(myIns);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void evaluate(Instances myIns){
		try {
			myEval  = new Evaluation(myIns);
			myEval.evaluateModel(myClassifier, myIns);
			System.out.println(myEval.toSummaryString());
			System.out.println(myEval.toClassDetailsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void crossValidate(Instances myIns, int numFolds){
		try {
			myEval = new Evaluation(myIns);
			myEval.crossValidateModel(myClassifier, myIns, numFolds, new Random(42));
			System.out.println(myEval.toSummaryString());
			System.out.println(myEval.toClassDetailsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
