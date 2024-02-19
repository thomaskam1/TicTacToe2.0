import java.util.InputMismatchException;
import java.util.Scanner;

public class GameLogic {
    private Board board;
    private int currentPlayer;
    private int size;
    private Player[] players;
    private int numToWin;

    /**
     * Constructor of game logic class
     * @param board Tictactoe board
     * @param numOfPlayers Number of players playing
     * @param numToWin Number in a row to win
     * @param players Array of players
     */
    public GameLogic(Board board, int numOfPlayers, int numToWin, Player[] players) {
        this.board = board;
        this.size = numOfPlayers + 1;
        this.numToWin = numToWin;
        this.players = players;
        this.currentPlayer = 1;
    }

    /**
     * Starts, plays, and manages game in order for the game to function properly
     */
    public void playGame() {
        Scanner input = new Scanner(System.in);
        boolean isGameOver = false;

        while(!isGameOver) {
            // Prints initial board
            board.printBoard(size);
            char piece = Player.getPlayerPiece(players, currentPlayer);
            System.out.print("It is player " + currentPlayer + "'s (" + piece + ") turn. ");
            boolean isValidMove = false;
            do {
                try {
                    System.out.print("Enter move (Example: 0 0): ");

                    // Takes user input and splits it into a string array
                    String[] userInput = input.nextLine().split(" ");

                    // Checks if user input can't split into 2 parts, if the input are not integers, if there are
                    // more than 2 zeros in the input
                    if(userInput.length != 2 || checkInt(userInput[0]) || checkInt(userInput[1])
                            || userInput[0].matches("00+") || userInput[1].matches("00+")) {
                        System.out.println("Invalid input. Please enter move again.");
                        continue;
                    }
                    // Converts input into integer
                    int rowInput = Integer.parseInt(userInput[0]);
                    int colInput = Integer.parseInt(userInput[1]);

                    // Checks if move is valid
                    isValidMove = validMove(rowInput, colInput);
                    if(!isValidMove) {
                        System.out.println("Invalid input. Please enter move again.");
                    } else {
                        board.addPiece(piece, rowInput, colInput);
                    }
                // Catches if invalid input or error with conversion to integer
                } catch(InputMismatchException | NumberFormatException exception) {
                    System.out.println("Invalid input. Please enter move again.");
                }
            } while(!isValidMove);

            // Determines if game is over or not
            isGameOver = gameOver(board.getBoard(), piece);

            // Transitions to next player then starts from beginning once players array is done
            currentPlayer = (currentPlayer % players.length) + 1;
            System.out.println();
        }
        // Prints final board
        board.printBoard(size);

        // Checks if there is a winner or tie in the game
        if(checkTie(board.getBoard())) {
            System.out.println("Game Over. No winners. It is a tie.");
        } else {
            System.out.println("Game Over. Player " + (currentPlayer - 1) + " wins.");
        }
        System.out.println();
    }

    /**
     * Checks for tie in the game
     * Tie if board is filled with no winner
     * @param board Board to check for tie
     * @return True if there is a tie, false if not
     */
    public boolean checkTie(char[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                // Checks if board has empty space
                if(board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there is a win vertically, horizontally, or diagonally on the board
     * @param board Board to check for win
     * @param piece Player's piece
     * @return True if there is a vertical, horizontal, or diagonal win by using the methods, false if not
     */
    public boolean checkWin(char[][] board, char piece) {
        // Checks through rows to see if winning number matches number of pieces in a row
        for(int row = 0; row < board.length; row++) {
            if(horizontalWin(board, piece, numToWin, row)) {
                return true;
            }
        }
        // Checks through columns to see if winning number matches number of pieces in a row
        for(int col = 0; col < board[0].length; col++) {
            if(verticalWin(board, piece, numToWin, col)) {
                return true;
            }
        }
        // Checks through diagonals to see if winning number matches number of pieces in a row
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[0].length; col++) {
                if(diagonalWin(board, piece, numToWin, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a vertical win in the game board
     * @param board Board to check for win in columns
     * @param piece Player's piece
     * @param winningNum Number to win in a row
     * @param col Column of the board array
     * @return True if winning number matches pieces in a row, false if not
     */
    public boolean verticalWin(char[][] board, char piece, int winningNum, int col) {
        int count = 0;
        for(int i = 0; i < board.length; i++) {
            // Checks if piece matches element in board
            if(board[i][col] == piece) {
                // Increments count if piece and element matches
                count++;
                if(count == winningNum) {
                    return true;
                }
            } else {
                // Resets count if no match
                count = 0;
            }
        }
        return false;
    }

    /**
     * Checks if there is a horizontal win in the game board
     * @param board Board to check for win in rows
     * @param piece Player's piece
     * @param winningNum NUmber to win in a row
     * @param row Row of the board array
     * @return True if winning number matches pieces in a row, false if not
     */
    public boolean horizontalWin(char[][] board, char piece, int winningNum, int row) {
        int count = 0;
        for(int i = 0; i < board.length; i++) {
            // Checks if piece matches element in board
            if(board[row][i] == piece) {
                // Increments count if piece and element matches
                count++;
                if(count == winningNum) {
                    return true;
                }
            } else {
                // Resets count if no match
                count = 0;
            }
        }
        return false;
    }

    /**
     * Checks if there is  diagonal win on the game board
     * @param board Board to check for win in the diagonals
     * @param piece Player's piece
     * @param winningNum Number to win in a row
     * @param row Row of the board array
     * @param col Column of the board array
     * @return True if the winning number matches pieces in a row diagonally anywhere on board, false if not
     */
    public boolean diagonalWin(char[][] board, char piece, int winningNum, int row, int col) {
        int count = 0;
        // Checks from bottom right to top left
        for(int i = 0; i < board.length; i++) {
            // Moves index top left every iteration
            int rowIndex = row - i;
            int colIndex = col - i;

            // Checks for out of bounds
            if(rowIndex < 0 || rowIndex >= board.length) {
                break;
            }
            if(colIndex < 0 || colIndex >= board[0].length) {
                break;
            }

            // Checks if piece matches element in board
            if(board[rowIndex][colIndex] == piece) {
                // Increments count if piece and element matches
                count++;
                if(count == winningNum) {
                    return true;
                }
            } else {
                // Resets count if no match
                count = 0;
            }
        }

        count = 0;
        // Checks from top right to bottom left
        for(int i = 0; i < board.length; i++) {
            // Moves index bottom left every iteration
            int rowIndex = row + i;
            int colIndex = col - i;

            // Checks for out of bounds
            if(rowIndex < 0 || rowIndex >= board.length) {
                break;
            }
            if(colIndex < 0 || colIndex >= board[0].length) {
                break;
            }

            // Checks if piece matches element in board
            if(board[rowIndex][colIndex] == piece) {
                // Increments count if piece and element matches
                count++;
                if(count == winningNum) {
                    return true;
                }
            } else {
                // Resets count if no match
                count = 0;
            }
        }
        return false;
    }

    /**
     * Checks if the game is over
     * @param board Game board
     * @param piece Player's piece
     * @return True if there is a win or tie, false if not
     */
    public boolean gameOver(char[][] board, char piece) {
        return checkWin(board, piece) || checkTie(board);
    }

    /**
     * Checks if the move made is a valid move
     * @param row Row index of move
     * @param col Column index of move
     * @return True if the move is within bounds and is on an empty space, false if not
     */
    public boolean validMove(int row, int col) {
        // Checks within boundaries
        if(row >= 0 && row < size && col >= 0 && col < size) {
            return board.getBoard()[row][col] == ' ';
        } else {
            return false;
        }
    }

    /**
     * Checks if a string is an integer
     * @param str Any string
     * @return True if the string cannot be converted to an integer, false if not
     */
    public boolean checkInt(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException exception) {
            return true;
        }
    }
}
