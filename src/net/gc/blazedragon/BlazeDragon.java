package net.gc.blazedragon;

import gnu.trove.list.array.TByteArrayList;
import org.apache.commons.lang3.ArrayUtils;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Malwani on 28.02.2015.
 */

    //******************************************************************************************************************//
    //                                                                                                                  //
    //                                           BLAZEDRAGON-PROTOCOLL                                                  //
    //                                                                                                                  //
    //******************************************************************************************************************//

public class BlazeDragon
{
    public static final byte  WRONG_DATA      = -1 ;
    public static List<Class> blazePackages;

    //******************************************************************************************************************//
    //                                       BLAZESIGNAL-DATATYPE INDICATORS                                            //
    //******************************************************************************************************************//

    public static final byte BOOLEAN_DATA   = -2 ;
    public static final byte DOUBLE_DATA    = -3 ;
    public static final byte STRING_DATA    = -4 ;
    public static final byte PACK_INIT_DATA = -50;

    //******************************************************************************************************************//
    //                                         BYTE-ARRAY-BINDER-VALUES                                                 //
    //******************************************************************************************************************//

    private static final byte PACKAGE_SEPERATOR = -100;
    private static final byte PACKAGE_END       = -128;

    //******************************************************************************************************************//
    //                                         SOME METHODES                                                            //
    //******************************************************************************************************************//

    public BlazeDragon()
    {
        blazePackages = new ArrayList();
    }

    public void addPackage(Object newPackageClass)
    {
        blazePackages.add(newPackageClass.getClass());
    }

    //******************************************************************************************************************//
    //                                     DE/SERIALISIERUNG SCHALE 3                                                   //
    //******************************************************************************************************************//

    //******************************************************************************************************************//
    //                                     DE/SERIALISIERUNG SCHALE 2                                                   //
    //******************************************************************************************************************//

    public static BlazeSignal[] byte_array2bs_array(byte[] inputArray)
    {
        ArrayList<BlazeSignal> blazeSignals    = new ArrayList<BlazeSignal>(0);
        TByteArrayList         tmpBytes        = new TByteArrayList(0);
        ByteBuffer             inputByteBuffer = ByteBuffer.wrap(inputArray);
        BlazeSignal[]          output;

        if(inputByteBuffer.get() != PACK_INIT_DATA)
        {
            output = new BlazeSignal[1];
            output[0] = new BlazeSignal();                                                                              // Falsches Signal
            return output;
        }
        else
        {
            inputByteBuffer.rewind();

            while(true)                                                                                                 // Bis Package-End erreicht
            {
                byte tmp = inputByteBuffer.get();                                                                       // Nächstes Byte aus Buffer holen

                if(tmp != PACKAGE_SEPERATOR && tmp != PACKAGE_END)                                                      // Wenn Byte kein Package-Seperator[Binder] oder Package-End[Binder]
                {
                    tmpBytes.add(tmp);                                                                                  // Byte der Bytelist adden
                }
                else if(tmp == PACKAGE_SEPERATOR)                                                                       // Wenn Byte ein Package-Seperator[Binder]
                {
                    blazeSignals.add(ba_2_bs(tmpBytes.toArray()));                                                      // tmp_bytes zu BlazeSignal konvertieren und der BlazeSignal-List adden
                    tmpBytes.clear();                                                                                   // tmp_bytes leeren
                    tmpBytes.trimToSize();                                                                              // und Kapazität auf Anzahl der Elemente (0) stellen
                }
                else                                                                                                    // Wenn Byte ein Package-End[Binder]
                {
                    blazeSignals.add(ba_2_bs(tmpBytes.toArray()));                                                      // tmp_bytes zu BlazeSignal konvertieren und der BlazeSignal-List adden
                    break;                                                                                              // Schleife verlassen
                }
            }

            output = new BlazeSignal[blazeSignals.size()];                                                              // OutputArray mit entsprechender Größe erstellen [BlazeSignal]

            for(int i = 0; i < blazeSignals.size(); ++i)                                                                // ArrayList "BlazeSignals" durchgehen und jedes BS-Objekt dem Output-Array zufügen
            {
                output[i] = blazeSignals.get(i);
            }

            return output;
        }
    }

    public static byte[] bs_array2byte_array(BlazeSignal[] bsArray) throws UnfittingBlazeDataException
    {
        ArrayList<byte[]> byteList = new ArrayList<byte[]>(0);                                                          // ArrayList für ByteArrays erstellen
        byte[]            outArray;

        if(bsArray[0].getType() != PACK_INIT_DATA )                                                                     // Falsches Signal
        {
            outArray = new byte[1];
            outArray[0] = WRONG_DATA;

            return outArray;
        }
        else
        {
            int byteListEnd;

            outArray = new byte[0];
            byteList.add(bs_2_ba(bsArray[0]));                                                                          // Inititalisierungwert der ArrayList zufügen

            for(int i = 1; i < bsArray.length; ++i)                                                                     // Solange bis BS-Array durchlaufen ist [Beginn bei Array[1] da [0] schon mit Init gefüllt]
            {
                byteList.ensureCapacity(i + 1);                                                                         // Kapazität um ein Arrayplatz erhöhen
                byteList.add(bs_2_ba(bsArray[i]));                                                                      // Nächstes BlazeSignal-Objekt als ByteArray in ArrayList speichern
            }

            byteListEnd = byteList.size() - 1;                                                                          // byte_list.size() - 1 -> Indexnummer des letzten ByteArrays der ArrayList

            for(int i = 0; i < byteList.size(); ++i)                                                                    // byte_list Index-Durchlauf
            {
                outArray = ArrayUtils.addAll(outArray, byteList.get(i));                                                // Nächstes Array der ArrayList dem outputArray zufügen

                if(i < byteListEnd)                                                                                     // Wenn kein Package Ende, Package-Seperator[Binder] dem outputArray einfügen
                {
                    outArray = ArrayUtils.addAll(outArray, PACKAGE_SEPERATOR);
                }
                else if(i == byteListEnd)                                                                               // Wenn Package Ende, Package-End[Binder] dem outputArray einfügen
                {
                    outArray = ArrayUtils.addAll(outArray, PACKAGE_END);
                }
            }

            return outArray;
        }
    }

    //******************************************************************************************************************//
    //                                         DE/SERIALISIERUNG SCHALE 1                                               //
    //******************************************************************************************************************//

    public static BlazeSignal ba_2_bs(byte[] byteData)
    {
        ByteBuffer buffer      = ByteBuffer.wrap(byteData);                                                             // Bytearray in Bytebuffer umwandeln
        byte       signalType  = buffer.get();                                                                          // Auslesen des Typs (Erstes Byte))

        switch (signalType)
        {
            case BOOLEAN_DATA:
            {
                return new BlazeSignal(buffer.get() != 0);                                                              // Next-Byte aus dem Buffer auslesen, in bool konvertieren und dem neuen Signal übergeben
            }
            case DOUBLE_DATA:
            {
                return new BlazeSignal(buffer.getDouble());                                                             // Double aus dem Buffer auslesen und dem neuen Signal übergeben
            }
            case STRING_DATA:
            {
                byte[] stringBytes = new byte[byteData.length - 1];                                                     // Bytearray für Bytes des Strings erstellen

                for (int i = 0; i < stringBytes.length; i++)                                                            // Stringbytes in Bytearray kopieren
                {
                    stringBytes[i] = buffer.get();                                                                      // Byte für Byte aus dem Buffer in den Array kopieren
                }

                return new BlazeSignal(Charset.forName("ISO-8859-1").decode(ByteBuffer.wrap(stringBytes)).toString());  // Bytearray in ByteBuffer schreiben und in String konvertieren
            }
            case PACK_INIT_DATA :
            {
                return new BlazeSignal(buffer.getShort());                                                              // PackInit-Short aus dem Buffer auslesen und dem neuen Signal übergeben
            }
            default:                                                                                                    // Falsches Signal empfangen
            {
                System.out.println("Wrong signal received! (type =" + signalType + ")");
                return new BlazeSignal();                                                                               // Leeres Signal wird zurückgegeben
            }
        }
    }

    public static byte[] bs_2_ba(BlazeSignal bsData) throws UnfittingBlazeDataException
    {
        byte[] byteData;                                                                                                // Wird später verschickt
        ByteBuffer buffer;

        switch (bsData.getType())
        {
            case BlazeDragon.BOOLEAN_DATA:
            {
                byteData = new byte[2];                                                                                 // Der Bytearray hat die Länge 2: 1 Byte für Type, 1 Byte für Boolean
                byteData[0] = bsData.getType();

                if (bsData.getDataBoolean())
                {
                    byteData[1] = 1;
                } else
                {
                    byteData[1] = 0;
                }
                break;
            }
            case BlazeDragon.DOUBLE_DATA:
            {
                byteData    = new byte[9];                                                                              // Länge 9: 1 Byte für Type, 8 Byte für Double
                byteData[0] = bsData.getType();
                buffer      = ByteBuffer.allocate(8);                                                                   // Bytebuffer erstellen, um Double in Bytes umzuwandeln
                buffer.putDouble(bsData.getDataDouble());

                for (int i = 0; i < 8; i++)
                {
                    byteData[i + 1] = buffer.get(i);                                                                    // Byte für Byte aus dem Buffer lesen und im Array speichern
                }

                break;
            }
            case BlazeDragon.STRING_DATA:
            {
                buffer = ByteBuffer.allocate(1 + bsData.getDataStr().length());                                         // Bytebuffer mit doppelter Byteanzahl für chars, da UNICODE 2 Byte pro char verwendet & +1 für Type
                buffer.rewind();
                buffer.put(bsData.getType());                                                                           // Type im buffer speichern

                try
                {
                    buffer.put(bsData.getDataStr().getBytes("ISO-8859-1"));                                             // String in Bytearray umwandeln und im Buffer speichern
                }
                catch (UnsupportedEncodingException e)
                {
                    byteData    = new byte[1];
                    byteData[0] = WRONG_DATA;                                                                           // WRONG SIGNAL zurückgeben
                    e.printStackTrace();
                }

                byteData = new byte[buffer.limit()];                                                                    // Bytearray mit der Größe des Buffers erstellen
                buffer.flip();                                                                                          // Buffer zum Schreiben "zurücksetzen"
                buffer.get(byteData);                                                                                   // Buffer in Bytearray schreiben
                break;
            }
            case BlazeDragon.PACK_INIT_DATA:
            {
                byteData    = new byte[3];                                                                              // Länge 3: 1 Byte für Type, 2 Byte für IdentifierShort
                byteData[0] = bsData.getType();
                buffer      = ByteBuffer.allocate(2);                                                                   // Bytebuffer erstellen, um Short in Bytes umzuwandeln
                buffer.putShort(bsData.getBlazePackIdentifier());

                byteData[1] = buffer.get(0);                                                                            // Erstes "Short Byte" ByteData anfügen (byteData[0] mit Typ belegt)
                byteData[2] = buffer.get(1);                                                                            // Zweites...

                break;
            }
            default:                                                                                                    // Nicht erkanntes Signal
            {
                byteData = new byte[1];
                byteData[0] = WRONG_DATA;                                                                               // Array mit Fehlercode -1 zurückgeben
                break;
            }
        }

        return byteData;
    }
}

    //******************************************************************************************************************//
    //                                              SENDEN / EMPFANGEN                                                  //
    //******************************************************************************************************************//

   /* public static GCP[] receiveGCPData() throws IOException
    {
        int readableBytes;
        byte[] data;

        if(this.inStr == null)
        {
            this.inStr  = this.socket.getInputStream();
        }

        try
        {
            while (true)
            {                                                                                                           // Prüfen ob ein Signal verfügbar ist
                readableBytes = inStr.available();

                if (readableBytes >= 1)
                {                                                                                                       // Wenn Signal, dann While-Schleife verlassen
                    break;
                }

                Thread.sleep(5);                                                                                        // sonst, warten, und nochmal
            }

            data = new byte[readableBytes];
            inStr.read(data);
        }
        catch (InterruptedException e)
        {
            data = new byte[1];                                                                                         // Thread.sleep() hat nicht funktioniert.
            data[0] = GCP.WrongGCP;                                                                                     // -1 im Bytearray für Fehlermeldung
        }
        catch (IOException e)
        {
            data = new byte[1];                                                                                         // Wird gethrowt, wenn ein Client disconnected
            data[0] = GCP.WrongGCP;

            System.out.println("Connection lost! (" + e.toString() + ")");
        }

        this.inStr.close();
        this.inStr = null;

        return GCP.byte_array2gcp_array(data);
    }

    public static void sendGCPData(GCP[] gcpData)
    {
        try
        {
            outStr.write(GCP.gcp_array2byte_array(gcpData));
            outStr.flush();
            outStr.close();

            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
*/