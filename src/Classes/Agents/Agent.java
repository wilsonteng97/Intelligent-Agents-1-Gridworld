package Classes.Agents;

import Classes.ActionUtilityPair;

import java.util.List;

public interface Agent {
    List<ActionUtilityPair[][]> getResults();

    ActionUtilityPair[][] getOptimalPolicy();

    int getNoOfIterations();
}
