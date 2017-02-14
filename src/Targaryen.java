import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

public class Targaryen extends personaje{

	private int direccion;

	//Constructor por defecto de los Targaryen
	public Targaryen(){
		this.nombre = "Targaryen";
		this.id = " ";
		direccion = 0;
		this.tipo = "targaryen";
	}

	//Constructor parametrizado que recibe nombre, marca, turno y sala de salida del personaje
	public Targaryen(String nombre,String marca, int turno, int sala){
		this.nombre=nombre;
		this.id=marca;
		this.turno = turno;
		this.salaI = sala;
		direccion = 0;
		this.tipo = "targaryen";
	}

	//accion del personaje Targaryen en el juego(en este caso, al igual que los stark, recogen llaves)
	public void action(Room r){
		if (!r.vaciaDeLlaves()) {
			llave auxKey = r.getKey();
			llaves.addData(auxKey);
			System.out.println("Personaje " + this.get_nombre() + " recoge llave "+ auxKey.get_id());
		}
	}

	//Escribe el el fichero de salida la informacion del personaje targaryen
	public void showInfo() throws IOException {
		BufferedWriter bufferout;
		bufferout = new BufferedWriter(new FileWriter("registro.log",true));
		Stack<llave> aux = new Stack<llave>();

		bufferout.write("("+tipo+":"+id+":"+salaI+":"+turno+":");

		while(!llaves.isEmpty()){
			llave k = llaves.getTop();
			bufferout.write(k.get_id()+" ");
			aux.addData(k);
			llaves.removeData();
		}
		bufferout.write(")");
		bufferout.newLine();
		while(!aux.isEmpty()){
			llave k = aux.getTop();
			llaves.addData(k);
			aux.removeData();
		}

		bufferout.close();
	}
	
	//Este modulo prueba una llave en la puerta de la sala actual.
	public void door_action(Door d){
		if(!llaves.isEmpty()){
			llave auxKey = llaves.getTop();
			llaves.removeData();
			d.open(auxKey);
			System.out.println(this.nombre + " probando llave "+auxKey.get_id()+" en puerta");
		}
	}

	public void calculateMovement(){
		Mapa map = Mapa.getInstancia();
		routesPj(0,map.getDimension()-1,map.getGraph(),map.getDimensionX());
	
	}
	
	//Este metodo calcula la ruta de los Targaryen siguiendo el algoritmo de la mano derecha
	public void routesPj(int origen, int destino, Grafo grafoMapa,  int dimensionX){
		LinkedList<Integer> visitados = new LinkedList<>();
		//Este vector contiene los movimientos que se hacen para cada direccion siendo 0:sur, 1:oeste, 2:norte, 3:este
		int[] posDirecciones = new int[4];
		posDirecciones[0] = dimensionX;
		posDirecciones[1] = -1;
		posDirecciones[2] = -dimensionX;
		posDirecciones[3] = 1;
		//Mientras no se haya llegado al destino se ejecuta
		while(origen != destino){
			int sigDir = dirSiguiente(posDirecciones,direccion);
			//Primero intenta girar hacia la derecha
			if(grafoMapa.adyacente(origen, origen + posDirecciones[sigDir])){
					direccion=sigDir;
					visitados.addLast(origen);
					origen = origen + posDirecciones[direccion];
			}
			//Si habia una pared a la derecha, se mueve hacia adelante
			else{
				if(grafoMapa.adyacente(origen, origen+posDirecciones[direccion])){
					visitados.addLast(origen);
					origen = origen + posDirecciones[direccion];
				}
				//En caso de no poder moverse adelante, cambia su direccion
				else{
					direccion=dirAnterior(posDirecciones,direccion);
				}
			}
								
		}
		visitados.addLast(destino);
		transformRoute(visitados,dimensionX);
		
	}
	
	
	//Estos modulos se utilizan como auxiliar en el modulo anterior. Devuelven la direccion siguiente y anterior del vector
	//teniendo en cuenta que la ultima posicion enlaza con la primera
	public int dirSiguiente(int[] vector, int pos){
		int res = 0;
		if(vector[vector.length-1] != vector[pos]){
			res = pos+1;
		}
		return res;
	}
	public int dirAnterior(int[] vector, int pos){
		int res = vector.length-1;
		if(vector[0] != vector[pos]){
			res = pos-1;
		}
		return res;
	}
	

}
