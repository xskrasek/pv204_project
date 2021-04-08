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
    public void hello() throws Exception {/* The orig test
        final CommandAPDU cmd = new CommandAPDU(0xB0, 0x50, 0, 0);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);
        Assert.assertNotNull(responseAPDU);
        Assert.assertEquals(0x9000, responseAPDU.getSW());
        Assert.assertNotNull(responseAPDU.getBytes());*/

        //TODO: Correct test formats
        //Only one connect, so it does not reset cards data
        final CardManager manager = connect();

        byte[] test = {0x70, 0x73, 0x62, 0x74, (byte) 0xFF, 0x6d, 0x61, 0x72, 0x63, 0x6f};
        final CommandAPDU cmd = new CommandAPDU(0xB0, 0x51, 0,0, test);
        final ResponseAPDU response = manager.transmit(cmd);
        Assert.assertEquals(0x9000, response.getSW());
        byte[] res = response.getData();


        //retrieve
        final CommandAPDU cmd2 = new CommandAPDU(0xB0, 0x70, 0, 0);
        final ResponseAPDU response2 = manager.transmit(cmd2);
        Assert.assertEquals(0x9000, response2.getSW());
        byte[] res2 = response2.getData();
        Assert.assertEquals((byte) 0x61, res2[0]);
    }

    /*@Test
    public void TestDataRetrieve() throws Exception{
        byte[] test = {0x70, 0x73, 0x62, 0x74, (byte) 0xFF, 0x01, 0x02,0x03};
        final CommandAPDU cmd = new CommandAPDU(0xB0, 0x70, 0, 0, test);
        final ResponseAPDU response = connect().transmit(cmd);
        Assert.assertEquals(0x9000, response.getSW());
        byte[] res = response.getData();
        Assert.assertEquals(res[0], (byte) 0x20);

    }*/
}
