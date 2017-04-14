package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Muhi on 11.04.2017.
 */

public class MainScreen extends Game {


    private OrthographicCamera camera;

    private GameField gamefield;


    @Override
    public void create() {

       camera = new OrthographicCamera();
        camera.setToOrtho(true);
        camera.update();



    }

    @Override
    public void render() {


    }
}
