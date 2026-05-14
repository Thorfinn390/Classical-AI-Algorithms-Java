# Classical-AI-Algorithms-Java
This repository contains a collection of foundational Artificial Intelligence projects focused on State-Space Search, Heuristic Optimization, and Adversarial Game Theory. Each project demonstrates the application of classic algorithms to solve logic puzzles and pathfinding problems.

# Projects Included

# 1. 8-Puzzle Problem Solver

A sliding puzzle solver that implements multiple search strategies to reach the goal state from a shuffled configuration.

    Algorithms: Breadth-First Search (BFS), Depth-First Search (DFS), Uniform Cost Search (UCS), and A Search* using Manhattan Distance heuristics.

    Key Concept: Comparing time and space complexity across different search frontiers.

# 2. Bridge and Torch Problem

A state-space search implementation that solves the classic "Midnight Train" riddle, where four people must cross a bridge with a single torch in under 15 minutes.

    Algorithm: A Search* using a custom heuristic function.
    
    State Representation: Uses a 5-character string (e.g., "eeeee") to track the locations of all four individuals and the torch across the East and West banks.
    
    Cost Modeling: The "Slowest Person" rule is implemented to calculate the cost ($g$) of each move, ensuring the agent finds a path that respects the 15          minute constraint.
    
    Heuristic: Employs a "remaining count" heuristic ($h$) to prioritize states where more people have successfully reached the destination.

# 3. Optimal Path Finder

A grid-based or graph-based utility to determine the shortest path between two points.

    Features: Implements Dijkstra’s and A* to handle various edge weights and obstacles.

# 4. Tic-Tac-Toe with Minimax

A classic game implementation featuring an unbeatable AI opponent.

    Algorithm: Minimax Algorithm with recursive state evaluation.

    Logic: The AI simulates all possible future moves to ensure it either wins or forces a draw, regardless of the player's move.
