package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tank {
    String name;
    int capacity;
    int water;
    private double div;
    boolean isSuccessful;
    Logs logs = null;
    int changes;
    static FileWriter write;

    Tank(String name, int capacity, int water) {
        this.capacity = capacity;
        this.name = name;
        this.water = water;

    }

    public static void writeNewTanks(ArrayList<Tank> object, int newNumbers) throws IOException {
        int x = 0;
        write = new FileWriter("database.txt", true);
        for (Tank tank : object) {
            x += 1;
            if (newNumbers < x) {
                write.append(tank.name).append("/").append(String.valueOf(tank.capacity)).append("/").append(String.valueOf(tank.water)).append("\n");
            }
        }
        write.close();
    }

    public void setDiv(double div) {
        this.div = div;
    }

    public double getDiv() {
        return div;
    }

    public void refill(int refill) throws IOException {
        if (water + refill <= capacity) {
            changes = refill;
            this.water += refill;
            isSuccessful = true;
        } else if (water + refill > capacity) {
            changes = 0;
            System.out.println("You tried to fit too much water");
        }
        logs = new Logs(this, "refill", isSuccessful, changes);
        Logs.storingLogs(logs);
        Logs.writeLogs(logs);
    }

    public void pouring(int pouring) throws IOException {
        if (water - pouring < 0) {
            System.out.println("Not enough water to pour");
            logs = new Logs(this, "pouring", false, 0);
            Logs.storingLogs(logs);
            Logs.writeLogs(logs);
            return;
        }
        this.water -= pouring;
        logs = new Logs(this, "pouring", true, pouring);
        Logs.storingLogs(logs);
        Logs.writeLogs(logs);
    }

    public void transfer(Tank from, int transfer) throws IOException {
        if (this.water + transfer <= this.capacity && from.water >= transfer) {
            this.water += transfer;
            from.water -= transfer;
            isSuccessful = true;
            changes = transfer;
        } else if (this.water + transfer > this.capacity && from.water >= transfer) {
            System.out.println("Not enough space water in " + this.name + " Tank");
            changes = 0;
        } else if (from.water < transfer) {
            System.out.println("Not enough water in " + from.name + " Tank");
            changes = 0;
        }
        logs = new Logs(this, "transfer", isSuccessful, changes);
        logs.setFromName(from.name);
        Logs.storingLogs(logs);
        Logs.writeLogs(logs);
    }

    public static Tank findMost(ArrayList<Tank> list) { // statyczna metoda nie musi być wywołana przez kostruktor i odwołujesz sie do niej przez Tank.
        int compare = 0;
        Tank highestValue = list.get(0);
        for (Tank tank : list) {
            if (compare < tank.water) {
                highestValue = tank;
                compare = tank.water;
            }
        }
        return highestValue;
    }

    public static Tank findMax(ArrayList<Tank> list) {
        double percentage = 0;
        Tank highestPercentageValue = list.get(0);
        for (Tank tank : list) {
            double div = (double) tank.water / tank.capacity;
            if (percentage < div) {
                percentage = div;
                highestPercentageValue = tank;
            }
            tank.setDiv(div * 100);
        }
        return highestPercentageValue;

    }

    public static void findEmpty(ArrayList<Tank> list) {
        for (Tank tank : list) if (tank.water == 0) System.out.println(tank.toString());
    }

    @Override
    public String toString() {
        return "name = " + name + "\ncapacity = " + capacity + "\nwater = " + water + "\n";
    }
}