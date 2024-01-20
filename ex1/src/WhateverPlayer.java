import java.util.Random;

/**
 * Implements the Player interface for a Tic Tac Toe player that makes random moves.
 *
 * @author Tomer Meidan
 */
public class WhateverPlayer implements Player {

    /**
     * Constructs a new WhateverPlayer instance.
     */
    public WhateverPlayer() {}

    /**
     * Attempt to place the specified mark in a random, unoccupied cell on the board.
     *
     * @param board The game board on which the player makes a move.
     * @param mark  The mark representing the player on the board.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        Random random = new Random();
        int row, col;
        do {
            int boardSize = board.getSize();
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
        } while (!board.putMark(mark, row, col));
    }
}
