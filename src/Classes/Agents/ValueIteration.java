package Classes.Agents;

import Classes.Action;
import Classes.ActionUtilityPair;
import Classes.GridWorld;
import Classes.States.GridState;
import Util.ApplicationInput;

import java.util.*;

public class ValueIteration implements Agent {

    private double maximum_error_allowed = 0.0;
    private double discount = 0.0;
    private int rows = 0;
    private int cols = 0;
    private int no_of_iterations = 0;
    private double convergenceCriteria = 0.0;

    private GridState[][] grid = null;
    private ActionUtilityPair[][] currActionUtilArr = null;
    private ActionUtilityPair[][] newActionUtilArr = null;
    private List<ActionUtilityPair[][]> ListOfActionUtilityArrays = new ArrayList<>();

    public ValueIteration(GridWorld gridWorld, double epsilon, double discount) {
        this.maximum_error_allowed = epsilon;
        this.discount = discount;

        this.rows = gridWorld.getRows();
        this.cols = gridWorld.getCols();
        this.grid = gridWorld.getGrid();

        this.currActionUtilArr = new ActionUtilityPair[rows][cols];
        this.newActionUtilArr = new ActionUtilityPair[rows][cols];

//        for (int row = 0; row < rows; row++) {
//            for (int col = 0; col < cols; col++) {
//                newActionUtilArr[row][col] = new ActionUtilityPair();
//            }
//        }
        newActionUtilArr = this.generateInitialPolicy();
//        this.ListOfActionUtilityArrays.add(newActionUtilArr);

        double maxChangeInUtility = 0;
        this.convergenceCriteria = this.maximum_error_allowed * ((1-this.discount) / this.discount);

        this.no_of_iterations = 0;

        double newUtility = 0.0;
        double currUtility = 0.0;
        do {
            this.no_of_iterations++;
            this.currActionUtilArr = replicateUtilArray(newActionUtilArr);
            this.ListOfActionUtilityArrays.add(this.currActionUtilArr);
            maxChangeInUtility = 0;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    // Check if GridState is Wall
                    if (!grid[row][col].isVisitable()) {
                        continue;
                    }

                    ActionUtilityPair chosenActionUtilityPair = getChosenActionUtilityPair(row, col);

                    newUtility = this.grid[row][col].getStateReward() + (discount * chosenActionUtilityPair.getUtility());
                    currUtility = this.currActionUtilArr[row][col].getUtility();

                    chosenActionUtilityPair.setUtility(newUtility);
                    this.newActionUtilArr[row][col] = chosenActionUtilityPair;

                    if (Math.abs(newUtility - currUtility) > maxChangeInUtility) {
                        maxChangeInUtility = Math.abs(newUtility - currUtility);
                    }
                }
            }
//            if (this.no_of_iterations%1000 == 0) break;
        } while (maxChangeInUtility >= this.convergenceCriteria);
        this.ListOfActionUtilityArrays.add(newActionUtilArr);
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

    public double getConvergenceCriteria() {
        return this.convergenceCriteria;
    }

    private ActionUtilityPair[][] replicateUtilArray(ActionUtilityPair[][] src) {
        return Arrays.stream(src).map(ActionUtilityPair[]::clone).toArray(ActionUtilityPair[][]::new);
    }

    private ActionUtilityPair[][] generateInitialPolicy() {
        ActionUtilityPair[][] actionUtilArr = new ActionUtilityPair[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
//                if (this.grid[row][col].isVisitable()) {
//                    actionUtilArr[row][col] = new ActionUtilityPair(Action.getRandAction());
//                } else {
//                    actionUtilArr[row][col] = new ActionUtilityPair();
//                }
                actionUtilArr[row][col] = new ActionUtilityPair();
            }
        }
        return actionUtilArr;
    }

    private ActionUtilityPair getChosenActionUtilityPair(int row, int col) {
        List<ActionUtilityPair> listOfActionUtilityPair = new ArrayList<>();
        EnumSet.allOf(Action.class).
                forEach(action -> listOfActionUtilityPair.add(
                                new ActionUtilityPair(action, getTransitionStateActionPairUtil(action, row, col))
                        )
                );

        ActionUtilityPair chosenActionUtilityPair = Collections.max(listOfActionUtilityPair);
        return chosenActionUtilityPair;
    }

    private double getTransitionStateActionPairUtil(Action action, int row, int col) {
        double actionUtility = 0.000;

        double intentUtility = getUtilOfStateActionPair(action, row, col);
        double stationaryUtility = getUtilOfStateActionPair(null, row, col);
        double clockwiseUtility = getUtilOfStateActionPair(action.getClockwiseAction(), row, col);
        double antiClockwiseUtility = getUtilOfStateActionPair(action.getAntiClockwiseAction(), row, col);
        double oppositeUtility = getUtilOfStateActionPair(action.getOppositeAction(), row, col);

        actionUtility = (ApplicationInput.PROB_INTENT * intentUtility) +
                        (ApplicationInput.PROB_STATIONARY * stationaryUtility) +
                        (ApplicationInput.PROB_CW * clockwiseUtility) +
                        (ApplicationInput.PROB_ACW * antiClockwiseUtility) +
                        (ApplicationInput.PROB_OPPOSITE * oppositeUtility);
        return actionUtility;
    }

    private double getUtilOfStateActionPair(Action action, int row, int col) {
        int newY = row;
        int newX = col;

        if (action != null) {
            newY = row + action.getActionY();
            newX = col + action.getActionX();
        }

        if (newY >= 0 && newX >= 0 && newY < this.rows && newX < this.cols &&
                this.grid[newY][newX].isVisitable()) {
            return this.currActionUtilArr[newY][newX].getUtility();
        }

        return this.currActionUtilArr[row][col].getUtility();
    }
}
