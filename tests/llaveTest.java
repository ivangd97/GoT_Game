import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ivangd97 on 1/22/17.
 */
public class llaveTest {
    @Test
    public void get_id() throws Exception {
        llave test = new llave(5);
        assertEquals(5, test.get_id());
    }

}