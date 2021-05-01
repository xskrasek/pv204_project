package tests;

import cardTools.CardManager;
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
    public void hello() throws Exception {

        final CardManager manager = connect();


        //retrieve
        final CommandAPDU cmd2 = new CommandAPDU(0xB0, 0x51, 0, 0);
        final ResponseAPDU response2 = manager.transmit(cmd2);
        Assert.assertEquals(0x9000, response2.getSW());
        byte[] res2 = response2.getData();
        Assert.assertEquals((byte) 0x00, res2[0]);
    }

}
