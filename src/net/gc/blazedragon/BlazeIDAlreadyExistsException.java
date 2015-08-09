package net.gc.blazedragon;

/**
 * Created by MPsyclopZ on 27.03.2015.
 */
public class BlazeIDAlreadyExistsException extends Exception
{
    BlazeIDAlreadyExistsException()
    {
        super("Your BlazeID already exists in BlazeDragon. Please fill in the ID in the correct way!");
    }
}