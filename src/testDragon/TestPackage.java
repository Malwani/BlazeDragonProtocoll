package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackage;

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
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
    }

}

