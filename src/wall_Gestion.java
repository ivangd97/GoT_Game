import java.util.LinkedList;

/*Nombre del grupo: Chupipandi
 *Nombre y apellidos de cada componente: Jaime Alcántara Arnela e Iván González Domínguez
 *Número de entrega (EC1, EC2, febrero, junio, etc.): Enero
 *Curso: 2º A
 */

/**
 * Created by ivangd97 on 11/25/16.
 */
public class wall_Gestion {
	
	//Este metodo llena la estructura de muros del mapa generando muros entre todas las salas en orden N E S O
	public LinkedList<int[]> levantarMuros(Mapa gameMap){
		Room[] vectorRoom;
		LinkedList<int[]> paredes = new LinkedList<int[]>();
		vectorRoom = gameMap.getVectorRoom();
		Room r, r2;
		int[] pareja = new int[2];
		for(int i = 0; i< gameMap.getDimension();i++){
			r = vectorRoom[i];
			if(r.getRoomID() - gameMap.getDimensionX() >= 0){
				pareja = new int[2];
				r2 = vectorRoom[r.getRoomID() - gameMap.getDimensionX()];
				pareja[0] = r.getRoomID();
				pareja[1] = r2.getRoomID();
				paredes.add(pareja);

			}
			
			if((r.getRoomID()+1)%gameMap.getDimensionX() != 0){
				pareja = new int[2];
				r2 = vectorRoom[r.getRoomID()+1];
				pareja[0] = r.getRoomID();
				pareja[1] = r2.getRoomID();
				paredes.add(pareja);
			}
			
			if(r.getRoomID() + gameMap.getDimensionX() < gameMap.getDimension()){
				pareja = new int[2];
				r2 = vectorRoom[r.getRoomID() + gameMap.getDimensionX()];
				pareja[0] = r.getRoomID();
				pareja[1] = r2.getRoomID();
				paredes.add(pareja);
			}
			
			if((r.getRoomID())%gameMap.getDimensionX() != 0){
				pareja = new int[2];
				r2 = vectorRoom[r.getRoomID()-1];
				pareja[0] = r.getRoomID();
				pareja[1] = r2.getRoomID();
				paredes.add(pareja);
			}
			gameMap.addNode(r.getRoomID());
			
		}
		return paredes;
		
	}
	
	
	//Este metodo llama al anterior para generar las paredes del mapa y posteriormente derriba varias para
	//formar el laberinto
	public void tirarMuros(){
		Room[] vectorRoom;
		Mapa gameMap = Mapa.getInstancia();
		vectorRoom = gameMap.getVectorRoom();	
		LinkedList<int[]> paredes = levantarMuros(gameMap);
		int	numParedes = (gameMap.getVectorRoom().length*5)/100;
		int paredesTiradas = 0;
		int dimX = gameMap.getDimensionX();
		Grafo auxGr = gameMap.getGraph();
		while(paredes.size()>0){
			int i = GenAleatorios.generarNumero(paredes.size());
			int[] pared = paredes.get(i);
			paredes.remove(i);
			//Si las marcas de las salas correspondientes a esta pared son distintas la pared puede tirarse y formar un arco
			if(vectorRoom[pared[0]].getMarca() != vectorRoom[pared[1]].getMarca()){
				gameMap.addArc(pared[0], pared[1], 1);
				gameMap.addArc(pared[1], pared[0], 1);
				int desMarc = vectorRoom[pared[1]].getMarca();
				//Todas las salas que tuviesen la marca de la sala con la que se trabaja cambian de marca ahora
				for (int x=0;x<vectorRoom.length;x++){
					 if(desMarc == vectorRoom[x].getMarca()){
						 vectorRoom[x].setMarca(vectorRoom[pared[0]].getMarca());
					 }
					 
				 }
			}
			
		}
		//En este segundo apartado buscamos las paredes extra que tirar para crear mas caminos
		while (paredesTiradas < numParedes){
			int salaAdyacente = -1;
			int[] pared = new int[2];
			boolean conseguido = false;
			int sala = GenAleatorios.generarNumero(gameMap.getVectorRoom().length);
			pared[0] = sala;
			//Si no esta en el limite norte, se comprueba si hay pared
			if(sala-dimX>=0 && !auxGr.adyacente(sala, sala-dimX)){
				salaAdyacente = sala - dimX;
				pared[1] = salaAdyacente;
				conseguido = tryWall(pared);
			}
			if(!conseguido){
				//Si no esta en el limite sur, se comprueba si hay pared
				if(sala+dimX<gameMap.getDimension() && !auxGr.adyacente(sala, sala+dimX)){
					salaAdyacente = sala + dimX;
					pared[1] = salaAdyacente;
					conseguido = tryWall(pared);
				}
				if(!conseguido){
					//Si no esta en el limite oeste, se comprueba si hay pared
					if(sala%gameMap.getDimensionX()!=0 && !auxGr.adyacente(sala, sala-1)){
						salaAdyacente = sala - 1;
						pared[1] = salaAdyacente;
						conseguido = tryWall(pared);
					}
					//Si no esta en el limite este, se comprueba si hay pared
					if(!conseguido){
						if((sala+1)%gameMap.getDimensionX()!=0 && !auxGr.adyacente(sala, sala+1)){
							salaAdyacente = sala + 1;
							pared[1] = salaAdyacente;
							conseguido = tryWall(pared);
							
						}
					}
				}
			}
			if(conseguido)
				paredesTiradas++;			
		}		
		
	}

	//Devuelve el menor de dos enteros
	public int menor(int a, int b){
		if(a<b)
			return a;
		else
			return b;
		
	}
	
	//Este modulo comprueba que la pared seleccionada no cree espacios
	public boolean tryWall(int[] pared){
		Mapa gameMap = Mapa.getInstancia();
		Grafo auxGr = gameMap.getGraph();
		int dimX = gameMap.getDimensionX();
		int menor = menor(pared[0],pared[1]);
		boolean paredTirada = false;
		//Si la pared es vertical
		if(pared[0]-pared[1] == 1 || pared[0]-pared[1] == -1){			
			//Si la pared no esta en los limites
			if(pared[0] -dimX>=0 && pared[0] +dimX < gameMap.getDimension()){					
				if((!auxGr.adyacente(menor, menor-dimX)||!auxGr.adyacente(menor-dimX, menor-dimX+1)||!auxGr.adyacente(menor-dimX+1, menor+1))
						&&(!auxGr.adyacente(menor, menor+dimX)||!auxGr.adyacente(menor+dimX, menor+dimX+1)||!auxGr.adyacente(menor+dimX+1, menor+1))){
					gameMap.addArc(pared[0], pared[1], 1);
					gameMap.addArc(pared[1], pared[0], 1);
					paredTirada = true;
				}
			}
			else{
				//Si la pared esta en el limite superior
				if(pared[0] -dimX<0){
					if(!auxGr.adyacente(menor, menor+dimX)||!auxGr.adyacente(menor+dimX, menor+dimX+1)||!auxGr.adyacente(menor+dimX+1, menor+1)){
						gameMap.addArc(pared[0], pared[1], 1);
						gameMap.addArc(pared[1], pared[0], 1);
						paredTirada = true;
					}
					
				}
				//Si la pared esta en el limite inferior
				else{
					if(!auxGr.adyacente(menor, menor-dimX)||!auxGr.adyacente(menor-dimX, menor-dimX+1)||!auxGr.adyacente(menor-dimX+1, menor+1)){
						gameMap.addArc(pared[0], pared[1], 1);
						gameMap.addArc(pared[1], pared[0], 1);
						paredTirada = true;
					}
				}
			}
		}
		//Si la pared es horizontal
		else{
			//Si la pared no esta en los limites
			if(pared[0]%dimX!=0 && (pared[0]+1)%dimX != 0){					
				if((!auxGr.adyacente(menor, menor-1)||!auxGr.adyacente(menor-1, menor+dimX-1)||!auxGr.adyacente(menor+dimX-1, menor+dimX))
						&&(!auxGr.adyacente(menor, menor+1)||!auxGr.adyacente(menor+1, menor+dimX+1)||!auxGr.adyacente(menor+dimX+1, menor+dimX))){
					gameMap.addArc(pared[0], pared[1], 1);
					gameMap.addArc(pared[1], pared[0], 1);
					paredTirada = true;
				}
			}
			else{
				//Si la pared esta en el limite izquierdo
				if(pared[0]%dimX==0){
					if(!auxGr.adyacente(menor, menor+1)||!auxGr.adyacente(menor+1, menor+dimX+1)||!auxGr.adyacente(menor+dimX+1, menor+dimX)){
						gameMap.addArc(pared[0], pared[1], 1);
						gameMap.addArc(pared[1], pared[0], 1);
						paredTirada = true;
					}
					
				}
				//Si la pared esta en el limite derecho
				else{
					if(!auxGr.adyacente(menor, menor-1)||!auxGr.adyacente(menor-1, menor+dimX-1)||!auxGr.adyacente(menor+dimX-1, menor+dimX)){
						gameMap.addArc(pared[0], pared[1], 1);
						gameMap.addArc(pared[1], pared[0], 1);
						paredTirada = true;
					}
				}
			}
		}
		
		
		return paredTirada;
	}
		
}
