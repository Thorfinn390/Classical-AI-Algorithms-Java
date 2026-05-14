import java.util.Arrays;

public class EightPuzzleAction implements Action {
    private String name;
    private int movedTile;

    public EightPuzzleAction(String name) {
        this.name = name;
    }

    @Override
    public Object apply(Object state) {
        int[] s = (int[]) state;
        int blankPosition = getBlankPostion(s);
        int newBlankPosition = getBlankPostion(s);
        if (name.equals("UP")) {
            newBlankPosition = blankPosition - 3;
        }
        if (name.equals("DOWN")) {
            newBlankPosition = blankPosition + 3;
        }
        if (name.equals("LEFT")) {
            newBlankPosition = blankPosition - 1;
        }
        if (name.equals("RIGHT")) {
            newBlankPosition = blankPosition + 1;
        }

        movedTile = s[newBlankPosition];

        int[] newState = Arrays.copyOf(s, s.length);
        newState[blankPosition] = s[newBlankPosition];
        newState[newBlankPosition] = 0;

        return newState;
    }

    public int getBlankPostion(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1; // dumb return, shouldn't happen
    }

    @Override
    public int getCostPerAction() {
        return 1;
    }

    public int getManhattanCost(int[] newState) {
        int tile = movedTile; 
        if (tile == 0)
            return 0;

        int index = -1;
        for (int i = 0; i < newState.length; i++) {
            if (newState[i] == tile) {
                index = i;
                break;
            }
        }

        int row = index / 3;
        int col = index % 3;

        int goalRow = (tile - 1) / 3;
        int goalCol = (tile - 1) % 3;

        return Math.abs(row - goalRow) + Math.abs(col - goalCol);
    }

    @Override
    public String toString() {
        return name; // so when you print the action, it shows "UP", "DOWN", etc.
    }

}
