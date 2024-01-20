/**
 * Provides a factory method to create instances of renderers.
 *
 * <p>
 * Renderers are built based on the specified renderer type using the buildRenderer method.
 * Supported renderer types include:
 * - console
 * - none
 * </p>
 *
 * @see Constants
 * @author Tomer Meidan
 */
public class RendererFactory {

    /**
     * Constructs a new RendererFactory instance.
     */
    public RendererFactory() {}

    /**
     * Builds and returns an instance of a renderer based on the specified renderer type.
     *
     * @param rendererType The type of the renderer to be created.
     * @param size         The size of the game board.
     * @return An instance of the corresponding renderer or null if the renderer type is not recognized.
     */
    public Renderer buildRenderer(String rendererType, int size) {
        switch (rendererType) {

            case Constants.VOID_RENDERER_NAME:
                return new VoidRenderer();

            case Constants.CONSOLE_RENDERER_NAME:
                return new ConsoleRenderer(size);

            default:
                return null;
        }
    }
}
