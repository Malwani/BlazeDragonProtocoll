package testDragon;

/**
 * Created by Benny on 07.03.2015.
 */

import net.gc.blazedragon.*;

import java.util.Random;
import java.util.Scanner;

public class DragonBenchmark
{
    public static void main (String[] args)
    {
        System.out.println("| BLAZE BENCHMARK |");
        System.out.println("_____________________________");
        BlazeSignal[] blazeSignals = readInData();

        try
        {
            System.out.println("_____________________________");
            testShellOne(blazeSignals[1]);
            System.out.println("_____________________________");
            testShellTwo(blazeSignals);
            System.out.println("_____________________________");
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    private static BlazeSignal[] readInData()
    {
        Scanner       scanner      = new Scanner(System.in);
        BlazeSignal[] blazeSignals = new BlazeSignal[4];
        BlazeSignal   bsType,bs1,bs2,bs3;

        System.out.println("BlazeSignal Input:");

        System.out.print("BSType - ");
        bsType = new BlazeSignal(scanner.nextShort());
        scanner.nextLine();

        System.out.print("BS1 - ");
        bs1 = new BlazeSignal(scanner.nextLine());

        System.out.print("BS2 - ");
        bs2 = new BlazeSignal(scanner.nextLine());

        System.out.print("BS3 - ");
        bs3 = new BlazeSignal(scanner.nextLine());

        blazeSignals[0] = bsType;
        blazeSignals[1] = bs1;
        blazeSignals[2] = bs2;
        blazeSignals[3] = bs3;

        return blazeSignals;
    }

    private static void testShellOne(BlazeSignal bs) throws UnfittingBlazeDataException
    {
        byte[] bytes;
        long timeBefore, timeAfter;

        System.out.println("| TESTE SCHALE 1 |\n");
        System.out.println("Type: " + bs.getType() + "| Value: " + bs.getDataStr());
        System.out.println("\nUmwandeln in ByteArray...");

        timeBefore = System.currentTimeMillis();
        bytes = BlazeDragon.bs_2_ba(bs);
        timeAfter = System.currentTimeMillis();

        System.out.print("Bytes: |");
        for (byte singleByte : bytes)
        {
            System.out.print(Integer.toHexString(singleByte) + "|");
        }

        System.out.println("\nZeit benötigt: " + (timeAfter - timeBefore) + "ms.");
        System.out.println("\nUmwandeln in Blaze Signal...");

        timeBefore = System.currentTimeMillis();
        BlazeSignal bs_new = BlazeDragon.ba_2_bs(bytes);
        timeAfter = System.currentTimeMillis();

        System.out.println("Type: " + bs_new.getType() + "| Value: " + bs_new.getDataStr());
        System.out.println("Zeit benötigt: " + (timeAfter - timeBefore) + "ms.");

        if(bs_new.getDataStr().equals(bs.getDataStr()))
        {
            System.out.println("\n[ SCHALE 1 : FUNKTIONIERT ]");
        }
        else
        {
            System.out.println("\n[ SCHALE 1 : FEHLERHAFT ]");
        }
    }

    private static void testShellTwo(BlazeSignal[] blazeSignals) throws UnfittingBlazeDataException
    {
        /* BlazeDragon.bs_array2byte_array();
        BlazeDragon.byte_array2bs_array();*/

        byte[] bytes;
        int corrects = 0;
        long timeBefore, timeAfter;
        BlazeSignal[] blazesignalsAfter;

        System.out.println("[ TESTE SCHALE 2 ]\n");
        System.out.print("BS0 - " + "| Type: " + blazeSignals[0].getType() + "| Value: "
                + blazeSignals[0].getBlazePackIdentifier() + '\n');

        for(int i = 1; i < 4; i++)
        {
            System.out.print("BS" + (i) + " - | Type: " + blazeSignals[i].getType() + "| Value: "
                    + blazeSignals[i].getDataStr() + '\n');
        }

        System.out.println("\nUmwandeln in ByteArray...");
        timeBefore = System.currentTimeMillis();
        bytes = BlazeDragon.bs_array2byte_array(blazeSignals);
        timeAfter = System.currentTimeMillis();

        System.out.print("Bytes: |");
        for (byte singleByte : bytes)
        {
            System.out.print(Integer.toHexString(singleByte) + "|");
        }

        System.out.println("\nZeit benötigt: " + (timeAfter - timeBefore) + "ms.");
        System.out.println("\nUmwandeln in BlazeSignal-Array...");

        timeBefore        = System.currentTimeMillis();
        blazesignalsAfter = BlazeDragon.byte_array2bs_array(bytes);
        timeAfter         = System.currentTimeMillis();

        System.out.print("BS0 - " + "| Type: " + blazesignalsAfter[0].getType() + "| Value: "
                + blazesignalsAfter[0].getBlazePackIdentifier() + '\n');

        for(int i = 1; i < 4; i++)
        {
            System.out.print("BS" + (i) + " - | Type: " + blazesignalsAfter[i].getType() + "| Value: "
                    + blazesignalsAfter[i].getDataStr() + '\n');
        }

        System.out.println("Zeit benötigt: " + (timeAfter - timeBefore) + "ms.");

        if(blazeSignals[0].getBlazePackIdentifier() == blazesignalsAfter[0].getBlazePackIdentifier())
            corrects++;

        for(int i = 1; i < 4; i++)
        {
            if(blazeSignals[i].getDataStr().equals(blazesignalsAfter[i].getDataStr()))
                corrects++;
        }

        if(corrects == 4)
        {
            System.out.println("\n[ SCHALE 2 : FUNKTIONIERT ]");
        }
        else
        {
            System.out.println("\n[ SCHALE 2 : FEHLERHAFT ]");
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