/**
 * The GeniusPlayer class implements the Player interface for a Tic Tac Toe player
 * with a "Genius" strategy.
 *
 * <p>
 * The genius player makes moves by attempting to place its mark in the first available cell
 * on the game board, starting from the second column, column by column.
 * </p>
 *
 * @author Tomer Meidan
 */
public class GeniusPlayer implements Player {

    /**
     * Constructs a new GeniusPlayer instance.
     */
    public GeniusPlayer() {}

    /**
     * Attempts to place the specified mark in the first available cell on the board,
     * starting from the second column, column by column.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The mark representing the player on the board.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int boardSize = board.getSize();

        for (int col = 1; col < boardSize; col++) {
            for (int row = 0; row < boardSize; row++) {
                if (board.putMark(mark, row, col)) {
                    return;
                }
            }
        }
    }
}
