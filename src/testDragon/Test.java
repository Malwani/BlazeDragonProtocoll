package testDragon;

/**
 * Created by Benny on 07.03.2015.
 */

import net.gc.blazedragon.*;

public class Test
{
    public static void main (String[] args)
    {
        try
        {
            testShells();
            testConvertingTime();
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.getMessage();
            ex.printStackTrace();
        }

    }

    private static void testShells() throws UnfittingBlazeDataException
    {
        byte[] bytes;
        long timeBefore, timeAfter;
        BlazeSignal bs = new BlazeSignal("Hallo Leon! :)");

        System.out.println("Type: " + bs.getType() + "| Value: " + bs.getDataStr());

        System.out.println("Umwandeln in Bytes...");

        timeBefore = System.currentTimeMillis();
        bytes = BlazeDragon.bs_2_ba(bs);
        timeAfter = System.currentTimeMillis();

        System.out.println("Zeit benötigt: " + (timeAfter - timeBefore) + "ms." );
        System.out.print("Bytes: |");
        for (byte singleByte : bytes)
        {
            System.out.print(Integer.toHexString(singleByte) + "|");
        }

        System.out.println("\nUmwandeln in Blaze Signal...");
        BlazeSignal bs_new = BlazeDragon.ba_2_bs(bytes);
        System.out.println("Type: " + bs_new.getType() + "| Value: " + bs_new.getDataStr());
    }

    private static void testConvertingTime() throws UnfittingBlazeDataException
    {
        byte[] bytes;
        long timeBefore,timeAfter;
        BlazeSignal bs = new BlazeSignal("Hallo Leon! :)");

        System.out.println("Berechne...");
        timeBefore = System.currentTimeMillis();

        for(int i = 0; i <= 9000000; i++)
        {
            bytes = BlazeDragon.bs_2_ba(bs);
            bs = BlazeDragon.ba_2_bs(bytes);
        }

        timeAfter = System.currentTimeMillis();

        timeBefore = timeAfter-timeBefore;
        System.out.println("Benötigte Zeit: " + timeBefore + "ms." );

    }
}
