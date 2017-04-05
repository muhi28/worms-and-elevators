package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * Created by Muhi on 05.04.2017.
 */

public class Game extends ApplicationAdapter {


    Texture image;
    TiledMap tiledMap;
    OrthographicCamera camera;

    TiledMapRenderer tiledMapRenderer;

    Stage stage;

    private float actorX = 0, actorY = 0;



    public class MyActor extends Actor {

        Texture player = new Texture(Gdx.files.internal("player_blau_icon.png"));


        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(player,40,40);
        }
    }

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();

        stage = new Stage();

        MyActor myActor = new MyActor();

        stage.addActor(myActor);


        camera.setToOrtho(true,w + 230,h + 250);
        camera.update();

        tiledMap = new TmxMapLoader().load("Spielbrett1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

       stage.draw();


    }




}
