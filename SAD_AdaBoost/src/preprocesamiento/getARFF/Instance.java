package preprocesamiento.getARFF;

public class Instance {
	private  String clase;
	private  String texto;
	
	public Instance(String p_clase, String p_texto){
		this.clase = p_clase;
		this.texto = p_texto;
	}
	
	public String getClase(){
		return clase;
	}
	public String getText(){
		return texto;
	}
}
