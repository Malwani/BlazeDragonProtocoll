package net.gc.blazedragon;

import java.util.ArrayList;
import java.util.List;

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
    private List<BlazeData> blazeDatas;
    private List<Byte>      blazeInitDataValues;

    //******************************************************************************************************************//
    //                                              KONSTRUKTOREN                                                       //
    //******************************************************************************************************************//

    public BlazePackage()                                                                                               // Konstruktor zur Package-Initialisierung
    {
        this.blazeDatas = new ArrayList<BlazeData>();

        initBlazeData();
    }

    public BlazePackage(short ID)                                                                                       // Konstruktor zur Package Deklaration
    {
        blazeInitDataValues = new ArrayList<Byte>();

        addBlazeDataType(BlazeDragon.PACKAGE_ID_DATA);
        defineBlazeData();

        try
        {
            BlazeDragon.addPackageInitVals(ID, blazeInitDataValues);
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
        short id = BlazeDragon.detectClassID(this.getClass());                                                          // Anhand von PackageClasses-List passende IndexValue der InitValsList ermitteln

        this.blazeInitDataValues = BlazeDragon.getPackageInitVals(id);                                                  // Anhand ID InitValues-List einholen         // Init Value registrieren

        for(int i = 0; i < this.blazeInitDataValues.size();i++)
        {
            byte typeVal = this.blazeInitDataValues.get(i);

            switch(typeVal)
            {
                case BlazeDragon.BOOLEAN_DATA:
                {
                    this.blazeDatas.add(new BlazeData(false));
                    break;
                }
                case BlazeDragon.DOUBLE_DATA:
                {
                    this.blazeDatas.add(new BlazeData((double) 0));
                    break;
                }
                case BlazeDragon.STRING_DATA:
                {
                    this.blazeDatas.add(new BlazeData(""));
                    break;
                }
                case BlazeDragon.PACKAGE_ID_DATA:
                {
                    this.blazeDatas.add(new BlazeData(id));                                                             // Package ID ist festgelegt
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
                this.blazeDatas.get(index).setBoolean((Boolean) data);
                break;
            }
            case BlazeDragon.DOUBLE_DATA:
            {
                this.blazeDatas.get(index).setDouble((Double) data);
                break;
            }
            case BlazeDragon.STRING_DATA:
            {
                this.blazeDatas.get(index).setStr((String) data);
                break;
            }
            case BlazeDragon.PACKAGE_ID_DATA:
            {
                this.blazeDatas.get(index).setBlazePackIdentifier((Short) data);
                break;
            }
        }
    }

    protected void addBlazeDataType(byte signalType)                                                                    // Verwendung in defineSignals() Methode des BlazeModuls
    {
        this.blazeInitDataValues.add(signalType);
    }

    protected BlazeData getBlazeData(int index)                                                                              // Für Getter des Blaze-Packages
    {
        return blazeDatas.get(index);
    }

    public BlazeData[] getBlazeDatas()                                                                                  // Für Serialisierung
    {
        BlazeData[] outArray = new BlazeData[this.blazeDatas.size()];
        outArray = this.blazeDatas.toArray(outArray);

        return outArray;
    }
}