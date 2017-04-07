package preprocesamiento.fssInfoGain;

import preprocesamiento.Data;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;

public class InfoGain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
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

		train.setClassIndex(0);

		AttributeSelection as = infoGain(train);
		train = as.reduceDimensionality(train);
		double rank;
		for (int i = 0; i < train.numAttributes() - 1; i++) {
			rank = as.rankedAttributes()[i][1];
			if (rank < 0.006) {
				train.deleteAttributeAt(i);
				i--;
			}
		}

		// Print timer
		Long tFin = System.currentTimeMillis();
		System.out.println("Tiempo BOW : " + (tFin - tInicio) + " milisegundos");

		
	}

	private static AttributeSelection infoGain(Instances data) throws Exception {
		weka.attributeSelection.AttributeSelection as = new weka.attributeSelection.AttributeSelection();
		Ranker r = new Ranker();
		r.setNumToSelect(-1); //Mantener todos los atributos
		r.setThreshold(-1.79);//
		r.setGenerateRanking(true);
		as.setEvaluator(new InfoGainAttributeEval());// InfoGain te da la
														// informaci󮠤e las
														// instancias
		as.setSearch(r);// Ranker las ordena seg򮠬os datos sacados de InfoGain
		as.SelectAttributes(data);
		return as;
	}

}
