package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackage;

/**
 * Created by Benny on 07.03.2015.
 */
public class TestPackage4 extends BlazePackage
{
    TestPackage4(short ID)                                                                                               // F�r BlazeDragon Init
    {
        super(ID);
    }

    TestPackage4()                                                                                                       // Normale Init (BD Init vorrausgesetzt))
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

