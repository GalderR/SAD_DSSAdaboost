package code;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.instance.*;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;

public class Preprocess {

	private Preprocess(){}
	
	/**
	 * 
	 * @param myIns Instancias de weka
	 * @param random Random seed para el filtro
	 * @return Devuelve las instancias colocadas aleatoriamente
	 * @throws Exception
	 */
	public static void Randomize(Instances myIns, int random) throws Exception{
		
		//Crear filtro
		Randomize rnd = new Randomize();
		rnd.setRandomSeed(random);
		rnd.setInputFormat(myIns);
		
		//Aplicar filtro
		myIns = Filter.useFilter(myIns, rnd);

	}
	
	/**
	 * 
	 * @param myIns Instancias de weka
	 * @return Devuelve las instancias colocadas aleatoriamente
	 * @throws Exception
	 */
	public static Instances Randomize(Instances myIns) throws Exception{
		//Si el usuario es vago aleatorizamos con el sentido de la vida
		Randomize(myIns,42);
		return myIns;
	}
	
	
	public static void AttributeSelect(Instances myIns) throws Exception {
		
		//Crear Filtro
		AttributeSelection filter= new AttributeSelection();
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search=new BestFirst();
		filter.setEvaluator(eval);
		filter.setSearch(search);
		filter.setInputFormat(myIns);
		
		//Aplicar filtro
		myIns = Filter.useFilter(myIns, filter); 

	}
}
