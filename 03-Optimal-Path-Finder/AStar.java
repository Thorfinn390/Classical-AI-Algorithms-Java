import java.util.*;

public class AStar {
    static int heuristic(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

    static List<Node> aStar(int[][] grid, int[] start, int[] goal) {
        int rows = grid.length;
        int cols = grid[0].length;
        PriorityQueue<Node> open = new PriorityQueue<>();
        HashSet<Node> closed = new HashSet<>();
        Node startNode = new Node(start[0], start[1], 0, heuristic(start[0], start[1], goal[0], goal[1]), null);
        open.add(startNode);

        while (!(open.isEmpty())) {
            Node current = open.poll();

            if (current.row == goal[0] && current.col == goal[1]) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closed.add(current);

            int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

            for (int[] dir : dirs) {
                int newR = current.row + dir[0];
                int newC = current.col + dir[1];

                if (newR < 0 || newR >= rows || newC < 0 || newC >= cols)
                    continue;

                int cost = grid[newR][newC];
                if (cost == Integer.MAX_VALUE)
                    continue;

                int tentative_g = current.g + cost;
                int h = heuristic(newR, newC, goal[0], goal[1]);

                Node neighbor = new Node(newR, newC, tentative_g, h, current);

                if (closed.contains(neighbor))
                    continue;

                boolean skip = false;
                for (Node openNode : open) {
                    if (openNode.equals(neighbor) && openNode.f <= neighbor.f) {
                        skip = true;
                        break;
                    }
                }
                if (!skip)
                    open.add(neighbor);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // 1=normal, 2=grass, 3=mud, INF=wall
        int INF = Integer.MAX_VALUE;
        int[][] grid = {
                { 1, 1, 1, 1, 1 },
                { INF, 2, 2, 3, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, INF, INF, 3, 1 },
                { 1, 1, 1, 1, 1 }
        };

        int[] start = { 0, 0 };
        int[] goal = { 4, 4 };

        List<Node> path = aStar(grid, start, goal);

        if (path != null) {
            int totalCost = 0;
            for (Node n : path) {
                totalCost = n.g;
                System.out.println("(" + n.row + "," + n.col + ") cost=" + n.g);
            }
            System.out.println("Total path cost = " + totalCost);
        } else {
            System.out.println("No path found.");
        }
    }
}
