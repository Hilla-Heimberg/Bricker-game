// USER_NAME = hilla_heimberg
// ID - 208916221

package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.gameobjects.*;
import java.util.Random;

/**
 * This class is the game manager of the "Brick" game.
 * Game manager - this class is responsible for game initialization, holding references for game objects
 * and calling update methods for every update iteration. Entry point for code should be in a main method
 * in this class.
 * @author Hilla Heimberg
 */
public class BrickerGameManager extends GameManager {
    // -------------------------------------- PRIVATE -------------------------------------
    // CONSTANTS
    private static final int BALL_SPEED = 200;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 20;
    private static final int NUM_BRICKS_IN_ROW = 8;
    private static final int GAP_FROM_BORDER = 5;
    private static final int NUM_BRICKS_IN_COL = 5;
    private static final int GAP_BETWEEN_BRICKS = 1;
    private static final int BRICK_HEIGHT = 15;
    private static final int MIN_DISTANCE_FROM_EDGE = 20;
    private static final int DIMENSIONS_FOR_HEART = 30;
    private static final int NUM_OF_LIVES = 4;
    private static final int LIFE_COUNTER_WIDTH = 70;
    private static final int LIFE_COUNTER_HEIGHT = 20;
    private static final int DISTANCE_FROM_LEFT_BORDER = 30;
    private static final int DISTANCE_FROM_BOTTOM_BORDER = 70;
    private static final int DISTANCE_FROM_BOTTOM_EDGE = 50;
    private static final int DISTANCE_FROM_BOTTOM = 30;
    private static final int WINDOW_WIDTH_VALUE = 700;
    private static final int WINDOW_HEIGHT_VALUE = 500;

    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private GraphicLifeCounter graphicLifeCounter;
    private NumericLifeCounter numericLifeCounter;
    private Counter livesCounter;
    private Counter bricksCounter;

    // --------------------------------- STRINGS FOR GAME ---------------------------------
    private static final String BRICKER = "Bricker";
    private static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    private static final String HEART_IMAGE = "assets/heart.png";
    private static final String PADDLE_IMAGE = "assets/paddle.png";
    private static final String BRICK_IMAGE = "assets/brick.png";
    private static final String BALL_IMAGE = "assets/ball.png";
    private static final String COLLISION_SOUND_FILE = "assets/blop_cut_silenced.wav";
    private static final String YOU_WON_MESSAGE = "You won!";
    private static final String YOU_LOSE_MESSAGE = "You lose!";
    private static final String PLAY_AGAIN_MESSAGE = " Play again?";

    // -------------------------------------- PUBLIC -------------------------------------
    /**
     * the border width for the "Bricker" game.
     */
    public static final int BORDER_WIDTH = 20;

    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor. Construct a new game.
     * @param windowTitle title of the window
     * @param windowDimensions pixel dimensions for game window height x width
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * The method will be called once when a GameGUIComponent is created,
     * and again after every invocation of windowController.resetGame().
     * Calling this function should initialize the game window. It should initialize objects in the game
     * window - ball, paddle, walls, life counters, bricks.
     * This version of the game has 5 rows, 8 columns of bricks.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. a SoundReader instance for reading soundclips from files for
     *                         rendering event sounds.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. an InputListener
     *                         instance for reading user input.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window. controls visual rendering of the game window and
     *                         object renderables.
     * @see ImageReader
     * @see SoundReader
     * @see UserInputListener
     * @see WindowController
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {

        // initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        MockPaddle.isInstantiated = false;

        createBall(imageReader, soundReader);

        createBricks(imageReader, soundReader, inputListener);

        createPaddle(imageReader, inputListener);

        createBorders(null);

        createBackground(imageReader, windowController);

        createCounters(imageReader); // Creating graphic counter and numeric counter
    }

    /*
    Creates background for the "Brick" game.
     */
    private void createBackground(ImageReader imageReader, WindowController windowController) {
        Renderable backgroundImage =  imageReader.readImage(BACKGROUND_IMAGE,false);
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                backgroundImage);

        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    /*
     Create counters for the "Brick" game. Numeric counter and Graphic counter.
     */
    private void createCounters(ImageReader imageReader) {
        this.livesCounter = new Counter(NUM_OF_LIVES);
        Renderable heartImage = imageReader.readImage(HEART_IMAGE, true);

        this.graphicLifeCounter = new GraphicLifeCounter(new Vector2(DISTANCE_FROM_LEFT_BORDER,
                this.windowDimensions.y() - DISTANCE_FROM_BOTTOM_EDGE), new Vector2(DIMENSIONS_FOR_HEART,
                DIMENSIONS_FOR_HEART), this.livesCounter, heartImage, gameObjects(), NUM_OF_LIVES);

        this.numericLifeCounter = new NumericLifeCounter(this.livesCounter,
                new Vector2(DISTANCE_FROM_LEFT_BORDER, this.windowDimensions.y() -
                        DISTANCE_FROM_BOTTOM_BORDER), new Vector2(LIFE_COUNTER_WIDTH, LIFE_COUNTER_HEIGHT),
                gameObjects());
    }

    /*
     Create paddle for the "Brick" game.
     */
    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE, true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage,
                inputListener, this.windowDimensions, MIN_DISTANCE_FROM_EDGE);
        paddle.setCenter(new Vector2(this.windowDimensions.x()/2,
                (int) this.windowDimensions.y() - DISTANCE_FROM_BOTTOM));
        gameObjects().addGameObject(paddle);
    }

    /*
     Create wall of bricks for the "Brick" game.
     */
    private void createBricks(ImageReader imageReader,SoundReader soundReader, UserInputListener inputListener) {
        this.bricksCounter = new Counter(NUM_BRICKS_IN_COL * NUM_BRICKS_IN_ROW);
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE, false);
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(),this,
                imageReader, soundReader, inputListener, this.windowController, this.windowDimensions);

        int totalWidth = (int) this.windowDimensions.x() -
                (2 * (BORDER_WIDTH)) - ((NUM_BRICKS_IN_ROW + 1) * GAP_BETWEEN_BRICKS);
        int brickWidth = totalWidth / NUM_BRICKS_IN_ROW;
        int brickStartIndX = BORDER_WIDTH + GAP_FROM_BORDER;

        for (int i = 0; i < NUM_BRICKS_IN_ROW; i++){
            int brickStartIndY = BORDER_WIDTH;
            for (int j = 0; j < NUM_BRICKS_IN_COL; j++){
                Brick brick = new Brick(new Vector2(brickStartIndX, brickStartIndY),
                        new Vector2(brickWidth, BRICK_HEIGHT), brickImage, brickStrategyFactory.getStrategy(),
                        this.bricksCounter);

                brickStartIndY += BRICK_HEIGHT + GAP_BETWEEN_BRICKS;
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
            brickStartIndX +=  GAP_BETWEEN_BRICKS + brickWidth;
        }
    }

    /*
     Create ball for the "Brick" game.
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage =  imageReader.readImage(BALL_IMAGE,true);
        Sound collisionSound = soundReader.readSound(COLLISION_SOUND_FILE);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

        repositionBall(this.ball);
        gameObjects().addGameObject(this.ball);
    }

    /*
     Create borders for the "Brick" game.
     */
    private void createBorders(Renderable renderable) {
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH,
                this.windowDimensions.y()), renderable)
        );
        gameObjects().addGameObject(new GameObject(new Vector2(this.windowDimensions.x() - BORDER_WIDTH,
                0), new Vector2(BORDER_WIDTH, this.windowDimensions.y()), renderable)
        );
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(),
                BORDER_WIDTH), renderable));
    }


    /**
     * Code in this function is run every frame update.
     * You do not need to call this method yourself.
     * @param deltaTime time between updates. For internal use by game engine.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnded(deltaTime);
        // we need to delete from gameObjects all the objects which across the bottom border
        for (GameObject gameObject : gameObjects()){
            if (gameObject.getCenter().y() > this.windowDimensions.y()){
                gameObjects().removeGameObject(gameObject);
            }
        }
    }


    /*
    Checks if the game ended and presents informative message for the player.
     */
    private void checkForGameEnded(float deltaTime) {
        float ballHeight = this.ball.getCenter().y();
        String prompt = "";

        if (this.bricksCounter.value() == 0){
            prompt = YOU_WON_MESSAGE;
        }
        if (this.livesCounter.value() == 0) {
            prompt = YOU_LOSE_MESSAGE;
        }
        if (ballHeight > this.windowDimensions.y()) {
            repositionBall(this.ball);
            this.graphicLifeCounter.update(deltaTime);
            this.numericLifeCounter.update(deltaTime);
        }
        if (!prompt.isEmpty()){
            prompt += PLAY_AGAIN_MESSAGE;
            if (this.windowController.openYesNoDialog(prompt)) {
                this.windowController.resetGame();
            }
            else {
                this.windowController.closeWindow();
            }
        }
    }

    /**
     * Repositions the ball in the middle of the window and set his velocity.
     * @param ball ball of the game
     */
    public void repositionBall(GameObject ball) {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()){
            ballVelX *= -1;
        }
        if (rand.nextBoolean()){
            ballVelY *= -1;
        }

        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(this.windowDimensions.mult(0.5F));
    }

    /**
     * Entry point for game. Should contain:
     * 1. An instantiation call to BrickerGameManager constructor.
     * 2. A call to run() method of instance of BrickerGameManager.
     * Should initialize game window of dimensions (x,y) = (700,500).
     * @param args arguments
     */
    public static void main(String[] args) {
        new BrickerGameManager(BRICKER, new Vector2(WINDOW_WIDTH_VALUE, WINDOW_HEIGHT_VALUE)).run();
    }
}
