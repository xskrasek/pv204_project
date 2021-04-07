package tests;

import cardTools.CardType;
import cardTools.RunConfig;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

/**
 * Example test class for the applet
 * Note: If simulator cannot be started try adding "-noverify" JVM parameter
 *
 * @author xsvenda, Dusan Klinec (ph4r05)
 */
public class AppletTest extends BaseTest {
    
    public AppletTest() {
        // Change card type here if you want to use physical card

        // setCardType(CardType.PHYSICAL);
        // setCardType(CardType.REMOTE);
        setCardType(CardType.JCARDSIMLOCAL);
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUpMethod() throws Exception {
    }

    @AfterEach
    public void tearDownMethod() throws Exception {
    }

    // Example test
    @Test
    public void hello() throws Exception {/* The orig test
        final CommandAPDU cmd = new CommandAPDU(0xB0, 0x50, 0, 0);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);
        Assert.assertNotNull(responseAPDU);
        Assert.assertEquals(0x9000, responseAPDU.getSW());
        Assert.assertNotNull(responseAPDU.getBytes());*/

        byte[] test = {0x70, 0x73, 0x62, 0x74, (byte) 0xFF, 0x6d, 0x61, 0x72, 0x63, 0x6f};
        final CommandAPDU cmd = new CommandAPDU(0xB0, 0x51, 0,0, test);
        final ResponseAPDU response = connect().transmit(cmd);
        Assert.assertEquals(0x9000, response.getSW());
        byte[] res = response.getData();
        int c = 2;
    }
}
