import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

public class Stark extends personaje{

	//Constructor por defecto del Stark
	public Stark(){
		this.tipo = "stark";
		this.nombre = "Stark";
		this.id = " ";
	}

	//Constructor parametrizado que recibe el nombre, la marca, el turno y la sala de nuestro querido Stark
	public Stark(String nombre,String marca, int turno, int sala){
		this.tipo = "stark";
		this.nombre=nombre;
		this.id=marca;
		this.turno = turno;
		this.salaI = sala;
	}

	//Accion del personaje Stark(en este caso recoge llaves de las salas)
	public void action(Room r){
		if (!r.vaciaDeLlaves()) {
			llave auxKey = r.getKey();
			llaves.addData(auxKey);
			System.out.println("Personaje " + this.get_nombre() + " recoge llave "+ auxKey.get_id());
		}
	}

	//Escribe en el fichero de salida la informacion del personaje stark
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

	//Con una instancia de mapa calcula la ruta que debe seguir el personaje stark en la simulacion
	public void calculateMovement(){
		Mapa map = Mapa.getInstancia();
		LinkedList<Integer> aux = new LinkedList<>();
		LinkedList<LinkedList<Integer>> paths = new LinkedList<LinkedList<Integer>>();
		bestRoutePj(0,map.getSalaTrono(),aux,map.getGraph(),paths,map.getDimensionX());
	}
	
	//De entre todos los caminos, este elige el que siempre selecciona la sala con identificador mas pequeño en las bifurcaciones
	public void bestRoutePj(int origen, int destino, LinkedList<Integer> visitados, Grafo grafoMapa, LinkedList<LinkedList<Integer>> caminos, int dimX){
		//Este modulo nos devuelve todos los caminos
		allRoutesPj(origen,destino,visitados, grafoMapa, caminos);
		int valorMenor = 0;
		int j = 0;
		int caminoElegido = 0;
		int p;
		boolean[] caminoCorrecto = new boolean[caminos.size()];
		//Usamos este vector para ir descartando caminos. Tiene el tamaño igual al numero de caminos y dentro todos sus valores
		//son true inicialmente
		for(int m=0;m<caminoCorrecto.length;m++){
			caminoCorrecto[m] = true;
		}
		
		boolean encontrado = false;
		//En este bucle buscamos el camino, el while va moviendose por cada sala en orden
		while(!encontrado){
			//Este será el valor menor hasta el momento, es decir, el camino que ha elegido una sala con identificador mas pequeño
			//(dicho camino es caminoElegido, valorMenor es el valor de la sala concreta de ese camino que se está comparando)
			valorMenor = caminos.get(caminoElegido).get(j);
			//Este for se mueve por cada camino, mirando en cada uno la sala que se seleccionó en el while con la variable j
			for(int i=0; i<caminos.size();i++){
				//Miramos si el camino que vamos a probar no ha sido descartado todavia
				if(caminoCorrecto[i] == true){
					//Si no ha sido descartado comprobamos si es mayor al camino que actualmente creemos que es el mejor
					//Si lo es se descarta
					if(valorMenor<caminos.get(i).get(j)){
						caminoCorrecto[i] = false;
					}
					else{
						//En caso de que el camino sea mejor que el mejor hasta el momento, cambiamos el valor de caminoElegido
						if(valorMenor>caminos.get(i).get(j)){
							caminoCorrecto[caminoElegido] = false;
							caminoElegido = i;
						}
					}
				}
			}
			p = 0;
			//Al final de cada comprobación de sala miramos si queda mas de un camino a true en el vector
			//Si solo queda uno significa que hemos encontrado el camino correcto y acabamos la busqueda
			for(int m=0;m<caminoCorrecto.length;m++){
				if(caminoCorrecto[m] == true){
					p++;
				}			
			}
			if(p == 1){
				encontrado = true;
			}
			j++;
		}
		transformRoute(caminos.get(caminoElegido),dimX);
	}

}
