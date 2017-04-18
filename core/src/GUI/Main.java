package GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import display.RenderPositionCalculator;
import display.SingleField;
import display.Worm;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import dice.Dice;
import game.Field;
import game.GameField;
import networking.NetworkManager;

/**
 * Created by Muhi on 12.04.2017.
 */

public class Main extends ApplicationAdapter implements InputProcessor, Observer{

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Sprite texturePlayer;
    private Texture tile1, tile2;
    private Dice dice;
    private String color;

    private final GameField gameField;
    private final RenderPositionCalculator renderPositionCalculator;

    Stage stage;
    Worm playerOne;

    public Main(String wormcolor) {
        this.gameField = GameField.createGameField();
        this.renderPositionCalculator = new RenderPositionCalculator(gameField);

        this.color = wormcolor;
    }


    @Override
    public void create() {
        NetworkManager.addNetworkListener(this);
        stage = new Stage();
        camera = new OrthographicCamera();

        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        batch = new SpriteBatch();

        texturePlayer = new Sprite(new Texture(Gdx.files.internal(String.format("player_%s.png",color))));

        tile1 = new Texture(Gdx.files.internal("background_grass.png"));


        dice = new Dice(6);

        playerOne = new Worm(texturePlayer, renderPositionCalculator);


        List<Field> fields = gameField.getFields();

        for (int i = 1; i <= fields.size(); i++) {

            SingleField singleField = new SingleField(tile1, renderPositionCalculator, i);
            stage.addActor(singleField);
        }


        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {


        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.addActor(playerOne);
        camera.update();


        stage.draw();
    }

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

        if(Gdx.input.isTouched() && gameField.getPlayer().getCurrentField().getNextField() != null) {

            gameField.getPlayer().move(dice.rollTheDice());

            camera.update();
        }


        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

    @Override
    public void update(Observable observable, Object o) {
        gameField.getPlayer().move(1);//dice.rollTheDice());

        camera.update();
    }
}
