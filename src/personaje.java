import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

public abstract class personaje implements Comparable<personaje>{

	//Nombre del personaje
	protected String nombre = null;

	//Identificador del personaje
	protected String id;

	//Turno en el que se encuentra el personaje(inicialmente 0)
	//Usada para saber si el personaje se ha movido o no
	protected int turno = 0;

	//Identificador de la sala donde se encuentra el personaje
	protected int salaI = 0;

	//Pila de llaves del personaje
	protected Stack <llave> llaves = new Stack <> ();

	//Estructura de datos que almacena las direcciones que tendra el personaje durante toda la simulacion
	protected ArrayList<Dir> direcciones = new ArrayList<Dir>();

	//Contador de movimientos del personaje
	protected int movimiento = 0;
	
	//Será true si el personaje abre la puerta del trono
	protected boolean winner;
	
	//El tipo del personaje (Stark, Targaryen, etc)
	protected String tipo;


	//Constructor por defecto del personaje
	public personaje(){
		this.nombre="Desconocido";
		this.id="N";
		this.turno = 0;
		this.salaI = 0;
		this.movimiento = 0;
		this.winner = false;
		this.tipo = "Desconocido";
	}

	//Constructor parametrizado de personaje cuyos parÃ¡metros son el nombre, el identificador(casa), el turno
	// y la sala en la que se encuentra
	public personaje(String nombre,String marca, int turno, int sala){
		this.nombre=nombre;
		this.id=marca;
		this.turno = turno;
		this.salaI = sala;
		this.movimiento = 0;
		this.winner = false;
		this.tipo = "Desconocido";
	}

	//Muestra la informacion del personaje, definida por cada uno posteriormente
	public abstract void showInfo() throws IOException;

	//Calcula la ruta de movimientos que debe seguir el personaje, como cada uno se mueve de forma distinta
	// lo realizaremos despues
	public abstract void calculateMovement();
	
	
	//Metodo que realizarÃ¡ la accion de puerta que cada personaje defina
	public abstract void door_action(Door d);

	//Metodo que realizarÃ¡ la accion de coger o soltar una llave segun el personaje
	public abstract void action(Room r);

	//Retorna si el personaje esta vacio o no de llaves
	public boolean emptyOfKeys(){return llaves.isEmpty(); }

	//Transforma una lista de enteros que corresponde a los identificadores de las salas que ha visitado el personaje
	// en un array de Direcciones
	public void transformRoute(LinkedList<Integer> visitados, int dimensionX){
		Integer aux,aux2;
		Dir direccion;
		List<Dir> Dirs= new List<>();
		for(int i = 0; i+1<visitados.size();i++){
			aux = visitados.get(i);
			aux2 = visitados.get(i+1);
			if(aux2-aux == 1){
				direccion = Dir.E;
				Dirs.addLast(direccion);
			}
			else{
				if(aux-aux2 == 1){
					direccion = Dir.O;
					Dirs.addLast(direccion);
				}
				else{
					if(aux-aux2 == dimensionX){
						direccion = Dir.N;
						Dirs.addLast(direccion);
					}
					else{
						direccion = Dir.S;
						Dirs.addLast(direccion);
					}
				}
			}
		}
		addMovements(Dirs);
	}

	
	
	//Este módulo encuentra todos los caminos posibles
		public void allRoutesPj(int origen, int destino, LinkedList<Integer> visitados, Grafo grafoMapa, LinkedList<LinkedList<Integer>> caminos){
			if(origen == destino){
				visitados.addLast(origen);
				LinkedList<Integer> copiaVisitados = new LinkedList<Integer>();
				//Como visitados va a ser modificado hay que copiar cada elemento, si copiasemos la lista completa de golpe 
				//se copiaría como referencia, de manera que las modificaciones a visitados afectarían a caminos
				for(int i=0; i<visitados.size();i++){
					int a = visitados.get(i);
					copiaVisitados.addLast(a);
				}
				caminos.add(copiaVisitados);
			}
			else{
				//Es un módulo recursivo que recorre todos los posibles caminos del mapa
				ArrayList<Integer> vecinos = new ArrayList<Integer>();
				grafoMapa.adyacentes(origen, vecinos);
				visitados.addLast(origen);

				for(int i = 0;i<vecinos.size();i++){
					if(!visitados.contains(vecinos.get(i)))
						allRoutesPj(vecinos.get(i), destino, visitados, grafoMapa, caminos);
				}

			}
			visitados.removeLast();

		}
		
	//Metodo que realiza el movimiento del personaje en el tablero
	public void movement(int dimensionX, int dimensionTotal){
		Dir d;
		//si el personaje sigue teniendo direcciones en su estructura de datos procesamos la siguiente
		if(!direcciones.isEmpty()){
			d = direcciones.get(movimiento);
			//Comprobamos que movimiento quiere realizar el personaje considerando los limites del tablero
			switch (d){
				case N:
					if(salaI-dimensionX < 0) {
						System.out.println("No se puede mover hacia el norte, siguiente movimiento");
					}
					else {
						salaI = salaI - dimensionX;
					}
					break;

				case E:
					if(salaI+1 % dimensionX == 0) {
						System.out.println("No se puede mover hacia el este, siguiente movimiento");
					}
					else{
						salaI = salaI+1;
					}
					break;

				case O:
					if(salaI % dimensionX == 0){
						System.out.println("No se puede mover hacia el oeste, siguiente movimiento");
					}
					else{
						salaI = salaI-1;
					}
					break;

				case S:
					if(salaI+dimensionX > dimensionTotal){
						System.out.println("No se puede mover hacia el sur, siguiente movimiento");
					}
					else{
						salaI = salaI+dimensionX;
					}
					break;

			}
			direcciones.remove(d);
		}
		else
			System.out.println("No hay mas movimientos");
	}

	//Nos dice si el personaje contiene movimientos aun en su array de direcciones
	public boolean emptyOfMovements(){ return direcciones.isEmpty();}

	//Metodo que aÃ±ade el array de movimientos al personaje
	public void addMovements(List<Dir> newDir){
			while(!newDir.estaVacia()) {
				Dir aux;
				aux = newDir.getFirst();
				direcciones.add(aux);
				newDir.removeFirst();
			}
	}

	//Proporciona el identificador de la sala donde se encuentra el personaje
	public int getSalaI(){
		return salaI;
	}
	
	//Devuelve el tipo del personaje
	public String getTipo(){
		return tipo;
	}
	
	//Devuelve true si el atributo winner está a true
	public boolean isWinner(){
		return winner;
	}
	
	//Pone el atributo winner a true
	public void setWinner(){
		winner = true;
	}
	
	//Da valor al atributo de la sala del personaje
	public void setSalaI(int sala){
		salaI = sala;
	}

	//Nos devuelve el identificador del personaje
	public String get_id(){
		return this.id;
	}

	//Retorna el nombre del personaje
	public String get_nombre(){ return this.nombre;}

	//Devuelve el turno en el que se encuentra el personaje
	public int get_Turno() { return this.turno; }
	
	@Override
    public boolean equals(Object obj){
    	if (this == obj) 
    		return true;
    	if (!(obj instanceof personaje))
    		return false;
    	personaje pAux = (personaje) obj;
    	return (this.id.equals(pAux.get_id()));

    }
    
   

	@Override
	public int compareTo(personaje p2) {
		if (this == p2) 
			return 0;
		return (this.id.compareTo(p2.get_id()));
	}
	


}
