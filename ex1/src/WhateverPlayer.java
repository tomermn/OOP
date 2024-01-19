import java.util.Random;

/**
 * WhateverPlayer class implements the Player interface for a Tic Tac Toe player
 * that makes random moves on the game board.
 */
public class WhateverPlayer implements Player {

    public WhateverPlayer() {}

    /**
     * Overrides the playTurn method from the Player interface to make a random move.
     * The player selects a random, unoccupied cell on the board and places its mark.
     *
     * @param board The Tic Tac Toe game board on which the player makes a move.
     * @param mark  The mark (X or O) representing the player on the board.
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
