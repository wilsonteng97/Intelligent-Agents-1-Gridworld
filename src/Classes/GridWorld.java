package Classes;

import Classes.States.*;
import Util.ApplicationInput;
import static Util.CommonUsedMethods.generateRandomInt;
import static Util.CommonUsedMethods.getHeader;

public class GridWorld {
    protected GridState[][] matrix;
    protected boolean setRandStartState = ApplicationInput.setRandStartState;
    protected int allowedNoOfStarts = 1;
    protected int allowedNoOfWalls = 5;
    protected int allowedNoOfRewards = 6;
    protected int allowedNoOfPenalties = 5;

    protected int noOfStarts = 1;
    protected int noOfWalls = 5;
    protected int noOfRewards = 6;
    protected int noOfPenalties = 5;

    public GridWorld(int rows, int cols, int scale) {
        buildGrid(rows, cols, scale);
    }

    public GridState[][] getGrid() {
        return this.matrix;
    }

    private void buildGrid(int rows, int cols, int scale) {
        matrix = new GridState[rows][cols];
        for (int row=0; row<getRows(); row++) {
            for (int col=0; col<getCols(); col++) {
                matrix[row][col] = new EmptyState(row, col);
            }
        }

        // Start Tile Initialisation
        String[] startTileCoord = ApplicationInput.START_TILE.split(ApplicationInput.COL_ROW_DELIM);
        int startTileRow = Integer.parseInt(startTileCoord[0]);
        int startTileCol = Integer.parseInt(startTileCoord[1]);
        if (setRandStartState) {
            startTileRow = generateRandomInt(0, this.getRows());
            startTileCol = generateRandomInt(0, this.getCols());
        }
        matrix[startTileRow][startTileCol] = new StartState(startTileRow, startTileCol);

        // Reward Tile Initialisation
        String[] rewardTileLs = ApplicationInput.REWARD_TILES.split(ApplicationInput.GRID_DELIM);
        for (String rewardTile : rewardTileLs) {
            rewardTile = rewardTile.trim();
            String[] gridCoord = rewardTile.split(ApplicationInput.COL_ROW_DELIM);
            int rewardTileRow = Integer.parseInt(gridCoord[0]);
            int rewardTileCol = Integer.parseInt(gridCoord[1]);
            matrix[rewardTileRow][rewardTileCol] = new RewardState(rewardTileRow, rewardTileCol);
        }
        // Penalty Tile Initialisation
        String[] penaltyTileLs = ApplicationInput.PENALTY_TILES.split(ApplicationInput.GRID_DELIM);
        for (String penaltyTile : penaltyTileLs) {
            penaltyTile = penaltyTile.trim();
            String[] gridCoord = penaltyTile.split(ApplicationInput.COL_ROW_DELIM);
            int penaltyTileRow = Integer.parseInt(gridCoord[0]);
            int penaltyTileCol = Integer.parseInt(gridCoord[1]);
            matrix[penaltyTileRow][penaltyTileCol] = new PenaltyState(penaltyTileRow, penaltyTileCol);
        }
        // Wall Tile Initialisation
        String[] wallTileLs = ApplicationInput.WALLS_TILES.split(ApplicationInput.GRID_DELIM);
        for (String wallTile : wallTileLs) {
            wallTile = wallTile.trim();
            String[] gridCoord = wallTile.split(ApplicationInput.COL_ROW_DELIM);
            int wallTileRow = Integer.parseInt(gridCoord[0]);
            int wallTileCol = Integer.parseInt(gridCoord[1]);
            matrix[wallTileRow][wallTileCol] = new WallState(wallTileRow, wallTileCol);
        }
    }

    public int getRows() {
        return matrix.length;
    }

    public int getCols() {
        return matrix[0].length;
    }

    public StringBuilder addInBetweenDesign(StringBuilder str) {
        str.append("   ");
        for (int col = 0; col < getCols(); col++) {
            str.append("+————");
        }
        return str.append("+");
    }
    public String toString(Boolean isSymbol) {
        StringBuilder str = new StringBuilder();

        if (isSymbol) {
//            str.append("Symbol Matrix:\n");
            str.append(getHeader("Symbol Matrix:", false));
            for (int row = 0; row < getRows(); row++) {
                if (row==0) {
                    for (int r = 0; r < getRows() + 1; r++) {
                        if (r-1 < 0) {
                            str.append("     "); continue;
                        }
                        str.append(r-1);
                        str.append("    ");
                    }
                    str.append("\n");
                }
                str = addInBetweenDesign(str);
                str.append("\n");
                for (int col = 0; col < getCols(); col++) {
                    if (col==0) {
                        str.append(row);
                        str.append("  ");
                    }
                    str.append(matrix[row][col].toString(true));
                    if (col==getRows()-1) {
                        str.append("|");
                    }
                }
                str.append("\n");
            }
            str = addInBetweenDesign(str);
        } else {
            str.append("Matrix:\n");
            for (int row = 0; row < getRows(); row++) {
                for (int col = 0; col < getCols(); col++) {
                    str.append(matrix[row][col].toString(false) + ", ");
                }
                str.append("\n");
            }
        }
        return str.toString();
    }

    public void displayGridWorld() {
        System.out.println(this.toString(true));
    }
}
