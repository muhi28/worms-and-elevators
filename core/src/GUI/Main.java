package GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FillViewport;

import cheat.CheatCountDown;
import display.RenderPositionCalculator;
import display.SingleField;
import display.Worm;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import dice.Dice;
import game.Elevator;
import game.Field;
import game.GameField;
import main_controler.Controler;

/**
 * Created by Muhi on 12.04.2017.
 */

public class Main extends ApplicationAdapter implements Observer {

    private static OrthographicCamera camera;

    private SpriteBatch batch;

    private Sprite texturePlayer;
    private Texture tile1, tile2;
    private static Dice dice;
    private String color;
    private static GameField gameField;
    private final RenderPositionCalculator renderPositionCalculator;
    private static CheatCountDown cheatCountDown;
    Stage stage;
    Worm playerOne;


    private static Sprite diceSprite;
    MyActor diceActor;


    private class MyActor extends Actor {

        public MyActor() {

            diceSprite = new Sprite(dice.getDice_p());
            diceSprite.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 800, 200, 200);

        }

        @Override
        public void draw(Batch batch, float parentAlpha) {

            diceSprite.draw(batch);
        }


    }

    public Main(String wormcolor) {
        this.gameField = GameField.createGameField();
        this.renderPositionCalculator = new RenderPositionCalculator(gameField);

        this.color = wormcolor;
    }


    @Override
    public void create() {

        batch = new SpriteBatch();
        cheatCountDown = new CheatCountDown();

        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));


        camera = new OrthographicCamera();

        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // batch = new SpriteBatch();


        // initialisieren der Textur des Spielers
        texturePlayer = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png", color))));


        tile1 = new Texture(Gdx.files.internal("background_grass.png"));
        tile2 = new Texture(Gdx.files.internal("background_elevator.png"));


        // DICE
        dice = new Dice(6);
        diceActor = new MyActor();
        diceActor.setTouchable(Touchable.enabled);


        // Textur des Wurms
        playerOne = new Worm(texturePlayer, renderPositionCalculator);


        // gibt jedem einzelnen Feld des Spielfelds ein Texture
        List<Field> fields = gameField.getFields();

        for (int i = 1; i <= fields.size(); i++) {

            SingleField singleField = new SingleField(tile1, renderPositionCalculator, i);
            stage.addActor(singleField);
        }

        // generiert die Aufzuege und plaziert sie auf dem Spielfeld
        int[] elevatorFields = Elevator.getElevatorFields();

        for (int i = 0; i < elevatorFields.length; i++) {

            SingleField singleField = new SingleField(tile2, renderPositionCalculator, elevatorFields[i]);
            stage.addActor(singleField);
        }


        // hiermit wird das Touchhandling ermöglicht

        Controler Controler = new Controler();
        Gdx.input.setInputProcessor(Controler.getInputProcessor());
    }

    @Override
    public void render() {


        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.addActor(playerOne);

        stage.addActor(cheatCountDown);
        //stage.addActor(diceActor);

        camera.update();


        batch.begin();

        diceSprite.draw(batch);

        batch.end();

        stage.draw();
    }

    public static Dice getDice() {
        return dice;
    }

    public static GameField getGameField(){
        return gameField;
    }

    public static CheatCountDown getCheatCountdown(){
        return cheatCountDown;
    }

    public static OrthographicCamera getCamera(){
        return camera;
    }

    public static Sprite getDiceSprite(){
        return diceSprite;
    }



/*
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return Controler.touchDown(screenX, screenY, pointer, button);


        Gdx.app.log("Main.touchDown", "X=" + screenX + "Y=" + screenY);

        if (cheatCountDown.touchDown(screenX, screenY)) {

            return true;
        }

        if (Gdx.input.isTouched()) {

            if (gameField.getPlayer().getCurrentField().getNextField() != null) {

                gameField.getPlayer().move(dice.rollTheDice());
                diceSprite.setTexture(dice.getDice_p());

                camera.update();

                if (gameField.getPlayer().getCurrentField().equals(gameField.getGoal())) {

                    System.out.println("YOU ARE A WINNER !!!");
                }
            }

        }


        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return Controler.touchUp(screenX, screenY, pointer, button);



        if (cheatCountDown.cheatingIsActive()) {

            Integer integer = cheatCountDown.stopCountDown();

            gameField.getPlayer().move(integer);

            camera.update();

            return true;
        }
        return false;


    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
*/
    @Override
    public void update(Observable observable, Object o) {
        //  gameField.getPlayer().move(1);//dice.rollTheDice());

        //  camera.update();
    }
}
