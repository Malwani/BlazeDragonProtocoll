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

    TestPackage()                                                                                                       // Normale Init (BD Init vorrausgesetzt))
    {
        super(true);
    }

    @Override
    public Byte[] defineBlazeData()
    {
        Byte[] initBytes = {BlazeDragon.BOOLEAN_DATA, BlazeDragon.STRING_DATA, BlazeDragon.STRING_DATA, BlazeDragon.DOUBLE_DATA};

        return initBytes;
    }

    // Sample Getter / Setter

    void setLoggedIn(boolean bool)
    {
        // Indexstart bei 1 da 0 mit PAckInit belegt ist
        try
        {
            this.setData(1, BlazeDragon.BOOLEAN_DATA, bool);
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
            this.setData(2, BlazeDragon.STRING_DATA, uname);
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }
    }

    void setPass(String pass)
    {
        try
        {
            this.setData(3, BlazeDragon.STRING_DATA, pass);
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
            this.setData(4, BlazeDragon.DOUBLE_DATA, d);
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

    String getPass() throws UnfittingBlazeDataException
    {
        return this.getBlazeData(3).getDataStr();
    }

    double getDouble() throws UnfittingBlazeDataException
    {
        return this.getBlazeData(4).getDataDouble();
    }
}

