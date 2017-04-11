package game_logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Muhi on 11.04.2017.
 */

public class MainScreen extends Game {


    private OrthographicCamera camera;

    @Override
    public void create() {

       camera = new OrthographicCamera();
        camera.setToOrtho(true);
        camera.update();



    }

    private TextureRegion playerTextureRegion, GameFieldTextureRegion;


    public void createResources(){


    }
    @Override
    public void render() {


    }
}
