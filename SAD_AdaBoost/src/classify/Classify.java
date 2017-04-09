package classify;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Classify {

	public static void main(String[] args) {
		if(args.length < 3){
			System.out.println("Parametros de entrada incorrectos");
			System.out.println("java -jar getmodel.jar /ruta/al/archivo.arff /ruta/del/modelo.model /ruta/de/resultados.txt");			
		}else{


			try {
				FileWriter fw = new FileWriter(new File(args[2]));
				
				Instances myData;				
				myData = new Instances(new FileReader(new File(args[0])));
				myData.setClassIndex(myData.attribute("clase").index());
				
				//Cargamos el modelo para clasificar
				Classifier cls = (Classifier) SerializationHelper.read(args[1]);
				
				for (int i = 0; i < myData.numInstances()-1; i++) {
					double res = cls.classifyInstance(myData.instance(i));
					System.out.println("Instance " + (i+1) + " classified as " + res);	
					fw.append("\nInstance " + (i+1) + " classified as " + res);
				}
				fw.flush();
				fw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
