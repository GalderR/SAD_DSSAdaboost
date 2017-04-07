package Classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NBayes {

	private static NBayes myself = new NBayes();
	static NaiveBayes cls = new NaiveBayes();

	private NBayes() {
	}

	public static void useKernel(){
		cls.setUseKernelEstimator(true);
	}

	public static void useSupervisedDisc(){
		cls.setUseSupervisedDiscretization(true);
	}

	public static Classifier getClassifier(){
		return cls;
	}
	
	public static void buildBayes(Instances myIns) throws Exception{
		cls.buildClassifier(myIns);
	}

}
