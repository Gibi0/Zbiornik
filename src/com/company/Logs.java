package com.company;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Logs {
    LocalDateTime dateTime;
    static ArrayList<Logs> list = new ArrayList<>();
    String name;
    String fromName;
    String operationType;
    boolean isSuccessful;
    int waterBefore;
    int waterAfter;
    static HashMap<String, Integer> count = new HashMap<>();
    static FileWriter writer;

    Logs(Tank object, String operationType, boolean isSuccessful, int changes) {
        this.dateTime = LocalDateTime.now();
        this.operationType = operationType;
        this.name = object.name;
        if (operationType.equals("refill")) this.waterBefore = object.water - changes;
        if (operationType.equals("pouring")) this.waterBefore = object.water + changes;
        if (operationType.equals("transfer")) this.waterBefore = object.water - changes;
        this.waterAfter = object.water;
        this.isSuccessful = isSuccessful;
    }

    public static void writeLogs(Logs object) throws IOException {
        writer = new FileWriter("log.txt", true);
        writer.append(String.valueOf(object)).append("\n");
        writer.close();
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromName() {
        if (fromName == null) fromName = "(Only in transfer operation)";
        return fromName;
    }

    public static void storingLogs(Logs e) {
        list.add(e);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(myFormatObj);
    }


    public static void gettingLogs() throws IOException {
       if(new File("log.txt").exists()){
           FileReader reader = new FileReader("log.txt");
            int data = reader.read();
            while (data !=-1){
                System.out.print((char)data);
                data = reader.read();
            }
            reader.close();
       }
       for(Logs loop: list){
           System.out.println(loop);
       }
    }

    public static void countingLogs(ArrayList<Tank> lista, String operationType) {
        int countingLogs = 0;
        if (!list.isEmpty()) {
            for (Tank loop : lista) {
                String name = loop.name;
                for (Logs logs : list) {
                    if (logs.operationType.equals(operationType) && logs.name.equals(name)) {
                        countingLogs += 1;
                    }
                }
                count.put(loop.name, countingLogs);
                countingLogs = 0;
            }
            int maxValueInMap = (Collections.max(count.values()));
            for (Map.Entry<String, Integer> entry : count.entrySet()) {
                if (entry.getValue() == maxValueInMap) {
                    System.out.println("\nTank's name: " + entry.getKey() + "\nHow many operations: " + maxValueInMap);
                }
            }
        } else {
            System.out.println("\nNo logs");
        }
    }


    public static void countingFailedLogs(ArrayList<Tank> lista) {
        int countingLogs = 0;
        if (!list.isEmpty()) {
            for (Tank loop : lista) {
                String name = loop.name;
                for (Logs logs : list) {
                    if (!logs.isSuccessful && logs.name.equals(name)) {
                        countingLogs += 1;
                    }
                }
                count.put(loop.name, countingLogs);
                countingLogs = 0;
            }
            int maxValueInMap = (Collections.max(count.values()));
            for (Map.Entry<String, Integer> entry : count.entrySet()) {
                if (entry.getValue() == maxValueInMap) {
                    System.out.println("\nTank's name: " + entry.getKey() + "\nHow many operations failed: " + maxValueInMap);
                }
            }
        } else {
            System.out.println("\nNo logs");
        }
    }

    @Override
    public String toString() {
        return "Date and Time: " + Logs.formatDateTime(dateTime) + "\nTank name: " + name + "\nFrom tank: " + getFromName() + "\nWhat was done: " + operationType + "\nWas operation successful: " + isSuccessful + "\nWater before operation: " + waterBefore + "\nWater after operation: " + waterAfter + "\n";
    }
}