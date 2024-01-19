/**
 * The `Game` class represents a Tic-Tac-Toe game between two players on a square game board.
 * It manages player turns, tracks game state, and checks for win conditions.
 */
public class Game {
    private Player playerX;
    private Player playerO;
    private Board board;
    private Renderer renderer;
    private int winStreak = Constants.DEFAULT_WIN_STREAK;
    private boolean isGameOver = false;
    private Player currentPlayer;
    private Mark currentMark;
    private int turnCounter = 0;


    /**
     * Constructs a new game with the given players and renderer.
     *
     * @param playerX   the first player (Mark X)
     * @param playerO   the second player (Mark O)
     * @param renderer  the renderer to display the game board
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.board = new Board();
    }

    /**
     * Constructs a new game with the given players, renderer, and board size.
     *
     * @param playerX   the first player (Mark X)
     * @param playerO   the second player (Mark O)
     * @param size      the size of the square game board
     * @param winStreak the number of consecutive marks required to win
     * @param renderer  the renderer to display the game board
     */
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

    /**
     * Gets the required win streak for winning the game.
     *
     * @return the win streak
     */
    public int getWinStreak() {
        return winStreak;
    }

    /**
     * Gets the size of the game board.
     *
     * @return the size of the game board
     */
    public int getBoardSize() {
        return board.getSize();
    }
    /**
     * Initiates and runs the game, returning the Mark of the victory streak of the game.
     *
     * @return Mark of the victory streak
     */
    public Mark run() {
        currentPlayer = playerX;
        currentMark = Mark.X;
        return handleGame();
    }

    private Mark handleGame() {
        Mark gameResult = Mark.BLANK;
        int boardSize = board.getSize();
        int cellsNumber = boardSize * boardSize;
        while (!isGameOver) {
            currentPlayerTurn();
            if (checkWinConditions()){
                gameResult = currentMark;
                isGameOver = true;
            } else if (cellsNumber == turnCounter) {
                isGameOver = true;
            }
            else {
                setupNextTurn();
            }
        }
        return gameResult;
    }

    private void currentPlayerTurn() {
        currentPlayer.playTurn(board, currentMark);
        turnCounter++;
        renderer.renderBoard(board);
    }

    private void setupNextTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        currentMark = (currentMark == Mark.X) ? Mark.O : Mark.X;
    }

    private boolean checkWinConditions() {
        return (checkRowStreak() || checkColStreak() || checkDiagonalLeftStreak() || checkDiagonalRightStreak());
    }

    private boolean checkRowStreak() {
        return checkStraightStreak(Constants.ROW);
    }

    private boolean checkColStreak() {
        return checkStraightStreak(Constants.COL);
    }

    private boolean checkDiagonalLeftStreak() {
        return checkDiagonalStreak(Constants.DIAGONAL_LEFT);
    }

    private boolean checkDiagonalRightStreak() {
        return checkDiagonalStreak(Constants.DIAGONAL_RIGHT);
    }

    private boolean checkStraightStreak(String streakType) {
        for (int i = 0; i < board.getSize(); i++) {
            int currentSequenceLength = 0;
            for (int j = 0; j < board.getSize(); j++) {
                if (checkStreakByType(streakType, i, j)) {
                    currentSequenceLength++;
                }else {
                    currentSequenceLength = 0;
                }
                if (currentSequenceLength == winStreak) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalStreak(String streakType) {
        for (int i = 0; i < board.getSize(); i++) {
            int currentSequenceLength = 0;
            for (int j = 0; j <= i; j++) {
                if (checkStreakByType(streakType, i, j)) {
                    currentSequenceLength++;
                }
                else{
                    currentSequenceLength = 0;
                }
                if (currentSequenceLength == winStreak) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkStreakByType(String streakType, int i, int j) {
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
        return false;
    }

    private boolean checkMark(int i, int j) {
        return (board.getMark(i, j) == currentMark);
    }

}
