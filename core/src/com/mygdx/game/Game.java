package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.display.RenderPositionCalculator;
import com.mygdx.game.display.Worm;

import logic.GameField;


/**
 * Created by Muhi on 05.04.2017.
 */

public class Game extends ApplicationAdapter {


    private final GameField gameField;
    private final RenderPositionCalculator renderPositionCalculator;

    public Game() {
        this.gameField = GameField.createGameField();
        this.renderPositionCalculator = new RenderPositionCalculator(gameField);
    }

    Texture image;
    TiledMap tiledMap;
    OrthographicCamera camera;

    TiledMapRenderer tiledMapRenderer;

    Stage stage;

    Texture player;//= new Texture(Gdx.files.internal("player_blau_icon.png"));


    Worm playerOne;
    Worm playerTwo;

    @Override
    public void create() {
        player = new Texture(Gdx.files.internal("player_blau_icon.png"));
        playerOne = new Worm(player, renderPositionCalculator);
        playerTwo = new Worm(player, renderPositionCalculator);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();

        stage = new Stage();


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

        stage.addActor(playerOne);


        camera.setToOrtho(true, w + 230, h + 250);
        camera.update();

        tiledMap = new TmxMapLoader().load("Spielbrett1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // stage.addActor(playerOne);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        stage.draw();

    }


}
