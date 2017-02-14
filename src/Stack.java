/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

/**
 * Implementation of the method for the Stack class.
*
* @version 2.0
* @author
* <b> la chupipandi </b><br>
* Program Development<br/>
* 16/17 Course
 */
public class Stack <type extends Comparable <type>>{
	
	/** Reference to the top of the stack*/
	private Node top;
	
    private class Node {
    	/** Data stored in each node */
        private type data;
    	/** Reference to the next node */
        private Node next;
        
        /**
    	 * Parametrized Constructor for the Node class
    	 */
        Node (type data) {
            this.data = data;
            this.next = null;
        }
    }//class Node
    
    /**
	 * Default Constructor for the Stack class
	 */
	public Stack() {
		top = null;
	}

	/**
	 * Parametrized method for the List class
	 *
	 * @param data the data that the Stack will store
	 */
	public Stack(type data) {
		Node node = new Node(data);
		node.next = top;
		top = node;
	}
	
	/**
	 * Method that returns the data in the top of the Stack
	 *
	 * @return the data in the top of the Stack
	 */
	public type getTop(){
		return top.data;
	}
	

	/**
	 * Method to check whether the stack is empty 
	 *
	 * @return true if the stack is empty and otherwise false
	 */
	public boolean isEmpty (){
		return (top==null);
	}
	

	/**
	 * Method that adds a data into the stack
	 *
	 * @param data the value that will be added 
	 */
	public void addData (type data) {
		Node node = new Node(data);
		node.next = top;
		top = node;
	}


	/**
	* Removes a data from the stack (the data in the top)
	*/
	public void removeData() {
		if (!isEmpty()){		
			top = top.next;
		}
	}
}
