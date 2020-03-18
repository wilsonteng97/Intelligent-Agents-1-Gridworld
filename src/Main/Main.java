package Main;

import Classes.ActionUtilityPair;
import Classes.Agents.ModifiedPolicyIteration;
import Classes.Agents.ValueIteration;
import Classes.GridWorld;
import Classes.States.GridState;
import Util.ApplicationInput;
import Util.FileIO;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import static Util.AgentHelperMethods.DisplayHelper.*;
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

        // Value Iteration
        printHeader("[1] Value Iteration", false);
        printSectionHeader("Parameters", true);
        printDetails("Discount : " + ApplicationInput.DISCOUNT);
        printDetails("Rmax : " + ApplicationInput.R_MAX);
        printDetails("Constant(c) : " + ApplicationInput.C);
        printDetails("Epsilon(c*Rmax) : " + ApplicationInput.EPSILON);

        ValueIteration valueIteration = new ValueIteration(gridWorld,
                                                        ApplicationInput.EPSILON,
                                                        ApplicationInput.DISCOUNT);

        final ActionUtilityPair[][] optimalPolicy_VI = valueIteration.getOptimalPolicy();

        displayActionPolicy(optimalPolicy_VI);
//        displayUtilities(matrix, optimalPolicy_VI);
        displayUtilitiesGrid(optimalPolicy_VI);
        print("Number of iterations: " + valueIteration.getNoOfIterations());
        print("Convergence Criteria: " + valueIteration.getConvergenceCriteria());

        // Output to csv file to plot utility estimates as a function of iteration
        FileIO.writeToFile(valueIteration.getResults(), "results/", "value_iteration_utility_history");

        // Policy Iteration
        printHeader("[2] Policy Iteration", false);
        printSectionHeader("Parameters", true);
        printDetails("Discount : " + ApplicationInput.DISCOUNT);
        printDetails("K : " + ApplicationInput.K +
                "\n(i.e. Simplified Bellman Update is repeated K times per state to produce next utility estimate)");

        ModifiedPolicyIteration mPolicyIteration = new ModifiedPolicyIteration(gridWorld,
                ApplicationInput.DISCOUNT,
                ApplicationInput.K);

        final ActionUtilityPair[][] optimalPolicy_MPI = mPolicyIteration.getOptimalPolicy();

        displayActionPolicy(optimalPolicy_MPI);
        displayUtilities(matrix, optimalPolicy_MPI);
        displayUtilitiesGrid(optimalPolicy_MPI);
        print("Number of iterations: " + mPolicyIteration.getNoOfIterations());

        // Output to csv file to plot utility estimates as a function of iteration
        FileIO.writeToFile(mPolicyIteration.getResults(), "results/", "modified_policy_iteration_utility_history");

        // Check if policies obtained are the same
        checkSamePolicy(optimalPolicy_VI, optimalPolicy_MPI);
    }
}

