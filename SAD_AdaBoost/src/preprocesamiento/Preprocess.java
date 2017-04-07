package preprocesamiento;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.Randomize;

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
	
	public static Instances headerFixing(Instances pTrain, Instances pArreglar) throws Exception{
		String sobran = "";
		int cont=0;
		boolean encontrado = false;
		//buscamos los atributos de pArreglar que no estan en pTrain
		for(int i=0;i<pArreglar.numAttributes();i++)
		{
			int j=0;
			encontrado = false;
			while(j<pTrain.numAttributes() && !encontrado){
				String nombre = pTrain.attribute(j).name();//.split("\\s+");
				
				if(nombre.equals(pArreglar.attribute(i).name()))
				{
					encontrado = true;
				}
				j++;
			}
			if(!encontrado)
				{
				//Si es la clase, no sobra. Antes nos la ponia como que sobraba y la borraba
				//if(i!=pArreglar.classIndex()){
					sobran += i+",";
					cont++;
				//}
			}
		}
		System.out.println("Al pArreglar le sobran "+cont+" atributos.");
		
		//los borramos
		String[] sobranArray = (sobran.split(","));
		System.out.println(sobran);
		int[] sobranArrayInt = new int[sobranArray.length];
		for(int i=0;i<sobranArray.length;i++){
			sobranArrayInt[i]=Integer.parseInt(sobranArray[i]);
		}
		Remove filter = new Remove();
		filter.setAttributeIndicesArray(sobranArrayInt);
		try {
			filter.setInputFormat(pArreglar);
			pArreglar = Filter.useFilter(pArreglar, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("El pArreglar ahora tiene "+pArreglar.numAttributes()+" atributos.");
		
		//añadimos los atributos de train que no existan
		for(int i=0;i<pTrain.numAttributes();i++)
		{
			String [] nombre = pTrain.attribute(i).name().split("\\s+");
			if( pArreglar.attribute(nombre[0]) == null )
			{
				pArreglar.insertAttributeAt(pTrain.attribute(i), pArreglar.numAttributes() );
			}
		}
		System.out.println("El pArreglar despues de añadir los que faltan tiene "+pArreglar.numAttributes()+" atributos.");
				
		// Aplicamos el filtro reorder para tener los atributos en el mismo orden
		/*Reorder filtroReorder = new Reorder();
		// Se le pone el formato del conjunto train
		// al filtro para compatibilizar el test.
		filtroReorder.setInputFormat(pTrain);
		Instances newArreglar = Filter.useFilter(pArreglar, filtroReorder);
		System.out.println(pArreglar.get(0));
		System.out.println(newArreglar.get(0));
		System.out.println(pArreglar.get(1));
		System.out.println(newArreglar.get(1));
		System.out.println(pArreglar.get(2));
		System.out.println(newArreglar.get(2));*/
		
		
		return pArreglar;
		//return newArreglar;
	}
	
}
