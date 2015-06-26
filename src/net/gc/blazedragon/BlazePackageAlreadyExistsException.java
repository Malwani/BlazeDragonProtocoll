package net.gc.blazedragon;

/**
 * Created by Malwani on 27.03.2015.
 */
public class BlazePackageAlreadyExistsException extends Exception
{
    BlazePackageAlreadyExistsException()
    {
        super("This BlazePackageClass is already initialized in your BlazeDragon!");
    }
}