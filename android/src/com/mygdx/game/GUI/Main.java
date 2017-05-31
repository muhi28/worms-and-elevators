package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.display.RenderPositionCalculator;
import com.mygdx.game.display.SingleField;
import com.mygdx.game.display.Worm;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.main_controler.Controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The type Main.
 */
public class Main extends BaseMain implements Observer {

    private SpriteBatch batch;
    private Controler controler;
    private Sprite texturePlayerOne;
    private Sprite texturePlayerTwo;
    private Sprite texturePlayerThree;
    private Sprite texturePlayerFour;
    private Texture tileOne;
    private Texture tileTwo;
    private String colorOne;
    private String colorTwo;
    private String colorThree;
    private String colorFour;
    private final RenderPositionCalculator renderPositionCalculatorOne;
    private final RenderPositionCalculator renderPositionCalculatorTwo;
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Player one.
     */
    Worm playerOne;

    Worm playerTwo;
    Worm playerThree;
    Worm playerFour;


    /**
     * Instantiates a new Main.
     *
     * @param playerList the wormcolor
     */
    public Main(List<Object[]> playerList) {
        gameField = GameField.createGameField();
        this.renderPositionCalculatorOne = new RenderPositionCalculator(gameField);
        this.renderPositionCalculatorTwo = new RenderPositionCalculator(gameField);
        this.colorOne = (String) playerList.get(0)[0];
        this.colorTwo = (String) playerList.get(1)[0];
        this.colorThree = (String) playerList.get(2)[0];
        this.colorFour = (String) playerList.get(3)[0];
    }


    @Override
    public void create() {

        batch = new SpriteBatch();
        cheatCountDown = new CheatCountDown();

        stage = new Stage();


        camera = new OrthographicCamera();

        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        int numberOfPlayers = Controler.getNumberOfPlayers();

        Controler.setSingleplayerBoolean(true);     //THIS IS ONLY TEMPORARY AND NEEDS TO BE REPLACED SOON!

        //initialisieren der Textur der Spielfigur
        texturePlayerOne = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorOne))));
        if (Controler.getSingleplayerBoolean()) {
            if (colorOne.equals("red")){
                texturePlayerTwo = new Sprite(new Texture(Gdx.files.internal("player_blue.png")));
            }
            else {
                texturePlayerTwo = new Sprite(new Texture(Gdx.files.internal("player_red.png")));
            }

        }
        else{
            texturePlayerTwo = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorTwo))));
        }
//        texturePlayerThree = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorThree))));
//        texturePlayerFour = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", colorFour))));

        tileOne = new Texture(Gdx.files.internal("background_grass.png"));
        tileTwo = new Texture(Gdx.files.internal("background_elevator.png"));


        // DICE
        dice = new Dice(6);
        diceSprite = new Sprite(dice.getDiceTexture());
        diceSprite.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 800, 200, 200);


        //Texture des Wurms
        playerOne = new Worm(texturePlayerOne, renderPositionCalculatorOne);
        playerTwo = new Worm(texturePlayerTwo, renderPositionCalculatorTwo);
//        playerThree = new Worm(texturePlayerThree, renderPositionCalculatorOne);
//        playerFour = new Worm(texturePlayerFour, renderPositionCalculatorOne);

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
        Gdx.input.setInputProcessor(controler.getInputProcessor());
    }

    private void setFieldTextures(List<Field> fields) {

        for (int i = 1; i <= fields.size(); i++) {


            SingleField singleField = new SingleField(tileOne, renderPositionCalculatorOne, i);
            stage.addActor(singleField);
        }

    }

    private void generateElevatorFieldTextures() {

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        int[] elevatorFields = Controler.getElevatorFields();

        for (int i = 0; i < elevatorFields.length; i++) {

            SingleField singleField = new SingleField(tileTwo, renderPositionCalculatorOne, elevatorFields[i]);
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


    @Override
    public void update(Observable observable, Object o) {
    }
}
