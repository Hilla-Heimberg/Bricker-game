// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.util.Counter;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;

/**
 * This class represents the brick of the "Brick" game.
 * In case of collision of the brick and the ball, the brick will remove from the game.
 */
public class Brick extends GameObject {
    // -------------------------------------- PRIVATE -------------------------------------
    private final CollisionStrategy collisionStrategy;
    private final Counter counter;
    private boolean checkIfFirstCollision = true;

    // -------------------------------------- METHODS --------------------------------------
    /**
     * Constructor. Construct a new GameObject instance. Construct a new brick.
     * @param topLeftCorner Position of the object, in window coordinates (pixels). Note that (0,0) is the
     *                     top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object.
     * @param collisionStrategy The collision
     * @param counter The counter of the collisions amount
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
    }

    /**
     * Called on the first frame of a collision.
     * @param other The GameObject with which a collision occurred.
     * @param collision Collision object. Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // making sure that only one collision will be considered (even if many balls collision with the
        // same brick)
        if (this.checkIfFirstCollision) {
            this.collisionStrategy.onCollision(this, other,  this.counter);
            this.checkIfFirstCollision = false;
        }
    }
}
