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
        BlazeData[] blazeDatas = readInData();

        try
        {
            System.out.println("_____________________________");
            testShellOne(blazeDatas[1]);
            System.out.println("_____________________________");
            testShellTwo(blazeDatas);
            System.out.println("_____________________________");
        }
        catch (UnfittingBlazeDataException ex)
        {
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    private static BlazeData[] readInData()
    {
        Scanner       scanner      = new Scanner(System.in);
        BlazeData[] blazeDatas = new BlazeData[4];
        BlazeData bsType,bs1,bs2,bs3;

        System.out.println("BlazeSignal Input:");

        System.out.print("BSType - ");
        bsType = new BlazeData(scanner.nextShort());
        scanner.nextLine();

        System.out.print("BS1 - ");
        bs1 = new BlazeData(scanner.nextLine());

        System.out.print("BS2 - ");
        bs2 = new BlazeData(scanner.nextLine());

        System.out.print("BS3 - ");
        bs3 = new BlazeData(scanner.nextLine());

        blazeDatas[0] = bsType;
        blazeDatas[1] = bs1;
        blazeDatas[2] = bs2;
        blazeDatas[3] = bs3;

        return blazeDatas;
    }

    private static void testShellOne(BlazeData bs) throws UnfittingBlazeDataException
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
        BlazeData bs_new = BlazeDragon.ba_2_bs(bytes);
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

    private static void testShellTwo(BlazeData[] blazeDatas) throws UnfittingBlazeDataException
    {
        /* BlazeDragon.bs_array2byte_array();
        BlazeDragon.byte_array2bs_array();*/

        byte[] bytes;
        int corrects = 0;
        long timeBefore, timeAfter;
        BlazeData[] blazesignalsAfter;

        System.out.println("[ TESTE SCHALE 2 ]\n");
        System.out.print("BS0 - " + "| Type: " + blazeDatas[0].getType() + "| Value: "
                + blazeDatas[0].getBlazePackIdentifier() + '\n');

        for(int i = 1; i < 4; i++)
        {
            System.out.print("BS" + (i) + " - | Type: " + blazeDatas[i].getType() + "| Value: "
                    + blazeDatas[i].getDataStr() + '\n');
        }

        System.out.println("\nUmwandeln in ByteArray...");
        timeBefore = System.currentTimeMillis();
        bytes = BlazeDragon.bs_array2byte_array(blazeDatas);
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

        if(blazeDatas[0].getBlazePackIdentifier() == blazesignalsAfter[0].getBlazePackIdentifier())
            corrects++;

        for(int i = 1; i < 4; i++)
        {
            if(blazeDatas[i].getDataStr().equals(blazesignalsAfter[i].getDataStr()))
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
        BlazeData bs = new BlazeData("Hallo Leon! :)");

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