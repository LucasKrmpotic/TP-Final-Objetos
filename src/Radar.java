
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.lang.Math;
/**
 * Clase que define el objeto Radar que permitir� a los objetos que lo posean escanear una determinada zona del 
 * Escenario y as� detectar otros objetos que haya en el.
 * @author Krmpotic-Saiegg
 *
 */
public class Radar {

	private double anguloApertura;
	private double alcance;
	private double direccion;
	private Elemento duenio;
	private Escenario escenario;
	private ArrayList<Elemento> elementosDetectados; 
	private ArrayList<RadarListener> listeners;
	
	/**
	 * Constructor de la clase. Recibe como parametro el elemento que ser� su due�o (Radar o Satelite)
	 * @param elemento
	 */
	public Radar(Elemento elemento){
		this.listeners = new ArrayList<RadarListener>();
		this.elementosDetectados = new ArrayList<Elemento>();
		this.anguloApertura = Config.RADAR_ANGULO_APERTURA;
		this.alcance = Config.RADAR_ALCANCE_MIN;
		this.direccion = Config.RADAR_DIRECCION;
		this.duenio = elemento;
		this.escenario = Escenario.getEscenario();
	}
	
	/**
	 *M�todo para detectar otros objetos del escenario que esten dentro de la zona de detecci�n del Radar.
	 *El m�todo hace uso de las clases Rectangle y Polygon (nativas de Java) y aprovecha el m�todo intersects
	 *de la interface shape que ambas implementan. 
	 */
	public void escanear(){
		
		//ArrayList<Elemento> detectados = new ArrayList<Elemento>();
		
		
		//TODO [MEJORA] Si van a utilizar muchas veces el mismo x e y, seria interesanto ponerlas en variables locales
		//asi el c�digo queda mucho mas entendible.
		
		// armamos zona de deteccion.
		Polygon zonaDeteccion = new Polygon();
		int x = this.getDuenio().getPosicionX();
		int y = this.getDuenio().getPosicionY();
		
		zonaDeteccion.addPoint(x,y);
		for (double i = (this.getDireccion() - (this.getAnguloApertura()/2)); 
					 i< (this.getDireccion() + (this.getAnguloApertura()/2));
					 i++){
			
			zonaDeteccion.addPoint((int)(Math.cos(Math.toRadians(i) * this.getAlcance())), 
								   (int)(Math.sin(Math.toRadians(i) * this.getAlcance())));
		}
		zonaDeteccion.addPoint(x,y);
		
		//Nuevo
		
		this.elementosDetectados = this.escenario.detectarElementos(zonaDeteccion);
		
		// Hasta ac�

		/*
		//TODO [CORRECCION] No exponer la lista
		for(Elemento e : it.next()){ //Escenario.getEscenario().getElementos()) {
			
			Rectangle r = new Rectangle (e.getPosicion().getX(), e.getPosicion().getY(),
										 e.getTamanio().getAncho(), e.getTamanio().getAlto());
			
			if (zonaDeteccion.intersects(r)){
				detectados.add(e);
			}			
		}*/
		for(RadarListener listener : this.listeners){
			listener.elementosDetectados(this.elementosDetectados);
		}
	}

	
	public void addRadarListener(RadarListener listener){
		this.listeners.add(listener);
	}
	
	public void removeRadarListener(RadarListener listener){
		this.listeners.remove(listener);
	}
	
	//Metodos get y set
	public double getAnguloApertura() {
		return anguloApertura;
	}

	public void setAnguloApertura(double anguloApertura) {
		this.anguloApertura = anguloApertura;
	}

	public double getAlcance() {
		return alcance;
	}

	
	@Override
	public String toString() {
		return "Radar detecto elemento";
	}
	
	public void setAlcance(double alcance) {
		if (Config.RADAR_ALCANCE_MIN <= alcance && alcance <= Config.RADAR_ALCANCE_MAX){
			this.alcance = alcance;
		}else if(Config.RADAR_ALCANCE_MIN > alcance){
			this.alcance = Config.RADAR_ALCANCE_MIN;
		}else{
			this.alcance = Config.RADAR_ALCANCE_MAX;
		}	
	}

	public double getDireccion() {
		return direccion;
	}

	public void setDireccion(double direccion) {
		this.direccion = direccion;
	}

	public Elemento getDuenio() {
		return duenio;
	}
	
	//TODO [CORRECCION] Si tienen un addRadarListener y un removeRadarListener, no deberian exponer la variable listeners

	//public ArrayList<RadarListener> getListeners() {
	//	return listeners;
	//}

	//public void setListeners(ArrayList<RadarListener> listeners) {
	//	this.listeners = listeners;
	//}
}
