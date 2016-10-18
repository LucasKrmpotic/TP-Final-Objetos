import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

public class Escenario {

	private static Escenario escenario;
	private ArrayList<Elemento> elementos;
	private int ancho;
	private int alto;
	
	private Escenario(){
		
		this.elementos = new ArrayList<Elemento>();
		this.alto = ConfigOld.ESCENARIO_ALTO;
		this.ancho = ConfigOld.ESCENARIO_ANCHO;
	}
	
	public static Escenario getEscenario(){
		
		if (escenario == null){
			escenario = new Escenario();
		}
		return escenario;
	}
	
	/**
	 * Método que crea los elementos que intervendrán en el juego y administra la dinámica del mismo
	 */
	public void iniciarJuego() throws IOException{
		
        boolean salir = false;
		
		crearElementos();
		while(true){
			do{
				turnos();
				verficarChoques();
				depurarElementos();
				mostrar();
				System.out.println("Presione ENTER para continuar ('q' para salir)");
				int caracter = System.in.read();
				if (caracter == 81 || caracter == 113){ // 81= Q  113= q
					salir = true;
				}
			} while (!salir);
		}		
	}

	// Crea los elementos que intervendrán en el juego y los agrega a la lista de elementos
	private void crearElementos() {

		
	}
	
	// Muestra el estado de cada elemento en el escenario
	private void mostrar() {
		
		
	}

	//Saca el elemento de la lista si no está vivo
	private void depurarElementos() {
		for(int i=0; i<this.elementos.size();i++){
			Elemento e1 = this.elementos.get(i);
			if (!e1.estaVivo()){
				this.elementos.remove(i);
			}
		}
	}

	private void verficarChoques() {
		for(int i=0; i<this.elementos.size();i++){
			Elemento e1 = this.elementos.get(i);
			
			for(int j=i+1; j<this.elementos.size(); j++){
				Elemento e2 = this.elementos.get(j);
				
				Rectangle r1 = new Rectangle(e1.getPosicion().getX(), e1.getPosicion().getY(),
											e1.getTamanio().getAncho(), e1.getTamanio().getAlto());
				
				Rectangle r2 = new Rectangle(e2.getPosicion().getX(), e2.getPosicion().getY(),
											e2.getTamanio().getAncho(), e2.getTamanio().getAlto());
				
				if (r1.intersects(r2)){
					e1.chocarContra(e2);
					e2.chocarContra(e1);
				}

			}			
		}
	}

	private void turnos() {
		for(int i=0; i<this.elementos.size(); i++){
			Elemento elemento = this.elementos.get(i);
			elemento.jugar();
		}
	}

	public ArrayList<Elemento> getElementos() {
		return elementos;
	}

	public void addElemento(Elemento elemento){
		this.elementos.add(elemento);
	}
	
	public void removeElemento(Elemento elemento){
		this.elementos.remove(elemento);
	}
	
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}
