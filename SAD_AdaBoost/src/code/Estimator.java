package code;

import weka.classifiers.bayes.*;
import weka.classifiers.lazy.*;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.SelectedTag;
import weka.core.neighboursearch.LinearNNSearch;

public class Estimator {
	
	private Estimator(){}
	
	public static NaiveBayes NaiveBayes(){
		return NaiveBayes();
	}
	
	public static IBk IBk(int k, DistanceFunction myDist, int myWeight ) throws Exception{
		IBk myEst = new IBk();
		LinearNNSearch lKnn = new LinearNNSearch();

		lKnn.setDistanceFunction(myDist);
		
		myEst.setKNN(k);
		myEst.setNearestNeighbourSearchAlgorithm(lKnn);
		myEst.setDistanceWeighting(new SelectedTag(myWeight, IBk.TAGS_WEIGHTING));
		
		return myEst;
	}
	
	public static IBk IBk() throws Exception{
		return IBk(1, new EuclideanDistance(), IBk.WEIGHT_NONE);
	}
	
	//TODO: Crear AdaBoost
	
}
