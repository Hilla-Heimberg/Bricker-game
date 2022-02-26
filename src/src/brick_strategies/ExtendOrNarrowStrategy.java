// USER_NAME = hilla_heimberg
// ID - 208916221

package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.StatusChanger;
import java.util.Random;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Extends or narrows the paddle which
 * participants in the collision.
 * @author Hilla Heimberg
 */
public class ExtendOrNarrowStrategy extends RemoveBrickStrategyDecorator{
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final int SPEED_FOR_STATUS = 160; // Ball speed * 0.8
    private static final float VALUE_FOR_LIMIT = 0.75f;
    private static final float EXTEND_VALUE = 1.2f;
    private static final float NARROW_VALUE = 0.8f;
    private static final int EXTEND_CASE = 0;
    private static final int NARROW_CASE = 1;

    private final ImageReader imageReader;
    private final Vector2 windowDimensions;
    private final Random random = new Random();
    private boolean isExtend;
    private boolean isNarrow;

    // --------------------------------- STRINGS FOR GAME ---------------------------------
    private static final String BUFF_NARROW_IMAGE = "assets/buffNarrow.png";
    private static final String BUFF_WIDEN_IMAGE = "assets/buffWiden.png";

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor of the "extend or narrow" strategy.
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * an ImageReader instance for reading images from files for rendering of objects.
     * @param windowDimensions pixel dimensions for game window height x width
     */
    public ExtendOrNarrowStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, Vector2
            windowDimensions){
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.isNarrow = false;
        this.isExtend = false;
        this.windowDimensions = windowDimensions;
    }

    /**
     * extend/narrow the paddle in the game in case of collision and delegate to held CollisionStrategy.
     * @param thisObj the brick which participant in the collision.
     * @param otherObj the ball which participant in the collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);

        Vector2 locationForStatus = thisObj.getCenter();
        Vector2 dimensionsForStatus = thisObj.getDimensions();

        // flipped coin for choosing between narrow or extend
        int randomCoin = this.random.nextInt(2);
        switch (randomCoin){
            case EXTEND_CASE: // Create extend
                this.isExtend = true;
                createStatusChanger(locationForStatus, dimensionsForStatus, BUFF_WIDEN_IMAGE);
                return;

            case NARROW_CASE: // Create narrow
                this.isNarrow = true;
                createStatusChanger(locationForStatus, dimensionsForStatus, BUFF_NARROW_IMAGE);
        }
    }

    /*
    Creates status changer for the game in accordance to the "extend" or "narrow" strategy.
     */
    private void createStatusChanger(Vector2 locationForStatus, Vector2 dimensionsForStatus,
                                     String buffWidenImage) {
        Renderable extendImage = this.imageReader.readImage(buffWidenImage, true);
        StatusChanger extended = new StatusChanger(Vector2.ZERO, dimensionsForStatus,
                extendImage, getGameObjectCollection(), this);

        extended.setVelocity(new Vector2(0, SPEED_FOR_STATUS));
        extended.setCenter(new Vector2(locationForStatus));
        getGameObjectCollection().addGameObject(extended);
    }

    /**
     * Extends and Narrows the paddle object in case of collision between the status changer to the paddle.
     * @param other the paddle which participant in the collision.
     */
    public void extendAndNarrow(GameObject other){
        if (this.isExtend){
            if (other.getDimensions().x() < windowDimensions.x() * VALUE_FOR_LIMIT){
                Vector2 dimensionsAfterExtend = new Vector2(other.getDimensions().x() * EXTEND_VALUE,
                        other.getDimensions().y());
                other.setDimensions(dimensionsAfterExtend);
            }
        }
        if (this.isNarrow) {
            Vector2 dimensionsAfterNarrow = new Vector2(other.getDimensions().x() * NARROW_VALUE,
                    other.getDimensions().y());
            other.setDimensions(dimensionsAfterNarrow);
        }
    }
}
