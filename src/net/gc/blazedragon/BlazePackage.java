package net.gc.blazedragon;

/**
 * Created by Malwani on 28.02.2015.
 */

    //******************************************************************************************************************//
    //                                                                                                                  //
    //                                       PACKAGE-CLASS FOR THE DRAGON                                               //
    //                                                                                                                  //
    //******************************************************************************************************************//

public abstract class BlazePackage implements BlazeModule
{
    private BlazeData[] blazeDatas = null;

    //******************************************************************************************************************//
    //                                              KONSTRUKTOREN                                                       //
    //******************************************************************************************************************//

    public BlazePackage(boolean init)                                                                                   // Konstruktor zur Package-Initialisierung
    {                                                                                                                   // Parameter -> true = mit Init | false = ohne Init(zur befüllung aus Bytes)
        if(init)
        {
            int size = BlazeDragon.getPackageInitBytes(BlazeDragon.detectClassID(this.getClass())).length;

            this.blazeDatas = new BlazeData[size];
            initBlazeData();
        }
    }

    public BlazePackage(short id)                                                                                       // Konstruktor zur Package Deklaration
    {
        try
        {
            BlazeDragon.addPackageInitVals(id, defineBlazeData());
        }
        catch(BlazeIDAlreadyExistsException ex)
        {
            ex.printStackTrace();
        }
    }

    //******************************************************************************************************************//
    //                                              METHODEN                                                            //
    //******************************************************************************************************************//

    private void initBlazeData()
    {
        Short id = BlazeDragon.detectClassID(this.getClass());                                                          // Anhand von PackageClasses-List passende IndexValue der InitValsList ermitteln
        byte[] initBytes = BlazeDragon.getPackageInitBytes(id);                                                         // Anhand ID InitValues-List einholen         // Init Value registrieren

        for(int i = 0; i < initBytes.length;i++)
        {
            byte typeVal = initBytes[i];

            switch(typeVal)
            {
                case BlazeDragon.BOOLEAN_DATA:
                {
                    this.blazeDatas[i] = new BlazeData(false);
                    break;
                }
                case BlazeDragon.DOUBLE_DATA:
                {
                    this.blazeDatas[i] = new BlazeData((double) 0);
                    break;
                }
                case BlazeDragon.STRING_DATA:
                {
                    this.blazeDatas[i] = new BlazeData("");
                    break;
                }
                case BlazeDragon.PACKAGE_ID_DATA:
                {
                    this.blazeDatas[i] = (new BlazeData(id));                                                             // Package ID ist festgelegt
                    break;
                }
            }
        }
    }
    
    protected void setData(int index, byte dataType, Object data) throws UnfittingBlazeDataException                    // F�r Setter des Endo-Packages dataType über Blazedragon
    {
        switch(dataType)
        {
            case BlazeDragon.BOOLEAN_DATA:
            {
                this.blazeDatas[index].setBoolean((Boolean) data);
                break;
            }
            case BlazeDragon.DOUBLE_DATA:
            {
                this.blazeDatas[index].setDouble((Double) data);
                break;
            }
            case BlazeDragon.STRING_DATA:
            {
                this.blazeDatas[index].setStr((String) data);
                break;
            }
            case BlazeDragon.PACKAGE_ID_DATA:
            {
                this.blazeDatas[index].setBlazePackIdentifier((Short) data);
                break;
            }
        }
    }


    protected BlazeData getBlazeData(int index)                                                                         // Für Getter des Blaze-Packages
    {
        return blazeDatas[index];
    }

    public BlazeData[] getBlazeDatas()                                                                                  // Für Serialisierung
    {
        return blazeDatas;
    }

    public void setBlazeDatas (BlazeData[] bdArray)
    {
        this.blazeDatas = bdArray;
    }
}