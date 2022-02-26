// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
 * All strategy decorators should inherit from this class.
 * @author Hilla Heimberg
 */
abstract public class RemoveBrickStrategyDecorator implements CollisionStrategy {
    // -------------------------------------- PRIVATE -------------------------------------
    private final CollisionStrategy toBeDecorated;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor for the decorator.
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator (CollisionStrategy toBeDecorated){
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * Should delegate to held Collision strategy object.
     * @param thisObj the brick which participant in the collision.
     * @param otherObj the ball which participant in the collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision (GameObject thisObj, GameObject otherObj, Counter counter){
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }

    /**
     * return held reference to global game object. Delegate to held object to be decorated.
     * @return held reference to global game object
     */
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }
}
