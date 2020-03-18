package Util;

import Classes.ActionUtilityPair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class FileIO {
    public static void writeToFile(List<ActionUtilityPair[][]> ListOfActionUtilityArrays, String directory, String fileName) {

        StringBuilder sb = new StringBuilder();
        String pattern = "00.0000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        for (int col = 0; col < ApplicationInput.NUM_COLS; col++) {
            for (int row = 0; row < ApplicationInput.NUM_ROWS; row++) {

                Iterator<ActionUtilityPair[][]> iter = ListOfActionUtilityArrays.iterator();
                while(iter.hasNext()) {

                    ActionUtilityPair[][] actionUtilityPair = iter.next();
                    sb.append(decimalFormat.format(
                            actionUtilityPair[row][col].getUtility()), 0, 7);

                    if(iter.hasNext()) {
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
        }
//        File dir = new File("results/");
//        dir.mkdirs();
        writeToFile(sb.toString().trim(), directory,  fileName + ".csv");
    }

    public static void writeToFile(String content, String directory, String fileName)
    {
        try
        {
            File dir = new File(directory);
            dir.mkdirs();
            String filePath = directory + fileName;
            FileWriter fw = new FileWriter(new File(filePath), false);

            fw.write(content);
            fw.close();

//            System.out.println("\nSuccessfully saved results to \"" + filePath + "\".");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
