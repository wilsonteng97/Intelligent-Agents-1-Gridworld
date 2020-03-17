package Classes.States;

import Util.ApplicationInput;

public class PenaltyState extends GridState {

    public PenaltyState(int x, int y) {
        super(x, y);
        super.setStateType(EnumState.PENALTY);
        super.setStateReward(ApplicationInput.PENALTY_REWARD);
        super.setVisitable(true);
        super.setEmpty(false);
        this.setFinal(true);
    }
}
