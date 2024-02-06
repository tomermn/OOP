package bricker.gameObject;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;


/* TODO inserted by liorg [2/1/2024,12:47 PM] :
    * check the speed val and check if it should be float or double and if it should be here or in the manager
    *
    *
 */



public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener ******
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        float topLeftCorner = this.getTopLeftCorner().x();
        float topRightCorner = topLeftCorner + getDimensions().x();

        if(this.inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                topLeftCorner > Vector2.ZERO.x()) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(this.inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                topRightCorner < BrickerGameManager.getWindowDimensions().x()) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

