package Classes.Agents;

import Classes.Action;
import Classes.ActionUtilityPair;
import Classes.GridWorld;
import Classes.States.GridState;
import Util.ApplicationInput;

import java.util.*;

public class ModifiedPolicyIteration implements  Agent {
    private double discount = 0.0;
    private int rows = 0;
    private int cols = 0;
    private int no_of_iterations = 0;
    private boolean policyIsUnstable = false;

    private GridState[][] grid = null;
    private ActionUtilityPair[][] currActionUtilArr = null;
//    private ActionUtilityPair[][] newActionUtilArr = null;
    private ActionUtilityPair[][] optimalPolicyActionUtilArr = null;
    private List<ActionUtilityPair[][]> ListOfActionUtilityArrays = new ArrayList<>();

    public ModifiedPolicyIteration(GridWorld gridWorld, double discount, int K) {
        this.discount = discount;

        this.rows = gridWorld.getRows();
        this.cols = gridWorld.getCols();
        this.grid = gridWorld.getGrid();

        this.no_of_iterations = 0;

        // Generate Random Policy
        this.currActionUtilArr = generateRandomPolicy();
        this.ListOfActionUtilityArrays.add(this.currActionUtilArr);

        double newMaxUtility = 0.0;
        double currPolicyUtility = 0.0;
        do {
            this.no_of_iterations++;
//            this.ListOfActionUtilityArrays.add(this.optimalPolicyActionUtilArr);

            // Policy Evaluation: Update Utilities
            this.optimalPolicyActionUtilArr = policyEvaluation(this.currActionUtilArr, this.grid, K, discount);

            // Policy Improvement: Update Actions
            this.policyIsUnstable = false;
            for (int row = 0; row < this.rows; row++){
                for (int col = 0; col < this.cols; col++) {
                    // Check if GridState is Wall
                    if (!grid[row][col].isVisitable()) {continue;}

                    ActionUtilityPair chosenActionUtilityPair = getChosenActionUtilityPair(this.optimalPolicyActionUtilArr, row, col);
                    newMaxUtility = chosenActionUtilityPair.getUtility();
                    Action policyAction = this.optimalPolicyActionUtilArr[row][col].getAction();
                    currPolicyUtility = getTransitionStateActionPairUtil(this.optimalPolicyActionUtilArr, policyAction, row, col);

                    if (newMaxUtility > currPolicyUtility) {
//                        System.out.print("LALA");
//                        System.out.print("CHOSEN->" + chosenActionUtilityPair.getAction().toString() + "\n");
                        this.optimalPolicyActionUtilArr[row][col].setAction(chosenActionUtilityPair.getAction());
                        this.policyIsUnstable = true;
                    }
                }
            }
            this.currActionUtilArr = replicateUtilArray(this.optimalPolicyActionUtilArr);
            this.ListOfActionUtilityArrays.add(this.currActionUtilArr);

//            System.out.print(no_of_iterations + "LALAiterations\n");
            if (this.no_of_iterations%100==0) {break;}
        } while (this.policyIsUnstable);
//        this.ListOfActionUtilityArrays.add(this.optimalPolicyActionUtilArr);
    }

    public List<ActionUtilityPair[][]> getResults() {
        return this.ListOfActionUtilityArrays;
    }

    public ActionUtilityPair[][] getOptimalPolicy() {
        return this.ListOfActionUtilityArrays.get(this.getNoOfIterations());
    }

    public int getNoOfIterations() {
        return this.no_of_iterations;
    }


    private ActionUtilityPair[][] replicateUtilArray(ActionUtilityPair[][] src) {
        return Arrays.stream(src).map(ActionUtilityPair[]::clone).toArray(ActionUtilityPair[][]::new);
    }

    private ActionUtilityPair[][] generateRandomPolicy() {
        ActionUtilityPair[][] actionUtilArr = new ActionUtilityPair[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                if (this.grid[row][col].isVisitable()) {
                    actionUtilArr[row][col] = new ActionUtilityPair(Action.getRandAction());
//                    actionUtilArr[row][col] = new ActionUtilityPair(Action.UP);
                } else {
                    actionUtilArr[row][col] = new ActionUtilityPair();
                }
            }
        }
        return actionUtilArr;
    }

    private ActionUtilityPair[][] policyEvaluation(ActionUtilityPair[][] ActionUtilArr, GridState[][] grid, int K, double discount) {
        ActionUtilityPair[][] currActionUtilArr = replicateUtilArray(ActionUtilArr);
        ActionUtilityPair[][] newActionUtilArr = generateInitialPolicy();

        ActionUtilityPair newActionUtility = null;
        for (int i=0; i<K; i++) {
//            currActionUtilArr = replicateUtilArray(newActionUtilArr);

            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < this.cols; col++) {
                    // Check if GridState is Wall
                    if (!grid[row][col].isVisitable()) {continue;}

                    newActionUtility = getSimplifiedBellmanUpdate(row, col, currActionUtilArr, grid, discount);
                    newActionUtilArr[row][col] = newActionUtility;
                }
            }
            currActionUtilArr = replicateUtilArray(newActionUtilArr);
        }
        return newActionUtilArr;
    }

    private ActionUtilityPair[][] generateInitialPolicy() {
        ActionUtilityPair[][] actionUtilArr = new ActionUtilityPair[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                actionUtilArr[row][col] = new ActionUtilityPair();
            }
        }
        return actionUtilArr;
    }

    private ActionUtilityPair getSimplifiedBellmanUpdate(int row, int col, ActionUtilityPair[][] currActionUtilArr, GridState[][] grid, double discount) {
        Action action = currActionUtilArr[row][col].getAction();
        double currUtility = getTransitionStateActionPairUtil(currActionUtilArr, action, row, col);
        double newUtility = grid[row][col].getStateReward() + (discount * currUtility);
        return new ActionUtilityPair(action, newUtility);
    }

    private double getTransitionStateActionPairUtil(ActionUtilityPair[][] actionUtilArr, Action action, int row, int col) {
        double actionUtility = 0.000;

        double intentUtility = getUtilOfStateActionPair(actionUtilArr, action, row, col);
//        System.out.print("OK1");
        double stationaryUtility = getUtilOfStateActionPair(actionUtilArr, null, row, col);
//        System.out.print("OK2");
//        if (actionUtilArr != null) {
//            System.out.print("NULL");
//        }
//        displayActionPolicy(actionUtilArr);
//        System.out.print(action.getClockwiseAction().toString());
//        System.out.print(row);
//        System.out.print(col);
        double clockwiseUtility = getUtilOfStateActionPair(actionUtilArr, action.getClockwiseAction(), row, col);
//        System.out.print("OK3");
        double antiClockwiseUtility = getUtilOfStateActionPair(actionUtilArr, action.getAntiClockwiseAction(), row, col);
        double oppositeUtility = getUtilOfStateActionPair(actionUtilArr, action.getOppositeAction(), row, col);

        actionUtility = (ApplicationInput.PROB_INTENT * intentUtility) +
                (ApplicationInput.PROB_STATIONARY * stationaryUtility) +
                (ApplicationInput.PROB_CW * clockwiseUtility) +
                (ApplicationInput.PROB_ACW * antiClockwiseUtility) +
                (ApplicationInput.PROB_OPPOSITE * oppositeUtility);
        return actionUtility;
    }

    private double getUtilOfStateActionPair(ActionUtilityPair[][] currActionUtilArr, Action action, int row, int col) {
        int newY = row;
        int newX = col;
//        System.out.print("troll0");

        if (action != null) {
            newY = row + action.getActionY();
            newX = col + action.getActionX();
        }
//        System.out.print("troll1");
        if (newY >= 0 && newX >= 0 && newY < this.rows && newX < this.cols &&
                this.grid[newY][newX].isVisitable()) {
//            System.out.print("troll2");
//            System.out.print("\n" + currActionUtilArr[newY][newX].getUtility());
            return currActionUtilArr[newY][newX].getUtility();
        }
//        System.out.print("\nhitWall");
//        System.out.print(currActionUtilArr[row][col].getUtility());
        return currActionUtilArr[row][col].getUtility();
    }

    private ActionUtilityPair getChosenActionUtilityPair(ActionUtilityPair[][] currActionUtilArr, int row, int col) {
        List<ActionUtilityPair> listOfActionUtilityPair = new ArrayList<>();
        EnumSet.allOf(Action.class).
                forEach(action -> listOfActionUtilityPair.add(
                        new ActionUtilityPair(action, getTransitionStateActionPairUtil(currActionUtilArr, action, row, col))
                        )
                );

        ActionUtilityPair chosenActionUtilityPair = Collections.max(listOfActionUtilityPair);
//        System.out.print(chosenActionUtilityPair.getAction().toString());
        return chosenActionUtilityPair;
    }
}

