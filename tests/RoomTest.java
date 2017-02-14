import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ivangd97 on 12/19/16.
 */

public class RoomTest {
    @Test
    public void insertarPJ() throws Exception {
        Room rTest = new Room();
        personaje aux = new Stark();
        personaje aux2 = new WhiteWalkers();
        rTest.insertarPJ(aux);
        rTest.insertarPJ(aux2);
        while(!rTest.vacia()){
            personaje test = rTest.getPJ();
            assertTrue(test == rTest.getPJ());
            rTest.removePJ();
        }

    }

    @Test
    public void getMarca() throws Exception {
        Room rTest = new Room();
        assertTrue(0 == rTest.getMarca());
    }

    @Test
    public void getRoomID() throws Exception {
        Room rTest = new Room();
        assertTrue(0 == rTest.getRoomID());
    }

    @Test
    public void getRoomDoor() throws Exception {
        Room rTest = new Room();
        Door aux = new Door(0);
        rTest.setRoomDoor(aux);
        assertEquals(aux,rTest.getRoomDoor());
    }

    @Test
    public void vacia() throws Exception {
        Room rTest = new Room();
        assertTrue(rTest.vacia());
    }

    @Test
    public void addKey() throws Exception {
        Room rTest = new Room();
        llave kAux = new llave(1);
        rTest.addKey(kAux);
        assertEquals(kAux,rTest.getKey());
    }

    @Test
    public void vaciaDeLlaves() throws Exception {
        Room rTest = new Room();
        assertTrue(rTest.vaciaDeLlaves());
    }

}
