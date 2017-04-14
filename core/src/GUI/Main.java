package GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Muhi on 12.04.2017.
 */

public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Texture texture;


    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(true,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        batch = new SpriteBatch();

        texture = new Texture(Gdx.files.internal("player_blau_icon.png"));

    }

    @Override
    public void render() {


        camera.update();

        Gdx.gl.glClearColor(1,1,1,1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();

        batch.draw(texture, 100,100, 25, 25);

        batch.end();
    }
}
