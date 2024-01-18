
/**
 * The Renderer interface defines a contract for classes responsible for rendering a game board.
 * Implementing classes should provide a concrete implementation of the renderBoard method
 * to visually represent the state of a game board.
 */
public interface Renderer {
    void renderBoard(Board board);
}
