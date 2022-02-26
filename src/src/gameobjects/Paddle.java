// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * This class represents the Paddle of the "Brick" game.
 * One of the main game objects. Repels the ball against the bricks.
 * @author Hilla Heimberg
 */
public class Paddle extends GameObject {
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final float MOVEMENT_SPEED = 300;

    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final float minDistanceFromScreenEdge;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor. Construct a new GameObject instance. Construct a new paddle.
     * @param topLeftCorner Position of the object, in window coordinates (pixels). Note that (0,0) is the
     *                     top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object.
     * @param inputListener checking the user response
     * @param windowDimensions the dimensions of the window
     * @param minDistanceFromEdge border for paddle movement
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromScreenEdge = minDistanceFromEdge;
    }

    /**
     * Update the paddle location in the "brick" game and ensure that the paddle can't cross the borders.
     * @param deltaTime time between updates.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;

        if (this.inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                getTopLeftCorner().x() > this.minDistanceFromScreenEdge) {
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if (this.inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                getTopLeftCorner().x() < this.windowDimensions.x() - this.minDistanceFromScreenEdge -
                        getDimensions().x()) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

