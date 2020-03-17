package Util.AgentHelperMethods;

import Classes.ActionUtilityPair;
import Classes.States.GridState;
import Util.ApplicationInput;

import java.text.DecimalFormat;

import static Util.CommonUsedMethods.getSectionHeader;
import static Util.CommonUsedMethods.printSectionHeader;

public class ValueIterationHelper {

    public static void displayActionPolicy(final ActionUtilityPair[][] actionUtilityPair) {

        StringBuilder str = new StringBuilder();
        int rows = actionUtilityPair.length;
        int cols = actionUtilityPair[0].length;
        
        str.append(getSectionHeader("Action Policy", true));
        for (int row = 0; row < rows; row++) {
            if (row==0) {
                for (int r = 0; r < rows + 1; r++) {
                    str.append(r);
                    str.append("    ");
                }
                str.append("\n");
            }
            str = addShortInBetweenDesign(str, cols);
            str.append("\n");
            for (int col = 0; col < cols; col++) {
                if (col==0) {
                    str.append(row+1);
                    str.append("  ");
                }
                str.append("| " + actionUtilityPair[row][col].toString() + " ");
                if (col==rows-1) {
                    str.append("|");
                }
            }
            str.append("\n");
        }
        str = addShortInBetweenDesign(str, cols);

        System.out.println(str.toString());
    }

    public static void displayUtilities(final GridState[][] grid, final ActionUtilityPair[][] actionUtilityPair) {
        int rows = actionUtilityPair.length;
        int cols = actionUtilityPair[0].length;

        printSectionHeader("Utilities", true);
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {

                if (grid[col][row].isVisitable()) {
                    System.out.printf("(%1d,%1d) : %-2.6f%n", col, row,
                            actionUtilityPair[col][row].getUtility());
                }
            }
        }
    }

    public static void displayUtilitiesGrid(final ActionUtilityPair[][] actionUtilityPair) {
        int rows = actionUtilityPair.length;
        int cols = actionUtilityPair[0].length;
        
        String pattern = "0.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        StringBuilder str = new StringBuilder();
        double util;

        str.append(getSectionHeader("Utilities Grid", true));
        for (int row = 0; row < rows; row++) {
            if (row == 0) {
                for (int r = 0; r < rows + 1; r++) {
                    str.append(r);
                    str.append("          ");
                }
                str.append("\n");
            }
            str = addLongInBetweenDesign(str, cols);
            str.append("\n");
            for (int col = 0; col < cols; col++) {
                if (col == 0) {
                    str.append(row + 1);
                    str.append("  ");
                }
                util = actionUtilityPair[row][col].getUtility();
                if (util == 0) {
                    str.append("|  " + "██████" + "  ");
                } else if (util < 0) {
                    str.append("| " + decimalFormat.format(util) + "  ");
                } else {
                    str.append("|  " + decimalFormat.format(util) + "  ");
                }

                if (col == rows - 1) {
                    str.append("|");
                }
            }
            str.append("\n");
        }
        str = addLongInBetweenDesign(str, cols);

        System.out.println(str.toString());
    }

    public static StringBuilder addShortInBetweenDesign(StringBuilder str, int cols) {
        str.append("   ");
        for (int col = 0; col < cols; col++) {
            str.append("+————");
        }
        return str.append("+");
    }

    public static StringBuilder addLongInBetweenDesign(StringBuilder str, int cols) {
        str.append("   ");
        for (int col = 0; col < cols; col++) {
            str.append("+——————————");
        }
        return str.append("+");
    }


}
