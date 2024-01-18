public class Game {
    private Player playerX;
    private Player playerO;
    private Board board;
    private Renderer renderer;
    private int winStreak = Constants.DEFAULT_WIN_STREAK;
    private boolean isGameOver = false;
    private Player currentPlayer;
    private Mark currentMark;


    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.board = new Board();
    }

    public Game(Player playerX, Player playerO, int size, int winStreak,Renderer renderer) {
        this(playerX, playerO, renderer);
        board = new Board(size);
        if (winStreak <= size && winStreak > 2) {
            this.winStreak = winStreak;
        }
        else{
            this.winStreak = size;
        }
    }

    public int getWinStreak() {
        return winStreak;
    }

    public int getBoardSize() {
        return board.getSize();
    }

    public Mark run() {
        Mark result = Mark.BLANK;
        currentPlayer = playerX;
        currentMark = Mark.X;
        while (!isGameOver) {
            playerTurn();
            if (checkCurrentPlayerWin()){
                result = currentMark;
                isGameOver = true;
            }
            else{
                setupNextTurn();
            }
        }
        return result;
    }

    private void playerTurn() {
        currentPlayer.playTurn(board, currentMark);
        renderer.renderBoard(board);
    }

    private void setupNextTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        currentMark = (currentMark == Mark.X) ? Mark.O : Mark.X;
    }

    private boolean checkCurrentPlayerWin() {
        if (checkRowStreak()) {
            return true;
        }
        return checkColStreak();
    }

    private boolean checkRowStreak() {
        return checkLineStreak(Constants.ROW);
    }

    private boolean checkColStreak() {
        return checkLineStreak(Constants.COL);
    }

    private int checkMark(int i, int j) {
        if (board.getMark(i, j) == currentMark) {
            return 1;
        }
        return -1;
    }

    private boolean checkLineStreak(String streakType) {
        for (int i = 0; i < board.getSize(); i++) {
            int countCorrectMarks = 0;
            for (int j = 0; j < board.getSize(); j++) {
                countCorrectMarks += calculateStreak(streakType, i, j);
                if (countCorrectMarks == winStreak) {
                    return true;
                }
            }
        }
        return false;
    }

    private int calculateStreak(String streakType, int i, int j) {
        switch (streakType) {
            case Constants.ROW:
                return checkMark(i, j);

            case Constants.COL:
                return checkMark(j, i);

            default:
                break;
        }
        return 0;
    }



}
