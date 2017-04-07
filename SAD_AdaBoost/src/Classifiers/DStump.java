package Classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.trees.DecisionStump;

public class DStump {
	
	private static DStump myself = new DStump();
	static DecisionStump cls;
	
	private DStump() {
		cls = new DecisionStump();
		cls.setBatchSize("100");
		cls.setNumDecimalPlaces(2);
	}
	
	public static Classifier getClassifier(){
		return cls;
	}
	
}
