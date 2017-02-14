
/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 4.0 -  15/10/2014 
 * @author Profesores DP
 */

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alc�ntara Arnela e Iv�n Gonz�lez Dom�nguez
 *N�mero de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2� A
 */

import java.io.FileNotFoundException;
import java.io.IOException;

public class ClasePrincipal {
	public static void main(String[] args) throws IOException {

		//Creamos un cargador
		Cargador cargador = new Cargador();
		try {

			//Método que procesa línea a línea el fichero de entrada inicio.txt
			
		     FicheroCarga.procesarFichero("inicio.txt", cargador);
		}
		catch (FileNotFoundException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
		catch (IOException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
		//Utilizamos una instancia del mapa para realizar la distribución de llaves y la simulación del juego
        Mapa gameMap = Mapa.getInstancia();
        gameMap.distribuirLlaves();
        gameMap.simulacion();
	}

}
