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
    private Sprite texturePlayer1;
    private Sprite texturePlayer2;
    private Sprite texturePlayer3;
    private Sprite texturePlayer4;
    private Texture tile1;
    private Texture tile2;
    private String color1;
    private String color2;
    private String color3;
    private String color4;
    private final RenderPositionCalculator renderPositionCalculator1;
    private final RenderPositionCalculator renderPositionCalculator2;
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
        this.renderPositionCalculator1 = new RenderPositionCalculator(gameField);
        this.renderPositionCalculator2 = new RenderPositionCalculator(gameField);
        this.color1 = (String) playerList.get(0)[0];
        this.color2 = (String) playerList.get(1)[0];
        this.color3 = (String) playerList.get(2)[0];
        this.color4 = (String) playerList.get(3)[0];
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
        texturePlayer1 = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", color1))));
        if (Controler.getSingleplayerBoolean()) {
            if (color1.equals("red")){
                texturePlayer2 = new Sprite(new Texture(Gdx.files.internal("player_blue.png")));
            }
            else {
                texturePlayer2 = new Sprite(new Texture(Gdx.files.internal("player_red.png")));
            }

        }
        else{
            texturePlayer2 = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", color2))));
        }
//        texturePlayer3 = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", color3))));
//        texturePlayer4 = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", color4))));

        tile1 = new Texture(Gdx.files.internal("background_grass.png"));
        tile2 = new Texture(Gdx.files.internal("background_elevator.png"));


        // DICE
        dice = new Dice(6);
        diceSprite = new Sprite(dice.getDiceTexture());
        diceSprite.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 800, 200, 200);


        //Texture des Wurms
        playerOne = new Worm(texturePlayer1, renderPositionCalculator1);
        playerTwo = new Worm(texturePlayer2, renderPositionCalculator2);
//        playerThree = new Worm(texturePlayer3, renderPositionCalculator1);
//        playerFour = new Worm(texturePlayer4, renderPositionCalculator1);

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


            SingleField singleField = new SingleField(tile1, renderPositionCalculator1, i);
            stage.addActor(singleField);
        }

    }

    private void generateElevatorFieldTextures() {

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        int[] elevatorFields = Controler.getElevatorFields();

        for (int i = 0; i < elevatorFields.length; i++) {

            SingleField singleField = new SingleField(tile2, renderPositionCalculator1, elevatorFields[i]);
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
