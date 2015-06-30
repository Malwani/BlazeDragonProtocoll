package testDragon;

import net.gc.blazedragon.BlazeDragon;
import net.gc.blazedragon.BlazePackageAlreadyExistsException;
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
        catch(BlazePackageAlreadyExistsException ex)
        {
            ex.printStackTrace();
        }

        TestPackage test = new TestPackage(true);

        test.setLoggedIn(true);
        test.setPass("mario123");
        test.setUsername("espanhola");
        test.setDouble(4578.5);

        try
        {
            System.out.println(test.getLoggedIn());
            System.out.println(test.getPass());
            System.out.println(test.getUsername());
            System.out.println(test.getDouble());
        }catch(UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }



        TestPackage test2 = new TestPackage(false);
        try
        {
            test2.setBlazeDatas(BlazeDragon.getBlazeDatas(BlazeDragon.getBytes(test.getBlazeDatas())));
        }catch (UnfittingBlazeDataException ex)
        {
            ex.printStackTrace();
        }

    }

    public static void addBdPackages(BlazeDragon bd) throws BlazePackageAlreadyExistsException
    {
        bd.addPackage(new TestPackage(BlazeDragon.detectNextPackageID()));
    }
}