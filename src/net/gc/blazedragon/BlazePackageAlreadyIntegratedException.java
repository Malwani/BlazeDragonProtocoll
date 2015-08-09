package net.gc.blazedragon;

public class BlazePackageAlreadyIntegratedException extends Exception
{
    BlazePackageAlreadyIntegratedException()
    {
        super("This BlazePackageClass is already initialized in your BlazeDragon!");
    }
}