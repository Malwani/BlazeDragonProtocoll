package net.gc.blazedragon;

/**
 * Created by Malwani on 28.02.2015.
 */

    //******************************************************************************************************************//
    //                                                                                                                  //
    //                                        DATA-CLASS FOR THE DRAGON                                                 //
    //                                                                                                                  //
    //******************************************************************************************************************//

public class BlazeSignal
{
    private byte    dataType;

    private short   blazePackIdentifier;
    private boolean boolData;
    private double  doubleData;
    private String  strData;

    public BlazeSignal()
    {                                                                                                                   // Wird aufgerufen wenn das Signal falsch ist.
        this.dataType = BlazeDragon.WRONG_DATA;
    }

    public BlazeSignal(short blazePackIdentifier)
    {
        this.blazePackIdentifier = blazePackIdentifier;
        this.dataType = BlazeDragon.PACK_INIT_DATA ;
    }

    public BlazeSignal(boolean boolData)
    {
        this.boolData = boolData;
        this.dataType = BlazeDragon.BOOLEAN_DATA;
    }

    public BlazeSignal(double doubleData)
    {
        this.doubleData = doubleData;
        this.dataType = BlazeDragon.DOUBLE_DATA;
    }

    public BlazeSignal(String strData)
    {
        this.strData = strData.trim();                                                                                  // Whitespace aus str_signal entfernen und BlazeDragon-Objekt zuteilen
        this.dataType = BlazeDragon.STRING_DATA;
    }

    //******************************************************************************************************************//
    //                                              GETTER                                                              //
    //******************************************************************************************************************//

    public byte getType()
    {
        return this.dataType;
    }

    public short getBlazePackIdentifier() throws UnfittingBlazeDataException
    {
        if (this.dataType == BlazeDragon.PACK_INIT_DATA )
        {
            return this.blazePackIdentifier;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public boolean getDataBoolean() throws UnfittingBlazeDataException
    {
        if (this.dataType == BlazeDragon.BOOLEAN_DATA)
        {
            return this.boolData;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public String getDataStr() throws UnfittingBlazeDataException
    {
        if(this.dataType == BlazeDragon.STRING_DATA)
        {
            return this.strData;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public double getDataDouble() throws UnfittingBlazeDataException
    {
        if(this.dataType == BlazeDragon.DOUBLE_DATA)
        {
            return this.doubleData;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    //******************************************************************************************************************//
    //                                              SETTER                                                              //
    //******************************************************************************************************************//

    public void setBlazePackIdentifier(short packInit) throws UnfittingBlazeDataException
    {
        if(this.dataType == BlazeDragon.PACK_INIT_DATA )
        {
            this.blazePackIdentifier = packInit;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public void setBoolean(boolean boolData) throws UnfittingBlazeDataException
    {
        if(this.dataType == BlazeDragon.BOOLEAN_DATA)
        {
            this.boolData = boolData;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public void setStr(String strData) throws UnfittingBlazeDataException
    {
        if(this.dataType == BlazeDragon.STRING_DATA)
        {
            this.strData = strData;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public void setDouble(double doubleData) throws UnfittingBlazeDataException
    {
        if(this.dataType == BlazeDragon.DOUBLE_DATA)
        {
            this.doubleData = doubleData;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }
}
