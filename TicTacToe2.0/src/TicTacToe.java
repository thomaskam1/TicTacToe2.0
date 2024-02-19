import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean playMore;
        do {
            int numOfPlayers;
            // User input for player number
            do {
                try {
                    System.out.print("Please enter the number of players (3-10 Players): ");
                    // Takes input, removes all white spaces, and turns it into an integer
                    numOfPlayers = Integer.parseInt(input.nextLine().replaceAll("\\s", ""));
                    if(numOfPlayers < 3 || numOfPlayers > 10) {
                        System.out.println("Invalid input. Please enter number of players again.");
                    }
                // Catches if invalid input or error with conversion to integer
                } catch (InputMismatchException | NumberFormatException exception) {
                    System.out.println("Invalid input. Please enter number of players again.");
                    // Resets number of players
                    numOfPlayers = 0;
                }
            } while(numOfPlayers < 3 || numOfPlayers > 10);

            // User input for player piece
            Player[] players = new Player[numOfPlayers];
            // Assigns piece to player
            for(int i = 0; i < numOfPlayers; i++) {
                char piece = Player.pickPiece(players, i + 1);
                players[i] = new Player(piece);
            }

            // User input for defining winner
            int size = numOfPlayers + 1;
            int numToWin;
            do {
                try {
                    System.out.print("Please enter the number of pieces in a row that defines a winner" +
                            " (3-" + size + "): ");
                    // Takes input, removes all white spaces, and turns it into an integer
                    numToWin = Integer.parseInt(input.nextLine().replaceAll("\\s", ""));;
                    if(numToWin < 3 || numToWin > size) {
                        System.out.println("Invalid input. Please enter number to win again.");
                    }
                    // Catches if invalid input or error with conversion to integer
                } catch (InputMismatchException | NumberFormatException exception) {
                    System.out.println("Invalid input. Please enter number to win again.");
                    // Resets winning number
                    numToWin = 0;
                }
            } while(numToWin < 3 || numToWin > size);
            System.out.println();

            // Creates board and game logic object
            Board gameBoard = new Board(size);
            GameLogic gameLogic = new GameLogic(gameBoard, numOfPlayers, numToWin, players);

            // Starts and plays the game
            gameLogic.playGame();

            // Asks user if they want to play again
            System.out.print("Play again? (Y/N): ");
            char playAgain = ' ';
            do {
                // Takes input, removes all white space, and makes it upper case
                String userInput = input.nextLine().replaceAll("\\s", "").toUpperCase();
                // Checks if input is not equal to 1 and if input is not Y or N
                if(userInput.length() != 1 || userInput.charAt(0) != 'Y' && userInput.charAt(0) != 'N') {
                    System.out.print("Invalid input. Play again? (Y/N): ");
                } else {
                    playAgain = userInput.charAt(0);
                }
            } while(playAgain != 'Y' && playAgain != 'N');

            playMore = playAgain == 'Y';
        } while(playMore);
    }
}
