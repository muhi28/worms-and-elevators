package GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.display.RenderPositionCalculator;
import com.mygdx.game.display.SingleField;
import com.mygdx.game.display.Worm;

import java.util.List;

import logic.Field;
import logic.GameField;
import logic.Player;

/**
 * Created by Muhi on 12.04.2017.
 */

public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Texture texturePlayer;
    private Texture textureGrass;

    private final GameField gameField;
    private final RenderPositionCalculator renderPositionCalculator;

    Stage stage;
    Worm playerOne;

    public Main() {
        this.gameField = GameField.createGameField();
        this.renderPositionCalculator = new RenderPositionCalculator(gameField);
    }


    @Override
    public void create() {
        stage = new Stage();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        batch = new SpriteBatch();

        texturePlayer = new Texture(Gdx.files.internal("player_blau_icon.png"));
        textureGrass = new Texture(Gdx.files.internal("background_grass.png"));

        List<Field> fields = gameField.getFields();
        for (int i = 1; i <= fields.size(); i++) {

            SingleField singleField = new SingleField(textureGrass, renderPositionCalculator, i);
            stage.addActor(singleField);
        }


        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameField.getPlayer().move(1);
                    camera.update();

                }

            }
        };
        t.start();

    }

    @Override
    public void render() {


        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        playerOne = new Worm(texturePlayer, renderPositionCalculator);

        stage.addActor(playerOne);

        camera.update();


        stage.draw();
    }
}
