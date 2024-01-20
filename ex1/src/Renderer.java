
/**
 * The Renderer interface defines the contract for rendering the game board.
 *
 * @author Tomer Meidan
 */
public interface Renderer {
    /**
     * Renders and displays the current state of the game board.
     *
     * @param board The game board to be rendered.
     */
    void renderBoard(Board board);
}
