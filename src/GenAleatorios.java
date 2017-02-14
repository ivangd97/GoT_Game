import java.util.Random;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alc�ntara Arnela e Iv�n Gonz�lez Dom�nguez
 *N�mero de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2� A
 */

/**
* Generador de n�meros aleatorios - proyecto12_13
* Implementacion de la clase que permite generar n�meros aleatorios
* @version 1.0
* @author
* <b> Profesores DP </b><br>
* Asignatura Desarrollo de Programas<br/>
* Curso 16/17
*/
public class GenAleatorios {
	
	/** Instancia de la clase Random que permite generar los n�meros aleatorios. */
	private static Random r;
	
	/** Constante con la semilla para inicializar la generaci�n de n�meros aleatorios. ��No cambiar!! */
	private static final int SEMILLA=1987;		 
	
	/** Contador de n�meros aleatorios generados */
	private static int numGenerados;			
	
	/** Instancia de la propia clase (s�lo habr� una en el sistema). Ver PATR�N DE DISE�O SINGLETON */
	private static GenAleatorios instancia=null;	
	
	/**
	* Constructor por defecto. Inicializa un objeto de tipo GenAleatorio
	* @return Devuelve un objeto de tipo GenAleatorio inicializado
	*/
	private GenAleatorios(){
    	// Inicializaci�n de la semilla para generar los n�meros aleatorios
    	r = new Random(GenAleatorios.SEMILLA);
    	// Inicializaci�n del atributo que cuenta cu�ntos n�meros aleatorios se han generado
    	numGenerados = 0;
    	}
	
	/**
	 * Metodo generarNumero. Genera un n�mero aleatorio entre 0 y (limiteRango-1)
	 * @param limiteRango El l�mite del rango en el que generar los aleatorios
	 * @return Devuelve el n�mero aleatorio generado
	 */
	public static int generarNumero(int limiteRango){
		if (instancia == null) 
			instancia = new GenAleatorios();
		numGenerados++;
		return r.nextInt(limiteRango);
	}
	
	/**
	 * Devuelve el n�mero de aleatorios generados
	 * @return N�mero de aleatorios generados
	 */
	public static int getNumGenerados(){
		return numGenerados;
	}
	
}
