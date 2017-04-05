package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screens.PlayScreen;
import Screens.TiledTest;

public class GameClass extends Game {

	public SpriteBatch batch;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 208;


	@Override
	public void create () {

		batch = new SpriteBatch();

	//	setScreen(new Game());

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}
