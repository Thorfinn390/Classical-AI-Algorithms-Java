import java.util.*;
import java.util.PriorityQueue;

public class AStar implements SearchAlgorithm {
    private PriorityQueue<Node> openList;
    private HashSet<String> visited;

    public AStar() {
        // Order nodes by f(n) = g(n) + h(n)
        openList = new PriorityQueue<>(Comparator.comparingDouble(this::f));
        visited = new HashSet<>();
    }

    private double f(Node n) {
        return n.getCost() + n.getHeuristic(); // g(n) + h(n)
    }

    @Override
    public void solve(FormulatedProblem p) {
        int nodesExamined = 0;
        int maxQueueSize = 0;
        boolean solutionFound = false;

        Object initialState = p.getInitialState();
        Node initialNode = new Node(initialState, null, 0, null);
        initialNode.setHeuristic(p.getHeuristic(initialState)); // Add heuristic

        pushNode(initialNode);
        visited.add(Arrays.toString((int[]) initialState));

        while (!openList.isEmpty()) {
            Node current = popNextNode();
            nodesExamined++;

            if (openList.size() > maxQueueSize)
                maxQueueSize = openList.size();

            Object currentState = current.getState();

            if (p.isGoalState(currentState)) {
                solutionFound = true;
                System.out.println("Goal found! Path:");
                current.printPath(current);
                break;
            }

            SuccessorFunction succFunc = p.getSuccessorFunction();
            ArrayList<Action> actions = succFunc.getPossibleApplicableActions(currentState);

            for (Action action : actions) {
                Object successorState = action.apply(currentState);
                String stateStr = Arrays.toString((int[]) successorState);

                if (!visited.contains(stateStr)) {
                    Node successor = new Node(
                            successorState,
                            current,
                            current.getCost() + action.getCostPerAction(),
                            action
                    );
                    successor.setHeuristic(p.getHeuristic(successorState));
                    pushNode(successor);
                    visited.add(stateStr);
                }
            }
        }

        if (!solutionFound) {
            System.out.println("No solution found.");
        }

        System.out.println("The number of nodes examined in A*: " + nodesExamined);
        System.out.println("The maximum queue size: " + maxQueueSize);
        System.out.println("Solution found: " + solutionFound);
    }

    private void pushNode(Node node) {
        openList.add(node);
    }

    private Node popNextNode() {
        return openList.poll();
    }
}
