package Classes.States;

import Util.ApplicationInput;

public class EmptyState extends GridState{

    public EmptyState(int x, int y) {
        super(x, y);
        super.setStateType(EnumState.EMPTY);
        super.setStateReward(ApplicationInput.EMPTY_REWARD);
        super.setVisitable(true);
        super.setEmpty(true);
    }
}
