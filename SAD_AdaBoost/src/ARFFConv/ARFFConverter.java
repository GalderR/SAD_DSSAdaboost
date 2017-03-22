package ARFFConv;

import java.util.ArrayList;

public class ARFFConverter {

	/**
	 * -csv (tweets)
	 * All the CSV files have the "Sentiment" column
	 * just replace the UNKNOWN for ? in the arff
	 * 
	 * -folder (reviews)
	 * The folders contain txt files, each file is the 
	 * complete text and the class is the name of the folder
	 * 
	 * -txt (SMS)
	 * first column is the class (ham,spam) the second column is the text
	 * 
	 */


	public static void main(String[] args) throws Exception {

		if (args.length >= 4){

			ArrayList<String> clases = new ArrayList<String>(); //Array en el que se guardar√°n las clases

			//Arrays en los que se guardar√°n los datos
			ArrayList<Instance> train = new ArrayList<Instance>();
			ArrayList<Instance> dev = new ArrayList<Instance>();
			ArrayList<Instance> test = new ArrayList<Instance>();

			switch (args[0]) {
			case "-csv":
				if (args.length == 6){
					train = CSVConv.instance().getCSVIns(args[3],clases, args[1],args[2]);
					dev = CSVConv.instance().getCSVIns(args[4],clases, args[1],args[2]);
					test = CSVConv.instance().getCSVIns(args[5],clases, args[1],args[2]);
					//creamos los arff
					ARFFout.instance().generateArff(args[3], train, clases);
					ARFFout.instance().generateArff(args[4], dev, clases);
					ARFFout.instance().generateArff(args[5], test, clases);
				}else{
					System.out.println("Par·metros incorrectos, ejemplos:\n"
							+ "java -jar ARFFConverter -txt /ruta/al/train.txt /ruta/al/dev.txt /ruta/al/test.txt\n\n"
							+ "java -jar ARFFConverter -csv Sentiment TweetText /ruta/al/train.csv /ruta/al/dev.csv /ruta/al/test.csv"
							+ "java -jar ARFFConverter -folder /ruta/a/train /ruta/a/dev /ruta/a/test");
				}
				break;
			case "-folder":
				train = FolderConv.instance().getFolderIns(args[1],clases);
				dev = FolderConv.instance().getFolderIns(args[2],clases);
				test = FolderConv.instance().getFolderInsTest(args[3],clases);
				//creamos los arff
				ARFFout.instance().generateArff(args[1], train, clases);
				ARFFout.instance().generateArff(args[2], dev, clases);
				ARFFout.instance().generateArff(args[3], test, clases);
				break;
			case "-txt":
				train = TXTConv.instance().getTXTIns(args[1], clases);
				dev = TXTConv.instance().getTXTIns(args[2], clases);
				test = TXTConv.instance().getTXTIns(args[3], clases);
				//creamos los arff
				ARFFout.instance().generateArff(args[1], train, clases);
				ARFFout.instance().generateArff(args[2], dev, clases);
				ARFFout.instance().generateArff(args[3], test, clases);
				break;

			default:
				break;
			}

		}else{
			System.out.println("Par·metros incorrectos, ejemplos:\n"
					+ "java -jar ARFFConverter.jar -txt /ruta/al/train.txt /ruta/al/dev.txt /ruta/al/test.txt\n"
					+ "java -jar ARFFConverter.jar -csv Sentiment TweetText /ruta/al/train.csv /ruta/al/dev.csv /ruta/al/test.csv\n"
					+ "java -jar ARFFConverter.jar -folder /ruta/a/train /ruta/a/dev /ruta/a/test");
		}
	}



}
