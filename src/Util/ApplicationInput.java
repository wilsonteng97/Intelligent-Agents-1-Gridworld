package Util;

public class ApplicationInput {
    // Extra Parameters to increase complexity
    public static final boolean setRandStartState = false;
    public static final boolean setRandStateGenerator = false;
    // Applicable when setRandStateGenerator is true
    protected int allowedNoOfStarts = 1;
    protected int allowedNoOfWalls = 5;
    protected int allowedNoOfRewards = 6;
    protected int allowedNoOfPenalties = 5;

    // Size of the Grid World
    public static final int NUM_COLS = 6;
    public static final int NUM_ROWS = 6;

    // Reward functions
    public static final double EMPTY_REWARD = -0.040;
    public static final double REWARD_REWARD = +1.000;
    public static final double PENALTY_REWARD = -1.000;
    public static final double WALL_REWARD = 0.000;

    /// Transition model
    public static final double PROB_INTENT = 0.800;
    public static final double PROB_STATIONARY = 0.000;
    public static final double PROB_CW = 0.100;
    public static final double PROB_ACW = 0.100;
    public static final double PROB_OPPOSITE = 0.000;
//    public static final double PROB_STATIONARY = 0.060;
//    public static final double PROB_CW = 0.060;
//    public static final double PROB_ACW = 0.060;
//    public static final double PROB_OPPOSITE = 0.020;

    // Delimiters
    public static final String GRID_DELIM = ";";
    public static final String COL_ROW_DELIM = ",";

    // Grid World information in (col, row) format delimited by semi-colon
    public static final String START_TILE = "3,2";
    public static final String REWARD_TILES = "0,0; 0,2; 0,5; 1,3; 2,4; 3,5";
    public static final String PENALTY_TILES = "1,1; 1,5; 2,2; 3,3; 4,4";
    public static final String WALLS_TILES = "0,1; 1,4; 4,1; 4,2; 4,3";

    // Agent's starting position
    // NOTE: A remarkable consequence of using discounted utilities with infinite
    // horizons is that the optimal policy is independent of the starting state
    public static final int AGENT_START_ROW = 3;
    public static final int AGENT_START_COL = 2;

    // Discount factor
    public static final double DISCOUNT =  0.990;

    // Rmax
    public static final double R_MAX = 1.000;

    // Constant c
    public static final double C = 0.100;	// FIXME: What to use for constant c

    // Epsilon e = c * Rmax
    public static final double EPSILON = C * R_MAX;

    // Constant k (i.e. number of times simplified Bellman update is executed
    // to produce the next utility estimate)
    public static final int K = 30;
}
