// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ExtendOrNarrowStrategy;


/**
 * One of the types of objects that can be set loose when a brick is hit. It's a status changer.
 * @author Hilla Heimberg
 */
public class StatusChanger extends GameObject {
    // -------------------------------------- PRIVATE -------------------------------------
    private final ExtendOrNarrowStrategy owner;
    private final GameObjectCollection gameObjectCollection;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor. Construct a new GameObject instance. Construct a status changer.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public StatusChanger(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         GameObjectCollection gameObjectCollection, ExtendOrNarrowStrategy owner) {
        super(topLeftCorner, dimensions, renderable);
        this.owner = owner;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Should this object be allowed to collide the specified other object.
     * If both this object returns true for the other, and the other returns true
     * for this one, the collisions may occur when they overlap, meaning that their
     * respective onCollisionEnter/onCollisionStay/onCollisionExit will be called.
     * Note that this assumes that both objects have been added to the same
     * GameObjectCollection, and that its handleCollisions() method is invoked.
     * @param other The other GameObject.
     * @return true if the objects should collide. This does not guarantee a collision
     * would actually collide if they overlap, since the other object has to confirm
     * this one as well.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle);
    }

    /**
     * Called on the first frame of a collision.
     * @param other     The GameObject with which a collision occurred.
     * @param collision Collision object. Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.gameObjectCollection.removeGameObject(this);
        this.owner.extendAndNarrow(other);
    }
}
