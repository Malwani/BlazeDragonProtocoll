package net.gc.blazedragon;

public class UnfittingBlazeDataException extends Exception
{
    UnfittingBlazeDataException()
    {
        super("Unfitting Blaze-Data! Please put the Data based of the Type or access the correct Type of Data.");
    }
}