package com.mygdx.game.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.cheat.CheatIcon;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.display.RenderPositionCalculator;
import com.mygdx.game.display.SingleField;
import com.mygdx.game.display.Worm;
import com.mygdx.game.game.Elevator;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;
import com.mygdx.game.maincontroller.Controller;
import com.mygdx.game.netwoking.NetworkManager;
import com.mygdx.game.sensor.AccelerationSensor;
import com.mygdx.game.util.SoundHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.mygdx.game.gui.DisplaySizeRatios.DICE_SIZE;
import static com.mygdx.game.gui.DisplaySizeRatios.X_DICE;
import static com.mygdx.game.gui.DisplaySizeRatios.X_LABEL;
import static com.mygdx.game.gui.DisplaySizeRatios.Y_DICE;
import static com.mygdx.game.gui.DisplaySizeRatios.Y_LABEL;

/**
 * The type Main.
 */
public class Main extends ApplicationAdapter  {


    private SpriteBatch batch;
    private Controller controller;
    private Sprite texturePlayerOne;
    private Sprite texturePlayerTwo;
    private String colorOne;
    private String colorTwo;
    private Long randomSeedDice;

    private final RenderPositionCalculator renderPositionCalculator;

    private BitmapFont font;
    /**
     * The Stage.
     */
    private static Stage stage;
    /**
     * The Player one.
     */
    private Worm playerOne;

    private Worm playerTwo;

    private Long lastTimeShaken;


    protected static OrthographicCamera camera;
    protected static Dice dice;
    protected static GameField gameField;
    protected static CheatCountDown cheatCountDown;
    protected static CheatIcon cheatIcon;
    protected Texture diceTextureIdle;
    protected Map<Integer, Texture> diceTextures = new HashMap<>();
    private int time = 0;
    private static boolean diceAnimationActive = false;
    private Map<Integer, Texture> fieldTextures = new HashMap<>();

    private boolean textureBoolean = false;

    private AccelerationSensor accelerationSensor = new AccelerationSensor();

    /**
     * Instantiates a new Main.
     *
     * @param playerList the wormcolor
     */
    public Main(List<String> playerList, Long randomSeedForDice) {

        Elevator.random = new Random(randomSeedForDice);
        gameField = GameField.createGameField();
        this.renderPositionCalculator = new RenderPositionCalculator(gameField);

        this.colorOne = playerList.get(0);
        this.colorTwo = playerList.get(1);
        this.randomSeedDice = randomSeedForDice;
    }


    @Override
    public void create() {

        DisplaySizeRatios.calculateRatios();
        batch = new SpriteBatch();
        cheatCountDown = new CheatCountDown();
        cheatIcon = new CheatIcon();

        stage = new Stage();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        Controller.setSinglePlayerBoolean(true);     //THIS IS ONLY TEMPORARY AND NEEDS TO BE REPLACED SOON!
        SoundHandler.initializeMusicManager();

        //initialisieren der Textur der Spielfigur
        texturePlayerOne = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorOne))));
        texturePlayerTwo = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorTwo))));


        // DICE
        int diceRange = 6;
        dice = new Dice(diceRange, randomSeedDice);

        diceTextureIdle = new Texture(Gdx.files.internal("dice_idle.png"));

        fillDiceTexturesMap(diceRange);
        fillFieldTexturesMap(fieldTextures);


        //Texture des Wurms
        playerOne = new Worm(texturePlayerOne, renderPositionCalculator, Player.PLAYER_ONE_ID);
        playerTwo = new Worm(texturePlayerTwo, renderPositionCalculator, Player.PLAYER_TWO_ID);

        List<Worm> wormList = new ArrayList<>();
        wormList.add(playerOne);
        wormList.add(playerTwo);


        // gibt jedem einzelnen Feld des Spielfelds ein Texture
        List<Field> fields = gameField.getFields();
        setFieldTextures(fields);

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        generateElevatorFieldTextures();

        //Sets the font of text output
        setFont();


        //setzen des InputProcessors der gui
        controller = new Controller(playerOne, playerTwo);
        Gdx.input.setInputProcessor(controller.getInputProcessor());

        lastTimeShaken = TimeUtils.millis();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.addActor(playerOne);

        checkIfSinglePlayer();

        stage.addActor(cheatCountDown);

        camera.update();

        stage.draw();

        batch.begin();

        checkShake();
        playerSwitchTextOutput();
        cheatIcon.draw(batch);
        doDiceAnimation(batch);

        batch.end();

    }

    private void checkIfSinglePlayer() {
        if (Controller.getSinglePlayerBoolean()) {
            stage.addActor(playerTwo);
        }
    }
    private void playerSwitchTextOutput() {


        if (!playerOne.stillMoving() && !controller.getPlayerOneTurn()) {

            if (NetworkManager.isMultiplayer() && !Controller.getWinnerDecided()) {
                font.draw(batch, "Anderer Spieler ist an der Reihe!", X_LABEL - 10, Y_LABEL);
            } else if (!Controller.getWinnerDecided() && NetworkManager.isSinglePlayer()) {
                font.draw(batch, " COM ist an der Reihe", X_LABEL, Y_LABEL);
            }


        } else if (!playerTwo.stillMoving() && controller.getPlayerOneTurn()) {
            if (NetworkManager.isMultiplayer() && !Controller.getWinnerDecided()) {
                font.draw(batch, " Du bist an der Reihe!", X_LABEL, Y_LABEL);

            } else if (!Controller.getWinnerDecided() && NetworkManager.isSinglePlayer()) {
                font.draw(batch, "Spieler ist an der Reihe", X_LABEL, Y_LABEL);
            }

        }
    }

    private void checkShake() {

        if (accelerationSensor.deviceShaken(lastTimeShaken)) {

            controller.checkSensorInput(accelerationSensor);

            lastTimeShaken = TimeUtils.millis();
        }
    }

    private void fillFieldTexturesMap(Map<Integer, Texture> fieldTextures) {

        fieldTextures.put(1, new Texture(Gdx.files.internal("background_grass.png")));
        fieldTextures.put(2, new Texture(Gdx.files.internal("elevator_closed.png")));
        fieldTextures.put(3, new Texture(Gdx.files.internal("start_field.png")));
        fieldTextures.put(4, new Texture(Gdx.files.internal("goal_field.png")));
        fieldTextures.put(5, new Texture(Gdx.files.internal("background_grassTwo.png")));

    }

    private void fillDiceTexturesMap(int diceRange) {

        for (int i = 1; i <= diceRange; i++) {
            diceTextures.put(i, new Texture(Gdx.files.internal("dice_" + i + ".png")));
        }
    }

    private void setFieldTextures(List<Field> fields) {

        for (int i = 1; i <= fields.size(); i++) {

            stage.addActor(checkForFieldTexture(i, renderPositionCalculator, fieldTextures));
        }

    }

    private void setFont() {
        // Font
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(4f, 4f);
    }

    private void generateElevatorFieldTextures() {

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        int[] elevatorFields = Controller.getElevatorFields();

        for (int elevatorField : elevatorFields) {

            SingleField singleField = new SingleField(fieldTextures.get(2), renderPositionCalculator, elevatorField);
            stage.addActor(singleField);
        }

    }

    private SingleField checkForFieldTexture(int i, RenderPositionCalculator renderPositionCalculator, Map<Integer, Texture> list) {

        SingleField fieldColors;
        switch (i) {

            case 1:
                fieldColors = new SingleField(list.get(3), renderPositionCalculator, i);
                break;
            case 91:
                fieldColors = new SingleField(list.get(4), renderPositionCalculator, i);
                textureBoolean = true;
                break;

            default:
                fieldColors = setFieldColors(list, i);
                break;

        }
        return fieldColors;
    }

    private SingleField setFieldColors(Map<Integer, Texture> list, int i) {
        if (!textureBoolean) {
            textureBoolean = i % 10 != 0;
            return new SingleField(list.get(1), renderPositionCalculator, i);
        } else {
            textureBoolean = i % 10 == 0;
            return new SingleField(list.get(5), renderPositionCalculator, i);
        }
    }

    /**
     * Gets dice.
     *
     * @return the dice
     */
    public static Dice getDice() {
        return dice;
    }

    /**
     * Gets game field.
     *
     * @return the game field
     */
    public static GameField getGameField() {
        return gameField;
    }

    /**
     * Gets cheat countdown.
     *
     * @return the cheat countdown
     */
    public static CheatCountDown getCheatCountdown() {
        return cheatCountDown;
    }

    public static CheatIcon getCheatIcon() {
        return cheatIcon;
    }

    /**
     * Gets camera.
     *
     * @return the camera
     */
    public static OrthographicCamera getCamera() {
        return camera;
    }

    public static Stage getStage() {
        return stage;
    }


    /**
     * Sets dice animation true.
     */
    public static void setDiceAnimationTrue() {
        diceAnimationActive = true;
    }

    private Texture getDiceTexture() {
        if (dice.getResult() == null)
            return diceTextureIdle;
        return diceTextures.get(dice.getResult());
    }

    /**
     * Do dice animation.
     *
     * @param batch the batch
     */
    private void doDiceAnimation(SpriteBatch batch) {

        if (diceAnimationActive) {
            Animation a = dice.createAnimation(diceTextures);
            batch.draw((TextureRegion) a.getKeyFrame((float) (Math.random() * dice.getRange() + 1), true), X_DICE, Y_DICE, DICE_SIZE, DICE_SIZE);
            time++;
            if (time > 12) {
                diceAnimationActive = false;
                time = 0;
                batch.draw(getDiceTexture(), X_DICE, Y_DICE, DICE_SIZE, DICE_SIZE);
            }

        } else {
            batch.draw(getDiceTexture(), X_DICE, Y_DICE, DICE_SIZE, DICE_SIZE);
        }
    }
}
