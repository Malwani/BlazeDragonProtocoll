package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackage;
import net.gc.blazedragon.UnfittingBlazeDataException;

/**
 * Created by Benny on 07.03.2015.
 */

public class TestPackage extends BlazePackage
{
    TestPackage(short ID)                                                                                               // Für BlazeDragon Init
    {
        super(ID);
    }

    TestPackage(boolean init)                                                                                           // Normale Init (BD Init vorrausgesetzt))
    {
        super(init);
    }

    @Override
    public Byte[] defineBlazeData()
    {
        Byte[] initBytes = {BlazeDragon.BOOLEAN_DATA, BlazeDragon.STRING_DATA, BlazeDragon.INTEGER_DATA,
                BlazeDragon.DOUBLE_DATA};

        return initBytes;
    }
                                                                                                                        // Sample Getter / Setter
    void setLoggedIn(boolean bool)
    {
        try
        {
            this.getBlazeData(1).setBoolean(bool);                                                                      // Indexstart bei 1 da 0 mit PAckInit belegt ist
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }
    }

    void setUsername(String uname)
    {
        try
        {
            this.getBlazeData(2).setStr(uname);
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }
    }

    void setInt(int val)
    {
        try
        {
            this.getBlazeData(3).setInt(val);
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }
    }

    void setDouble(double d)
    {
        try
        {
            this.getBlazeData(4).setDouble(d);
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }
    }

    boolean getLoggedIn() throws UnfittingBlazeDataException
    {
        return this.getBlazeData(1).getDataBoolean();
    }

    String getUsername() throws UnfittingBlazeDataException
    {
        return this.getBlazeData(2).getDataStr();
    }

    int getInt() throws UnfittingBlazeDataException
    {
        return this.getBlazeData(3).getDataInt();
    }

    double getDouble() throws UnfittingBlazeDataException
    {
        return this.getBlazeData(4).getDataDouble();
    }
}