import java.util.*;

public class BridgeAndTorch {

    // Each person’s crossing time
    static Map<Character, Integer> time = Map.of(
        'A', 1,
        'B', 2,
        'C', 5,
        'D', 8
    );

    // Heuristic: count of people still on East (torch doesn’t matter)
    static int heuristic(String loc) {
        int h = 0;
        for (char c : loc.toCharArray()) {
            if (c == 'e') h++;
        }
        return h;
    }

    // --- Generate possible next states ---
    static List<State> getSuccessors(State current) {
        List<State> successors = new ArrayList<>();
        char[] s = current.location.toCharArray();

        boolean torchEast = (s[4] == 'e');   // where is the torch?
        char newSide = torchEast ? 'w' : 'e';

        // Who’s on the same side as the torch
        List<Character> people = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (s[i] == (torchEast ? 'e' : 'w')) {
                people.add((char)('A' + i));
            }
        }

        // Try all 1-person and 2-person groups
        for (int i = 0; i < people.size(); i++) {
            for (int j = i; j < people.size(); j++) {
                List<Character> group = new ArrayList<>();
                group.add(people.get(i));
                if (j != i) group.add(people.get(j));

                // Create new state by flipping their side + torch side
                char[] newS = Arrays.copyOf(s, s.length);
                for (char p : group) {
                    int idx = p - 'A';
                    newS[idx] = newSide;
                }
                newS[4] = newSide;

                // Cost = slowest person
                int cost = group.stream().mapToInt(p -> time.get(p)).max().getAsInt();

                // Make readable action text
                String moveText = "Move " + group + " from " + (torchEast ? "East→West" : "West→East")
                                  + " (+" + cost + " min)";

                State next = new State(new String(newS),
                                       current.g + cost,
                                       heuristic(new String(newS)),
                                       moveText,
                                       current);
                // Only add legal states (time ≤ 15)
                if (next.g <= 15) successors.add(next);
            }
        }
        return successors;
    }

    // --- A* Search ---
    static List<State> AStar(String start, String goal) {
        PriorityQueue<State> open = new PriorityQueue<>();
        Set<String> closed = new HashSet<>();

        State startState = new State(start, 0, heuristic(start), "Start", null);
        open.add(startState);

        while (!open.isEmpty()) {
            State current = open.poll();

            if (current.location.equals(goal)) {
                // reconstruct path
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(0, current);
                    current = current.parent;
                }
                return path;
            }

            closed.add(current.location);

            for (State next : getSuccessors(current)) {
                if (!closed.contains(next.location))
                    open.add(next);
            }
        }
        return null; // no path
    }

    // --- MAIN ---
    public static void main(String[] args) {
        String start = "eeeee"; // everyone East + torch East
        String goal = "wwwww"; // everyone West + torch West

        List<State> path = AStar(start, goal);

        if (path == null) {
            System.out.println("No solution found.");
            return;
        }

        System.out.println("=== Bridge and Torch Solution (≤15 min) ===");
        for (State s : path) {
            System.out.println(s.action + " → " + s.location + "  Total=" + s.g + " min");
        }

        System.out.println("-------------------------------------------");
        System.out.println("Total Time = " + path.get(path.size() - 1).g + " minutes");
    }
}
