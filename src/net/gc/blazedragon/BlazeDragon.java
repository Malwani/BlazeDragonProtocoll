package net.gc.blazedragon;

import org.apache.commons.lang3.ArrayUtils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
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
    private static ArrayList<Class> packageClasses;
    private static ArrayList<List<Byte>> packageDataInitValues;

    //******************************************************************************************************************//
    //                                            BLAZEDATA-INDICATORS                                                  //
    //******************************************************************************************************************//

    public static final byte PACKAGE_END     = -128;
    public static final byte DATA_END        = -127;
    public static final byte PACKAGE_ID_DATA = -126;
    public static final byte BOOLEAN_DATA    = -1 ;
    public static final byte INTEGER_DATA    = -2 ;
    public static final byte DOUBLE_DATA     = -3 ;
    public static final byte STRING_DATA     = -4 ;

    //******************************************************************************************************************//
    //                                               KONSTRUKTOR                                                        //
    //******************************************************************************************************************//

    public BlazeDragon()
    {
        packageDataInitValues = new ArrayList<List<Byte>>();
        packageClasses        = new ArrayList<Class>();
    }

    //******************************************************************************************************************//
    //                                        PACKAGE-INIT-METHODEN                                                     //
    //******************************************************************************************************************//

    protected static byte[] getPackageInitBytes(Short id)                                                               // Für Package Init
    {
        List<Byte> list   = packageDataInitValues.get(id);
        byte[] array = new byte[list.size()];

        for(int i = 0; i < list.size(); i++)
        {
            array[i] = list.get(i);
        }

        return array;
    }

    protected static Short detectClassID(Class PackageClass)                                                            // Für Package Init
    {
        return (short) packageClasses.indexOf(PackageClass);
    }

    public void addPackage(BlazePackage newPackageClass) throws BlazePackageAlreadyIntegratedException                  // Packagedeklaration
    {
        if( ! packageClasses.contains(newPackageClass.getClass()))
        {
            packageClasses.add(newPackageClass.getClass());
        }
        else
        {
            throw new BlazePackageAlreadyIntegratedException();
        }
    }

    public static void addPackageInitVals(short id, Byte[] initBytes) throws BlazeIDAlreadyExistsException              // Für Deklarations-Konstruktor eines BlazePackages
    {
        if( (packageDataInitValues.size() - 1) < id)                                                                    // ID -> Index[-1 zu Size]
        {
            List initList = new ArrayList<Byte>();

            initList.add(BlazeDragon.PACKAGE_ID_DATA);                                                                  // ID-typ hinzufügen
            initList.addAll(Arrays.asList(initBytes));                                                                  // InitList zusammenfügen
            packageDataInitValues.add(id,initList);                                                                     // InitList hinzufügen
        }
        else
        {
            throw new BlazeIDAlreadyExistsException();
        }
    }

    public static short detectNextPackageID()                                                                           // Nächste zu deklarierende Package ID ermitteln
    {
        if(packageDataInitValues.isEmpty())                                                                             // Wenn keine Größe vorhanden -> Next Index 0
            return (short) 0;
        else
            return (short) (packageDataInitValues.size());                                                              // Wenn Größe vorhanden size = index
    }

    //******************************************************************************************************************//
    //                                             SERIALISIERUNG                                                       //
    //******************************************************************************************************************//

    public static BlazeData[] getBlazeDatas(byte[] inputBytes) throws UnfittingBlazeDataException
    {
        short  packId;
        byte[] initBytes;

        BlazeData[]  blazeDatas;
        ByteBuffer   inputBytesBuffer = ByteBuffer.wrap(inputBytes);

        if(inputBytesBuffer.get() != PACKAGE_ID_DATA)                                                                   // Unbekannte/Fehlerhafte PackageID
        {
            throw new UnfittingBlazeDataException();
        }
        else
        {
            packId        = inputBytesBuffer.getShort();                                                                // PackageID auslesen
            initBytes     = BlazeDragon.getPackageInitBytes(packId);                                                    // passende BlazeData InitValues ermitteln
            blazeDatas    = new BlazeData[initBytes.length];                                                            // BlazeData-Container
            blazeDatas[0] = new BlazeData(packId);
        }

        for(int i = 1; i < initBytes.length;i++)
        {
            byte dataInit = initBytes[i];

            if(dataInit == inputBytesBuffer.get())                                                                      // Prüfen ob DataInit-Byte der Vorliegenden Definition (packageDataInitValues) entspricht
            {
                switch(dataInit)
                {
                    case BOOLEAN_DATA:
                    {
                        blazeDatas[i] = new BlazeData(inputBytesBuffer.get() != 0);                                     // Next-Byte aus dem Buffer auslesen, in bool konvertieren und dem neuen Signal übergeben

                        break;
                    }
                    case INTEGER_DATA:
                    {
                        blazeDatas[i] = new BlazeData(inputBytesBuffer.getInt());
                        break;
                    }
                    case DOUBLE_DATA:
                    {
                        blazeDatas[i] = new BlazeData(inputBytesBuffer.getDouble());                                    // Double aus dem Buffer auslesen und dem neuen Signal übergeben

                        break;
                    }
                    case STRING_DATA:
                    {
                        List<Byte> strBytes = new ArrayList<Byte>();                                                       // Bytearray für Bytes des Strings erstellen
                        ByteArrayOutputStream bout = new ByteArrayOutputStream(strBytes.size());

                        while(true)
                        {
                            byte currByte = inputBytesBuffer.get();

                            if(currByte != BlazeDragon.DATA_END)
                                strBytes.add(currByte);
                            else
                                break;
                        }

                        for(Byte curr : strBytes)                                                                       // Bout füllen
                            bout.write(curr);

                        try
                        {
                            blazeDatas[i] = new BlazeData(bout.toString("UTF-8"));
                        }
                        catch(UnsupportedEncodingException ex)
                        {
                            ex.printStackTrace();
                        }

                        break;
                    }
                    case PACKAGE_ID_DATA:
                    {
                        blazeDatas[i] = new BlazeData(inputBytesBuffer.getShort());                                     // PackInit-Short aus dem Buffer auslesen und dem neuen Signal übergeben

                        break;
                    }
                    default:                                                                                            // Falsches Signal empfangen
                    {
                        throw new UnfittingBlazeDataException();                                                        // Fehler
                    }
                }
            }
            else
                throw new UnfittingBlazeDataException();                                                                // Initialisierung Fehlerhaft
        }

        return blazeDatas;
    }

    public static byte[] getBytes(BlazeData[] bdArray) throws UnfittingBlazeDataException
    {
        List<Byte> byteData = new ArrayList<Byte>();                                                                       // ArrayList für ByteArrays erstellen
        ByteBuffer bbuff;

        if(bdArray[0].getType() != PACKAGE_ID_DATA )                                                                    // wenn Falsches Signal
        {
            throw new UnfittingBlazeDataException();
        }
        else
        {
            for(int i = 0; i < bdArray.length;i++)
            {
                short  type = bdArray[i].getType();

                switch(type)
                {
                    case BlazeDragon.BOOLEAN_DATA:
                    {
                        byteData.add(bdArray[i].getType());

                        if (bdArray[i].getDataBoolean())
                        {
                            Byte b = 1;
                            byteData.add(b);
                        }
                        else
                        {
                            Byte b = 0;
                            byteData.add(b);
                        }
                        break;
                    }
                    case INTEGER_DATA:
                    {
                        byteData.add(bdArray[i].getType());
                        bbuff = ByteBuffer.allocate(4);
                        bbuff.putInt(bdArray[i].getDataInt());

                        for (int a = 0; a < 4; a++)
                        {
                            byteData.add(bbuff.get(a));                                                                 // Byte für Byte aus dem Buffer lesen und im Array speichern
                        }
                        break;
                    }
                    case BlazeDragon.DOUBLE_DATA:
                    {
                        byteData.add(bdArray[i].getType());
                        bbuff = ByteBuffer.allocate(8);                                                                 // Bytebuffer erstellen, um Double in Bytes umzuwandeln
                        bbuff.putDouble(bdArray[i].getDataDouble());

                        for (int a = 0; a < 8; a++)
                        {
                            byteData.add(bbuff.get(a));                                                                 // Byte für Byte aus dem Buffer lesen und im Array speichern
                        }
                        break;
                    }
                    case BlazeDragon.STRING_DATA:
                    {
                        bbuff = ByteBuffer.allocate(1 + bdArray[i].getDataStr().length());                              // Bytebuffer mit doppelter Byteanzahl für chars, da UNICODE 2 Byte pro char verwendet & +1 für Type
                        bbuff.rewind();
                        bbuff.put(bdArray[i].getType());                                                                // Type im buffer speichern

                        try
                        {
                            bbuff.put(bdArray[i].getDataStr().getBytes("UTF-8"));                                       // String in Bytearray umwandeln und im Buffer speichern
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            throw new UnfittingBlazeDataException();
                        }

                        byte[] tmpBytes = new byte[bbuff.limit()];

                        bbuff.flip();                                                                                   // Buffer zum Schreiben "zurücksetzen"
                        bbuff.get(tmpBytes);

                        for(int a = 0;a < bbuff.limit();a++)
                        {
                            byteData.add(tmpBytes[a]);
                        }

                        byteData.add(BlazeDragon.DATA_END);                                                             // DATA_END-Byte zufügen da Größe beliebig
                        break;
                    }
                    case BlazeDragon.PACKAGE_ID_DATA:
                    {
                        byteData.add(bdArray[i].getType());
                        bbuff = ByteBuffer.allocate(2);                                                                 // Bytebuffer erstellen, um Short in Bytes umzuwandeln
                        bbuff.putShort(bdArray[i].getBlazePackIdentifier());

                        byteData.add(bbuff.get(0));                                                                     // Erstes "Short Byte" ByteData anfügen (byteData[0] mit Typ belegt)
                        byteData.add(bbuff.get(1));
                        break;
                    }
                    default:
                    {
                        throw new UnfittingBlazeDataException();
                    }
                }
            }
        }

        byteData.add(BlazeDragon.PACKAGE_END);                                                                          // End-Byte dem ByteArray zufügen
        return ArrayUtils.toPrimitive(byteData.toArray(new Byte[byteData.size()]));
    }
}