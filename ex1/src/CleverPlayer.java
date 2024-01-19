/**
 * CleverPlayer class implements the Player interface for a Tic Tac Toe player
 * that makes moves in a systematic way by iterating through each cell on the game board.
 *
 * The player attempts to place its mark in the first available unoccupied cell,
 * starting from the top-left corner and moving row by row.
 * This class is designed to be used as part of a Tic Tac Toe game.
 */
public class CleverPlayer implements Player {

    public CleverPlayer() {}

    /**
     * Overrides the playTurn method from the Player interface to make a systematic move.
     * The player iterates through each cell on the board, attempting to place its mark
     * in the first available unoccupied cell.
     *
     * @param board The Tic Tac Toe game board on which the player makes a move.
     * @param mark  The mark (X or O) representing the player on the board.
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