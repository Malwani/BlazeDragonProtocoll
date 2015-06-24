package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackageAlreadyIExistsException;
import net.gc.blazedragon.UnfittingBlazeDataException;

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
        catch(BlazePackageAlreadyIExistsException ex)
        {
            ex.printStackTrace();
        }

        TestPackage test = new TestPackage();

        test.setLoggedIn(true);
        test.setPass("mario123");
        test.setUsername("espanhola");

        try
        {
            System.out.println(test.getLoggedIn());
            System.out.println(test.getPass());
            System.out.println(test.getUsername());
        }catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

    }

    public static void addBdPackages(BlazeDragon bd) throws BlazePackageAlreadyIExistsException
    {
        bd.addPackage(new TestPackage(BlazeDragon.detectNextPackageID()));
        bd.addPackage(new TestPackage2(BlazeDragon.detectNextPackageID()));
        bd.addPackage(new TestPackage3(BlazeDragon.detectNextPackageID()));
        bd.addPackage(new TestPackage4(BlazeDragon.detectNextPackageID()));
    }
}
