package net.gc.blazedragon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malwani on 28.02.2015.
 */

public abstract class BlazePackage implements BlazeModule
{
    private  short             packageID;
    private  List<BlazeSignal> blazeSignals;
    private  List<Byte>        blazeSignalTypeValues;

    public BlazePackage(short packageID)
    {
        this.packageID = packageID;
        this.blazeSignalTypeValues = new ArrayList<Byte>();
        this.blazeSignals          = new ArrayList<BlazeSignal>();

        addBlazeDataType(BlazeDragon.PACK_INIT_DATA);
        defineBlazeData();
        initBlazeData();
    }

    private void initBlazeData()
    {
        for(int i = 0; i < this.blazeSignalTypeValues.size();i++)
        {
            byte typeVal = this.blazeSignalTypeValues.get(i);

            switch(typeVal)
            {
                case BlazeDragon.PACK_INIT_DATA:
                {
                    this.blazeSignals.add(new BlazeSignal((short) 0));
                }
                case BlazeDragon.BOOLEAN_DATA:
                {
                    this.blazeSignals.add(new BlazeSignal(false));
                }
                case BlazeDragon.DOUBLE_DATA:
                {
                    this.blazeSignals.add(new BlazeSignal((double) 0));
                }
                case BlazeDragon.STRING_DATA:
                {
                    this.blazeSignals.add(new BlazeSignal(""));
                }
            }
        }
    }

    public short getPackageID()
    {
        return  this.packageID;
    }

    public List getDataInitVals()
    {
        return this.blazeSignalTypeValues;
    }

    public void addBlazeDataType(byte signalType)
    {
        this.blazeSignalTypeValues.add(signalType);
    }
}