package code;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class InstanceConfigurator {

	public static Instances myInstances;
	
	private InstanceConfigurator(){}
	
	public static Instances loadData(String path){
		
    	try {
    		FileReader fi=null;
			fi= new FileReader(path); 
			myInstances = new Instances(fi);
			fi.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos:" + path);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: " + path);
		}

    	myInstances.setClassIndex(myInstances.attribute("clase").index());
		
		return myInstances;
		
	}
	
}
