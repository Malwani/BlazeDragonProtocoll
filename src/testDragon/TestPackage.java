package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackage;

/**
 * Created by Benny on 07.03.2015.
 */
public class TestPackage extends BlazePackage
{
    TestPackage()
    {
        super((short)0);
    }

    @Override
    public void defineBlazeData()
    {
        addBlazeDataType(BlazeDragon.STRING_DATA);
        addBlazeDataType(BlazeDragon.STRING_DATA);
    }

}

