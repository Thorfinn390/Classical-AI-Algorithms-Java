import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;

public class DFS implements SearchAlgorithm {
    private ArrayList<Node> stack;
    private HashSet<String> visited; // track visited states as Strings

    public DFS() {
        stack = new ArrayList<>();
        visited = new HashSet<>();
    }

    @Override
    public void solve(FormulatedProblem p) {
    
        int nodesExamined = 0;
        int maxQueueSize = 0;
        boolean solutionFound = false;

        Object initialState = p.getInitialState();
        Node initialNode = new Node(initialState, null, 0, null);
        pushNode(initialNode);
        visited.add(Arrays.toString((int[]) initialState));

        while (!stack.isEmpty()) {
            Node nodeToExplore = popNextNode();
            nodesExamined++; 

            if (stack.size() > maxQueueSize) {
                maxQueueSize = stack.size();
            }

            Object stateToExplore = nodeToExplore.getState();

            if (p.isGoalState(stateToExplore)) {
                solutionFound = true;
                System.out.println("Goal found! Path:");
                nodeToExplore.printPath(nodeToExplore);
                break; 
            }

            SuccessorFunction succFunc = p.getSuccessorFunction();
            ArrayList<Action> actions = succFunc.getPossibleApplicableActions(stateToExplore);

            for (Action action : actions) {
                Object successorState = action.apply(stateToExplore);
                String stateStr = Arrays.toString((int[]) successorState);

                if (!visited.contains(stateStr)) {
                    Node successorNode = new Node(
                            successorState,
                            nodeToExplore,
                            nodeToExplore.getCost() + action.getCostPerAction(),
                            action
                    );
                    pushNode(successorNode);
                    visited.add(stateStr); // mark as visited
                }
            }
        }

        if (!solutionFound) {
            System.out.println("No solution found.");
        }

        System.out.println("The number of nodes examined in DFS is: " + nodesExamined);
        System.out.println("The maximum queue size: " + maxQueueSize);
        System.out.println("Solution found: " + solutionFound);
    }

    private void pushNode(Node node) {
        stack.add(node);
    }

    private Node popNextNode() {
        return stack.remove(stack.size() - 1);
    }
}
