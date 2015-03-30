package testDragon;

/**
 * Created by Benjie on 25.03.2015.
 */

import net.gc.blazedragon.*;

public class TestSchale3
{
    private static BlazeDragon blazeDragon;

    public static void main(String[] args)
    {
        blazeDragon = new BlazeDragon();
        blazeDragon.addPackage(new TestPackage());
    }

}
