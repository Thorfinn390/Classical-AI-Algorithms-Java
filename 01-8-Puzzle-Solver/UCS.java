import java.util.*;

public class UCS implements SearchAlgorithm {
    private PriorityQueue<Node> frontier;
    private HashSet<String> visited;

    public UCS() {
        frontier = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        visited = new HashSet<>();
    }

    @Override
    public void solve(FormulatedProblem p) {
   
        int nodesExamined = 0;
        int maxQueueSize = 0;
        boolean solutionFound = false;

        Object initialState = p.getInitialState();
        Node initialNode = new Node(initialState, null, 0, null);
        addToFrontier(initialNode);
        visited.add(Arrays.toString((int[]) initialState));

        while (!frontier.isEmpty()) {
            Node nodeToExplore = frontier.poll();
            nodesExamined++; 

            if (frontier.size() > maxQueueSize) {
                maxQueueSize = frontier.size();
            }

            Object stateToExplore = nodeToExplore.getState();

            if (p.isGoalState(stateToExplore)) {
                solutionFound = true;
                System.out.println("Goal found with cost " + nodeToExplore.getCost());
                System.out.println("Path:");
                nodeToExplore.printPath(nodeToExplore);
                break; 
            }

            SuccessorFunction succFunc = p.getSuccessorFunction();
            ArrayList<Action> actions = succFunc.getPossibleApplicableActions(stateToExplore);

            for (Action action : actions) {
                Object successorState = action.apply(stateToExplore);
                String stateStr = Arrays.toString((int[]) successorState);

                if (!visited.contains(stateStr)) {
                    EightPuzzleAction epAction = (EightPuzzleAction) action;
                    int manhattanCost = epAction.getManhattanCost((int[]) successorState);

                    int moveCost = 1 + manhattanCost; 

                    Node successorNode = new Node(
                            successorState,
                            nodeToExplore,
                            nodeToExplore.getCost() + moveCost,
                            action);
                    addToFrontier(successorNode);
                    visited.add(stateStr);
                }
            }
        }

        if (!solutionFound) {
            System.out.println("No solution found.");
        }

        System.out.println("The number of nodes examined in UCS is: " + nodesExamined);
        System.out.println("The maximum queue size: " + maxQueueSize);
        System.out.println("Solution found: " + solutionFound);
    }

    private void addToFrontier(Node node) {
        frontier.add(node);
    }
}
