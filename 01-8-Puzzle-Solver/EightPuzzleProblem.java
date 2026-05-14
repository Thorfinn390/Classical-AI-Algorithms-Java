import java.util.Arrays;

public class EightPuzzleProblem implements FormulatedProblem {

    private int[] initialState;
    private int[] goal;

    public EightPuzzleProblem(int[] s, int[] g) {
        this.initialState = s;
        this.goal = g;
    }

    @Override
    public Object getInitialState() {
        return initialState;
    }

    @Override
    public boolean equalStates(Object state1, Object state2) {
        return Arrays.equals((int[]) state1, (int[]) state2);
    }

    @Override
    public boolean isGoalState(Object state) {
        return Arrays.equals((int[]) state, goal);
    }

    @Override
    public SuccessorFunction getSuccessorFunction() {
        return new EightPuzzleSuccessorFunction();
    }

    @Override
    public Object getGoalState() {
        return goal; // ✅ fixed — should return the goal, not the initial state
    }

    @Override
    public double getHeuristic(Object state) {
        int[] current = (int[]) state;
        int total = 0;

        for (int i = 0; i < current.length; i++) {
            int tile = current[i];
            if (tile != 0) { // skip blank
                int currentRow = i / 3;
                int currentCol = i % 3;

                // find tile’s goal position in the goal array
                int goalIndex = findIndex(goal, tile);
                int goalRow = goalIndex / 3;
                int goalCol = goalIndex % 3;

                total += Math.abs(currentRow - goalRow) + Math.abs(currentCol - goalCol);
            }
        }
        return total;
    }

    private int findIndex(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) return i;
        }
        return -1;
    }
}
