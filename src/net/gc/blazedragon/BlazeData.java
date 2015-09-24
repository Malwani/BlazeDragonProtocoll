package net.gc.blazedragon;

    //******************************************************************************************************************//
    //                                                                                                                  //
    //                                        DATA-CLASS FOR THE DRAGON                                                 //
    //                                                                                                                  //
    //******************************************************************************************************************//
 public class BlazeData
{
    private byte dataType;                                                                                              // Beinhaltet Blazedata-Type Indikator [Blazedragon]

    //******************************************************************************************************************//
    //                                              DATA-CONTAINER                                                      //
    //******************************************************************************************************************//

    private short   blazePackId;
    private boolean boolData;
    private double  doubleData;
    private String  strData;
    private int     intData;

    //******************************************************************************************************************//
    //                                              KONSTRUKTOREN                                                       //
    //******************************************************************************************************************//

    public BlazeData(short blazePackId)
    {
        this.blazePackId = blazePackId;
        this.dataType = BlazeDragon.PACKAGE_ID_DATA ;
    }

    public BlazeData(boolean boolData)
    {
        this.boolData = boolData;
        this.dataType = BlazeDragon.BOOLEAN_DATA;
    }

    public BlazeData(int intData)
    {
        this.intData = intData;
        this.dataType = BlazeDragon.INTEGER_DATA;
    }

    public BlazeData(double doubleData)
    {
        this.doubleData = doubleData;
        this.dataType = BlazeDragon.DOUBLE_DATA;
    }

    public BlazeData(String strData)
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
        if (this.dataType == BlazeDragon.PACKAGE_ID_DATA )
        {
            return this.blazePackId;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public int getDataInt() throws UnfittingBlazeDataException
    {
        if (this.dataType == BlazeDragon.INTEGER_DATA)
        {
            return this.intData;
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
        if(this.dataType == BlazeDragon.PACKAGE_ID_DATA )
        {
            this.blazePackId = packInit;
        }
        else
        {
            throw new UnfittingBlazeDataException();
        }
    }

    public void setInt(int intData) throws UnfittingBlazeDataException
    {
        if(this.dataType == BlazeDragon.INTEGER_DATA)
        {
            this.intData = intData;
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