// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * General type for brick strategies, part of decorator pattern implementation.
 * All brick strategies implement this interface.
 * This interface including the "getGameObjectCollection" method and "onCollision" method.
 * @author Hilla Heimberg
 */
public interface CollisionStrategy {
    // -------------------------------------- METHODS --------------------------------------
    GameObjectCollection getGameObjectCollection();
    void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);
}
