/**
 * The Board class represents a game board for a two-dimensional grid of marks.
 * It allows placing marks, retrieving marks, and checking the validity of coordinates.
 * @author Tomer Meidan
 */
public class Board {
    private int boardSize = Constants.DEFAULT_BOARD_SIZE;
    private Mark[][] board;

    /**
     * Constructs a new Board instance with the default size.
     */
    public Board() {
        createBoard(boardSize);
    }

    /**
     * Constructs a new Board instance with the specified size.
     *
     * @param size The size of the game board.
     */
    public Board(int size) {
        boardSize = size;
        createBoard(size);
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getSize() {return boardSize;}

    /**
     * Places a mark on the specified row and column if the coordinates are on the board
     * and the cell is empty.
     *
     * @param mark The mark to be placed on the board.
     * @param row  The row index of the cell.
     * @param col  The column index of the cell.
     * @return true if the mark was successfully placed, false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (!isCoordinateOnBoard(row, col)) {
            return false;
        }
        if (board[row][col] != Mark.BLANK) {
            return false;
        }
        board[row][col] = mark;
        return true;
    }

    /**
     * Retrieves the mark at the specified row and column.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The mark at the specified coordinates, or {@code Mark.BLANK} if the coordinates are
     * not on the board.
     */
    public Mark getMark(int row, int col) {
        if (isCoordinateOnBoard(row, col)) {
            return board[row][col];
        }
        return Mark.BLANK;
    }

    /*
     * initialize the game board with BLANK marks and specified size.
     */
    private void createBoard(int size) {
        board = new Mark[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /*
     * Helper method to check if the coordinates are within the valid range of the board
     */
    private boolean isCoordinateOnBoard(int row, int col) {
        return ((row < boardSize) && (row >= 0)) && ((col < boardSize) && (col >= 0));
    }
}
