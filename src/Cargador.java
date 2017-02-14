import java.util.List;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alc�ntara Arnela e Iv�n Gonz�lez Dom�nguez
 *N�mero de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2� A
 */

/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Estacion, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 5.0 -  27/10/2016 
 * @author Profesores DP
 */
public class Cargador {
	/**  
	número de elementos distintos que tendrá la simulación - Mapa, Stark, Lannister, Baratheon, Targaryen
	*/
	static final int NUMELTOSCONF  = 5;
	/**  
	atributo para almacenar el mapeo de los distintos elementos
	*/
	static private DatoMapeo [] mapeo;
	
	/**
	 *  constructor parametrizado 
	 *  e referencia a la instancia del patrón Singleton
	 */
	Cargador()  {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		mapeo[0]= new DatoMapeo("MAPA", 5);
		mapeo[1]= new DatoMapeo("STARK", 4);
		mapeo[2]= new DatoMapeo("TARGARYEN", 4);
		mapeo[3]= new DatoMapeo("LANNISTER", 4);
		mapeo[4]= new DatoMapeo("CAMINANTE", 4);
		
	}
	
	/**
	 *  busca en mapeo el elemento leído del fichero inicio.txt y devuelve la posición en la que está 
	 *  @param elto elemento a buscar en el array
	 *  @return res posición en mapeo de dicho elemento
	 */
	private int queElemento(String elto)  {
	    int res=-1;
	    boolean enc=false;

	    for (int i=0; (i < NUMELTOSCONF && !enc); i++)  {
	        if (mapeo[i].getNombre().equals(elto))      {
	            res=i;
	            enc=true;
	        }
	    }
	    return res;
	}
	
	/**
	 *  método que crea las distintas instancias de la simulación 
	 *  @param elto nombre de la instancia que se pretende crear
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo de la instancia
	 */
	public void crear(String elto, int numCampos, List<String> vCampos)	{
	    //Si existe elemento y el n�mero de campos es correcto, procesarlo... si no, error
	    int numElto = queElemento(elto);

	    //Comprobaci�n de datos básicos correctos
	    if ((numElto!=-1) && (mapeo[numElto].getCampos() == numCampos))   {
	        //procesar
	        switch(numElto){
	        case 0:	   
	            crearMapa(numCampos,vCampos);
	            break;
	        case 1:
	            crearStark(numCampos,vCampos);
	            break;
	        case 2:
	            crearTargaryen(numCampos,vCampos);
	            break;
	        case 3:
	            crearLannister(numCampos,vCampos);
	            break;
	        case 4:
	            crearCaminante(numCampos,vCampos);
	            break;
	     	}
	    }
	    else
	        System.out.println("ERROR Cargador::crear: Datos de configuración incorrectos... " + elto + "," + numCampos+"\n");
	}

	/**
	 *  método que crea una instancia de la clase Planta
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearMapa(int numCampos, List<String> vCampos)
	{
	    System.out.println("Creado Mapa: " + vCampos.get(1) + "\n");
	    //inicializar mapa
		Integer dimY = Integer.parseInt(vCampos.get(1));
		Integer dimX = Integer.parseInt(vCampos.get(2));
		Integer salaPuerta = Integer.parseInt(vCampos.get(3));
		Integer alturaArbol = Integer.parseInt(vCampos.get(4));
		Mapa gameMap = Mapa.getInstancia(dimY,dimX,salaPuerta,alturaArbol);

		//Creaci�n y configuraci�n de la puerta
		Door gameDoor = new Door(gameMap.alturaArbol);
		int numLlaves = 15;
		int [] listaIdLlaves = new int [numLlaves];
		int j=1;
		for (int i = 0; i < numLlaves; i++) {
			listaIdLlaves[i] = j;
			j=j+2;
		}

		llave [] combinacion = new llave [numLlaves];
		for (int i = 0; i < combinacion.length; i++) {
			combinacion [i] = new llave(listaIdLlaves[i]);
		}
		gameMap.Keys(gameDoor.keyTree, combinacion, 0, 15, 4);
		gameDoor.saveCombination();

		//Insertamos la puerta
		gameMap.insertarPuerta(gameDoor);
		
		//Creamos la clase que configurara los muros del mapa
		wall_Gestion wg = new wall_Gestion();
		wg.tirarMuros();

	}

	/**
	 *  método que crea una instancia de la clase Stark
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearStark(int numCampos, List<String> vCampos)
	{
	    System.out.println("Creado Stark: " + vCampos.get(1) + "\n");
	    String nombre = vCampos.get(1);
		String marca = vCampos.get(2);
		Integer turno = Integer.parseInt(vCampos.get(3));

		Stark st = new Stark(nombre, marca, turno, 0);
		Mapa gameMap = Mapa.getInstancia();
		st.calculateMovement();
		/*ArrayList<Dir> direccionesS = new ArrayList<Dir>(Arrays.asList(Dir.E, Dir.E, Dir.S, Dir.S, Dir.E, Dir.E, Dir.S,
				Dir.S, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E, Dir.E, Dir.E));
		st.addMovements(direccionesS);*/
		gameMap.insertarPersonaje(st);
	}

	/**
	 *  método que crea una instancia de la clase Targaryen
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearTargaryen(int numCampos, List<String> vCampos)
	{
	    System.out.println("Creado Targaryen: " + vCampos.get(1) + "\n");
	    String nombre = vCampos.get(1);
		String marca = vCampos.get(2);
		Integer turno = Integer.parseInt(vCampos.get(3));

		Targaryen tg = new Targaryen(nombre, marca, turno, 0);
		/*
		ArrayList<Dir> direccionesD = new ArrayList<Dir>(Arrays.asList(Dir.E, Dir.E, Dir.S, Dir.S, Dir.E, Dir.E, Dir.S,
				Dir.S, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E, Dir.E, Dir.N, Dir.O, Dir.E, Dir.S, Dir.E));
		tg.addMovements(direccionesD);
		*/
		Mapa gameMap = Mapa.getInstancia();
		tg.calculateMovement();
		gameMap.insertarPersonaje(tg);
	}	

	/**
	 *  método que crea una instancia de la clase Lannister
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearLannister(int numCampos, List<String> vCampos)
	{
	    System.out.println("Creado Lannister: " + vCampos.get(1) + "\n");
		String nombre = vCampos.get(1);
		String marca = vCampos.get(2);
		Integer turno = Integer.parseInt(vCampos.get(3));
		Mapa gameMap = Mapa.getInstancia();
		Lannister ln = new Lannister(nombre, marca, turno, gameMap.getSalaTrono());
		ln.calculateMovement();
		/*ArrayList<Dir> direccionesT = new ArrayList<Dir>(Arrays.asList(Dir.O, Dir.O, Dir.O, Dir.N, Dir.O, Dir.S, Dir.O,
				Dir.N, Dir.N, Dir.O, Dir.O, Dir.N, Dir.N, Dir.O, Dir.O));
		ln.addMovements(direccionesT);*/

		Door gameDoor = gameMap.getDoor();
		ln.transformKeys(gameDoor.keyTree);

		gameMap.insertarPersonaje(ln);
	}	

	/**
	 *  método que crea una instancia de la clase White Walker
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearCaminante(int numCampos, List<String> vCampos)
	{
	    System.out.println("Creado Caminante Blanco: " + vCampos.get(1) + "\n");
		String nombre = vCampos.get(1);
		String marca = vCampos.get(2);
		Integer turno = Integer.parseInt(vCampos.get(3));
		Mapa gameMap = Mapa.getInstancia();
		WhiteWalkers ww = new WhiteWalkers(nombre, marca, turno, gameMap.esquinaSuroeste());
		ww.calculateMovement();
		/*ArrayList<Dir> direccionesW = new ArrayList<Dir>(Arrays.asList(Dir.N, Dir.N, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E,
				Dir.E, Dir.S, Dir.S, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E, Dir.E, Dir.N, Dir.N, Dir.E));
		ww.addMovements(direccionesW);*/

		gameMap.insertarPersonaje(ww);
	}

}
