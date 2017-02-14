/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */


import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

enum Dir {S, E, N, O};

public class Mapa {

	//Dimension X del mapa
	private int dimX;

	//Dimension Y del tablero
	private int dimY;

	//Resultado de la dimension total del mapa, usada como parametro en algunos modulos
	private int dimMapa;

	//Entero que indica el identificador de la sala que contiene la puerta del trono
	private int salaPuerta;

	//Altura del arbol de llaves que tiene la puerta en su configuracion
	protected int alturaArbol;
	private static Mapa mapAux = null;

	//Vector de Salas que posee el mapa
	private Room [] vectorRoom;

	//Turno limite que tiene el mapa, para no quedar en bucle infinito de movimientos el juego
	private int turnoLimite = 0;

	//Entero que almacena el turno actual del juego, usado mas adelante como comparacion para que los personajes no
	//se muevan dos veces en un mismo turno
	private int turnoActual = 0;

	//Sala donde se almacenan los personajes que consiguen acceder al trono y ganar el juego
    private Room winnersRoom = new Room(1111);

	//Grafo del mapa
	private Grafo wallGraph;
	
	private boolean firstTimeEx;


	//Constructor por defecto de mapa, los parametros resultantes de este constuctor nos darian un mapa
	//con la puerta en la primera sala y sin arbol de llaves
	public Mapa(){
		this.dimX = 0;
		this.dimY = 0;
		this.dimMapa = dimX*dimY;
		this.salaPuerta = 0;
		this.alturaArbol = 0;
		this.turnoActual = 0;
		this.turnoLimite = 100;
		firstTimeEx = true;
		wallGraph = new Grafo();
	}

	//Constructor parametrizado del mapa, entran por parametros las dimensiones, el identificador de la sala que
	//contendra la puerta del trono y la altura de su arbol de llaves para la combinacion
	//Este constructor daria salida a un mapa con el que la simulacion es posible sin errores
	private Mapa(int dimX, int dimY, int salaPuerta, int alturaArbol){
		Room aux;
		int k = 0;
		this.dimX = dimX;
		this.dimY = dimY;
		this.dimMapa = dimX*dimY;
		this.salaPuerta = salaPuerta;
		this.alturaArbol = alturaArbol;
		this.turnoActual = 0;
		this.turnoLimite = 100;
		this.vectorRoom = new Room[this.dimMapa];
		firstTimeEx = true;
		for(int i = 0; i<dimX;i++){
			for(int j = 0; j<dimY; j++){
				aux = new Room(k);
				//Creamos todas las salas del mapa
				this.vectorRoom[k] = aux;
				k++;
			}
		}
		wallGraph = new Grafo();
	}
	
	//Añade un nuevo nodo al grafo del mapa
	public void addNode(int valor){
		wallGraph.nuevoNodo(valor);
	}

	//Añade un nuevo arco al grafo del mapa
	public void addArc(int or, int des,int valor){
		wallGraph.nuevoArco(or, des, valor);
	}

	//Devuelve el grafo del mapa
	public Grafo getGraph(){
		return wallGraph;
	}

	//Devuelve el turno limite que se tiene en el juego
	public int getTurnoLimite(){
		return turnoLimite;
	}

	//Retorna la dimension Y del mapa
	public int getDimensionY(){return dimY;}

	//Devuelve una instancia del mapa por defecto
	public static Mapa getInstancia(){
		if(mapAux == null) {
			return mapAux = new Mapa();
		}
		else
			return mapAux;
	}

	//Devuelve una instancia del mapa parametrizada con los valores pasados
	public static Mapa getInstancia(int dimX, int dimY, int salaPuerta, int alturaArbol){
		if(mapAux == null) {
			return mapAux = new Mapa(dimX, dimY, salaPuerta, alturaArbol);
		}
		else
			return mapAux;
	}

	//Genera el arbol de llaves que se debe probar en Kings Landing
	public void Keys(Arbol<llave> Vkeys,llave [] newVector,int pInicio, int pFinal, int profundidad){
		int mitad;
		if(profundidad !=0){
			mitad = pInicio +(pFinal-pInicio)/2;
			Vkeys.insertar(newVector[mitad]);
			Keys(Vkeys,newVector,pInicio,mitad-1,profundidad-1);
			Keys(Vkeys,newVector,mitad+1,pFinal,profundidad-1);
		}

	}

	//Selecciona las 9 salas mas altas del vector para luego introducir las llaves
	public int[] selectRooms(){
		int[] vectorResultado = new int[9];
		int j, n;
		j = 0;
		n = 0;
		int[] vectorOrigen = mostVisitedRooms();
		int valor = vectorOrigen[0];
		while(j<9){		
			for(int i = 0; i<vectorOrigen.length;i++){
				if(vectorOrigen[i]>valor){
					valor = vectorOrigen[i];
					n = i;
				}
			}
			vectorOrigen[n] = -1;
			vectorResultado[j] = n;
			valor = -1;
			n = -1;	
			j++;
		}
		return vectorResultado;
	}
	
	
	//Distribuye las llaves por las salas del mapa que mas visitan los personajes
	public void distribuirLlaves(){
		//Creo el vector donde estaran las llaves ordenadas
		llave [] vectorLlaves = new llave[45];
		int i = 0;
		int j = 0;
		while(j<45){
			//Voy añadiendo llaves
			llave key = new llave(i);
			vectorLlaves[j] = key;
			j++;
			i++;
			//Si es impar entra dos veces en el vector
			if(key.get_id()%2 != 0){
				vectorLlaves[j] = key;
				j++;
			}
		}
		i = 0;
		int k = 0;
		Room r;
		//Una vez tengo el vector de llaves las meto en las salas correspondientes
		int[] idSalasConLlaves = selectRooms();
		while(i<idSalasConLlaves.length){
			r = vectorRoom[idSalasConLlaves[i]];
			j=0;
			while(j<5){
				r.addKey(vectorLlaves[k]);
				j++;
				k++;
			}
			i++;
		}

	}
	
	
	//Encuentra todos los caminos posibles entre el origen y el destino y los devuelve en la variable caminos
	public void allPaths(int origen, int destino, LinkedList<Integer> visitados, Grafo grafoMapa, LinkedList<LinkedList<Integer>> caminos){
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
					allPaths(vecinos.get(i), destino, visitados, grafoMapa, caminos);
			}

		}
		visitados.removeLast();
	}
	
	//Llama al método anterior y con el resultado (caminos posibles) crea un vector con la frecuencia de visitas de cada sala
	public int[] mostVisitedRooms(){
		int[] numberOfVisits = new int[dimX*dimY];
		LinkedList<LinkedList<Integer>> paths = new LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> visitados = new LinkedList<Integer>();
		allPaths(vectorRoom[0].getRoomID(), vectorRoom[salaPuerta].getRoomID(), visitados, wallGraph, paths);
		//El primer bucle recorre cada camino
		for(int i=0; i<paths.size();i++){
			//Este bucle recorre todas las salas y en cada una busca si está en el camino actual
			for(int j=0; j<numberOfVisits.length; j++){
				if(paths.get(i).contains(j)){
					numberOfVisits[j]++;
				}
			}
		}
		
		return numberOfVisits;
	}
	
	

	//Devuelve la esquina suroeste del mapa, usada para la generacion de personajes
	public int esquinaSuroeste(){
		return dimX*dimY-dimX;
	}


	//Retorna el identificador de la sala que contiene la puerta, que es la misma que tiene el trono
	public int getSalaTrono(){
		return salaPuerta;
	}

	//Inserta un personaje p en una sala del mapa
	public void insertarPersonaje(personaje p){
		Room sala;
		//Busco la sala que corresponde al personaje
		sala = vectorRoom[p.getSalaI()];
		//Inserto al personaje
		sala.insertarPJ(p);
		
	}

	//Devuelve la puerta de la sala del trono
	public Door getDoor(){
		return vectorRoom[getSalaTrono()].getRoomDoor();
	}
	

	//Inserta la puerta en la sala cuyo identificador corresponda al parametro del mapa indicado
	public void insertarPuerta(Door p){
		vectorRoom[salaPuerta].setRoomDoor(p);
	}

	//Nos devuelve la dimension total del mapa
	public int getDimension(){return dimMapa;}

	//Devuelve la dimension X del mapa
	public int getDimensionX(){return dimX;}

	//El módulo recorre todas las salas y llama al módulo de la simulación de cada sala
	public void simulacion() throws IOException{
		Room r;
		boolean isThroneDoor = false;
		Cola <personaje> aux = new Cola<personaje>();
		//Obtenemos la puerta del trono
		Door throneDoor = vectorRoom[salaPuerta].getRoomDoor();
		//La simulacion se ejecuta mientras la puerta del trono este cerrada y el turno actual no haya llegado al
		//limite
		while(turnoActual < turnoLimite && !throneDoor.isOpen()){
			System.out.println();
			System.out.println("----TURNO "+ turnoActual+"----");
			for(int i = 0;i<dimX*dimY && !throneDoor.isOpen();i++){
				r = vectorRoom[i];
				if(r.getRoomID() == salaPuerta)
					isThroneDoor = true;			
				else
					isThroneDoor = false;

				//LLamamos a la simulacion de cada sala
				r.simulateRoom(vectorRoom,dimX,dimY,turnoActual,isThroneDoor);
				
			}
			
			if(throneDoor.isOpen()){
				boolean winnerFound = false;
				personaje winner;
				while(!vectorRoom[salaPuerta].vacia() && !winnerFound){
					winner = vectorRoom[salaPuerta].getPJ();
					vectorRoom[salaPuerta].removePJ();
					if(winner.isWinner()){
						winnersRoom.addPJ(winner);
						winnerFound = true;
					}
					else{
						aux.addData(winner);
					}
					
				}
				while(!aux.isEmpty()){
					winner = aux.getFirst();
					vectorRoom[salaPuerta].addPJ(winner);
					aux.removeData();
				}
			}
			pintar();
			turnoActual++;
		}
		
	}

	//Devuelve el vector de salas que contiene el mapa para tratarlas
	public Room[] getVectorRoom(){ return vectorRoom; }

	//Escribe el mapa en el archivo de salida
	public void mostrarMapa() throws IOException{
		BufferedWriter bufferout;
		bufferout = new BufferedWriter(new FileWriter("registro.log",true));
		bufferout.newLine();
		for(int x=0; x<dimX; x++){
			bufferout.write(" _");
		}
		bufferout.newLine();
		int n = 0;
		for(int x=0; x<dimY; x++){
			bufferout.write("|");
			for(int y=0; y<dimX; y++){
				if(vectorRoom[n].vacia()){
					if(!wallGraph.adyacente(n, n+dimX)){
						bufferout.write("_");
					}
					else
						bufferout.write(" ");
				}
				else{
					if(vectorRoom[n].numPjs()>1)
						bufferout.write(""+vectorRoom[n].numPjs()+"");
					else
						bufferout.write(vectorRoom[n].getPJ().get_id());
				}
				if(!wallGraph.adyacente(n, n+1)){
					bufferout.write("|");
				}
				else
					bufferout.write(" ");
				n++;
			}
			bufferout.newLine();
		}
		bufferout.close();
		
	}

	//Escribe en el fichero de salida el estado actual de la partida, incluyendo el mapa, las llaves de cada sala, es estado de los
	//jugadores, etc.
	public void pintar() throws IOException{
		BufferedWriter bufferout;
		if(firstTimeEx){
			bufferout = new BufferedWriter(new FileWriter("registro.log"));
			firstTimeEx = false;
		}
		else
			bufferout = new BufferedWriter(new FileWriter("registro.log",true));
		bufferout.write("(turno:"+turnoActual+")");
		bufferout.newLine();
		bufferout.write("(mapa:"+salaPuerta+")");
		bufferout.newLine();
		bufferout.close();
		vectorRoom[salaPuerta].showDoorInfo();
		mostrarMapa();
		for(int i=0;i<vectorRoom.length;i++){
			if(!vectorRoom[i].vaciaDeLlaves()){
				Room r = vectorRoom[i];
				r.showRoomInfo();	
			}
		}
		for(int i=0;i<vectorRoom.length;i++){
			if(!vectorRoom[i].vacia()){
				Room r = vectorRoom[i];
				r.showPJS();
			}
		}
		Door d = vectorRoom[salaPuerta].getRoomDoor();
		bufferout = new BufferedWriter(new FileWriter("registro.log",true));
		if(d.isOpen()){
			personaje winner = winnersRoom.getPJ();
			bufferout.write("(miembrostrono)");
			bufferout.newLine();
			bufferout.write("(nuevorey:"+winner.getTipo()+":"+winner.get_id()+":"+winnersRoom.getRoomID()+":"+turnoActual+")");
		}
		bufferout.newLine();
		bufferout.write("###########################################");
		bufferout.newLine();
		bufferout.newLine();
		bufferout.close();
		
	}
}


