/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

public class llave implements Comparable<llave> {

	//identificador de la llave
	private int id;

	//Constructor por defecto de la llave
	public llave() {
		this.id = 0;
	}

	//Contructor parametrizado de la llave que recibe el identificador que queremos asignarle
	public llave(int id) {
		this.id = id;
	}

	//Retorna el identificador de la llave
	public int get_id() {
		return this.id;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof llave))
			return false;
		llave vAux = (llave) obj;
		return (this.id == vAux.get_id());

	}


	@Override
	public int compareTo(llave v2) {
		if (this.get_id() == v2.get_id())
			return 0;
		else {
			if (this.get_id() < v2.get_id()) {
				return -1;
			} else {
				return 1;
			}
		}
	}


}




