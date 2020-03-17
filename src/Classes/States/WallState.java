package Classes.States;

import Util.ApplicationInput;

public class WallState extends GridState {

    public WallState(int x, int y) {
        super(x, y);
        super.setStateType(EnumState.WALL);
        super.setStateReward(ApplicationInput.WALL_REWARD);
        super.setVisitable(false);
        super.setEmpty(false);
        this.setFinal(true);
    }
}
