import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

public class WhiteWalkers extends personaje {

	//Este tipo de personajes tienen una estructura auxiliar para almacenar los personajes que matan
	private Cola <personaje> deadPjs;

	//Constructor por defecto del caminante blanco
	public WhiteWalkers(){
		this.nombre = "WhiteWalkers";
		this.id = " ";
		deadPjs = new Cola <personaje>();
		this.tipo = "caminante";
	}

	//Contructor parametrizado del caminante que recibe su nombre, su marca, su turno y la sala de salida de este
	public WhiteWalkers(String nombre,String marca, int turno, int sala){
		this.nombre=nombre;
		this.id=marca;
		deadPjs = new Cola <personaje>();
		this.turno = turno;
		this.salaI = sala;
		this.tipo = "caminante";
	}

	//Realiza la accion del caminante blanco, en su caso elimina un personaje de la sala a la que se mueve
	//si esta contiene alguno y lo almacena en su estructura de "muertos"
	public void action(Room r){
		if(!r.vacia()) {
			personaje pjaux = r.getPJ();
			r.removePJ();
			deadPjs.addData(pjaux);
			System.out.println("Caminante blanco elimina a jugador " + pjaux.get_nombre());
		}

		
	}

	//Escribe en el fichero de salida la informacion del personaje caminante blanco
	public void showInfo() throws IOException {
		BufferedWriter bufferout;
		bufferout = new BufferedWriter(new FileWriter("registro.log",true));
		Cola<personaje> aux = new Cola<>();

		bufferout.write("("+tipo+":"+id+":"+salaI+":"+turno+":");

		while(!deadPjs.isEmpty()){
			personaje pj = deadPjs.getFirst();
			bufferout.write(pj.get_id()+" ");
			aux.addData(pj);
			deadPjs.removeData();
		}
		bufferout.write(")");
		bufferout.newLine();
		while(!aux.isEmpty()){
			personaje pj = aux.getFirst();
			deadPjs.addData(pj);
			aux.removeData();
		}

		bufferout.close();
	}
	
	//Cierra la puerta, con lo que reinicia su combinación
	public void door_action(Door d){
		d.closeDoor();
		System.out.println(this.nombre + " cerrando puerta.");
	}

	//Calcula la ruta de movimientos que seguira el caminante blanco durante la simulacion
	public void calculateMovement(){
		Mapa map = Mapa.getInstancia();
		LinkedList<Integer> aux = new LinkedList<>();
		LinkedList<LinkedList<Integer>> paths = new LinkedList<LinkedList<Integer>>();
		while(direcciones.size()<map.getTurnoLimite()){
			aux = new LinkedList<>();
			paths = new LinkedList<LinkedList<Integer>>();			
			bestRoutePj(map.getDimension() - map.getDimensionX(),0,aux,map.getGraph(),paths,map.getDimensionX());
			paths = new LinkedList<LinkedList<Integer>>();
			bestRoutePj(0,map.getDimensionX()-1,aux,map.getGraph(),paths,map.getDimensionX());
			paths = new LinkedList<LinkedList<Integer>>();
			bestRoutePj(map.getDimensionX()-1,map.getDimension()-1,aux,map.getGraph(),paths,map.getDimensionX());
			paths = new LinkedList<LinkedList<Integer>>();
			bestRoutePj(map.getDimension()-1,map.getDimension()-map.getDimensionX(),aux,map.getGraph(),paths,map.getDimensionX());
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
