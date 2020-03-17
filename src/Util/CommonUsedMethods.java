package Util;

import java.util.Random;

public class CommonUsedMethods {
    private static String convertToUpperCase(String msg, boolean toUpperCase) {
        if (toUpperCase) {
            return msg.toUpperCase();
        }
        return msg;
    }

    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void print(String msg) {
        System.out.println(msg);
    }

    public static String getHeader(String msg, boolean toUpperCase) {
        StringBuilder str = new StringBuilder();
        msg = convertToUpperCase(msg, toUpperCase);
        str.append("\n▒▒▒▒▒▒▒▒▒ " + msg + " ▒▒▒▒▒▒▒▒▒\n");
        return str.toString();
    }

    public static String getSectionHeader(String msg, boolean toUpperCase) {
        StringBuilder str = new StringBuilder();
        msg = convertToUpperCase(msg, toUpperCase);
        str.append("===========" + msg + "===========\n");
        return str.toString();
    }

    public static String getDetails(String msg) {
        StringBuilder str = new StringBuilder();
        str.append("> ");
        str.append(msg + "\n");
        return str.toString();
    }

    public static void printHeader(String msg, boolean toUpperCase) {
        msg = getHeader(msg, toUpperCase);
        System.out.print(msg);
    }

    public static void printSectionHeader(String msg, boolean toUpperCase) {
        msg = getSectionHeader(msg, toUpperCase);
        System.out.print(msg);
    }

    public static void printDetails(String msg) {
        msg = getDetails(msg);
        System.out.print(msg);
    }
}
