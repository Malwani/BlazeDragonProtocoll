package net.gc.blazedragon;

/**
 * Created by PsyclopZ on 27.03.2015.
 */
public class UnfittingBlazeDataException extends Exception
{
    UnfittingBlazeDataException()
    {
        super("Unfitting Blaze-Data! Please put the Data based of the Type or access the correct Type of Data.");
    }
}