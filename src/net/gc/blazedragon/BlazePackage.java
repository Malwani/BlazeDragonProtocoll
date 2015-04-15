package net.gc.blazedragon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malwani on 28.02.2015.
 */

public abstract class BlazePackage implements BlazeModule
{
    public  short              packageID;
    public  List<BlazeSignal>  blazeSignals;
    private List<Byte>         blazeSignalTypeValues;

    public BlazePackage(short packageID)
    {
        this.packageID = packageID;
        this.blazeSignalTypeValues = new ArrayList<Byte>();
        this.blazeSignals          = new ArrayList<BlazeSignal>();

        addBlazeDataType(BlazeDragon.DATA_PACK_INIT);
        defineSignals();
        initBlazeSignals();
    }

    public void addBlazeDataType(byte signalType)
    {
        this.blazeSignalTypeValues.add(signalType);
    }

    private void initBlazeSignals()
    {
        for(int i = 0; i < this.blazeSignalTypeValues.size();i++)
        {
            byte typeVal = this.blazeSignalTypeValues.get(i);

            switch(typeVal)
            {
                case BlazeDragon.DATA_PACK_INIT:
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
}
