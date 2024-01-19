public class RendererFactory {

    public Renderer createRenderer(String rendererName, int size) {
        switch (rendererName) {
            case Constants.VOID_RENDERER_NAME:
                return new VoidRenderer();

            case Constants.CONSOLE_RENDERER_NAME:
                return new ConsoleRenderer(size);

            default:
                return null;
        }
    }
}
