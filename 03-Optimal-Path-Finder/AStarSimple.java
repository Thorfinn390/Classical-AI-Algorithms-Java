// Prints: ASCII path, Total Cost g(G), Path Length (moves).

import java.util.Arrays;

public class AStarSimple {

 // Grid size and Start/Goal
   static final int N = 8;
   static final int SR = 0, SC = 0; // Start (0,0)
   static final int GR = 7, GC = 7; // Goal (7,7)

 // Tile costs
   static final int ROAD = 1, GRASS = 2, MUD = 3, BLOCKED = -1;

 // The map (filled in buildMapA)
   static int[][] grid = new int[N][N];

   public static void main(String[] args) {
      buildMapA(); // exact Map A from the lab
      runAStarSimple(); // prints the required output
   }


 // ---------------- A* with simple linear scan (no PQ) ----------------
   static void runAStarSimple() {
      final int INF = 1_000_000;
    // Best known cost to reach (r,c)
      int[][] g = new int[N][N];
    // f = g + h (priority)
      int[][] f = new int[N][N];
    // Parents for path reconstruction
      int[][] parentR = new int[N][N];
      int[][] parentC = new int[N][N];
   // Open/Closed sets
      boolean[][] open = new boolean[N][N];
      boolean[][] closed = new boolean[N][N];
    // Init
      for (int r=0; r<N; r++) {
         Arrays.fill(g[r], INF);
         Arrays.fill(f[r], INF);
         Arrays.fill(parentR[r], -1);
         Arrays.fill(parentC[r], -1);
      }
   // Start
      g[SR][SC] = 0;
      f[SR][SC] = heuristic(SR, SC);
      open[SR][SC] = true;
      while (true) {
      // (A) Pick open cell with smallest f
         int curR = -1, curC = -1, bestF = INF;
         for (int r=0; r<N; r++) {
            for (int c=0; c<N; c++) {
               if (open[r][c] && f[r][c] < bestF) {
                  bestF = f[r][c];
                  curR = r; curC = c;
               }
            }
         }
      // No open cells -> no path
         if (curR == -1) {
            System.out.println("No path found.");
            return;
         }
      // Pop current
         open[curR][curC] = false;
         if (closed[curR][curC]) 
            continue; // defensive
         closed[curR][curC] = true;
      // Goal reached -> print answer
         if (curR == GR && curC == GC) {
            printPathAndMetrics(g[GR][GC], parentR, parentC);
            return;
         }
      // (B) Relax neighbors (4-directional)
         final int[] dr = {-1, +1, 0, 0};
         final int[] dc = { 0, 0, -1, +1};
         for (int k=0; k<4; k++) {
            int nr = curR + dr[k];
            int nc = curC + dc[k];
            if (!inside(nr, nc)) 
               continue;
            if (grid[nr][nc] == BLOCKED) 
               continue;
            if (closed[nr][nc]) 
               continue;
         // newCost = cost to reach current + cost to ENTER neighbor
            int step = grid[nr][nc]; // 1, 2, or 3
            int newCost = g[curR][curC] + step;
            if (newCost < g[nr][nc]) {
               g[nr][nc] = newCost;
               parentR[nr][nc] = curR;
               parentC[nr][nc] = curC;
               f[nr][nc] = newCost + heuristic(nr, nc);
               open[nr][nc] = true;
            }
         }
      }
   }
// Manhattan heuristic (admissible for 4-neighbor moves)
   static int heuristic(int r, int c) {
      return Math.abs(r - GR) + Math.abs(c - GC);
   }
   static boolean inside(int r, int c) {
      return r >= 0 && r < N && c >= 0 && c < N;
   }

// Print ASCII map with path overlay + required metrics
   static void printPathAndMetrics(int totalCost, int[][] pr, int[][] pc) {
      char[][] canvas = new char[N][N];
   // Base tiles
      for (int r=0; r<N; r++) {
         for (int c=0; c<N; c++) {
            if (grid[r][c] == BLOCKED) 
               canvas[r][c] = '#';
            else
               if (grid[r][c] == ROAD) canvas[r][c] = '.';
               else 
                  if (grid[r][c] == GRASS) canvas[r][c] = 'g';
                  else 
                     canvas[r][c] = 'm'; // MUD
         }
      }
   // Walk parents from G to S, mark '*'
      int r = GR, c = GC, moves = 0;
      while (!(r == SR && c == SC)) {
         canvas[r][c] = '*';
         int nr = pr[r][c], nc = pc[r][c];
         if (nr == -1 || nc == -1) 
            break; // safety
         r = nr; c = nc; moves++;
      }
      canvas[SR][SC] = 'S';
      canvas[GR][GC] = 'G';
   // Print from top row so (0,0) appears bottom-left
      System.out.println("== A* (Simple Scan) Path ==");
      for (int rr = N-1; rr >= 0; rr--) {
         for (int cc = 0; cc < N; cc++) System.out.print(canvas[rr][cc] + " ");
         System.out.println();
      }
      System.out.println("Total Cost g(G): " + totalCost);
      System.out.println("Path Length : " + moves + " moves");
   }
// Exact Map A from the lab
   static void buildMapA() {
   // default: road everywhere
      for (int r=0; r<N; r++) Arrays.fill(grid[r], ROAD);
   // grass patches
      int[][] grass = {{2,1},{2,2},{2,3},{3,3},{3,4},{4,4},{1,5},{2,5},{3,5}};
      for (int[] g : grass) grid[g[0]][g[1]] = GRASS;
   // mud patches
      int[][] mud = {{5,1},{5,2},{6,2},{1,2},{1,3},{1,4}};
      for (int[] m : mud) grid[m[0]][m[1]] = MUD;
   // blocked cells
      int[][] blk = {{4,1},{4,2},{4,3},{5,3},{6,3},{6,4},{6,5},{6,6}};
      for (int[] b : blk) grid[b[0]][b[1]] = BLOCKED;
   }
}