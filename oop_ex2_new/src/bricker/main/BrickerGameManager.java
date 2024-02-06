package bricker.main;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameObject.Ball;
import bricker.gameObject.Brick;
import bricker.gameObject.Heart;
import bricker.gameObject.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;

import danogl.util.Vector2;
import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    /* Dimensions of gameObjects */
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 500;
    private static final int BORDER_WIDTH = 5;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final int BRICK_HEIGHT = 15;
    private static final int BALL_RADIUS = 35;
    private static final float BALL_SPEED = 150;
    private static final float HEART_SIZE = 15;
    private static final float COUNTER_SIZE = 12;


    /* Default values for the game */
    private static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(255, 255, 255));
    private static final int FIRST_BRICK_X_POSITION = 15;
    private static final int FIRST_BRICK_Y_POSITION = 30;
    private static final int PIXELS_BETWEEN_BRICKS = 5;
    private static final int INITIAL_PLAYERS_LIVES = 3;
    private static final int MAX_PLAYERS_LIVES = 4;
    private static int numBricksRows = 7;
    private static int numBricksCols = 8;

    private static int playerLives = INITIAL_PLAYERS_LIVES;
    private static Vector2 windowDimensions;
    private Ball ball;
    private WindowController windowController;
    private Vector2 nextHeartPosition;
    private GameObject[] heartObjects;
    private TextRenderable livesCounterText;

    /* UI Messages */
    private static final String WIN_MESSAGE = "You win! ";
    private static final String LOSE_MESSAGE = "You lose! ";
    private static final String PLAY_AGAIN_MESSAGE = "Play again?";


    public BrickerGameManager(String windowTitle, Vector2 windowDimension) {
        super(windowTitle, windowDimension);
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager = new BrickerGameManager("BrickerGame", new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT));
        if (args.length > 0) {
            numBricksRows = Integer.parseInt(args[1]);
            numBricksCols = Integer.parseInt(args[0]);
        }
        gameManager.run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        createBackground(imageReader);
        createBall(imageReader, soundReader);
        createPaddle(imageReader, inputListener);
        createBorders();
        createBricks(imageReader);
        createLivesUI(imageReader);
    }

    public static Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /* Game conditions Methods */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = checkLoseCondition(ballHeight);
        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN_MESSAGE;
            if (windowController.openYesNoDialog(prompt)) {
                playerLives = INITIAL_PLAYERS_LIVES;
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    private String checkLoseCondition(double ballHeight) {
        String prompt = "";
        if (ballHeight > windowDimensions.y()) {
            playerLives--;
            System.out.println(playerLives);
            removeHeart();
            setCounter(playerLives);
            if (playerLives > 0) {
                Vector2 startPosition = windowDimensions.mult(0.5f);
                ball.setCenter(startPosition);
                setBallInitialVelocity();
            } else {
                prompt = LOSE_MESSAGE;
            }
        }
        return prompt;
    }

    /* Bricks Methods */
    private void createBricks(ImageReader imageReader) {
        Renderable brickImage = imageReader.readImage("assets/brick.png", true);
        int brickWidth = calculateBrickWidth((int) windowDimensions.x());
        int brickXPosition = FIRST_BRICK_X_POSITION;
        int brickYPosition = FIRST_BRICK_Y_POSITION;
        for (int i = 0; i < numBricksRows; i++) {
            for (int j = 0; j < numBricksCols; j++) {
                createBrick(brickWidth, brickXPosition, brickYPosition, brickImage);
                brickXPosition += brickWidth + PIXELS_BETWEEN_BRICKS;
            }
            brickYPosition += BRICK_HEIGHT + PIXELS_BETWEEN_BRICKS;
            brickXPosition = FIRST_BRICK_X_POSITION;
        }
    }

    private int calculateBrickWidth(int windowWidth) {
        /*this calculation involves:
        1. cut the window width from both sizes in FIRST_BRICK_X_POSITION pixels
        2. add PIXELS_BETWEEN_BRICKS to the result from above because every brick have this interval after the instance.
        3. divide the sum from prev step with numBricksCols to get the brickWidth + the interval.
        4. subtract the PIXELS_BETWEEN_BRICKS to remove the interval from the result.
         */

        int windowWidthWithoutBorders = windowWidth - ((FIRST_BRICK_X_POSITION * 2) - PIXELS_BETWEEN_BRICKS);
        return (windowWidthWithoutBorders / numBricksCols) - PIXELS_BETWEEN_BRICKS;
    }

    private void createBrick(int width, int positionX, int positionY, Renderable brickImage) {
        Vector2 brickDimensions = new Vector2(width, BRICK_HEIGHT);
        Vector2 startPosition = new Vector2(positionX, positionY);
        CollisionStrategy strategy = new BasicCollisionStrategy(gameObjects());
        Brick newBrick = new Brick(startPosition, brickDimensions, brickImage, strategy);
        gameObjects().addGameObject(newBrick);
    }

    /* Ball methods */
    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Vector2 startPosition = windowDimensions.mult(0.5f);
        Vector2 dim = new Vector2(BALL_RADIUS, BALL_RADIUS);
        ball = new Ball(startPosition, dim, ballImage, collisionSound);
        setBallInitialVelocity();
        gameObjects().addGameObject(ball);

    }

    private void setBallInitialVelocity() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /* Lives Methods */
    private void createLivesUI(ImageReader imageReader) {
        createHearts(imageReader);
        createLivesCounter();

    }

    private void createLivesCounter() {
        livesCounterText = new TextRenderable(String.valueOf(playerLives));
        Vector2 position = new Vector2(15, windowDimensions.y() - 15);
        GameObject livesCounter = new GameObject(position, new Vector2(COUNTER_SIZE, COUNTER_SIZE), livesCounterText);
        gameObjects().addGameObject(livesCounter, Layer.UI);
        setCounter(playerLives);
    }

    private void setCounter(int lives) {
        switch (lives) {
            case 4:
            case 3:
                livesCounterText.setColor(Color.green);
                break;
            case 2:
                livesCounterText.setColor(Color.yellow);
                break;
            case 1:
                livesCounterText.setColor(Color.red);
                break;
            default:
                return;
        }
        livesCounterText.setString(String.valueOf(playerLives));
    }

    private void createHearts(ImageReader imageReader) {
        heartObjects = new GameObject[MAX_PLAYERS_LIVES];
        nextHeartPosition = new Vector2(50, windowDimensions.y() - HEART_SIZE);
        for (int i = 0; i < playerLives; i++) {
            addHeart(imageReader, i);
        }

    }

    private void addHeart(ImageReader imageReader, int index) {
        if (playerLives < MAX_PLAYERS_LIVES) {
            Renderable heartImage = imageReader.readImage("assets/heart.png", true);
            GameObject heart = new GameObject(nextHeartPosition, new Vector2(HEART_SIZE, HEART_SIZE), heartImage);
            gameObjects().addGameObject(heart);
            heartObjects[index] = heart;
            nextHeartPosition = new Vector2(nextHeartPosition.x() + HEART_SIZE + PIXELS_BETWEEN_BRICKS, nextHeartPosition.y());
        }
    }

    private void removeHeart() {
        System.out.println(heartObjects[playerLives] != null);
        gameObjects().removeGameObject(heartObjects[playerLives]);
        System.out.println(heartObjects[playerLives] != null);
        nextHeartPosition = new Vector2(nextHeartPosition.x() - HEART_SIZE - PIXELS_BETWEEN_BRICKS, nextHeartPosition.y());
    }

    /* Paddle methods */
    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        Vector2 startPosition = new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30);
        Vector2 paddleDimension = new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle newPaddle = new Paddle(startPosition, paddleDimension, paddleImage, inputListener);
        gameObjects().addGameObject(newPaddle);
    }

    /* Borders and Background methods */
    private void createBorders() {
        // Left
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        BORDER_RENDERABLE)
        );
        // Right
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        BORDER_RENDERABLE));
        // Upper
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(0, 0),
                        new Vector2(windowDimensions.x(), 20),
                        BORDER_RENDERABLE));
    }

    private void createBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        Vector2 backgroundSize = new Vector2(windowDimensions.x(), windowDimensions.y());
        GameObject newBackground = new GameObject(Vector2.ZERO, backgroundSize, backgroundImage);
        gameObjects().addGameObject(newBackground, Layer.BACKGROUND);
    }
}


