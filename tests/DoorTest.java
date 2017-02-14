import org.junit.Assert;
import org.junit.Test;




public class DoorTest {
    @Test
    public void open() throws Exception {
        Door test = new Door(2);
        llave aux = new llave(15);
        llave aux2 = new llave(7);
        llave aux3 = new llave(23);
        test.open(aux3);
        test.open(aux2);
        test.open(aux);
        Assert.assertEquals(true,test.isOpen());

    }

    @Test
    public void isOpen() throws Exception {
        Door test = new Door(1);
        llave aux = new llave(15);
        test.open(aux);
        Assert.assertTrue(test.isOpen());
    }

    @Test
    public void closeDoor() throws Exception {
        Door test = new Door(1);
        llave aux = new llave(15);
        test.open(aux);
        test.closeDoor();
        Assert.assertFalse(test.isOpen());
    }

}
