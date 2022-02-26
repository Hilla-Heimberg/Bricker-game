// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces extra paddle to game window which remains until colliding
 * NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE with other game objects.
 * @author Hilla Heimberg
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final int MIN_DISTANCE_FROM_EDGE = 20;
    private static final int NUM_COLLISION_TO_DISAPPEAR = 3;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;

    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    // --------------------------------- STRINGS FOR GAME ---------------------------------
    private static final String MOCK_PADDLE_IMAGE = "assets/paddle.png";

    // -------------------------------------- METHODS --------------------------------------
    /**
     * Constructor of the another paddle strategy.
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * an ImageReader instance for reading images from files for rendering of objects.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether a given key is
     * currently pressed by the user or not. an InputListener instance for reading user input.
     * @param windowDimensions pixel dimensions for game window height x width.
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                              ImageReader imageReader,
                              UserInputListener inputListener,
                              Vector2 windowDimensions){
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Adds additional paddle to game and delegates to held object.
     * @param thisObj the brick which participant in the collision.
     * @param otherObj the ball which participant in the collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter){
        super.onCollision(thisObj, otherObj, counter);

        if (!MockPaddle.isInstantiated) {
            Vector2 dimensionsForMockPaddle = new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT);
            Renderable mockPaddleImage = imageReader.readImage(MOCK_PADDLE_IMAGE,true);
            MockPaddle mockPaddle = new MockPaddle(Vector2.ZERO, dimensionsForMockPaddle,
                    mockPaddleImage, inputListener, this.windowDimensions, getGameObjectCollection(),
                    MIN_DISTANCE_FROM_EDGE, NUM_COLLISION_TO_DISAPPEAR);
            mockPaddle.setCenter(new Vector2(this.windowDimensions.x()/2,this.windowDimensions.y()/2));
            getGameObjectCollection().addGameObject(mockPaddle);
        }
    }
}
