
/**
 * Implements the Renderer interface for a renderer that does not display the game board.
 * This class is used for scenarios where rendering the game board is not required or desired.
 *
 * @author Tomer Meidan
 */
public class VoidRenderer implements Renderer {
    /**
     * Constructs a new VoidRenderer instance.
     */
    public VoidRenderer() {}
    /**
     * Does not render and display the game board.
     * This method is intentionally left empty as this renderer does not provide any visual output.
     *
     * @param board The game board (unused in this implementation).
     */
    public void renderBoard(Board board){}
}
