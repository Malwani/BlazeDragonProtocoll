package net.gc.blazedragon;

/**
 * Created by Malwani on 27.03.2015.
 */
public class BlazeIDAlreadyExistsExcpetion extends Exception
{
    BlazeIDAlreadyExistsExcpetion()
    {
        super("Your BlazeID already exists in BlazeDragon. Please fill in the ID in the correct way!");
    }
}