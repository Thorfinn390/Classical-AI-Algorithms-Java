import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    private Object state;
    private Node parent;
    private int pathCost;
    private Action action;

    private double heuristic; //for AStar
    

    public Node(Object state,Node parent,int pathCost,Action action){
        this.state=state;
        this.parent=parent;
        this.pathCost=pathCost;
        this.action=action;
    }

    public Object getState(){
        return this.state;
    }

    public Node getParent(){
        return this.parent;
    }

    public int getCost(){
        return this.pathCost;
    }

    public Action getAction(){
        return this.action;
    }
    public void printPath(Node goalNode) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = goalNode;
        while (current != null) {
            path.add(0, current); 
            current = current.getParent();
        }
        for (Node node : path) {
        System.out.println(node);
        }

    }

   

    public double getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    
    @Override
    public String toString() {
        return "Action=" + (action != null ? action : "START") +
            ", State=" + Arrays.toString((int[]) state) +
            ", Cost=" + pathCost;
    }



}
