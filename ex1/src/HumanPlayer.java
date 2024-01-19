
/**
 * The HumanPlayer class represents a human player in a game. This class implements
 * the Player interface and allows a human player to make moves on the TicTacToe game board.
 */
public class HumanPlayer implements Player {


    /**
     * Plays a turn for the human player on the provided game board.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The mark associated with the player (e.g., "X" or "O").
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        String playerMark = (mark == Mark.X) ? Constants.MARK_X : Constants.MARK_O;
        System.out.println(Constants.playerRequestInputString(playerMark));
        handleUserInput(board, mark);
    }

    private void handleUserInput(Board board, Mark mark) {
        int userInput, row, col;
        do {
            userInput = KeyboardInput.readInt();
            row = (userInput / 10) % 10;
            col = userInput % 10;
        } while (!hasPutMark(row, col, board, mark));
    }

    private boolean hasPutMark(int row, int col, Board board, Mark mark) {
        if (!isValidCoordinates(row, col, board.getSize())) {
            System.out.println(Constants.INVALID_COORDINATE);
            return false;

        } else if (!board.putMark(mark, row, col)) {
            System.out.println(Constants.OCCUPIED_COORDINATE);
            return false;
        }

        return true;
    }

    private boolean isValidCoordinates(int row, int col, int size) {
        boolean isRowValid = (row < size) && (row >= 0);
        boolean isColValid = (col < size) && (col >= 0);
        return isRowValid && isColValid;
    }
}
