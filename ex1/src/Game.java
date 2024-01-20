
/**
 * The Game class represents a Tic-Tac-Toe game instance with two players, a game board, and a renderer.
 * It manages the flow of the game, handles player turns, and checks for win conditions.
 *
 * <p>
 * Players are assigned the marks 'X' and 'O', and the game board is rendered using the specified renderer.
 * The game can be customized with different board sizes and win streak lengths.
 * </p>
 *
 * @author Tomer Meidan
 */
public class Game {
    private Player playerX;
    private Player playerO;
    private Board board;
    private Renderer renderer;
    private int winStreak = Constants.DEFAULT_WIN_STREAK;
    private Player currentPlayer;
    private Mark currentMark;
    private int turnCounter = 0;


    /**
     * Constructs a new Game instance with the specified players and renderer using the default board size.
     *
     * @param playerX   The player assigned the mark 'X'.
     * @param playerO   The player assigned the mark 'O'.
     * @param renderer  The renderer used to display the game board.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.board = new Board();
    }

    /**
     * Constructs a new Game instance with the specified players, renderer, custom board size, and win streak length.
     *
     * @param playerX    The player assigned the mark 'X'.
     * @param playerO    The player assigned the mark 'O'.
     * @param size       The custom size of the game board.
     * @param winStreak  The custom win streak length.
     * @param renderer   The renderer used to display the game board.
     */
    public Game(Player playerX, Player playerO, int size, int winStreak,Renderer renderer) {
        this(playerX, playerO, renderer);
        board = new Board(size);

        if (winStreak <= size && winStreak >= Constants.MIN_WIN_STREAK) {
            this.winStreak = winStreak;
        } else {
            this.winStreak = size;
        }
    }

    /**
     * Gets the current win streak length for the game.
     *
     * @return The win streak length.
     */
    public int getWinStreak() {
        return winStreak;
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getBoardSize() {
        return board.getSize();
    }

    /**
     * Initiates the game and returns the mark of the winning player or 'BLANK' if it's a tie.
     *
     * @return The mark of the winning player or 'BLANK' if it's a tie.
     */
    public Mark run() {
        currentPlayer = playerX;
        currentMark = Mark.X;
        return handleGame();
    }

    /*
     * Handles the flow of the game, player turns, and win conditions.
     */
    private Mark handleGame() {
        Mark gameResult = Mark.BLANK;
        int boardSize = board.getSize();
        int cellsNumber = boardSize * boardSize;
        boolean isGameOver = false;

        while (!isGameOver) {
            currentPlayerTurn();
            if (checkWinConditions()){ // The player achieved a streak equals to the game winStreak
                gameResult = currentMark;
                isGameOver = true;
            } else if (cellsNumber == turnCounter) { // The board is full
                isGameOver = true;
            } else {
                setupNextTurn();
            }
        }
        return gameResult;
    }

    /*
     * Executes the current player's turn, updates the turn counter, and renders the board.
     */
    private void currentPlayerTurn() {
        currentPlayer.playTurn(board, currentMark);
        turnCounter++;
        renderer.renderBoard(board);
    }

    /*
     * Sets up the next turn by switching players and marks.
     */
    private void setupNextTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        currentMark = (currentMark == Mark.X) ? Mark.O : Mark.X;
    }

    /*
     * Checks streaks for rows, columns, and diagonals.
     */
    private boolean checkWinConditions() {
        return (checkStreakByType(Constants.ROW) ||
                checkStreakByType(Constants.COL) ||
                checkStreakByType(Constants.DIAGONAL_LEFT) ||
                checkStreakByType(Constants.DIAGONAL_RIGHT));
    }

    /*
     * Checks win conditions for a specific streak type (row, column, diagonal (left = /, right = \)
     */
    private boolean checkStreakByType(String streakType) {
        int boardSize = board.getSize();

        for (int i = 0; i < boardSize; i++) {
            int currentSequenceLength = 0;
            int bound = getBoundByType(streakType, i, boardSize); // Get bound for the inner loop.

            // Calculate sequence of the player's mark.
            // Start over if the sequence has been interrupted by different mark or blank cell.
            for (int j = 0; j <= bound; j++) {
                if (checkMarkByType(streakType, i, j)) {
                    currentSequenceLength++;
                } else {
                    currentSequenceLength = 0;
                }

                if (currentSequenceLength == winStreak) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Helper method to the function checkStreakByType.
     * in diagonal streak, the bound should be the index of the outer loop.
     * in row or col streak, the bound should be the board size.
     */
    private int getBoundByType (String streakType, int i, int boardSize) {
        if (streakType.equals(Constants.DIAGONAL_LEFT) ||
                streakType.equals(Constants.DIAGONAL_RIGHT)) {
            return i;
        }
        return boardSize;
    }

    /*
     * Checks the mark by a specific streak type and coordinate.
     */
    private boolean checkMarkByType(String streakType, int i, int j) {
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

    /*
     * Checks the mark at the specified row and column.
     */
    private boolean checkMark(int i, int j) {
        return (board.getMark(i, j) == currentMark);
    }
}
