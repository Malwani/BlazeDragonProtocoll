package net.gc.blazedragon;

/**
 * Created by PsyclopZ on 27.03.2015.
 */
public class BlazePackageAlreadyIntegratedException extends Exception
{
    BlazePackageAlreadyIntegratedException()
    {
        super("This BlazePackageClass is already initialized in your BlazeDragon!");
    }
}