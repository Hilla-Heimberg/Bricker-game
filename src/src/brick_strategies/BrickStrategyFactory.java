// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;
import java.util.Random;

/**
 * Factory class for creating Collision strategies.
 * @author Hilla Heimberg
 */
public class BrickStrategyFactory {
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final int REMOVE_BRICK_STRATEGY_VALUE = 0;
    private static final int PUCKS_STRATEGY_VALUE = 1;
    private static final int CAMERA_STRATEGY_VALUE = 2;
    private static final int PADDLE_STRATEGY_VALUE = 3;
    private static final int EXTEND_NARROW_STRATEGY = 4;
    private static final int COMPLEX_STRATEGY = 5;

    private final GameObjectCollection gameObjectCollection;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    private final Random random = new Random();

    /**
     * Constructor of the factory.
     * @param gameObjectCollection A container for accumulating/removing instances of GameObject and for
     * handling their collisions.
     * @param gameManager game manager of the "Brick" game
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader Contains a single method: readSound, which reads a wav file from disk. a
     * SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether a given key is
     * currently pressed by the user or not. an InputListener instance for reading user input.
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     * controls visual rendering of the game window and object renderables.
     * @param windowDimensions pixel dimensions for game window height x width
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions) {
        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }

    /**
     * method randomly selects between 5 strategies and returns one CollisionStrategy object which is a
     * wo randomly selected strategies, or decorated by one of the decorator strategies and a pair of
     * additional two decorator strategies.
     * @return CollisionStrategy object.
     */
    public CollisionStrategy getStrategy() {
        int counter = 1; // Number of strategies left to choose randomly

        // regular removeBrick strategy, will be decorated
        CollisionStrategy toBeDecorated = new RemoveBrickStrategy(this.gameObjectCollection);

        // range [minNumber, maxNumber] of number to randomize
        int maxNumber = 5;
        int minNumber = 0;

        while (counter > 0) {
            // randomize a number in range [minNumber, maxNumber]
            int randomStrategy = this.random.nextInt(maxNumber - minNumber + 1) + minNumber;

            switch (randomStrategy) {
                case REMOVE_BRICK_STRATEGY_VALUE:
                    break;
                case PUCKS_STRATEGY_VALUE:
                    toBeDecorated = new PuckStrategy(toBeDecorated, this.imageReader,
                            this.soundReader);
                    break;
                case CAMERA_STRATEGY_VALUE:
                    toBeDecorated = new ChangeCameraStrategy(toBeDecorated, this.windowController,
                            this.gameManager);
                    break;
                case PADDLE_STRATEGY_VALUE:
                    toBeDecorated = new AddPaddleStrategy(toBeDecorated, this.imageReader,
                            this.inputListener, this.windowDimensions);
                    break;
                case EXTEND_NARROW_STRATEGY:
                    toBeDecorated = new ExtendOrNarrowStrategy(toBeDecorated, this.imageReader,
                            this.windowDimensions);

                    break;
                case COMPLEX_STRATEGY: // Complex strategy -  double / trio strategy
                    counter += 2;

                    // trio- choose 3 strategies excluding combinations
                    if (minNumber == 1) {
                        maxNumber = 4;
                    }
                    // double - choose 2 strategies excluding regular
                    minNumber = 1;
                    break;
            }

            // trio will be only after 2 consecutive picks of 5, in order to preserve probability of 1/30
            // namely, this is the entrance of the double strategy
            if (minNumber == 1 && randomStrategy != 5) {
                maxNumber = 4;
            }
            counter --; // one strategy has been chosen
        }
        return toBeDecorated;
    }
}

