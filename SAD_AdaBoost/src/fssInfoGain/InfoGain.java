package fssInfoGain;

import preprocesamiento.Data;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;

public class InfoGain {

	public static void main(String[] args) throws Exception {
		// Cargar los datos
		Instances train = Data.getData().cargarDatos(args[0]);

		/**
		 * 
		 * INFOGAIN
		 * 
		 */
		// Inicio Timer (cambiar por clase Timer)
		Long tInicio = System.currentTimeMillis();
		System.out.println("Aplicando InfoGain...");

		//InfoGain
		AttributeSelection as = infoGain(train);
		Instances infoGainedTrain = as.reduceDimensionality(train);
		
		double rank;
		for (int i = 0; i < infoGainedTrain.numAttributes() - 1; i++) {
			rank = as.rankedAttributes()[i][1];
			if (rank < 0.006) {
				infoGainedTrain.deleteAttributeAt(i);
				i--;
			}
		}

		// Print timer
		Long tFin = System.currentTimeMillis();
		System.out.println("Tiempo BOW : " + (tFin - tInicio) + " milisegundos");

		Data.getData().generateArff(Data.getData().formatearPath(args[0]) + "\\trainBOW_FSS_InfoGain.arff", infoGainedTrain);
		
	}

	private static AttributeSelection infoGain(Instances data) throws Exception {
		AttributeSelection as = new AttributeSelection();
		Ranker r = new Ranker();
		r.setNumToSelect(-1); //Mantener todos los atributos
		r.setThreshold(Long.MIN_VALUE);//Por defecto se usa este valor
		r.setGenerateRanking(true);
		as.setEvaluator(new InfoGainAttributeEval());// InfoGain te da la
														// informacion de las
														// instancias
		as.setSearch(r);// Ranker las ordena segun los datos sacados de InfoGain
		as.SelectAttributes(data);
		return as;
	}

}
