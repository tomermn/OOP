/**
 * The HumanPlayer class represents a player controlled by a human in the Tic-Tac-Toe game.
 * It implements the Player interface and provides functionality for a human player to make moves
 * on the game board.
 *
 * @author Tomer Meidan
 */
public class HumanPlayer implements Player {

    /**
     * Constructs a new HumanPlayer instance.
     */
    public HumanPlayer() {}

    /**
     * Makes a move on the board by receiving input from the human player.
     * The move is determined by the user's input for row and column coordinates.
     *
     * @param board The game board on which the move is to be made.
     * @param mark  The mark to be placed on the board by the human player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        String playerMark = (mark == Mark.X) ? Constants.MARK_X : Constants.MARK_O;
        System.out.println(Constants.playerRequestInputString(playerMark));
        handleUserInput(board, mark);
    }

    /*
     * Handles the user input for row and column coordinates and ensures a valid move is made.
     */
    private void handleUserInput(Board board, Mark mark) {
        int userInput, row, col;
        do {
            userInput = KeyboardInput.readInt();
            row = (userInput / 10) % 10;
            col = userInput % 10;
        } while (!hasPutMark(row, col, board, mark));
    }

    /*
     * Checks if a mark can be placed at the specified row and column coordinates on the board.
     */
    private boolean hasPutMark(int row, int col, Board board, Mark mark) {
        if (!isValidCoordinate(row, col, board.getSize())) {
            System.out.println(Constants.INVALID_COORDINATE);
            return false;

        } else if (!board.putMark(mark, row, col)) {
            System.out.println(Constants.OCCUPIED_COORDINATE);
            return false;
        }

        return true;
    }

    /*
     * Checks if the specified row and column coordinates are valid for the given board size.
     */
    private boolean isValidCoordinate(int row, int col, int size) {
        boolean isRowValid = (row < size) && (row >= 0);
        boolean isColValid = (col < size) && (col >= 0);
        return isRowValid && isColValid;
    }
}
