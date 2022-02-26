// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import java.awt.*;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.collisions.GameObjectCollection;

/**
 * This class represents the NumericLifeCounter of the "Brick" game.
 * Display a graphic object on the game window showing a numeric count of lives left.
 * @author Hilla Heimberg
 */
public class NumericLifeCounter extends GameObject {
    // -------------------------------------- PRIVATE -------------------------------------
    private final Counter livesCounter;
    private final TextRenderable textRenderable;

    // ----------------------------- STRINGS FOR GAME --------------------------------------
    private static final String CURRENT_LIVES_TEXT = "Current lives: ";

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor of the NumericLifeCounter in the "Brick" game.
     * @param livesCounter global lives counter of game.
     * @param topLeftCorner top left corner of renderable
     * @param dimensions dimensions of renderable
     * @param gameObjectCollection A container for accumulating/removing instances of GameObject and for
     * handling their collisions.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection){
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.textRenderable = new TextRenderable(CURRENT_LIVES_TEXT + livesCounter.value());
        this.textRenderable.setColor(Color.white);

        GameObject numericCounter = new GameObject(topLeftCorner, dimensions, this.textRenderable);
        numericCounter.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjectCollection.addGameObject(numericCounter, Layer.BACKGROUND);
    }

    /**
     * Update the lives numeric counter in the "brick" game.
     * @param deltaTime the time the update happened.
     */
    public void update(float deltaTime){
        this.textRenderable.setString(CURRENT_LIVES_TEXT + this.livesCounter.value());
    }
}
