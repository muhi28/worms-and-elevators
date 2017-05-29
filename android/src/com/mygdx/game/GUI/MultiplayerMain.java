package com.mygdx.game.GUI;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.display.Coordinates;
import com.mygdx.game.display.RenderPositionCalculator;
import com.mygdx.game.display.SingleField;
import com.mygdx.game.display.Worm;
import com.mygdx.game.game.Elevator;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.main_controler.Controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * The type Main.
 */
public class MultiplayerMain extends BaseMain implements Observer {


    private SpriteBatch batch;
    private Controler controler;
    private Sprite texturePlayer;
    private Sprite texturePlayerOther;
    private Long randomSeedDice;
    private Texture tilePlayer;
    private Texture tilePlayerOther;

    private String colorPlayer;
    private String colorPlayerOther;

    private final RenderPositionCalculator renderPositionCalculator;
    private final RenderPositionCalculator renderPositionCalculatorOther;


    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Player one.
     */
    Worm playerOne;

    Worm playerTwo;


    public MultiplayerMain(List<String> playerList, Long randomSeedForDice) {
        Elevator.random = new Random(randomSeedForDice);
        this.gameField = GameField.createGameField();
        this.renderPositionCalculator = new RenderPositionCalculator(gameField);
        this.renderPositionCalculatorOther = new RenderPositionCalculator(gameField);
        this.colorPlayer = playerList.get(0);
        this.colorPlayerOther = playerList.get(1);
        this.randomSeedDice = randomSeedForDice;
    }


    @Override
    public void create() {

        batch = new SpriteBatch();
        cheatCountDown = new CheatCountDown();

        stage = new Stage();


        camera = new OrthographicCamera();

        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //initialisieren der Textur der Spielfigur
        texturePlayer = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorPlayer))));
        texturePlayerOther = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorPlayerOther))));
        tilePlayer = new Texture(Gdx.files.internal("background_grass.png"));
        tilePlayerOther = new Texture(Gdx.files.internal("background_elevator.png"));


        // DICE
        dice = new Dice(6, true, randomSeedDice);
        diceSprite = new Sprite(dice.getDiceTexture());
        diceSprite.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 800, 200, 200);


        //Texture des Wurms
        playerOne = new Worm(texturePlayer, renderPositionCalculator);
        playerTwo = new Worm(texturePlayerOther, renderPositionCalculatorOther);


        List<Worm> wormList = new ArrayList<>();
        wormList.add(playerOne);
        wormList.add(playerTwo);


        // gibt jedem einzelnen Feld des Spielfelds ein Texture
        List<Field> fields = gameField.getFields();
        setFieldTextures(fields);

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        generateElevatorFieldTextures();


        //setzen des InputProcessors der GUI
        controler = new Controler(playerOne);
        controler.addObserver(this);
        Gdx.input.setInputProcessor(controler.getInputProcessor());
    }

    private void setFieldTextures(List<Field> fields) {

        for (int i = 1; i <= fields.size(); i++) {

            SingleField singleField = new SingleField(tilePlayer, renderPositionCalculator, i);
            stage.addActor(singleField);
        }

    }

    private void generateElevatorFieldTextures() {

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        int[] elevatorFields = Controler.getElevatorFields();

        for (int i = 0; i < elevatorFields.length; i++) {

            SingleField singleField = new SingleField(tilePlayerOther, renderPositionCalculator, elevatorFields[i]);
            stage.addActor(singleField);
        }

    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.addActor(playerOne);

        if (Controler.getSingleplayerBoolean()) {
            stage.addActor(playerTwo);
        }

        stage.addActor(cheatCountDown);

        camera.update();

        stage.draw();

        batch.begin();

        doDiceAnimation(batch);

        batch.end();

    }

    public void touchDown(Coordinates coordinates) {
        Log.d("TEST", "touchDown: " + coordinates);
    }


    @Override
    public void update(Observable observable, Object o) {
        touchDown((Coordinates) o);
    }
}
