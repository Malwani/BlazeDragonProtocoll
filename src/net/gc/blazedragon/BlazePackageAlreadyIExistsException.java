package net.gc.blazedragon;

/**
 * Created by Malwani on 27.03.2015.
 */
public class BlazePackageAlreadyIExistsException extends Exception
{
    BlazePackageAlreadyIExistsException()
    {
        super("This BlazePackageClass is already initialized in your BlazeDragon!");
    }
}