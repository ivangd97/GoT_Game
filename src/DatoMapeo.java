/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */


/**
 * Clase creada para ser usada en la utilidad cargador
 * se utiliza para realizar el mapeo de los distintos tipos de datos que se utilizarï¿½n en la aplicaciï¿½n
 * 
 * @version 4.0 -  15/10/2014 
 * @author Profesores DP
 */
public class DatoMapeo {
  private String nombre;
  private int numCampos;
 /**
  * constructor por defecto
  */
  DatoMapeo() {
	  nombre = new String();
	  nombre = "..";
	  numCampos = 0;
  }

  /**
   *  constructor parametrizado 
   *  @param _nombre nombre del tipo de datos
   *  @param _numCampos nÃºmero de campos-atributos que contendrÃ¡
   */
  DatoMapeo(String _nombre, int _numCampos) {
	  nombre = new String(_nombre);
	  numCampos = _numCampos;
  }
 
  /**
   * devuelve el nombre del tipo
   * @return nombre
   */
  public String getNombre() {
	  return nombre;
  }
  
  /**
   * devuelve el nÃºmero de campos del tipo
   * @return numCampos
   */
  public int getCampos() {
	  return numCampos;
  }
  
  
}
