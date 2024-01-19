import java.util.Random;

public class WhateverPlayer implements Player {
    @Override
    public void playTurn(Board board, Mark mark) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(board.getSize());
            col = random.nextInt(board.getSize());
        } while (!board.putMark(mark, row, col));

    }
}
