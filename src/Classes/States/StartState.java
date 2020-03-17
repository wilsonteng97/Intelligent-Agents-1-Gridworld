package Classes.States;

import Util.ApplicationInput;

public class StartState extends GridState{

    public StartState(int x, int y) {
        super(x, y);
        super.setStateType(EnumState.START);
        super.setStateReward(ApplicationInput.EMPTY_REWARD);
        super.setVisitable(true);
        super.setEmpty(false);
    }
}
