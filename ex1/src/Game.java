public class Game {
    private Player playerX;
    private Player playerO;
    private Board board;
    private Renderer renderer;
    private int winStreak = Constants.DEFAULT_WIN_STREAK;
    private boolean isGameOver = false;
    private Player currentPlayer;
    private Mark currentMark;
    private int turnsNumber = 0;


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
        currentPlayer = playerX;
        currentMark = Mark.X;
        return handleGame();
    }

    private Mark handleGame() {
        Mark gameResult = Mark.BLANK;
        int cellsNumber = board.getSize() * board.getSize();
        while (!isGameOver) {
            playerTurn();
            if (checkCurrentPlayerWin()){
                gameResult = currentMark;
                isGameOver = true;
            } else if (cellsNumber == turnsNumber) {
                isGameOver = true;
            }
            else{
                setupNextTurn();
            }
        }
        return gameResult;
    }

    private void playerTurn() {
        currentPlayer.playTurn(board, currentMark);
        turnsNumber++;
        renderer.renderBoard(board);
    }

    private void setupNextTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        currentMark = (currentMark == Mark.X) ? Mark.O : Mark.X;
    }

    private boolean checkCurrentPlayerWin() {
        return (checkRowStreak() || checkColStreak() || checkLeftDiagonalStreak() || checkRightDiagonalStreak());
    }

    private boolean checkRowStreak() {
        return checkStraightStreak(Constants.ROW);
    }

    private boolean checkColStreak() {
        return checkStraightStreak(Constants.COL);
    }

    private boolean checkLeftDiagonalStreak() {
        return checkDiagonalStreak(Constants.DIAGONAL_LEFT);
    }

    private boolean checkRightDiagonalStreak() {
        return checkDiagonalStreak(Constants.DIAGONAL_RIGHT);
    }



    private boolean checkStraightStreak(String streakType) {
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

    private boolean checkDiagonalStreak(String streakType) {
        for (int i = 0; i < board.getSize(); i++) {
            int countCorrectMarks = 0;
            for (int j = 0; j < i; j++) {
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

            case Constants.DIAGONAL_LEFT:
                return checkMark(i - j, j);

            case Constants.DIAGONAL_RIGHT:
                return checkMark(board.getSize() - i - 1 + j, j);

            default:
                break;
        }
        return 0;
    }

    private int checkMark(int i, int j) {
        if (board.getMark(i, j) == currentMark) {
            return 1;
        }
        return -1;
    }

}
