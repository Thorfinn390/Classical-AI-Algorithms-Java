public interface FormulatedProblem {
    Object getInitialState();
    boolean equalStates(Object state1, Object state2);
    boolean isGoalState(Object state);
    SuccessorFunction getSuccessorFunction();
    double getHeuristic(Object state); //for AStar
    Object getGoalState();

}
