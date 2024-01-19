public class GeniusPlayer implements Player {

    public GeniusPlayer() {}
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
