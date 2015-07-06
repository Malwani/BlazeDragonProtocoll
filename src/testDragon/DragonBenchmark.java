package testDragon;

/**
 * Created by Benny on 07.03.2015.
 */

import net.gc.blazedragon.*;

import java.util.Scanner;

public class DragonBenchmark
{
    public static void main (String[] args)
    {
        System.out.println("| BLAZE BENCHMARK |");
        System.out.println("_____________________________");
       // BlazeData[] blazeDatas = readInData();

        BlazeData[] blazeDatas;
        BlazeDragon bd = new BlazeDragon();

        try
        {
            addBdPackages(bd);
        }catch(BlazePackageAlreadyIntegratedException ex)
        {
            ex.printStackTrace();
        }



        TestPackage tp = new TestPackage(true);

        tp.setLoggedIn(true);
        tp.setPass("mario123");
        tp.setUsername("espanhola");
        tp.setDouble(4578.5);
        blazeDatas = tp.getBlazeDatas();

        try
        {
           /* System.out.println("_____________________________");
            testShellOne(blazeDatas[1]);
            System.out.println("_____________________________");*/
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
        Scanner       scanner  = new Scanner(System.in);
        BlazeData[] blazeDatas = new BlazeData[5];
        BlazeData bsType,bs1,bs2,bs3;

        System.out.println("BlazeSignal Input:");


        bsType = new BlazeData((short) 0);
        /*System.out.print("BSType - ");
        bsType = new BlazeData(scanner.nextShort());
        scanner.nextLine();*/

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


    private static void testShellTwo(BlazeData[] blazeDatas) throws UnfittingBlazeDataException
    {
        /* BlazeDragon.bs_array2byte_array();
        BlazeDragon.byte_array2bs_array();*/

        byte[] bytes;
        int corrects = 0;
        long timeBefore, timeAfter;
        BlazeData[] blazesignalsAfter;
        BlazeDragon bd = new BlazeDragon();

        try
        {
            addBdPackages(bd);
        }
        catch(BlazePackageAlreadyIntegratedException ex)
        {
            ex.printStackTrace();
        }

        System.out.println("[ TESTE SCHALE 2 ]\n");
        System.out.print("BS0 - " + "| Type: " + blazeDatas[0].getType() + "| Value: "
                + blazeDatas[0].getBlazePackIdentifier() + '\n');

        System.out.print("BS1  - | Type: " + blazeDatas[1].getType() + "| Value: "
                + blazeDatas[1].getDataBoolean() + '\n');
        System.out.print("BS2  - | Type: " + blazeDatas[2].getType() + "| Value: "
                + blazeDatas[2].getDataStr() + '\n');
        System.out.print("BS3 - | Type: " + blazeDatas[3].getType() + "| Value: "
                + blazeDatas[3].getDataStr() + '\n');
        System.out.print("BS4 - | Type: " + blazeDatas[4].getType() + "| Value:"
                + blazeDatas[4].getDataDouble() + '\n');

        System.out.println("\nUmwandeln in ByteArray...");
        timeBefore = System.currentTimeMillis();
        bytes = BlazeDragon.getBytes(blazeDatas);
        timeAfter = System.currentTimeMillis();

        System.out.print("Bytes: |");
        for (byte singleByte : bytes)
        {
            System.out.print(Integer.toHexString(singleByte) + "|");
        }

        System.out.println("\nZeit benötigt: " + (timeAfter - timeBefore) + "ms.");
        System.out.println("\nUmwandeln in BlazeSignal-Array...");

        timeBefore        = System.currentTimeMillis();
        blazesignalsAfter = BlazeDragon.getBlazeDatas(bytes);
        timeAfter         = System.currentTimeMillis();

        System.out.print("BS0 - " + "| Type: " + blazesignalsAfter[0].getType() + "| Value: "
                + blazesignalsAfter[0].getBlazePackIdentifier() + '\n');

        System.out.print("BS1  - | Type: " + blazeDatas[1].getType() + "| Value: "
                + blazeDatas[1].getDataBoolean() + '\n');
        System.out.print("BS2  - | Type: " + blazeDatas[2].getType() + "| Value: "
                + blazeDatas[2].getDataStr() + '\n');
        System.out.print("BS3 - | Type: " + blazeDatas[3].getType() + "| Value: "
                + blazeDatas[3].getDataStr() + '\n');
        System.out.print("BS4 - | Type: " + blazeDatas[4].getType() + "| Value:"
                + blazeDatas[4].getDataDouble() + '\n');

        System.out.println("Zeit benötigt: " + (timeAfter - timeBefore) + "ms.");

        if(blazeDatas[0].getBlazePackIdentifier() == blazesignalsAfter[0].getBlazePackIdentifier())
            corrects++;

    }

    public static void addBdPackages(BlazeDragon bd) throws BlazePackageAlreadyIntegratedException
    {
        bd.addPackage(new TestPackage(BlazeDragon.detectNextPackageID()));
    }

    /*private static void testConvertingTime() throws UnfittingBlazeDataException
    {
        byte[] bytes;
        int times = 100000;
        long timeBefore,timeAfter;
        BlazeData bs = new BlazeData("Hallo Leon! :)");

        System.out.println("Berechne " + times * 2 + " Konvertierungen...");
        timeBefore = System.currentTimeMillis();

        for(int i = 0; i <= times; i++)
        {
            bytes = BlazeDragon.bd_2ba(bs);
            bs = BlazeDragon.ba_2_bd(bytes);
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
    }*/
}