package applet;

import javacard.framework.*;

public class MainApplet extends Applet implements MultiSelectable {
    final static byte CLA_MAINAPPLET = (byte) 0xB0;

    //instructions
    final static byte INS_TEST = (byte) 0x50;
    final static byte INS_RECEIVE = (byte) 0x51;

    //PSBT constants
    final static byte PSBT_GLOBAL_UNSIGNED_TX = 0x00;

    final static byte[] psbt = {(byte) 0x70, (byte) 0x73, (byte) 0x62, (byte) 0x74, (byte) 0xff, (byte) 0x01, (byte) 0x00, (byte) 0x75, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x26, (byte) 0x81, (byte) 0x71, (byte) 0x37, (byte) 0x1e, (byte) 0xdf, (byte) 0xf2, (byte) 0x85, (byte) 0xe9, (byte) 0x37, (byte) 0xad, (byte) 0xee, (byte) 0xa4, (byte) 0xb3, (byte) 0x7b, (byte) 0x78, (byte) 0x00, (byte) 0x0c, (byte) 0x05, (byte) 0x66, (byte) 0xcb, (byte) 0xb3, (byte) 0xad, (byte) 0x64, (byte) 0x64, (byte) 0x17, (byte) 0x13, (byte) 0xca, (byte) 0x42, (byte) 0x17, (byte) 0x1b, (byte) 0xf6, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xfe, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x02, (byte) 0xd3, (byte) 0xdf, (byte) 0xf5, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x19, (byte) 0x76, (byte) 0xa9, (byte) 0x14, (byte) 0xd0, (byte) 0xc5, (byte) 0x99, (byte) 0x03, (byte) 0xc5, (byte) 0xba, (byte) 0xc2, (byte) 0x86, (byte) 0x87, (byte) 0x60, (byte) 0xe9, (byte) 0x0f, (byte) 0xd5, (byte) 0x21, (byte) 0xa4, (byte) 0x66, (byte) 0x5a, (byte) 0xa7, (byte) 0x65, (byte) 0x20, (byte) 0x88, (byte) 0xac, (byte) 0x00, (byte) 0xe1, (byte) 0xf5, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17, (byte) 0xa9, (byte) 0x14, (byte) 0x35, (byte) 0x45, (byte) 0xe6, (byte) 0xe3, (byte) 0x3b, (byte) 0x83, (byte) 0x2c, (byte) 0x47, (byte) 0x05, (byte) 0x0f, (byte) 0x24, (byte) 0xd3, (byte) 0xee, (byte) 0xb9, (byte) 0x3c, (byte) 0x9c, (byte) 0x03, (byte) 0x94, (byte) 0x8b, (byte) 0xc7, (byte) 0x87, (byte) 0xb3, (byte) 0x2e, (byte) 0x13, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0xfd, (byte) 0xa5, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x89, (byte) 0xa3, (byte) 0xc7, (byte) 0x1e, (byte) 0xab, (byte) 0x4d, (byte) 0x20, (byte) 0xe0, (byte) 0x37, (byte) 0x1b, (byte) 0xbb, (byte) 0xa4, (byte) 0xcc, (byte) 0x69, (byte) 0x8f, (byte) 0xa2, (byte) 0x95, (byte) 0xc9, (byte) 0x46, (byte) 0x3a, (byte) 0xfa, (byte) 0x2e, (byte) 0x39, (byte) 0x7f, (byte) 0x85, (byte) 0x33, (byte) 0xcc, (byte) 0xb6, (byte) 0x2f, (byte) 0x95, (byte) 0x67, (byte) 0xe5, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17, (byte) 0x16, (byte) 0x00, (byte) 0x14, (byte) 0xbe, (byte) 0x18, (byte) 0xd1, (byte) 0x52, (byte) 0xa9, (byte) 0xb0, (byte) 0x12, (byte) 0x03, (byte) 0x9d, (byte) 0xaf, (byte) 0x3d, (byte) 0xa7, (byte) 0xde, (byte) 0x4f, (byte) 0x53, (byte) 0x34, (byte) 0x9e, (byte) 0xec, (byte) 0xb9, (byte) 0x85, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x86, (byte) 0xf8, (byte) 0xaa, (byte) 0x43, (byte) 0xa7, (byte) 0x1d, (byte) 0xff, (byte) 0x14, (byte) 0x48, (byte) 0x89, (byte) 0x3a, (byte) 0x53, (byte) 0x0a, (byte) 0x72, (byte) 0x37, (byte) 0xef, (byte) 0x6b, (byte) 0x46, (byte) 0x08, (byte) 0xbb, (byte) 0xb2, (byte) 0xdd, (byte) 0x2d, (byte) 0x01, (byte) 0x71, (byte) 0xe6, (byte) 0x3a, (byte) 0xec, (byte) 0x6a, (byte) 0x48, (byte) 0x90, (byte) 0xb4, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17, (byte) 0x16, (byte) 0x00, (byte) 0x14, (byte) 0xfe, (byte) 0x3e, (byte) 0x9e, (byte) 0xf1, (byte) 0xa7, (byte) 0x45, (byte) 0xe9, (byte) 0x74, (byte) 0xd9, (byte) 0x02, (byte) 0xc4, (byte) 0x35, (byte) 0x59, (byte) 0x43, (byte) 0xab, (byte) 0xcb, (byte) 0x34, (byte) 0xbd, (byte) 0x53, (byte) 0x53, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x02, (byte) 0x00, (byte) 0xc2, (byte) 0xeb, (byte) 0x0b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x19, (byte) 0x76, (byte) 0xa9, (byte) 0x14, (byte) 0x85, (byte) 0xcf, (byte) 0xf1, (byte) 0x09, (byte) 0x7f, (byte) 0xd9, (byte) 0xe0, (byte) 0x08, (byte) 0xbb, (byte) 0x34, (byte) 0xaf, (byte) 0x70, (byte) 0x9c, (byte) 0x62, (byte) 0x19, (byte) 0x7b, (byte) 0x38, (byte) 0x97, (byte) 0x8a, (byte) 0x48, (byte) 0x88, (byte) 0xac, (byte) 0x72, (byte) 0xfe, (byte) 0xf8, (byte) 0x4e, (byte) 0x2c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17, (byte) 0xa9, (byte) 0x14, (byte) 0x33, (byte) 0x97, (byte) 0x25, (byte) 0xba, (byte) 0x21, (byte) 0xef, (byte) 0xd6, (byte) 0x2a, (byte) 0xc7, (byte) 0x53, (byte) 0xa9, (byte) 0xbc, (byte) 0xd0, (byte) 0x67, (byte) 0xd6, (byte) 0xc7, (byte) 0xa6, (byte) 0xa3, (byte) 0x9d, (byte) 0x05, (byte) 0x87, (byte) 0x02, (byte) 0x47, (byte) 0x30, (byte) 0x44, (byte) 0x02, (byte) 0x20, (byte) 0x27, (byte) 0x12, (byte) 0xbe, (byte) 0x22, (byte) 0xe0, (byte) 0x27, (byte) 0x0f, (byte) 0x39, (byte) 0x4f, (byte) 0x56, (byte) 0x83, (byte) 0x11, (byte) 0xdc, (byte) 0x7c, (byte) 0xa9, (byte) 0xa6, (byte) 0x89, (byte) 0x70, (byte) 0xb8, (byte) 0x02, (byte) 0x5f, (byte) 0xdd, (byte) 0x3b, (byte) 0x24, (byte) 0x02, (byte) 0x29, (byte) 0xf0, (byte) 0x7f, (byte) 0x8a, (byte) 0x5f, (byte) 0x3a, (byte) 0x24, (byte) 0x02, (byte) 0x20, (byte) 0x01, (byte) 0x8b, (byte) 0x38, (byte) 0xd7, (byte) 0xdc, (byte) 0xd3, (byte) 0x14, (byte) 0xe7, (byte) 0x34, (byte) 0xc9, (byte) 0x27, (byte) 0x6b, (byte) 0xd6, (byte) 0xfb, (byte) 0x40, (byte) 0xf6, (byte) 0x73, (byte) 0x32, (byte) 0x5b, (byte) 0xc4, (byte) 0xba, (byte) 0xa1, (byte) 0x44, (byte) 0xc8, (byte) 0x00, (byte) 0xd2, (byte) 0xf2, (byte) 0xf0, (byte) 0x2d, (byte) 0xb2, (byte) 0x76, (byte) 0x5c, (byte) 0x01, (byte) 0x21, (byte) 0x03, (byte) 0xd2, (byte) 0xe1, (byte) 0x56, (byte) 0x74, (byte) 0x94, (byte) 0x1b, (byte) 0xad, (byte) 0x4a, (byte) 0x99, (byte) 0x63, (byte) 0x72, (byte) 0xcb, (byte) 0x87, (byte) 0xe1, (byte) 0x85, (byte) 0x6d, (byte) 0x36, (byte) 0x52, (byte) 0x60, (byte) 0x6d, (byte) 0x98, (byte) 0x56, (byte) 0x2f, (byte) 0xe3, (byte) 0x9c, (byte) 0x5e, (byte) 0x9e, (byte) 0x7e, (byte) 0x41, (byte) 0x3f, (byte) 0x21, (byte) 0x05, (byte) 0x02, (byte) 0x48, (byte) 0x30, (byte) 0x45, (byte) 0x02, (byte) 0x21, (byte) 0x00, (byte) 0xd1, (byte) 0x2b, (byte) 0x85, (byte) 0x2d, (byte) 0x85, (byte) 0xdc, (byte) 0xd9, (byte) 0x61, (byte) 0xd2, (byte) 0xf5, (byte) 0xf4, (byte) 0xab, (byte) 0x66, (byte) 0x06, (byte) 0x54, (byte) 0xdf, (byte) 0x6e, (byte) 0xed, (byte) 0xcc, (byte) 0x79, (byte) 0x4c, (byte) 0x0c, (byte) 0x33, (byte) 0xce, (byte) 0x5c, (byte) 0xc3, (byte) 0x09, (byte) 0xff, (byte) 0xb5, (byte) 0xfc, (byte) 0xe5, (byte) 0x8d, (byte) 0x02, (byte) 0x20, (byte) 0x67, (byte) 0x33, (byte) 0x8a, (byte) 0x8e, (byte) 0x0e, (byte) 0x17, (byte) 0x25, (byte) 0xc1, (byte) 0x97, (byte) 0xfb, (byte) 0x1a, (byte) 0x88, (byte) 0xaf, (byte) 0x59, (byte) 0xf5, (byte) 0x1e, (byte) 0x44, (byte) 0xe4, (byte) 0x25, (byte) 0x5b, (byte) 0x20, (byte) 0x16, (byte) 0x7c, (byte) 0x86, (byte) 0x84, (byte) 0x03, (byte) 0x1c, (byte) 0x05, (byte) 0xd1, (byte) 0xf2, (byte) 0x59, (byte) 0x2a, (byte) 0x01, (byte) 0x21, (byte) 0x02, (byte) 0x23, (byte) 0xb7, (byte) 0x2b, (byte) 0xee, (byte) 0xf0, (byte) 0x96, (byte) 0x5d, (byte) 0x10, (byte) 0xbe, (byte) 0x07, (byte) 0x78, (byte) 0xef, (byte) 0xec, (byte) 0xd6, (byte) 0x1f, (byte) 0xca, (byte) 0xc6, (byte) 0xf7, (byte) 0x9a, (byte) 0x4e, (byte) 0xa1, (byte) 0x69, (byte) 0x39, (byte) 0x33, (byte) 0x80, (byte) 0x73, (byte) 0x44, (byte) 0x64, (byte) 0xf8, (byte) 0x4f, (byte) 0x2a, (byte) 0xb3, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    //Parse state representation, one for each map
    final static short globalMap = 1;
    final static short inputMap = 2;
    final static short outputMap = 3;

    short offsetChange = 0;

    private byte KeyType;
    private byte[] ValueData = null;


    /**Hard to find reasonable way to save individual data in one-dimensional fixed size arrays.
    Might be easier to parse whole part on card and send it back. Parsing of specific part could then
    be done with more appropriate data structures. The only way I can think of on JC is creating arrays
    of size inputs/outputs*2 and holding indexes of given parts
     I am not sure, if this approach does not go against the idea of using JavaCard for this though**/
    private byte[] GlobalValueData = null;

    private byte[] InputValueData= null;


    private byte[] OutputValueData = null;
    private byte[] inp;

    //Input and output counters
    short inputs = 0;
    short outputs = 0;


    //Exceptions
    final static short SW_WRONG_DATA = (short) 0x6A80;

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MainApplet(bArray, bOffset, bLength);
    }

    public MainApplet(byte[] buffer, short offset, byte length) {


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

/* I wanted to use NIO, but it is supported only by JC 3.1, so I decided to use this SO answer
    https://stackoverflow.com/questions/14827398/converting-byte-array-values-in-little-endian-order-to-short-values
    There are some minor modifications for JC
*/
    public static short byteArrayToShortLE(final byte[] b, final short offset)

    {
        short value = 0;
        for (short i = 0; i < 2; i++)
        {
            value |= (b[(short) (i + offset)] & 0x000000FF) << (i * 8);
        }

        return value;
    }


    void Test(APDU apdu) {
        byte[] apdubuf = apdu.getBuffer();
        short randomDataLen = (short) 20;
        apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, randomDataLen);

    }

    /**
     * Reads the encoded length
     * @param index index in psbt array
     * @return how many bytes were read including the first one, which specified how many are used
     */
    short GetLength(short index) {
        index++;
        byte key_lengthb = psbt[index];
        //so we dont read the keylength in copying
        index++;
        //
        switch (key_lengthb) {
            case (byte) 0xfd: {
                byte[] buf = new byte[2];

                offsetChange = 3;
                return byteArrayToShortLE(psbt, index);

            }
            case (byte) 0xfe: {

                offsetChange = 5;
                return byteArrayToShortLE(psbt, index);

            }
            case (byte) 0xff: {

                offsetChange = 9;
                return byteArrayToShortLE(psbt, index);
            }
            default:
                offsetChange = 1;
                //Convert to short, imho not as fun as slides make it up to be
                return (short) (key_lengthb & 0xff);
        }

    }

    /**
     * Parses transaction, both normal and segwit
      * @param txindex index in psbt, where the transaction to be parsed starts. As the length
     * of the whole transaction is known, no reason to count and return its length
     */
    void ParseTransaction(short txindex) {
        //skip over version

        boolean segwit = false;
        txindex += 4;
        inputs = GetLength(txindex);
        txindex += offsetChange;


        txindex++;

        if (inputs == 0) {
            //Segwit
            segwit = true;
            txindex++;

            if (ValueData[txindex] != 0x01) {
                //Should be segwit but is not
                ISOException.throwIt(ISO7816.SW_WRONG_DATA);
            }

            inputs = GetLength(txindex);
            txindex += offsetChange;
        }

        //does not parse out the info, just jumps ahead
        //read inputs
        for (short i = 0; i < inputs; i++) {

            //+4 jumps too far
            txindex += 32 + 3;
            short sig_length = GetLength(txindex);
            //this combines sum of the prev and offset
            //we actually read sig_length bytes
            txindex += 4 + offsetChange + sig_length;
        }


        outputs = GetLength(txindex);
        txindex += offsetChange;

        //now go through outputs
        for (short i = 0; i < outputs; i++) {
            txindex += 8;

            short p_length = GetLength(txindex);
            //we actually read p_length bytes, so they must be added
            txindex += offsetChange;
            txindex += p_length;


        }


        if (segwit) {
            //cycle through inputs
        }
        //locktime, 4 bytes



    }

    void ParsePsbt(APDU apdu) throws ISOException {

        //Here would be some APDU buffer processing

        //check magic bytes and separator
        byte[] magic = {0x70, 0x73, 0x62, 0x74, (byte) 0xFF};
        for (short i = 0; i < 5; i++) {
            if (magic[i] != psbt[i]) {
                ISOException.throwIt(ISO7816.SW_WRONG_DATA);
            }
        }
        //Global map
        boolean global = true;
        short currentArrayIndex = 4;
        short parsing = globalMap;
        short currentInputs = 0;
        short currentOutputs = 0;
        //cycle until end of data in APDU
        short transaction_index = 0;
        //As the part to send PSBT to card is not implemented, we can not use offset here, only size of the hardcoded data
        while (currentArrayIndex < (short) (psbt.length - 1)) {
            short keylen = GetLength(currentArrayIndex);
            currentArrayIndex += offsetChange;


            //map separator check
            if (keylen != 0) {
                //DECIDE
                short todo = 4;

                //New should be called in constructor only, but in this case, we do not know the sizes
                //ahead of time
                //Byte arrays for storing processed data, messy but OOP design is not recommended in slides
                byte[] key = new byte[keylen];
                Util.arrayCopyNonAtomic(psbt, (short) (currentArrayIndex + 1), key, (short) 0, keylen);
                currentArrayIndex += keylen;


                KeyType = key[0];

                if (keylen > 1) {
                    byte[] keyData = new byte[(short) (keylen - 1)];
                    Util.arrayCopyNonAtomic(psbt, currentArrayIndex, keyData, (short) 0, (short) (keylen - 1));
                    currentArrayIndex += keylen - 1;
                }

                short valuelen = GetLength(currentArrayIndex);
                currentArrayIndex += offsetChange;


                if (parsing == globalMap) {
                    GlobalValueData = new byte[valuelen];

                    //Store the transaction position in buffer
                    transaction_index = (short) (currentArrayIndex);
                    Util.arrayCopyNonAtomic(psbt, (short) (currentArrayIndex + 1), GlobalValueData, (short) 0, valuelen);
                    ValueData = GlobalValueData;
                } else if (parsing == inputMap) {
                    InputValueData = new byte[valuelen];


                    //Store the transaction position in buffer
                    transaction_index = (short) (currentArrayIndex);
                    Util.arrayCopyNonAtomic(psbt, (short) (currentArrayIndex + 1), InputValueData, (short) 0, valuelen);
                    ValueData = InputValueData;
                } else if (parsing == outputMap) {
                    OutputValueData = new byte[valuelen];
                    if (!(KeyType == 0x00 || KeyType == 0x01 || KeyType == 0x02)) {
                        ISOException.throwIt(ISO7816.SW_DATA_INVALID);
                    }
                    //Store the transaction position in buffer
                    transaction_index = (short) (currentArrayIndex);
                    Util.arrayCopyNonAtomic(psbt, (short) (currentArrayIndex + 1), OutputValueData, (short) 0, valuelen);
                    ValueData = OutputValueData;
                }


                currentArrayIndex += valuelen;

            }

            if (keylen == 0) {
                if (parsing == globalMap) {
                    parsing = inputMap;
                } else if (parsing == inputMap) {
                    currentInputs++;
                    if (currentInputs == inputs) parsing = outputMap;
                } else if (parsing == outputMap) {
                    currentOutputs++;
                    //All maps have already been parsed
                    if (currentOutputs == outputs) parsing = 4;
                } else {
                    //Parsing done
                    break;
                }
            } else if (parsing == globalMap) {
                if (KeyType == PSBT_GLOBAL_UNSIGNED_TX) {
                    ParseTransaction(transaction_index);


                }

            }


        }

        //Everything OK
        byte[] response = JCSystem.makeTransientByteArray((short) 1, JCSystem.CLEAR_ON_DESELECT);
        ;
        if (parsing == 4) {
            byte[] apdubuf = apdu.getBuffer();
            response[0] = (byte) 0x9000;
            Util.arrayCopyNonAtomic(response, (short) 0, apdubuf, ISO7816.OFFSET_CDATA, (short) 1);
            apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, (short) 5);
        }
        //Unexpected case
        else {
            byte[] apdubuf = apdu.getBuffer();
            response[0] = 0x01;
            Util.arrayCopyNonAtomic(response, (short) 0, apdubuf, ISO7816.OFFSET_CDATA, (short) 1);
            apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA, (short) 5);
        }

    }



    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {

    }
}
