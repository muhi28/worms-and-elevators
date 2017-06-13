package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * The type Main.
 */
public class Main extends BaseMain implements Observer {

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
        dice = new Dice(6, true, randomSeedDice);
        diceSprite = new Sprite(dice.getDiceTexture());
        diceSprite.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 800, 200, 200);


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

            font.draw(batch, "Spieler 2 ist an der Reihe", 210, 675);
        } else if (!playerTwo.stillMoving() && Controler.getPlayerOneTurn()) {
            font.draw(batch, "Spieler 1 ist an der Reihe", 210, 675);
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
}
