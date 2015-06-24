package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackage;

/**
 * Created by Benny on 07.03.2015.
 */
public class TestPackage2 extends BlazePackage
{
    TestPackage2(short ID)                                                                                               // Für BlazeDragon Init
    {
        super(ID);
    }

    TestPackage2()                                                                                                       // Normale Init (BD Init vorrausgesetzt))
    {
        super();
    }

    @Override
    public void defineBlazeData()
    {
        addBlazeDataType(BlazeDragon.BOOLEAN_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
    }

}

