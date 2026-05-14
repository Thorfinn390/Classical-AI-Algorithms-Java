import java.util.*;

public class Dijkstra {
    
    // Dijkstra is A* where h is always 0
    static int noHeuristic() {
        return 0;
    }

    static List<Node> findPath(int[][] grid, int[] start, int[] goal) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        PriorityQueue<Node> open = new PriorityQueue<>();
        // Using a Map to track the best 'g' score for each coordinate (row,col)
        Map<String, Integer> dist = new HashMap<>(); 
        HashSet<Node> closed = new HashSet<>();

        // Initialize start node with h=0
        Node startNode = new Node(start[0], start[1], 0, noHeuristic(), null);
        open.add(startNode);
        dist.put(start[0] + "," + start[1], 0);

        while (!open.isEmpty()) {
            Node current = open.poll();

            // Goal Check
            if (current.row == goal[0] && current.col == goal[1]) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            if (closed.contains(current)) continue;
            closed.add(current);

            // 4-Directional movement
            int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

            for (int[] dir : dirs) {
                int newR = current.row + dir[0];
                int newC = current.col + dir[1];

                // Boundary check
                if (newR < 0 || newR >= rows || newC < 0 || newC >= cols) continue;

                // Wall check (using your INF convention)
                int stepCost = grid[newR][newC];
                if (stepCost == Integer.MAX_VALUE) continue;

                int tentative_g = current.g + stepCost;
                String posKey = newR + "," + newC;

                // If we found a shorter path to this neighbor
                if (tentative_g < dist.getOrDefault(posKey, Integer.MAX_VALUE)) {
                    dist.put(posKey, tentative_g);
                    // h is 0 for Dijkstra
                    Node neighbor = new Node(newR, newC, tentative_g, noHeuristic(), current);
                    open.add(neighbor);
                }
            }
        }
        return null; // No path found
    }

    public static void main(String[] args) {
        int INF = Integer.MAX_VALUE;
        // Same test grid from your AStar.java
        int[][] grid = {
                { 1, 1, 1, 1, 1 },
                { INF, 2, 2, 3, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, INF, INF, 3, 1 },
                { 1, 1, 1, 1, 1 }
        };

        int[] start = { 0, 0 };
        int[] goal = { 4, 4 };

        List<Node> path = findPath(grid, start, goal);

        if (path != null) {
            System.out.println("=== Dijkstra Path Found ===");
            for (Node n : path) {
                System.out.println("(" + n.row + "," + n.col + ") Total Cost G=" + n.g);
            }
            System.out.println("Final Path Cost: " + path.get(path.size()-1).g);
        } else {
            System.out.println("No path found.");
        }
    }
}