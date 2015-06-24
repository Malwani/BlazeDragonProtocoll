package testDragon;

import net.gc.blazedragon.BlazeData;
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
        super();
    }

    @Override
    public void defineBlazeData()
    {
        addBlazeDataType(BlazeDragon.BOOLEAN_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
    }

    // Sample Getter / Setter

    void setLoggedIn(boolean bool)
    {
        // Indexstart bei 1 da 0 mit PAckInit belegt ist
        this.setData(1, new BlazeData(bool));
    }

    void setUsername(String uname)
    {
        this.setData(2, new BlazeData(uname));
    }

    void setPass(String pass)
    {
        this.setData(3, new BlazeData(pass));
    }

    boolean getLoggedIn() throws UnfittingBlazeDataException
    {
        return this.getData(1).getDataBoolean();
    }

    String getPass() throws UnfittingBlazeDataException
    {
        return this.getData(2).getDataStr();
    }

    String getUsername() throws UnfittingBlazeDataException
    {
        return this.getData(3).getDataStr();
    }
}

