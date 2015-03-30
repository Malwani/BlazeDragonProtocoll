package net.gc.blazedragon;

/**
 * Created by Malwani on 27.03.2015.
 */
public class UnfittingBlazeDataException extends Exception
{
    UnfittingBlazeDataException()
    {
        super("Unfitting BlazeSignal-Data! Please put the Data based of the Type or access the correct Type of Data.");
    }
}
