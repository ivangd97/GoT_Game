import org.junit.Test;

import org.junit.Assert.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapaTest {
    @Test
    public void getDimensionY() throws Exception {
        Mapa testMap = new Mapa();
        assertEquals(0,testMap.getDimensionY());
    }

    @Test
    public void esquinaSuroeste() throws Exception {
        Mapa testMap = new Mapa();
        assertEquals(0,testMap.esquinaSuroeste());
    }

    @Test
    public void getSalaTrono() throws Exception {
        Mapa testMap = new Mapa();
        assertEquals(0,testMap.getSalaTrono());
    }

    @Test
    public void getDimension() throws Exception {
        Mapa testMap = new Mapa();
        assertEquals(0,testMap.getDimension());
    }

    @Test
    public void getDimensionX() throws Exception {
        Mapa testMap = new Mapa();
        assertEquals(0,testMap.getDimensionX());
    }

}
