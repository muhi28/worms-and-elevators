package com.mygdx.game.GUI;

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
import com.mygdx.game.main_controler.Controler;
import com.mygdx.game.netwoking.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static com.mygdx.game.GUI.DisplaySizeRatios.DICE_SIZE;
import static com.mygdx.game.GUI.DisplaySizeRatios.X_DICE;
import static com.mygdx.game.GUI.DisplaySizeRatios.X_LABEL;
import static com.mygdx.game.GUI.DisplaySizeRatios.Y_DICE;
import static com.mygdx.game.GUI.DisplaySizeRatios.Y_LABEL;

/**
 * The type Main.
 */
public class Main extends ApplicationAdapter implements Observer {


    private SpriteBatch batch;
    private Controler controler;
    private Sprite texturePlayerOne;
    private Sprite texturePlayerTwo;
    private Texture tileOne;
    private Texture tileTwo;
    private String colorOne;
    private String colorTwo;
    private Long randomSeedDice;

    private final RenderPositionCalculator renderPositionCalculator;

    private BitmapFont font;
    /**
     * The Stage.
     */
    private Stage stage;
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
    private static int time = 0;
    private static boolean diceAnimationActive = false;

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


        Controler.setSinglePlayerBoolean(true);     //THIS IS ONLY TEMPORARY AND NEEDS TO BE REPLACED SOON!

        //initialisieren der Textur der Spielfigur
        texturePlayerOne = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorOne))));
        texturePlayerTwo = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorTwo))));


        tileOne = new Texture(Gdx.files.internal("background_grass.png"));
        tileTwo = new Texture(Gdx.files.internal("background_elevator.png"));


        // DICE
        int diceRange = 6;
        dice = new Dice(diceRange, randomSeedDice);

        diceTextureIdle = new Texture(Gdx.files.internal("dice_idle.png"));
        for (int i = 1; i <= diceRange; i++) {
            diceTextures.put(i, new Texture(Gdx.files.internal("dice_" + i + ".png")));
        }


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


        // Font
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(4, 4);


        //setzen des InputProcessors der GUI
        controler = new Controler(playerOne, playerTwo);
        Gdx.input.setInputProcessor(controler.getInputProcessor());

        lastTimeShaken = TimeUtils.millis();
    }

    private void setFieldTextures(List<Field> fields) {

        for (int i = 1; i <= fields.size(); i++) {


            SingleField singleField = new SingleField(tileOne, renderPositionCalculator, i);
            stage.addActor(singleField);
        }

    }

    private void generateElevatorFieldTextures() {

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        int[] elevatorFields = Controler.getElevatorFields();

        for (int elevatorField : elevatorFields) {

            SingleField singleField = new SingleField(tileTwo, renderPositionCalculator, elevatorField);
            stage.addActor(singleField);
        }

    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.addActor(playerOne);

        if (Controler.getSinglePlayerBoolean()) {
            stage.addActor(playerTwo);
        }

        stage.addActor(cheatCountDown);

        camera.update();

        stage.draw();

        batch.begin();

        checkDeviceShaken();
        playerSwitchTextOutput();
        cheatIcon.draw(batch);
        doDiceAnimation(batch);

        batch.end();

    }

    private void playerSwitchTextOutput() {


        if (!playerOne.stillMoving() && !Controler.getPlayerOneTurn()) {

            if (NetworkManager.isMultiplayer()) {
                font.draw(batch, "Andere Spieler  ist an der Reihe!", X_LABEL, Y_LABEL);
            } else {
                font.draw(batch, "Spieler 2 ist an der Reihe", X_LABEL, Y_LABEL);
            }

        } else if (!playerTwo.stillMoving() && Controler.getPlayerOneTurn()) {
            if (NetworkManager.isMultiplayer()) {
                font.draw(batch, "Du bist an der Reihe!", X_LABEL, Y_LABEL);

            } else {
                font.draw(batch, "Spieler 1 ist an der Reihe", X_LABEL, Y_LABEL);
            }

        }
    }

    private void checkDeviceShaken() {

        if (TimeUtils.millis() > (lastTimeShaken + 500)) {

            boolean shaken = controler.checkAcceleration();

            Gdx.app.log("GESTURE CONTROLL", String.format("Device shaken: %b", shaken));


            lastTimeShaken = TimeUtils.millis();
        }
    }


    @Override
    public void update(Observable observable, Object o) {
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


    /**
     * Sets dice animation true.
     */
    public static void setDiceAnimationTrue() {
        diceAnimationActive = true;
    }

    public Texture getDiceTexture() {
        if (dice.getResult() == null)
            return diceTextureIdle;
        return diceTextures.get(dice.getResult());
    }

    /**
     * Do dice animation.
     *
     * @param batch the batch
     */
    public void doDiceAnimation(SpriteBatch batch) {

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
