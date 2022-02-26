// USER_NAME = hilla_heimberg
// ID - 208916221

package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Layer;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * An object of this class is instantiated on collision of ball with a brick with a change camera strategy.
 * It checks ball's collision counter every frame, and once the it finds the ball has collided countDownValue
 * times since instantiation, it calls the strategy to reset the camera to normal.
 * @author Hilla Heimberg
 */
public class BallCollisionCountdownAgent extends GameObject {
    // -------------------------------------- PRIVATE -------------------------------------
    private final ChangeCameraStrategy owner;
    private final int countDownValue;
    private final int counterCollisionFromStart;
    private final Ball ball;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor. Construct a new GameObject instance. Construct a BallCollisionCountdownAgent.
     * @param ball Ball object whose collisions are to be counted.
     * @param owner Object asking for countdown notification.
     * @param countDownValue Number of ball collisions. Notify caller object that the ball collided
     * countDownValue times since instantiation.
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue){
        super(ball.getTopLeftCorner(), ball.getDimensions(), null);
        this.owner = owner;
        this.ball = ball;
        this.countDownValue = countDownValue;
        this.counterCollisionFromStart =  ball.getCollisionCount();
    }

    /**
     * updates the camera status respectively to the counter of the collisions.
     * @param deltaTime time between updates.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (this.ball.getCollisionCount() - this.counterCollisionFromStart >  this.countDownValue) {
            this.owner.turnOffCameraChange();
            this.owner.getGameObjectCollection().removeGameObject(this, Layer.BACKGROUND);
        }
    }
}
