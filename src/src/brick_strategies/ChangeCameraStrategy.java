// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Changes camera focus from ground to ball
 * until ball collides NUM_BALL_COLLISIONS_TO_TURN_OFF times.
 * @author Hilla Heimberg
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final int COUNT_DOWN_VALUE = 4;

    private final BrickerGameManager gameManager;
    private final WindowController windowController;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor of the camera strategy.
     * @param toBeDecorated the strategy we will decorate
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     * controls visual rendering of the game window and object renderables.
     * @param gameManager the game manager of the "Brick" game.
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                 WindowController windowController,
                                 BrickerGameManager gameManager){
        super(toBeDecorated);
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    /**
     * Change camera position on collision and delegate to held CollisionStrategy.
     * @param thisObj the brick which participant in the collision.
     * @param otherObj the ball which participant in the collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);

        if (gameManager.getCamera() == null) {
            for (GameObject gameObject : getGameObjectCollection()){
                if (gameObject instanceof Ball && (!(gameObject instanceof Puck))){

                    BallCollisionCountdownAgent ballCollisionCountdownAgent =
                            new BallCollisionCountdownAgent((Ball)gameObject, this, COUNT_DOWN_VALUE);
                    getGameObjectCollection().addGameObject(ballCollisionCountdownAgent, Layer.BACKGROUND);

                    gameManager.setCamera(new Camera(gameObject, Vector2.ZERO,
                            windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                            windowController.getWindowDimensions()));  //share the window dimensions
                    return;
                }
            }
        }
    }

    /**
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange(){
        this.gameManager.setCamera(null);
    }
}
