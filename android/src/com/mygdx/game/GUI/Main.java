package com.mygdx.game.GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.display.RenderPositionCalculator;
import com.mygdx.game.display.SingleField;
import com.mygdx.game.display.Worm;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.main_controler.Controler;

/**
 * Created by Muhi on 12.04.2017.
 */
public class Main extends ApplicationAdapter implements Observer {

    private static OrthographicCamera camera;

    private SpriteBatch batch;
    private Controler controler;
    private Sprite texturePlayer;
    private Texture tile1;
    private Texture tile2;
    private static Dice dice;
    private String color;
    private static GameField gameField;
    private final RenderPositionCalculator renderPositionCalculator;
    private static CheatCountDown cheatCountDown;
    private static int time = 0;
    private static boolean diceAnimationActive = false;
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Player one.
     */
    Worm playerOne;

    private static Sprite diceSprite;


    /**
     * Instantiates a new Main.
     *
     * @param wormcolor the wormcolor
     */
    public Main(String wormcolor) {
        gameField = GameField.createGameField();
        this.renderPositionCalculator = new RenderPositionCalculator(gameField);

        this.color = wormcolor;
    }


    @Override
    public void create() {

        batch = new SpriteBatch();
        cheatCountDown = new CheatCountDown();

        stage = new Stage();


        camera = new OrthographicCamera();

        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //initialisieren der Textur der Spielfigur
        texturePlayer = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", color))));

        tile1 = new Texture(Gdx.files.internal("background_grass.png"));
        tile2 = new Texture(Gdx.files.internal("background_elevator.png"));


        // DICE
        dice = new Dice(6);
        diceSprite = new Sprite(dice.getDiceTexture());
        diceSprite.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 800, 200, 200);


        //Texture des Wurms
        playerOne = new Worm(texturePlayer, renderPositionCalculator);


        // gibt jedem einzelnen Feld des Spielfelds ein Texture
        List<Field> fields = gameField.getFields();
        setFieldTextures(fields);

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        generateElevatorFieldTextures();


        //setzen des InputProcessors der GUI
         controler = new Controler();
        Gdx.input.setInputProcessor(controler.getInputProcessor());
    }

    private void setFieldTextures(List<Field> fields){

        for (int i = 1; i <= fields.size(); i++) {


            SingleField singleField = new SingleField(tile1, renderPositionCalculator, i);
            stage.addActor(singleField);
        }

    }

    private void generateElevatorFieldTextures(){

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        int[] elevatorFields = Controler.getElevatorFields();

        for (int i = 0; i < elevatorFields.length; i++) {

            SingleField singleField = new SingleField(tile2, renderPositionCalculator, elevatorFields[i]);
            stage.addActor(singleField);
        }

    }


    @Override
    public void render() {


        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.addActor(playerOne);

        stage.addActor(cheatCountDown);

        camera.update();

        stage.draw();

        batch.begin();

        doDiceAnimation(batch);

        batch.end();

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

    /**
     * Gets camera.
     *
     * @return the camera
     */
    public static OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Gets dice sprite.
     *
     * @return the dice sprite
     */
    public static Sprite getDiceSprite() {
        return diceSprite;
    }

    /**
     * Sets dice animation true.
     */
    public static void setDiceAnimationTrue() {
        diceAnimationActive = true;
    }

    /**
     * Do dice animation.
     *
     * @param batch the batch
     */
    public static void doDiceAnimation(SpriteBatch batch) {
        float x = Gdx.graphics.getWidth() / 2 - 100;
        float y = Gdx.graphics.getHeight() / 2 - 800;
        float i = 200;
        diceSprite.draw(batch);
        Animation a = dice.createAnimation();

        if (diceAnimationActive) {
            batch.draw((TextureRegion) a.getKeyFrame((float) (Math.random() * dice.getRange() + 1), true), x, y, i, i);
            time++;
            if (time > 12) {
                diceAnimationActive = false;
                time = 0;
            }

        }


    }


    @Override
    public void update(Observable observable, Object o) {
    }
}
