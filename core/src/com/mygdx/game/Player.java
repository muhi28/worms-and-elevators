package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Muhi on 05.04.2017.
 */

public class Player implements ApplicationListener{

    public class MyActor extends Actor{

        Texture player = new Texture(Gdx.files.internal("assets/player_blau_icon.png"));

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(player,0,0);
        }
    }


    private Stage stage;

    @Override
    public void create() {

        stage = new Stage();

        MyActor myActor = new MyActor();

        stage.addActor(myActor);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
