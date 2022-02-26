// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.util.Vector2;
import danogl.util.Counter;
import danogl.gui.rendering.Renderable;


/**
 * This class represents the GraphicLifeCounter of the "Brick" game.
 * Display a graphic object on the game window showing as many widgets as lives left.
 * @author Hilla Heimberg
 */
public class GraphicLifeCounter extends GameObject {
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final int GAP_BETWEEN_HEARTS = 2;

    private final GameObject[] livesArray;
    private final Counter livesCounter;
    private final GameObjectCollection collection;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor of the GraphicLifeCounter in the "Brick" game.
     * @param  widgetTopLeftCorner - top left corner of left most life widgets. Other widgets will be
     * displayed to its right, aligned in height.
     * @param widgetDimensions - dimensions of widgets to be displayed.
     * @param  livesCounter - global lives counter of game.
     * @param widgetRenderable - image to use for widgets.
     * @param gameObjectsCollection - global game object collection managed by game manager.
     * @param numOfLives - global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter,
                               Renderable widgetRenderable, GameObjectCollection gameObjectsCollection,
                               int numOfLives){
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.livesArray = new GameObject[numOfLives];
        this.collection = gameObjectsCollection;
        this.livesCounter = livesCounter;

        for (int i = 0; i < numOfLives; i++){
            GameObject heartImage = new GameObject(widgetTopLeftCorner, widgetDimensions,
                    widgetRenderable);
            heartImage.setCenter(new Vector2(widgetTopLeftCorner.x() + (i * widgetDimensions.x() +
                    GAP_BETWEEN_HEARTS), widgetTopLeftCorner.y() + widgetDimensions.y()));
            heartImage.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            gameObjectsCollection.addGameObject(heartImage, Layer.BACKGROUND);
            this.livesArray[i] = heartImage;
        }
    }

    /**
     * Update the lives graphic counter in the "brick" game.
     * @param deltaTime the time the update happened.
     */
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        if (this.livesCounter.value() > 0){
            this.collection.removeGameObject(this.livesArray[this.livesCounter.value() - 1], Layer.BACKGROUND);
            this.livesCounter.decrement();
        }
    }
}
