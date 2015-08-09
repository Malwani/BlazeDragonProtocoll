package net.gc.blazedragon;

public class BlazeIDAlreadyExistsException extends Exception
{
    BlazeIDAlreadyExistsException()
    {
        super("Your BlazeID already exists in BlazeDragon. Please fill in the ID in the correct way!");
    }
}