package com.company;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) throws IOException{
        int remover, picker, calc = 0;
        File f = new File("database.txt");
        ArrayList<Tank> tankList = new ArrayList<>();

        if (f.exists() && !f.isDirectory()) {
            Scanner input = new Scanner(new File("database.txt"));
            input.useDelimiter("[/|\n]");

            while (input.hasNext()) {
                String name = input.next();
                int capacity = input.nextInt();
                int water = input.nextInt();
                Tank tank = new Tank(name, capacity, water);
                tankList.add(tank);
                calc += 1;
            }
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("""

                    Pick what u want to do

                    1 - add tank
                    2 - remove tank
                    3 - refill tank
                    4 - pour tank
                    5 - transfer from one tank to other
                    6 - check database
                    7 - end
                    8 - FindMost
                    9 - FindMax
                    10 - FindEmpty
                    11 - check logs
                    12 - Finding most operations
                    13 - Finding most failed operations""");

            int pick = scanner.nextInt();
            scanner.nextLine();
            switch (pick) {
                case 1:
                    System.out.println("Enter the name");
                    String name = scanner.nextLine();

                    System.out.println("Enter the capacity");
                    int capacity = scanner.nextInt();

                    System.out.println("Enter the water inside");
                    int water = scanner.nextInt();

                    Tank tank = new Tank(name, capacity, water);

                    tankList.add(tank);
                    scanner.nextLine();

                    Tank.writeNewTanks(tankList, calc);
                    calc +=1;

                    break;
                case 2:
                    System.out.println("Index to delete");
                    remover = scanner.nextInt();

                    if (remover < tankList.size()) {
                        tankList.remove(remover);

                    } else System.out.println("This index doesn't exist");
                    break;
                case 3:
                    System.out.println("Chose tank to refill");
                    picker = scanner.nextInt();

                    if (picker < tankList.size()) {
                        System.out.println("How much water you want to refill");
                        int refill = scanner.nextInt();
                        tankList.get(picker).refill(refill);
                    } else {
                        System.out.println("You didn't choose correct Tank");
                    }
                    break;
                case 4:
                    System.out.println("Choose tank to pour");
                    picker = scanner.nextInt();
                    if (picker < tankList.size()) {
                        System.out.println("How much water do you want to pour out?");
                        int pouring = scanner.nextInt();
                        tankList.get(picker).pouring(pouring);
                    } else {
                        System.out.println("You didn't choose correct Tank");
                    }
                    break;
                case 5:
                    System.out.println("Choose a Tank to witch pour to");
                    picker = scanner.nextInt();
                    System.out.println("choose a Tank from which to pour");
                    int picker2 = scanner.nextInt();
                    if (picker < tankList.size() && picker2 < tankList.size()) {
                        System.out.println("How much water to transfer?");
                        int transfer = scanner.nextInt();
                        tankList.get(picker).transfer(tankList.get(picker2), transfer);
/*
                        Tank a = tankList.get(picker);
                        Tank b = tankList.get(picker2);
                        a.transfer(b, transfer);
*/
                    }
                    break;
                case 6:
                    if (!tankList.isEmpty()) {
                        for (Tank loop : tankList) {
                            System.out.println(loop.name + " " + loop.capacity + " " + loop.water);
                        }
                    } else System.out.println("No records");
                    System.out.println();
                    break;
                case 7:
                    exit = true;
                    break;
                case 8:
                    Tank c = Tank.findMost(tankList);
                    System.out.println(c.toString());
                    break;
                case 9:
                    Tank d = Tank.findMax(tankList);
                    double inputD = d.getDiv();
                    System.out.println(d.toString() + "\nfilling = " + df.format(inputD) + " %");
                    break;
                case 10:
                    Tank.findEmpty(tankList);
                    break;
                case 11:
                    Logs.gettingLogs();
                    break;
                case 12:
                    System.out.println("What operation?(refill, pouring, transfer)");
                    String picker3 = scanner.nextLine().toLowerCase(Locale.ROOT);
                    if (picker3.equals("refill") || picker3.equals("pouring") || picker3.equals("transfer"))
                        Logs.countingLogs(tankList, picker3);
                    else {
                        System.out.println("Wrong operation");
                    }
                    break;
                case 13:
                    Logs.countingFailedLogs(tankList);
                    break;
                default:
                    System.out.println("from 1-13");
                    System.out.println();
                    break;
            }
        }
    }
}