import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

public class Lannister extends personaje {

	//Contructor por defecto
	public Lannister(){
		this.nombre = "Lannister";
		this.id = " ";
		this.tipo = "lannister";
	}

	//Constructor parametrizado que recibe el nombre, la marca, el turno y la sala del personaje
	public Lannister(String nombre,String marca, int turno, int sala){
		this.nombre=nombre;
		this.id=marca;
		this.turno = turno;
		this.salaI = sala;
		this.tipo = "lannister";
	}

	//Transforma el arbol de llaves para que el personaje lannister las tenga guardadas en su estructura de datos
	//al inicio del programa
	public void transformKeys(Arbol<llave> abb){
		llave auxKey;
		Arbol <llave> aux = null;
		//Comprobamos si el arbol esta vacio
		if (!abb.vacio()) {
			if ((aux=abb.getHijoIzq())!=null) {
				transformKeys(aux);
			}
			//obtenemos la raiz y la añadimos a la estructura del personaje lannister
			auxKey = abb.getRaiz();
			llaves.addData(auxKey);

			if ((aux=abb.getHijoDer())!=null){
				transformKeys(aux);
			}
		}
	}

	//Metodo que define la accion del personaje, en este caso la del Lannister, soltando una llave en la sala
	public void action(Room r){
		//Comprobamos que el personaje Lannister sigue teniendo llaves en su poder y la perdemos
		if(!llaves.isEmpty()){
			if(r.getRoomID()%2 == 0){
				llave auxKey;
				auxKey = llaves.getTop();
				llaves.removeData();
				r.addKeyInOrder(auxKey);
				System.out.println(this.get_nombre() + " pierde llave " + auxKey.get_id());
			}
		}
	}

	//Metodo que escribe en el fichero de salida la informacion del personaje Lannister
	public void showInfo() throws IOException{
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
	
	//Cierra la puerta, con lo que reinicia su combinación
	public void door_action(Door d){
		d.closeDoor();
		System.out.println(this.nombre + " cerrando puerta.");
	}

	//Modulo que calcula las direcciones que seguira el personaje
	public void calculateMovement(){
		Mapa map = Mapa.getInstancia();
		while(direcciones.size()<map.getTurnoLimite()){
			LinkedList<Integer> aux = new LinkedList<>();	
			LinkedList<LinkedList<Integer>> paths = new LinkedList<LinkedList<Integer>>();
			bestRoutePj(map.getDimension()-1,map.getDimensionX()-1,aux,map.getGraph(),paths,map.getDimensionX());
			paths = new LinkedList<LinkedList<Integer>>();
			bestRoutePj(map.getDimensionX()-1,0,aux,map.getGraph(),paths,map.getDimensionX());
			paths = new LinkedList<LinkedList<Integer>>();
			bestRoutePj(0,map.getDimension()-map.getDimensionX(),aux,map.getGraph(),paths,map.getDimensionX());
			paths = new LinkedList<LinkedList<Integer>>();
			bestRoutePj(map.getDimension()-map.getDimensionX(),map.getDimension()-1,aux,map.getGraph(),paths,map.getDimensionX());
			
		}
	}
	
	//De entre todos los caminos, este elige el mas corto
	public void bestRoutePj(int origen, int destino, LinkedList<Integer> visitados, Grafo grafoMapa, LinkedList<LinkedList<Integer>> caminos, int dimX){
		allRoutesPj(origen,destino,visitados, grafoMapa, caminos);
		int n = 0;
		int contador;
		int cuentaMenor = 9999;
		for(int i=0; i<caminos.size();i++){
			contador = 0;
			for(int j=0; j<caminos.get(i).size(); j++){
				contador++;
			}
			if(contador<cuentaMenor){
				cuentaMenor = contador;
				n=i;
			}
		}
		transformRoute(caminos.get(n),dimX);
	}
}
	


