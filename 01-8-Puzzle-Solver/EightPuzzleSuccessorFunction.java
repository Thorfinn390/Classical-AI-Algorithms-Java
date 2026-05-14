import java.util.ArrayList;

public class EightPuzzleSuccessorFunction implements SuccessorFunction {

    private Action UP = new EightPuzzleAction("UP");
    private Action DOWN = new EightPuzzleAction("DOWN");
    private Action LEFT = new EightPuzzleAction("LEFT");
    private Action RIGHT = new EightPuzzleAction("RIGHT");

    @Override
    public ArrayList<Action> getPossibleApplicableActions(Object state) {
        int[] s = (int[]) state;
        int blankPosition = getBlankPosition(s);

        ArrayList<Action> actions = new ArrayList<>();

        if (blankPosition >= 3) actions.add(UP);         
        if (blankPosition <= 5) actions.add(DOWN);       
        if (blankPosition % 3 != 0) actions.add(LEFT);   
        if (blankPosition % 3 != 2) actions.add(RIGHT);  

        return actions;
    }

    private int getBlankPosition(int[] state) {
       for(int i=0;i<state.length;i++){
            if(state[i]==0){
                return i;
            }
        }
        return 0; //dumb return
    }
}
