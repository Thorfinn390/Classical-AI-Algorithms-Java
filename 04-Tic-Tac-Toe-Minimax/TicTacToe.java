import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private static final char PLAYER = 'O';
    private static final char AI = 'X';
    private static final char EMPTY = ' ';

    public TicTacToe() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) board[i][j] = EMPTY;
        }
    }

    // Main Game Loop
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe: You are 'O', AI is 'X'");
        printBoard();

        while (true) {
            // Human Turn
            playerMove(scanner);
            printBoard();
            if (checkGameOver()) break;

            // AI Turn
            System.out.println("AI is thinking...");
            int[] bestMove = minimax(board, 0, true);
            board[bestMove[1]][bestMove[2]] = AI;
            printBoard();
            if (checkGameOver()) break;
        }
    }

    private void playerMove(Scanner scanner) {
        int r, c;
        while (true) {
            System.out.print("Enter row and col (0-2) separated by space: ");
            r = scanner.nextInt();
            c = scanner.nextInt();
            if (r >= 0 && r < 3 && c >= 0 && c < 3 && board[r][c] == EMPTY) {
                board[r][c] = PLAYER;
                break;
            }
            System.out.println("Invalid move, try again.");
        }
    }

    // Minimax Algorithm
    private int[] minimax(char[][] currentBoard, int depth, boolean isMax) {
        int score = evaluate(currentBoard);

        if (score == 10) return new int[]{score - depth, -1, -1};
        if (score == -10) return new int[]{score + depth, -1, -1};
        if (!isMovesLeft(currentBoard)) return new int[]{0, -1, -1};

        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == EMPTY) {
                    currentBoard[i][j] = isMax ? AI : PLAYER;
                    int currentScore = minimax(currentBoard, depth + 1, !isMax)[0];
                    currentBoard[i][j] = EMPTY;

                    if (isMax) {
                        if (currentScore > bestScore) {
                            bestScore = currentScore;
                            bestRow = i;
                            bestCol = j;
                        }
                    } else {
                        if (currentScore < bestScore) {
                            bestScore = currentScore;
                            bestRow = i;
                            bestCol = j;
                        }
                    }
                }
            }
        }
        return new int[]{bestScore, bestRow, bestCol};
    }

    private int evaluate(char[][] b) {
        // Rows & Cols
        for (int i = 0; i < 3; i++) {
            if (b[i][0] == b[i][1] && b[i][1] == b[i][2]) {
                if (b[i][0] == AI) return +10;
                else if (b[i][0] == PLAYER) return -10;
            }
            if (b[0][i] == b[1][i] && b[1][i] == b[2][i]) {
                if (b[0][i] == AI) return +10;
                else if (b[0][i] == PLAYER) return -10;
            }
        }
        // Diagonals
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2] || b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[1][1] == AI) return +10;
            else if (b[1][1] == PLAYER) return -10;
        }
        return 0;
    }

    private boolean isMovesLeft(char[][] b) {
        for (char[] row : b) for (char c : row) if (c == EMPTY) return true;
        return false;
    }

    private boolean checkGameOver() {
        int res = evaluate(board);
        if (res == 10) { System.out.println("AI wins!"); return true; }
        if (res == -10) { System.out.println("You win!"); return true; }
        if (!isMovesLeft(board)) { System.out.println("It's a draw!"); return true; }
        return false;
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println(" " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) System.out.println("-----------");
        }
    }

    public static void main(String[] args) {
        new TicTacToe().play();
    }
}