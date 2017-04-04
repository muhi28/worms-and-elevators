package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameClass;



/**
 * Created by Muhi on 03.04.2017.
 */

public class PlayScreen implements Screen{

    private TiledMap map;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera cam;
    private Viewport gameport;

    private GameClass game;



    public PlayScreen(GameClass game){

        this.game = game;
        cam = new OrthographicCamera();
        gameport = new FitViewport(GameClass.WIDTH,GameClass.HEIGHT, cam);


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Spielbrett1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);


        //Änderung der Kameraposition

        cam.position.set(gameport.getWorldWidth()/2,gameport.getWorldHeight()/2,0);
    }


    public void handleInput(float deltatime){


        if(Gdx.input.isTouched()){

            System.out.println("TOUCHED !!!");
        }
    }
    public void update(float deltatime){

        handleInput(deltatime);

        cam.update();
        renderer.setView(cam);

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //renderer.render();
    }

    @Override
    public void show() {

        //map = new TmxMapLoader().load("Maps/Spielbrett.tmx");

        //renderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void resize(int width, int height) {

        gameport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
    }
}
