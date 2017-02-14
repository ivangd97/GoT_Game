/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */
//Nombre y Apellidos de cada alumno: Jaime Alcántara Arnela e Iván González Domínguez
//Curso:2º A

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Door {

    //arbol de llaves que contiene la combinacion de la puerta
	protected Arbol <llave> keyTree = new Arbol<llave>();
	
	protected Arbol <llave> reservedKeyTree = new Arbol<llave>();

    //Arbol de llaves que contiene las llaves usadas en la puerta
    protected Arbol <llave> usedKeysTree = new Arbol<llave>();

    //Atributo que nos dice si la puerta está abierta o no
    protected boolean Open;

    //Altura del arbol de llaves que posee la puerta
    protected int alturaCerradura;


    //Constructor parametrizado de la puerta que recibe la altura de su arbol de llaves
    public Door(int altura){
        Open = false;
        alturaCerradura = altura;
    }

    //Algoritmo que prueba las llaves de un determinado personaje en la configuracion de la puerta
    public void open(llave key){
    	//introducimos la llave usada en el arbol auxiliar y la eliminamos del arbol principal
    	if(!usedKeysTree.pertenece(key))
    		usedKeysTree.insertar(key);
    	//si la llave key pertenece al arbol de la puerta
    	if(keyTree.pertenece(key)){
    		keyTree.borrar(key);
            keyTree.numElementos--;
          //si la puerta se abre cambiamos el booleano de abierta
		    if(!Open){
		    if (keyTree.profundidadArbol() < alturaCerradura){
		        Open = true;
		        System.out.println("Puerta abierta");
		    }
		    int elementosInternos = keyTree.numElementos - keyTree.contarHojas();
		    if (keyTree.contarHojas()< elementosInternos){
		    	Open = true;
		        System.out.println("Puerta abierta");
		    }
		    }
    	}
    }

    //Devuelve el estado de la puerta
    public boolean isOpen(){
    	return Open;
    }

    //Cierra la puerta
    public void closeDoor(){
    	keyTree = new Arbol<llave>();
    	copyTree(reservedKeyTree, keyTree);
    	usedKeysTree = new Arbol<llave>();
    }
    
    public void copyTree(Arbol<llave> or, Arbol<llave> des){
		llave auxKey;
		Arbol <llave> aux = null;
		//Comprobamos si el arbol esta vacio
		if (!or.vacio()) {
			//obtenemos la raiz y la añadimos a la estructura
			auxKey = or.getRaiz();
			des.insertar(auxKey);
			if ((aux=or.getHijoIzq())!=null) {
				copyTree(aux,des);
			}

			if ((aux=or.getHijoDer())!=null){
				copyTree(aux,des);
			}
		}
	}

    //Guarda el arbol principal en el auxiliar para que la cerradura se recombine llegado el momento
    public void saveCombination(){
    	reservedKeyTree = new Arbol<llave>();
    	copyTree(keyTree, reservedKeyTree);
    }

    //Muestra la informacion de la puerta
    public void showInfo() throws IOException{
        BufferedWriter bufferout;
        bufferout = new BufferedWriter(new FileWriter("registro.log",true));
    	if (Open){
            bufferout.write("(puerta:abierta:"+alturaCerradura+":");
        }

    	else
    		bufferout.write("(puerta:cerrada:"+alturaCerradura+":");

        bufferout.close();
        showKeys(keyTree);
        bufferout = new BufferedWriter(new FileWriter("registro.log",true));
        bufferout.write(":");
        bufferout.close();
        showKeys(usedKeysTree);
        bufferout = new BufferedWriter(new FileWriter("registro.log",true));
        bufferout.write(")");
        bufferout.newLine();

        bufferout.close();
    	
    }

    //Modulo que escribe las llaves del arbol en el fichero de salida
    public void showKeys(Arbol<llave> abb) throws IOException{
        BufferedWriter bufferout;
        bufferout = new BufferedWriter(new FileWriter("registro.log",true));
        llave auxKey;
        Arbol <llave> aux = null;
        //Comprobamos si el arbol esta vacio
        if (!abb.vacio()) {
            if ((aux=abb.getHijoIzq())!=null) {
                showKeys(aux);
            }
            //obtenemos la raiz y la añadimos a la estructura del personaje lannister
            auxKey = abb.getRaiz();
            bufferout.write(auxKey.get_id()+" ");
            if ((aux=abb.getHijoDer())!=null){
                bufferout.close();
                showKeys(aux);
            }
            bufferout.close();
        }
    }

}
