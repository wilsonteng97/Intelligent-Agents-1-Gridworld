package Classes.States;

import static Util.CommonUsedMethods.generateRandomInt;

public enum EnumState {
    START("AG"),
    EMPTY("  "),
    PENALTY("——"),
    REWARD("+R"),
//    REWARD(new String(Character.toChars(0x221A))), // √
    WALL("██");
//    WALL(new String(Character.toChars(0x2588))); // █

    private String strRep;
    EnumState(String strRep) {
        this.strRep = strRep;
    }

    @Override
    public String toString() {
        return this.strRep;
    }

    private static final EnumState[] AVAILABLE_STATES = values();
    private static final int NO_OF_STATES = AVAILABLE_STATES.length;

    public static EnumState generateRandState() {
        /** Randomly generates a state with the exception of EnumState.START */
        return AVAILABLE_STATES[generateRandomInt(1, NO_OF_STATES)];
    }
}
