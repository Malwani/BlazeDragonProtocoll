package net.gc.blazedragon;

import org.apache.commons.lang3.ArrayUtils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.*;

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

            initList.add(PACKAGE_ID_DATA);                                                                              // ID-typ hinzufügen
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
        return (short) (packageDataInitValues.size());                                                                  // Wenn Größe vorhanden size = index
    }

    //******************************************************************************************************************//
    //                                          DE-SERIALISIERUNG                                                       //
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
                        try
                        {
                            blazeDatas[i] = deser2Str(inputBytesBuffer);
                        }catch(UnfittingBlazeDataException ex)
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

    //******************************************************************************************************************//
    //                                         DE-SERIALISIERER-METHODEN                                                //
    //******************************************************************************************************************//

    private static BlazeData deser2Str(ByteBuffer inputBytesBuf) throws UnfittingBlazeDataException                     // Bytefolge -> String
    {
        List<Byte> strBytes = new ArrayList<Byte>();                                                                        // ByteList für Bytes des Strings erstellen
        ByteArrayOutputStream bout = new ByteArrayOutputStream(strBytes.size());

        while(true)
        {
            byte currByte = inputBytesBuf.get();

            if(currByte != DATA_END)
                strBytes.add(currByte);
            else
                break;
        }

        for(Byte curr : strBytes)                                                                                       // Bout füllen
            bout.write(curr);

        try
        {
            return new BlazeData(bout.toString("UTF-8"));
        }
        catch(UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            throw new UnfittingBlazeDataException();
        }
    }

    //******************************************************************************************************************//
    //                                              SERIALISIERUNG                                                      //
    //******************************************************************************************************************//

    public static byte[] getBytes(BlazeData[] bdArray) throws UnfittingBlazeDataException
    {
        List<Byte> byteData = new ArrayList<Byte>();                                                                       // ArrayList für ByteArrays erstellen

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
                    case BOOLEAN_DATA:
                    {
                        serBool(bdArray[i], byteData);
                        break;
                    }
                    case INTEGER_DATA:
                    {
                        serInt(bdArray[i], byteData);
                        break;
                    }
                    case DOUBLE_DATA:
                    {
                        serDouble(bdArray[i], byteData);
                        break;
                    }
                    case STRING_DATA:
                    {
                        serStr(bdArray[i], byteData);
                        break;
                    }
                    case PACKAGE_ID_DATA:
                    {
                        serShortID(bdArray[i], byteData);
                        break;
                    }
                    default:
                    {
                        throw new UnfittingBlazeDataException();
                    }
                }
            }
        }

        byteData.add(PACKAGE_END);                                                                                      // End-Byte dem ByteArray zufügen
        return ArrayUtils.toPrimitive(byteData.toArray(new Byte[byteData.size()]));
    }

    //******************************************************************************************************************//
    //                                            SERIALISIERER-METHODEN                                                //
    //******************************************************************************************************************//

    private static void serShortID (BlazeData bdInput, List<Byte> outBytes)
    {
        ByteBuffer bbuff = ByteBuffer.allocate(2);                                                                      // Bytebuffer erstellen, um Short in Bytes umzuwandeln

        outBytes.add(bdInput.getType());

        try
        {
            bbuff.putShort(bdInput.getBlazePackIdentifier());
        }catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

        outBytes.add(bbuff.get(0));                                                                                     // Erstes "Short Byte" ByteData anfügen (byteData[0] mit Typ belegt)
        outBytes.add(bbuff.get(1));
    }

    private static void serBool (BlazeData bdInput, List<Byte> outBytes)
    {
        outBytes.add(bdInput.getType());

        try
        {
            if (bdInput.getDataBoolean())
            {
                Byte b = 1;
                outBytes.add(b);
            }
            else
            {
                Byte b = 0;
                outBytes.add(b);
            }
        }catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void serInt(BlazeData bdInput, List<Byte> outBytes)
    {
        ByteBuffer bbuff = ByteBuffer.allocate(4);
        outBytes.add(bdInput.getType());

        try
        {
            bbuff.putInt(bdInput.getDataInt());
        }catch (UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

        for (int a = 0; a < 4; a++)
        {
            outBytes.add(bbuff.get(a));                                                                                 // Byte für Byte aus dem Buffer lesen und im Array speichern
        }
    }

    private static void serDouble(BlazeData bdInput, List<Byte> outBytes)
    {
        ByteBuffer bbuff = ByteBuffer.allocate(8);                                                                      // Bytebuffer erstellen, um Double in Bytes umzuwandeln
        outBytes.add(bdInput.getType());

        try
        {
            bbuff.putDouble(bdInput.getDataDouble());
        }catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

        for (int a = 0; a < 8; a++)
        {
            outBytes.add(bbuff.get(a));                                                                                 // Byte für Byte aus dem Buffer lesen und im Array speichern
        }
    }

    private static void serStr(BlazeData bdInput, List<Byte> outBytes)
    {
        int length = 0;
        ByteBuffer bbuff;

        try
        {
            length = bdInput.getDataStr().length() + 1;
        }catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

        bbuff =  ByteBuffer.allocate(length);                                                                           // Bytebuffer mit doppelter Byteanzahl für chars, da UNICODE 2 Byte pro char verwendet & +1 für Type
        bbuff.rewind();
        bbuff.put(bdInput.getType());                                                                                   // Type im buffer speichern

        try
        {
            bbuff.put(bdInput.getDataStr().getBytes("UTF-8"));                                                          // String in Bytearray umwandeln und im Buffer speichern
        }
        catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
        catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

        byte[] tmpBytes = new byte[bbuff.limit()];

        bbuff.flip();                                                                                                   // Buffer zum Schreiben "zurücksetzen"
        bbuff.get(tmpBytes);

        for(int a = 0;a < bbuff.limit();a++)
        {
            outBytes.add(tmpBytes[a]);
        }

        outBytes.add(DATA_END);                                                                                         // DATA_END-Byte zufügen da Größe beliebig
    }
}