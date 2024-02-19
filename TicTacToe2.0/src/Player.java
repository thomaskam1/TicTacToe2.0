import java.util.Scanner;

public class Player {
    private char piece;

    /**
     * Constructor for player class
     * @param piece Piece for player
     */
    public Player(char piece) {
        this.piece = piece;
    }

    /**
     * Getter for piece
     * @return Piece for player
     */
    public char getPiece() {
        return piece;
    }

    /**
     * Allows the user to pick a piece of their choice
     * @param players Players in the game
     * @param number Player number
     * @return Player's piece
     */
    public static char pickPiece(Player[] players, int number) {
        Scanner input = new Scanner(System.in);
        char piece;
        boolean validPiece;
        do {
            System.out.print("Please enter the piece for player " + number + ": ");
            String in = input.nextLine();
            // Replaces white space with nothing, then checks to see if length of input isn't equal to 1
            while(in.replaceAll("\\s", "").length() != 1) {
                System.out.println("Invalid input. Please enter piece again.");
                System.out.print("Please enter the piece for player " + number + ": ");
                in = input.nextLine();
            }
            // Converts string to char
            piece = in.trim().charAt(0);
            validPiece = true;
            // Iterates through players array
            for(Player player : players) {
                // Checks if piece is taken
                if(player != null && player.getPiece() == piece) {
                    validPiece = false;
                    System.out.println("Invalid input. Please enter piece again.");
                    break;
                }
            }
        } while(!validPiece);
        return piece;
    }

    /**
     * Gets the current player's piece
     * @param players Array of players
     * @param currentPlayer Index of current player
     * @return Piece of the current player
     */
    public static char getPlayerPiece(Player[] players, int currentPlayer) {
        return players[currentPlayer - 1].getPiece();
    }
}
