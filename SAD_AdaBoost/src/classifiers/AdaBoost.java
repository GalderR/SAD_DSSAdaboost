package classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class AdaBoost {

	private static AdaBoost myself = new AdaBoost();
	static AdaBoostM1 cls;

	private AdaBoost() {
		cls = new AdaBoostM1();
		cls.setClassifier(DStump.getClassifier());
	}

	public static void setModel(String modelPath) throws Exception{
		cls =  (AdaBoostM1) SerializationHelper.read(modelPath);
	}	

	public static void setClassifier(Classifier myCls){
		cls.setClassifier(myCls);
	}

	public static void buildAdaBoost(Instances myIns) throws Exception{
		cls.buildClassifier(myIns);
	}

	public static double classify(Instance myIns) throws Exception{
		return cls.classifyInstance(myIns);
	}

	public static Classifier getClassifier(){
		return cls;
	}

}
