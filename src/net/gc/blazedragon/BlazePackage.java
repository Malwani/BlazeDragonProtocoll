package net.gc.blazedragon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malwani on 28.02.2015.
 */

public abstract class BlazePackage implements BlazeModule
{
    private   List<BlazeData> blazeDatas;
    protected List<Byte>      blazeInitDataValues;

    public BlazePackage(short ID)                                                                                       // Konstruktor zur Package Deklaration
    {
        blazeInitDataValues = new ArrayList<Byte>();

        addBlazeDataType(BlazeDragon.PACKAGE_ID_DATA);
        defineBlazeData();
        try
        {
            BlazeDragon.addDataInitVals(ID, blazeInitDataValues);
        }
        catch(BlazeIDAlreadyExistsException ex)
        {
            ex.printStackTrace();
        }

    }

    public BlazePackage()                                                                                               // Konstruktor zur Package-Initialisierung
    {
        this.blazeDatas          = new ArrayList<BlazeData>();

        //initBlazeData(BlazeDragon.getAmountRegClasses());
    }

    private void initBlazeData(short packageID)
    {
        this.blazeDatas.add(new BlazeData(packageID));

        for(int i = 1; i < this.blazeInitDataValues.size();i++)
        {
            byte typeVal = this.blazeInitDataValues.get(i);

            switch(typeVal)
            {
                case BlazeDragon.BOOLEAN_DATA:
                {
                    this.blazeDatas.add(new BlazeData(false));
                }
                case BlazeDragon.DOUBLE_DATA:
                {
                    this.blazeDatas.add(new BlazeData((double) 0));
                }
                case BlazeDragon.STRING_DATA:
                {
                    this.blazeDatas.add(new BlazeData(""));
                }
            }
        }
    }

    public BlazeData getData(int index)
    {
        return blazeDatas.get(index);
    }

    void fillBlazeData(int index, BlazeData bData)
    {

    }

    public BlazeData[] getBlazeDatas()
    {
        BlazeData[] outArray = new BlazeData[this.blazeDatas.size()];
        outArray = this.blazeDatas.toArray(outArray);

        return outArray;
    }

    protected short getPackageID() throws UnfittingBlazeDataException
    {
        return this.blazeDatas.get(0).getBlazePackIdentifier();
    }

    protected void addBlazeDataType(byte signalType)                                                                    // Verwendung in defineSignals() Methode des BlazeModuls
    {
        this.blazeInitDataValues.add(signalType);
    }
}