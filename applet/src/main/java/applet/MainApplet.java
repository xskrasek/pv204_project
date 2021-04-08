package applet;

import javacard.framework.*;
import javacard.security.RandomData;
import javacard.security.*;
import javacardx.crypto.*;

public class MainApplet extends Applet implements MultiSelectable {
    final static byte CLA_MAINAPPLET = (byte) 0xB0;


    final static byte INS_TEST = (byte) 0x50;
    final static byte INS_RECEIVE = (byte) 0x51;
    final static byte INS_GET_KEYLEN = (byte) 0x70;

    private byte m_ramArray[] = null;

    private byte KeyLenArray[] = new byte[4];

    //Exceptions
    final static short SW_WRONG_DATA = (short) 0x6A80;

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MainApplet(bArray, bOffset, bLength);
    }

    public MainApplet(byte[] buffer, short offset, byte length) {


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

                    case INS_GET_KEYLEN:
                        SendKeyLen(apdu);
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
        short randomDataLen = (short) 20;
        apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, randomDataLen);

    }

    void ParsePsbt(APDU apdu) throws ISOException {
// Part of valid PSBT 70736274ff0100750200000001268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff02d3dff50
//Using the ram array is more of an prototype experiment, then the future go to variant.
        byte[] apdubuf = apdu.getBuffer();
        //copy data to local ram part
        Util.arrayCopyNonAtomic(apdubuf, (short) 5, m_ramArray, (short) 0, apdubuf[ISO7816.OFFSET_LC]);

        //copy half of input data from ram to outgoing packet
        Util.arrayCopyNonAtomic(m_ramArray, (short) 0, apdubuf, ISO7816.OFFSET_CDATA, (short) 5);
        byte[] magic = {0x70, 0x73, 0x62, 0x74, (byte) 0xFF};

        //check magic bytes
        for (short i = 0; i < 5; i++) {
            if (magic[i] != m_ramArray[i]) {
                ISOException.throwIt(ISO7816.SW_WRONG_DATA);
            }
        }
        //Global map
        byte key_lengthb = m_ramArray[6];
        byte keylen = 0;

        //TODO:Handle the parsing
        switch (key_lengthb) {
            case (byte) 0xfd: {
                byte[] buf = new byte[2];
                //Next two bytes
            }
            case (byte) 0xfe: {
                byte[] buf = new byte[4];
                //next four bytes

            }
            case (byte) 0xff: {
                byte[] buf = new byte[8];
                //next 8 bytes
            }
            default:
//               //just use they byte
                keylen = key_lengthb;
        }
        Util.arrayFillNonAtomic(KeyLenArray, (short) 0, (short)1, keylen);
        apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, (short) 5);
    }

    private byte[] parse_key() {

        //TODO:
        return new byte[1];
    }

     void SendKeyLen(APDU apdu) {
        //TODO:error handling, dynamic value
        byte[] apdubuf = apdu.getBuffer();
        Util.arrayCopyNonAtomic(KeyLenArray, (short) 0, apdubuf, ISO7816.OFFSET_CDATA, (short) 1);
        apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, (short) 5);
    }


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


    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {

    }
}
