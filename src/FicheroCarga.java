/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */


/**
 * Clase creada para ser usada en la utilidad cargador
 * estudiada previamente en sesiï¿½n prÃ¡ctica "Excepciones"
 * 
 * @version 1.0 -  02/11/2011 
 * @author Profesores DP
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroCarga {
	/**  
	atributo de la clase que indica el numero mÃ¡ximo de campos que se pueden leer
	*/
	public static final int MAXCAMPOS  = 10;

	/**  
	buffer para almacenar el flujo de entrada
	*/
	private static BufferedReader bufferIn;
	/**
	 * Metodo para procesar el fichero. Sin excepciones
	 * @return codigoError con el error que se ha producido
	 * lanza Exception. Puede lanzar una excepciï¿½n en la apertura del buffer de lectura
	 */
	 public static void procesarFichero(String nombreFichero, Cargador cargador) throws  FileNotFoundException, IOException {
		 //**String vCampos[]=new String[MAXCAMPOS];
		 List<String> vCampos = new ArrayList<String>();
	     String S=new String();
	     int numCampos=0;

	     System.out.println( "Procensando el fichero..." );
	     bufferIn = new BufferedReader(new FileReader(nombreFichero));//creaciï¿½n del filtro asociado al flujo de datos

         while((S=bufferIn.readLine())!= null) {
	     	 System.out.println( "S: "+S );
  	 		 if (!S.startsWith("--"))  {
  	 			 vCampos.clear();
  	 			 numCampos = trocearLinea(S, vCampos);
  	 			 cargador.crear(vCampos.get(0), numCampos, vCampos);
	 		}
	     }
	     bufferIn.close();	     //cerramos el filtro
	 }

	 /**
	  * Metodo para trocear cada lï¿½nea y separarla por campos
	  * @param S cadena con la lï¿½nea completa leï¿½da
	  * parametro vCampos. Array de String que contiene en cada posiciï¿½n un campo distinto
	  * @return numCampos. Nï¿½mero campos encontrados
	 */
	 private static int trocearLinea(String S,List<String> vCampos) {
		 boolean eol = false;
		 int pos=0,posini=0, numCampos=0;

   	     while (!eol)
   	     {
	    			pos = S.indexOf("#");
	     	    	if(pos!=-1) {
	     	    		vCampos.add(new String(S.substring(posini,pos)));
	     	    		S=S.substring(pos+1, S.length());
	     	    		numCampos++;
	     	    	}
	     	    	else eol = true;
		  }
		  return numCampos;
	 }

}
