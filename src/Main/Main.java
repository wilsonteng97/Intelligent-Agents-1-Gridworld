package Main;

import Classes.ActionUtilityPair;
import Classes.Agents.ValueIteration;
import Classes.GridWorld;
import Classes.States.GridState;
import Util.ApplicationInput;
import Util.FileIO;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import static Util.AgentHelperMethods.ValueIterationHelper.*;
import static Util.CommonUsedMethods.*;

public class Main {

//    public static void main(String[] args) {
//	// write your code here
//        System.out.print("Test");
//    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null,null);

        int rows = ApplicationInput.NUM_ROWS;
        int cols = ApplicationInput.NUM_COLS;
        GridWorld gridWorld = new GridWorld(rows, cols);
        GridState[][] matrix = gridWorld.getGrid();

        gridWorld.displayGridWorld();

        printHeader("Value Iteration", false);
        printSectionHeader("Parameters", true);
        printDetails("Discount: " + ApplicationInput.DISCOUNT);
        printDetails("Rmax: " + ApplicationInput.R_MAX);
        printDetails("Constant 'c': " + ApplicationInput.C);
        printDetails("Epsilon (c * Rmax): " + ApplicationInput.EPSILON);

        ValueIteration valueIteration = new ValueIteration(gridWorld,
                                                        ApplicationInput.EPSILON,
                                                        ApplicationInput.DISCOUNT);

        // Output to csv file to plot utility estimates as a function of iteration
        FileIO.writeToFile(valueIteration.getResults(), "results/", "value_iteration_utility_history");

        final ActionUtilityPair[][] optimalPolicy = valueIteration.getOptimalPolicy();

        displayActionPolicy(optimalPolicy);
        displayUtilities(matrix, optimalPolicy);
        displayUtilitiesGrid(optimalPolicy);
        print("Number of iterations: " + valueIteration.getNoOfIterations());
        print("Convergence Criteria: " + valueIteration.getConvergenceCriteria());
    }
}

