package com.mygdx.game.display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * The type Worm.
 */
public class Worm extends Actor {

    private final Sprite player;
    private final RenderPositionCalculator renderPositionCalculator;

    /**
     * Instantiates a new Worm.
     *
     * @param player                   the player
     * @param renderPositionCalculator the render position calculator
     */
    public Worm(Sprite player, RenderPositionCalculator renderPositionCalculator) {

        this.player = player;
        this.renderPositionCalculator = renderPositionCalculator;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Coordinates coordinatesOfPlayer = renderPositionCalculator.getCoordinatesOfPlayer();
        batch.draw(player, coordinatesOfPlayer.getX(), coordinatesOfPlayer.getY());
    }
}