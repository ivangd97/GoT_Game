import org.junit.Test;
import static org.junit.Assert.*;

public class personajeTest {
    @Test
    public void emptyOfKeys() throws Exception {
        personaje test = new Stark("Edard","E",1,20);
        assertTrue(test.emptyOfKeys());
    }

    @Test
    public void getSalaI() throws Exception {
        personaje test = new Stark("Edard","E",1,20);
        assertEquals(20,test.getSalaI());
    }

    @Test
    public void get_id() throws Exception {
        personaje test = new Stark("Edard","E",1,20);
        assertEquals("E",test.get_id());
    }

    @Test
    public void get_nombre() throws Exception {
        personaje test = new Stark("Edard","E",1,20);
        assertEquals("Edard",test.get_nombre());
    }

    @Test
    public void get_Turno() throws Exception {
        personaje test = new Stark("Edard","E",1,20);
        assertEquals(1,test.get_Turno());
    }

}
