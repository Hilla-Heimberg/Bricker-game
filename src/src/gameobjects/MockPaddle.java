// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * One of the types of objects that can be set loose when a brick is hit. It's another paddle.
 * @author Hilla Heibmberg
 */
public class MockPaddle extends Paddle {
    // -------------------------------------- PRIVATE -------------------------------------
    private final GameObjectCollection gameObjectCollection;
    private final int numCollisionsToDisappear;
    private int numOfCurrentCollisions = 0;

    // -------------------------------------- PUBLIC -------------------------------------

    /**
     * Boolean value for avoiding more than one paddle in the game.
     */
    public static boolean isInstantiated;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor. Construct a new GameObject instance. Construct a new paddle.
     * @param topLeftCorner Position of the object, in window coordinates (pixels). Note that (0,0) is the
     *                     top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null.
     * @param inputListener listener object for user input.
     * @param windowDimensions dimensions of game window.
     * @param gameObjectCollection A container for accumulating/removing instances of GameObject and for
     * handling their collisions.
     * @param minDistanceFromEdge border for paddle movement.
     * @param numCollisionsToDisappear number of collisions to disappear the paddle.
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      UserInputListener inputListener, Vector2 windowDimensions,
                      GameObjectCollection gameObjectCollection, int minDistanceFromEdge,
                      int numCollisionsToDisappear){
        super(topLeftCorner,dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        isInstantiated = true;
    }

    /**
     * Called on the first frame of a collision.
     * @param other the ball which participant in the collision.
     * @param collision Collision object. Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision){
        if (other instanceof Ball) {
            this.numOfCurrentCollisions ++;
        }

        // Checking the amount of the collisions
        if (this.numOfCurrentCollisions >= this.numCollisionsToDisappear){
            this.gameObjectCollection.removeGameObject(this);
            isInstantiated = false;
        }
    }
}
