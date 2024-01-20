/**
 * Implements the Player interface for a Tic Tac Toe player with a "Clever" strategy.
 *
 * <p>
 * The clever player makes moves by attempting to place its mark in the first available cell
 * on the game board, starting from the top left, row by row.
 *</p>
 *
 * @author Tomer Meidan
 */
public class CleverPlayer implements Player {

    /**
     * Constructs a new CleverPlayer instance.
     */
    public CleverPlayer() {}

    /**
     * Attempts to place the specified mark in the first available cell on the board,
     * starting from the top left, row by row.
     * @param board The game board on which the player makes a move.
     * @param mark  The mark representing the player on the board.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int boardSize = board.getSize();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.putMark(mark, row, col)) {
                    return;
                }
            }
        }
    }
}