/**
 * The Player interface defines the contract for Player instances in the Tic-Tac-Toe game.
 *
 * @author Tomer Meidan
 */
public interface Player {
    /**
     * Place a mark on the board.
     *
     * @param board The game board on which the move is to be made.
     * @param mark  The mark to be placed on the board by the player.
     */
    void playTurn(Board board, Mark mark);
}
