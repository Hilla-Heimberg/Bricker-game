// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * This class represents the Concrete brick strategy implementing CollisionStrategy interface. Removes
 * holding brick on collision.
 * @author Hilla Heimberg
 */
public class RemoveBrickStrategy implements CollisionStrategy {
    // -------------------------------------- PRIVATE -------------------------------------
    private final GameObjectCollection gameObjectCollection;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor of the "remove brick" strategy.
     * @param gameObjectCollection global game object collection
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * All collision strategy objects should hold a reference to the global game object collection and be
     * able to return it.
     * @return global game object collection whose reference is held in object.
     */
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }

    /**
     * To be called on brick collision.
     * Removes brick from game object collection on collision.
     * @param thisObj the object we will remove
     * @param otherObj the other object in the collision
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if (this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            counter.decrement();
        }
    }
}
