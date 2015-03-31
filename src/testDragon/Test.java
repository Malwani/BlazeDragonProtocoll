package testDragon;

/**
 * Created by Benny on 07.03.2015.
 */

import net.gc.blazedragon.*;

import java.util.Random;

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

        try
        {
            byte[] bytes;
            long timeBefore, timeAfter;
            BlazeSignal bs = new BlazeSignal("Test");
            Thread.sleep(genRandom(64,256));

            System.out.println("Type: " + bs.getType() + "| Value: " + bs.getDataStr());
            Thread.sleep(genRandom(64,256));
            System.out.println("Umwandeln in Bytes...");
            Thread.sleep(genRandom(64,256));
            timeBefore = System.currentTimeMillis();
            Thread.sleep(genRandom(64,256));
            bytes = BlazeDragon.bs_2_ba(bs);
            Thread.sleep(genRandom(64,256));
            timeAfter = System.currentTimeMillis();
            Thread.sleep(genRandom(64,256));

            System.out.println("Zeit benötigt: " + (timeAfter - timeBefore) + "ms.");
            Thread.sleep(genRandom(64,256));
            System.out.print("Bytes: |");
            Thread.sleep(genRandom(64,256));
            for (byte singleByte : bytes)
            {
                Thread.sleep(genRandom(64,256));
                System.out.print(Integer.toHexString(singleByte) + "|");
            }
            Thread.sleep(genRandom(64,256));
            System.out.println("\nUmwandeln in Blaze Signal...");
            Thread.sleep(genRandom(64,256));
            BlazeSignal bs_new = BlazeDragon.ba_2_bs(bytes);
            Thread.sleep(genRandom(64,256));
            System.out.println("Type: " + bs_new.getType() + "| Value: " + bs_new.getDataStr());
            Thread.sleep(genRandom(64,256));
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void testConvertingTime() throws UnfittingBlazeDataException
    {
        byte[] bytes;
        int times = 100000;
        long timeBefore,timeAfter;
        BlazeSignal bs = new BlazeSignal("Hallo Leon! :)");

        System.out.println("Berechne " + times * 2 + " Konvertierungen...");
        timeBefore = System.currentTimeMillis();

        for(int i = 0; i <= times; i++)
        {
            bytes = BlazeDragon.bs_2_ba(bs);
            bs = BlazeDragon.ba_2_bs(bytes);
        }

        timeAfter = System.currentTimeMillis();

        timeBefore = timeAfter-timeBefore;
        System.out.println("Benötigte Zeit: " + timeBefore + "ms." );

    }

    private static int genRandom(int maximum, int minimum)
    {
        Random rn = new Random();
        int n,r,randomNum;

        n = maximum - minimum +1;
        r = rn.nextInt() % n;
        randomNum = minimum - r;

        return randomNum;
    }
}