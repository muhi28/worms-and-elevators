package com.mygdx.game.display;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Worm extends Actor {

    private final Texture player;
    private final RenderPositionCalculator renderPositionCalculator;

    public Worm(Texture player, RenderPositionCalculator renderPositionCalculator) {
        this.player = player;
        this.renderPositionCalculator = renderPositionCalculator;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Coordinates coordinatesOfPlayer = renderPositionCalculator.getCoordinatesOfPlayer();
        batch.draw(player, coordinatesOfPlayer.getX(), coordinatesOfPlayer.getY());
    }
}