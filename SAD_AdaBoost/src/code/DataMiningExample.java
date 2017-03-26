package code;

public class DataMiningExample {
	
    public static void main(String[] args) throws Exception {
		
    	////////////////////
    	// Carga de datos //
    	////////////////////
    	if(args.length < 1){
    		System.out.println("Lanzar el programa con la direccion al archivo .arff");
    		System.out.println("java -jar DataMinigExample.jar /home/alguien/archivo.arff");
    	}else{
    		InstanceConfigurator.loadData(args[0]);
    	}
		//////////////////
    	// PREPROCESADO //
		//////////////////
		
    	Preprocess.AttributeSelect(InstanceConfigurator.myInstances); 	
		Preprocess.Randomize(InstanceConfigurator.myInstances, 42);
		
		///////////////
		// ESTIMADOR //
		///////////////
		
		
		
		////////////////
		// EVALUACION //
		////////////////
		
		Evaluacion.crossValidateModel(Estimator.IBk(), InstanceConfigurator.myInstances);
		Evaluacion.getResults();
    }
}