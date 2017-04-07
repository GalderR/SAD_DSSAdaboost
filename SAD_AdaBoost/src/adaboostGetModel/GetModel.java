package adaboostGetModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import Classifiers.AdaBoost;
import Classifiers.Clasificador;
import Classifiers.NBayes;
import preprocesamiento.Data;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class GetModel {

	public static void main(String[] args){

		//Comprobamos los parámetros de entrada
		if(args.length < 3){
			System.out.println("Parametros de entrada incorrectos");
			System.out.println("java -jar getmodel.jar /ruta/al/archivo_train.arff /ruta/al/archivo_dev.arff /ruta/del/modelo <parametro de clasificador> <-F>");
			System.out.println("\nEl parámetro de clasificador podrá ser uno de los siguientes:");
			System.out.println("\t-NB Naive Bayes");
			System.out.println("\t-NK Naive Bayes con Estimador Kernel");
			System.out.println("\t-ND Naive Bayes con Discretizacion Supervisada");
			System.out.println("\t-AB Adaboost de Decision Stump");
			System.out.println("\t-AN Adaboost de Naive Bayes");
			System.out.println("\t-AK Adaboost de Naive Bayes con Estimador Kernel");
			System.out.println("\t-AD Adaboost de Naive Bayes con Discretizacion Supervisada");
			System.out.println("Si no se especifica ningun clasificador, o es erroneo, se usará por defecto el parametro -AB");
			System.out.println("El modelo generado se creará como nombre_del_clasificador.model");
			System.out.println("En caso de usar el flag -F se realizará la evaluacion no honesta y 10 fold cross validation");
		}else{

			try {
				String classifier = "-AB";

				System.out.println("Inicializando Modelo");
				//Carga de instancias
				Instances trainData = Data.getData().cargarDatos(args[0]);
				Instances devData = Data.getData().cargarDatos(args[1]);

				System.out.print("Construyendo modelo con ");
				long startTime = System.currentTimeMillis();

				if(args.length >= 4){classifier = args[3];}

				if(args.length == 5 && args[4].equals("-F")){
					trainData = Data.getData().append(trainData, devData);
				}

				switch (classifier) {
				case "-NB":
					System.out.println("Naive Bayes");
					NBayes.buildBayes(trainData);
					Clasificador.setClassifier(NBayes.getClassifier());
					classifier = "naive_bayes";
					break;
				case "-NK":
					System.out.println("Naive Bayes con Estimador Kernel");
					NBayes.useKernel();
					NBayes.buildBayes(trainData);
					Clasificador.setClassifier(NBayes.getClassifier());
					classifier = "Naive_kernel";
					break;
				case "-ND":
					System.out.println("Bayes con Discretizacion Supervisada");
					NBayes.useSupervisedDisc();
					NBayes.buildBayes(trainData);
					Clasificador.setClassifier(NBayes.getClassifier());
					classifier = "naive_supervdisc";
					break;
				case "-AN":
					System.out.println("Adaboost de Naive Bayes");
					AdaBoost.setClassifier(NBayes.getClassifier());
					AdaBoost.buildAdaBoost(trainData);
					Clasificador.setClassifier(AdaBoost.getClassifier());
					classifier = "boost_naive_bayes";
					break;
				case "-AK":
					System.out.println("Adaboost de Naive Bayes con Estimador Kernel");
					NBayes.useKernel();
					AdaBoost.setClassifier(NBayes.getClassifier());
					AdaBoost.buildAdaBoost(trainData);
					Clasificador.setClassifier(AdaBoost.getClassifier());
					classifier = "boost_nbayes_kernel";
					break;
				case "-AD":
					System.out.println("Adaboost de Naive Bayes con Discretizacion Supervisada");
					NBayes.useSupervisedDisc();
					AdaBoost.setClassifier(NBayes.getClassifier());
					AdaBoost.buildAdaBoost(trainData);
					Clasificador.setClassifier(AdaBoost.getClassifier());
					classifier = "boost_nbayes_superbdisc";
					break;
				default:
					//Adaboost + Decision Stump
					System.out.println("Adaboost de Decision Stump");
					AdaBoost.buildAdaBoost(trainData);
					Clasificador.setClassifier(AdaBoost.getClassifier());
					classifier = "boost_dstump";
					break;
				}


				long endTime = System.currentTimeMillis();
				System.out.println("Modelo completado en " + (endTime-startTime)/1000 + " segundos");

				if (args.length == 5 && args[4].equals("-F")){
					System.out.println("Evaluación no-honesta");
					Clasificador.evaluate(trainData);
					System.out.println("10-crossfold Validation");
					Clasificador.crossValidate(trainData, 10);
				}else{
					System.out.print("Evaluando Clasificador");
					Clasificador.evaluate(devData);	
					//Creamos el archivo binario con el modelo
					SerializationHelper.write(args[2] + "\\" + classifier + ".model", AdaBoost.getClassifier());
					System.out.println("Modelo correctamente generado en " + args[2] + "\\" + classifier + ".model");
				}


			} catch (FileNotFoundException e) {
				System.out.println("Archivo no encontrado");
				//e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
