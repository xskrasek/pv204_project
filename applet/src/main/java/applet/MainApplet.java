package applet;

import javacard.framework.*;
import javacard.security.RandomData;
import javacard.security.*;
import javacardx.crypto.*;

public class MainApplet extends Applet implements MultiSelectable {
    final static byte CLA_MAINAPPLET = (byte) 0xB0;
    private static final short BUFFER_SIZE = 32;

    private byte[] tmpBuffer = JCSystem.makeTransientByteArray(BUFFER_SIZE, JCSystem.CLEAR_ON_DESELECT);
    private RandomData random;
    final static byte INS_TEST = (byte) 0x50;
    final static byte INS_RECEIVE = (byte) 0x51;


    private byte m_ramArray[] = null;

    //Exceptions
    final static short SW_WRONG_DATA = (short) 0x6A80 ;

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MainApplet(bArray, bOffset, bLength);
    }

    public MainApplet(byte[] buffer, short offset, byte length) {
        random = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
        m_ramArray = JCSystem.makeTransientByteArray((short) 260, JCSystem.CLEAR_ON_DESELECT);
        register();
    }

    public void process(APDU apdu) {

		byte[] apduBuffer = apdu.getBuffer();

        // ignore the applet select command dispached to the process
        if (selectingApplet()) {
            return;
        }

        try {
            // APDU instruction parser
            if (apduBuffer[ISO7816.OFFSET_CLA] == CLA_MAINAPPLET) {
                switch (apduBuffer[ISO7816.OFFSET_INS]) {
                    case INS_TEST:
                        Test(apdu);
                        break;
                    case INS_RECEIVE:
                        ParsePsbt(apdu);
                        break;
                    default:
                        // The INS code is not supported by the dispatcher
                        ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
                        break;
                }
            } else {
                ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
            }

            // Capture all reasonable exceptions and change into readable ones (instead of 0x6f00)
        } catch (ISOException e) {
            throw e; // Our exception from code, just re-emit
        }

    }

    void Test(APDU apdu) {
    	byte[] apdubuf = apdu.getBuffer();
    	short randomDataLen = (short) 42069;
		apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, randomDataLen);

	}

	void ParsePsbt(APDU apdu) throws  ISOException{
//Using the ram array is more of an prototype experiment, then the future go to variant.
        byte[] apdubuf = apdu.getBuffer();
        //copy data to local ram part
        Util.arrayCopyNonAtomic(apdubuf, (short) 5, m_ramArray, (short) 0, apdubuf[ISO7816.OFFSET_LC] );

        //copy half of input data from ram to outgoing packet
        Util.arrayCopyNonAtomic(m_ramArray, (short) 0, apdubuf, ISO7816.OFFSET_CDATA, (short) 5);
        byte[] magic = {0x70, 0x73 ,0x62, 0x74 , (byte) 0xFF};

        //check magic bytes
        for(short i =0; i <5 ;i++) {
            if (magic[i] != m_ramArray[i]) {
                ISOException.throwIt(ISO7816.SW_WRONG_DATA);
            }
        }
        apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, (short) 5);

/*
 <psbt> := <magic> <global-map> <input-map>* <output-map>*
 <magic> := 0x70 0x73 0x62 0x74 0xFF
 <global-map> := <keypair>* 0x00
 <input-map> := <keypair>* 0x00
 <output-map> := <keypair>* 0x00
 <keypair> := <key> <value>
 <key> := <keylen> <keytype> <keydata>
 <value> := <valuelen> <valuedata>
 */

    }
    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {

    }
}
