// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;
import java.util.Random;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces several pucks instead of brick once removed.
 * @author Hilla Heimberg
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator {
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final int PUCK_BALL_SPEED = 160; // Ball speed * 0.8
    private static final int NUM_OF_PUCK_BALLS = 3;
    private static final int GAP_BETWEEN_PUCKS = 5;
    private static final int GAP_FROM_BRICK = 20;

    private static Renderable puckImage = null; // In order to avoid Linux error
    private static Sound collisionSound = null;  // In order to avoid Linux error
    private final int [] valuesArrayForVelocity = {0, 1, -1};

    // --------------------------------- STRINGS FOR GAME ---------------------------------
    private static final String MOCK_BALL_IMAGE = "assets/mockBall.png";
    private static final String COLLISION_SOUND_WAV = "assets/Bubble5_4.wav";

    // -------------------------------------- METHODS --------------------------------------
    /**
     * Constructor of the puck balls strategy.
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader the image of the mock ball
     * @param soundReader the sound of the collision
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader){
        super(toBeDecorated);
        if (puckImage == null) {
            puckImage =  imageReader.readImage(MOCK_BALL_IMAGE,true);
        }
        if (collisionSound == null) {
            collisionSound = soundReader.readSound(COLLISION_SOUND_WAV);
        }
    }

    /**
     * Add pucks to game on collision and delegate to held CollisionStrategy.
     * @param thisObj the brick which participant in the collision.
     * @param otherObj the ball which participant in the collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);

        float xLocationForPuckBall = thisObj.getCenter().x();
        float yLocationForPuckBall = thisObj.getCenter().y();

        float radiusForPuckBall = thisObj.getDimensions().x()/3;
        Vector2 dimensionsForPuckBall =  new Vector2(radiusForPuckBall, radiusForPuckBall);

        for (int i = 0; i < NUM_OF_PUCK_BALLS; i++) {
            createPucksBalls(new Vector2(xLocationForPuckBall + GAP_BETWEEN_PUCKS *
                    this.valuesArrayForVelocity[i], yLocationForPuckBall + GAP_FROM_BRICK),
                    dimensionsForPuckBall);
        }
    }

    /*
    Creates the pucks ball and adds them to the game.
     */
    private void createPucksBalls(Vector2 locationForPuckBall, Vector2 dimensionsForPuckBall) {
        Puck puckBall = new Puck(Vector2.ZERO, dimensionsForPuckBall, this.puckImage, this.collisionSound);
        float mockBallVelX = PUCK_BALL_SPEED;
        Random rand = new Random();
        mockBallVelX *= this.valuesArrayForVelocity[rand.nextInt(this.valuesArrayForVelocity.length)];

        puckBall.setVelocity(new Vector2(mockBallVelX, (float) PUCK_BALL_SPEED));
        puckBall.setCenter(locationForPuckBall);
        getGameObjectCollection().addGameObject(puckBall);
    }
}
