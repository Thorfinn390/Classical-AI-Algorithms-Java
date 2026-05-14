import java.util.*;
public class Node implements Comparable<Node>{

    int row,col;
    int f,g,h;
    Node parent;
    public Node(int row, int col, int g, int h, Node parent) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.f = g + h;
        this.h = h;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other){
        return Integer.compare(this.f, other.f);
    }

    @Override 
    public int hashCode() {
        return Objects.hash(row,col);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return this.row == other.row && this.col == other.col;
    }
}