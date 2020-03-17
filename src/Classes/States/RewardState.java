package Classes.States;

import Util.ApplicationInput;

public class RewardState extends GridState {

    public RewardState(int x, int y) {
        super(x, y);
        super.setStateType(EnumState.REWARD);
        super.setStateReward(ApplicationInput.REWARD_REWARD);
        super.setVisitable(true);
        super.setEmpty(false);
        this.setFinal(true);
    }
}
