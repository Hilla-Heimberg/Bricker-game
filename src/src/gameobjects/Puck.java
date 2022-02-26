// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * One of the types of objects that can be set loose when a brick is hit. It's a gray ball.
 * @author Hilla heimberg
 */
public class Puck extends Ball {
    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor. Construct a new GameObject instance. Construct a puck ball.
     * @param topLeftCorner Position of the object, in window coordinates (pixels). Note that (0,0) is
     *                     the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable  The renderable representing the object. Can be null.
     * @param collisionSound the sound in the collision.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound){
        super(topLeftCorner, dimensions, renderable, collisionSound);
    }
}
