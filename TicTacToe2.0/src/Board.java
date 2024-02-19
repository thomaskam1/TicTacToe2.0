public class Board {
    private char[][] board;

    /**
     * Constructor of board class inputs empty spaces into board
     * @param size Size of board
     */
    public Board(int size) {
        board = new char[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Getter for board
     * @return array for board
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Prints the board according to inputted size and piece placement
     * @param size Size of board
     */
    public void printBoard(int size) {
        System.out.print("  |");
        // Prints column grid numbers
        for(int i = 0; i < size; i++) {
            System.out.print(" " + i + " |");
        }
        System.out.println();

        // Prints first row of dashes
        for(int i = 0; i <= size; i++) {
            System.out.print("----");
        }
        System.out.println();

        // Prints row grid numbers
        for(int i = 0; i < size; i++) {
            System.out.print(i + " |");
            // Prints grid boxes as well as pieces inside the boxes
            for(int j = 0; j < size; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println();

            // Prints row of dashes
            for(int k = 0; k <= size; k++) {
                System.out.print("- - ");
            }
            System.out.println();
        }
    }

    /**
     * Adds piece to the board's grid
     * @param piece Piece of player
     * @param row Row of the board
     * @param col Column of the board
     */
    public void addPiece(char piece, int row, int col) {
        board[row][col] = piece;
    }
}
