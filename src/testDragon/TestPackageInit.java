package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackageAlreadyInitializedException;

/**
 * Created by Benjie on 23.06.2015.
 */
public class TestPackageInit
{
    public static void main(String[] args)
    {
        BlazeDragon bd;
        bd = new BlazeDragon();

        try
        {
            addBdPackages(bd);
        }
        catch(BlazePackageAlreadyInitializedException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void addBdPackages(BlazeDragon bd) throws BlazePackageAlreadyInitializedException
    {
        bd.addPackage(new TestPackage(BlazeDragon.detectNextPackageID()));
        bd.addPackage(new TestPackage2(BlazeDragon.detectNextPackageID()));
    }
}
