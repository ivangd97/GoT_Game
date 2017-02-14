import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

public class Room implements Comparable<Room> {

	//Identificador de la sala
	private int roomID;


	private int marca;

	//Lista de llaves de la sala
	private LinkedList<llave> keyList;

	//Cola de personajes contenidos en la sala
	private Cola <personaje> pjInRoom;

	//Si es la puerta del trono contendra la puerta
	private Door roomDoor;

	//Constructor por defecto de la sala
	public Room(){
		roomID   = 0;
		marca = 0;
		keyList = new LinkedList<llave>();
		pjInRoom = new Cola <personaje> ();
	}

	//Constructor parametrizado de la sala, al que se le pasa por parametro el identificador de la sala y una puerta
	//(para el caso de la sala Kings Landing)
	public Room(int newID, Door newDoor){
		this.roomID = newID;
		keyList = new LinkedList <llave>();
		pjInRoom = new Cola <personaje> ();
		this.roomDoor = newDoor;
		marca = 0;
	}

	//Constructor parametrizado de la sala al que le entra el identificador de la puerta por parÃ¡metro
	public Room(int newID){
		this.roomID = newID;
		marca = newID;
		keyList = new LinkedList <llave> ();
		pjInRoom = new Cola <personaje> ();
	}



	//Inserta un personaje pasado por parametro a la estructura de la sala
	public void insertarPJ(personaje p){
		pjInRoom.addData(p);
	}
	
	//Devuelve la marca de la sala
	public int getMarca(){
		return marca;
	}
	
	//Sobreescribe la marca de la sala
	public void setMarca(int marca){
		this.marca = marca;
	}

	//Retorna el identificador de la sala
	public int getRoomID(){ return roomID;}

	//Devuelve la puerta de la sala en el caso de que esta tenga
	public Door getRoomDoor(){
		return roomDoor;
	}

	//Proporciona el primer personaje de la cola
	public personaje getPJ(){return pjInRoom.getFirst();};

	//Asigna una puerta pasada por parametro a la sala
	public void setRoomDoor(Door p){
		this.roomDoor = p;
	}

	//Nos dice si la sala se encuentra vacia
	public boolean vacia(){return pjInRoom.isEmpty();}

	//Añade una llave a la lista de llaves de la sala
	public void addKey(llave key){
            keyList.add(key);
	}
	
	//Añade una llave en el lugar que le corresponda (estando estas ordenadas de menor a mayor)
	public void addKeyInOrder(llave key){
		llave auxKey;
		boolean encontrado = false;
        if(!keyList.isEmpty()){
        	if(key.get_id() > keyList.getFirst().get_id()){
	        	LinkedList<llave> aux = new LinkedList<llave>();
	        	while(!keyList.isEmpty() && !encontrado){
	        		auxKey=keyList.getFirst();
	        		keyList.remove();
	        		if(key.get_id() < auxKey.get_id()){
	        			aux.addLast(key);
	        			aux.addLast(auxKey);
	        			encontrado = true;
	        		}
	        		else{
	        			aux.addLast(auxKey);
	        		}
	        	}
	        	while(!aux.isEmpty()){
	        		auxKey = aux.getLast();
	        		aux.removeLast();
	        		keyList.addFirst(auxKey);
	        	}
	        	if(!encontrado){
	        		keyList.addLast(key);
	        	}
        	}
	        else
	        	keyList.addFirst(key);
        	
        }
        else
        	keyList.addFirst(key);
}
	
	//Cuenta el numero de personajes en la sala
	public int numPjs(){
		int personajes = 0;
		personaje pj;
		Cola<personaje> aux = new Cola<>();
		while(!pjInRoom.isEmpty()){
			pj = pjInRoom.getFirst();
			aux.addData(pj);
			pjInRoom.removeData();
			personajes++;
		}
		while(!aux.isEmpty()){
			pj = aux.getFirst();
			pjInRoom.addData(pj);
			aux.removeData();
		}
		return personajes;
	}

	//Muestra la informacion de la sala
	public void showRoomInfo() throws IOException{
		llave k;
		BufferedWriter bufferout;
		bufferout = new BufferedWriter(new FileWriter("registro.log",true));
		LinkedList<llave> auxKeyList = new LinkedList<llave>();
		//Mostramos el ID de la sala y de cada una de sus llaves
		bufferout.write("(sala:"+roomID+":");
		while(!keyList.isEmpty()){
			k = keyList.remove();
			auxKeyList.add(k);
			bufferout.write(" "+k.get_id());
		}
		bufferout.write(")");
		bufferout.newLine();
		bufferout.close();
		while(!auxKeyList.isEmpty()){
			k = auxKeyList.remove();
			keyList.add(k);
		}
	}

	//Muestra los personajes que contiene en su cola
	public void showPJS() throws IOException{
		personaje pj;
		Cola<personaje> aux = new Cola<>();

		//Mostramos el ID de la sala y de cada una de sus llaves

		while(!pjInRoom.isEmpty()){
			pj = pjInRoom.getFirst();
			pj.showInfo();
			aux.addData(pj);
			pjInRoom.removeData();
		}
		while(!aux.isEmpty()){
			pj = aux.getFirst();
			pjInRoom.addData(pj);
			aux.removeData();
		}

	}



	//Muestra la informacion de la puerta en caso de que la sala posea una
	public void showDoorInfo() throws IOException{
		roomDoor.showInfo();
	}

	//Nos dice si la sala se encuentra vacia de llaves
	public boolean vaciaDeLlaves(){
		return keyList.isEmpty();
	}

	//aÃ±ade un personaje que se encuentra en la sala a su estructura de datos
	public void addPJ(personaje PJ){
		pjInRoom.addData(PJ);
	}

	//Elimina un personaje de la sala
	public void removePJ(){
		pjInRoom.removeData();
	}

	//Elimina la llave y la devuelve
	public llave getKey(){
		return keyList.remove();
	}

	//Este método ejecuta las acciones de cada personaje dentro de la sala en un turno
	public void simulateRoom(Room[] vectorRoom,int dimX, int dimY, int turnoActual, boolean isThroneDoor){
		personaje pj;
		Cola <personaje> aux = new Cola<personaje>();
		boolean puertaAbierta = false;
		//Mientras la sala tenga personajes en su estructura y (en caso de puerta del trono)
		//la puerta este cerrada se ejecuta el bucle
		while(!this.vacia() && !puertaAbierta){
			//El bucle procesa a cada personaje
			pj = this.getPJ();		
			//Comprobamos si el turno de movimiento del personaje es el adecuado.
			//Esto sirve además para que un personaje no se mueva varias veces en un turno
			//cuando este bucle pase por la sala a la que se ha movido
			if(pj.get_Turno() == turnoActual){
				//Si es la puerta del trono se realiza la acción propia de cada personaje
				if(isThroneDoor){
					pj.door_action(roomDoor);
					puertaAbierta = roomDoor.isOpen();
					if(puertaAbierta)
						pj.setWinner();
				}
				//El movimiento depende del resultado de la acción de la puerta
				pj.movement(dimX,dimX*dimY);		
				pj.turno++;
				//En el caso de que el personaje no se haya movido (por moverse hacia una pared) lo metemos en una estructura
				//auxiliar para seguir procesando al resto
				if(this.getRoomID() == pj.getSalaI()){
					aux.addData(pj);
					pj.action(vectorRoom[pj.getSalaI()]);
				}
				
				else {
					pj.action(vectorRoom[pj.getSalaI()]);
					vectorRoom[pj.getSalaI()].addPJ(pj);					
				}
				//Borramos al personaje de la sala si se ha movido a otra
				this.removePJ();
			}
			else{
				this.removePJ();
				aux.addData(pj);
			}
		}
		//Volcamos la estructura auxiliar que habiamos usado para almacenar personajes iban a permanecer en la sala de nuevo
		//sobre la sala
		while(!aux.isEmpty()){
			pj = aux.getFirst();
			this.addPJ(pj);
			aux.removeData();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof llave))
			return false;
		Room rAux = (Room) obj;
		return (this.roomID == rAux.getRoomID());

	}


	@Override
	public int compareTo(Room r2) {
		if (this.getRoomID() == r2.getRoomID())
			return 0;
		else {
			if (this.getRoomID() < r2.getRoomID()) {
				return -1;
			} else {
				return 1;
			}
		}
	}
	
}
