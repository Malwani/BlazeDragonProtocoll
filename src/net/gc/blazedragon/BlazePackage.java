package net.gc.blazedragon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malwani on 28.02.2015.
 */

public abstract class BlazePackage implements BlazeModule
{
    private List<BlazeData> blazeDatas;
    private List<Byte>      blazeInitDataValues;

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

    public BlazePackage()                                                                                               // Konstruktor zur Package-Initialisierung
    {
        this.blazeDatas = new ArrayList<BlazeData>();

        initBlazeData();
    }

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

    protected BlazeData getData(int index)                                                                              // Für Getter des Endo-Packages
    {
        return blazeDatas.get(index);
    }

    protected void setData(int i, BlazeData bData)                                                                      // Für Setter des Endo-Packages
    {
        try
        {
            switch(blazeDatas.get(i).getType())
            {
                case BlazeDragon.BOOLEAN_DATA:
                {
                    blazeDatas.get(i).setBoolean(bData.getDataBoolean());
                }
                case BlazeDragon.DOUBLE_DATA:
                {
                    blazeDatas.get(i).setDouble(bData.getDataDouble());
                }
                case BlazeDragon.STRING_DATA:
                {
                    blazeDatas.get(i).setStr(bData.getDataStr());
                }
                case BlazeDragon.PACKAGE_ID_DATA:
                {
                    blazeDatas.get(i).setBlazePackIdentifier(bData.getBlazePackIdentifier());
                }
            }
        }catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

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