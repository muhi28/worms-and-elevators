package com.mygdx.game.display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.game.Field;

import static com.mygdx.game.gui.DisplaySizeRatios.WORM_SIZE;


/**
 * The type Worm.
 */
public class Worm extends Actor {

    /**
     * Worm Sprite
     */
    private final Sprite player;

    /**
     * ID of matching player.
     */
    private final String playerId;

    /**
     * Used for the worm movement.
     */
    private final WormMovementThread wormMovementThread;


    /**
     * Instantiates a new Worm.
     *
     * @param player                   the player
     * @param renderPositionCalculator the render position calculator
     * @param playerId                 the player id
     */
    public Worm(Sprite player, RenderPositionCalculator renderPositionCalculator, final String playerId) {
        this.wormMovementThread = new WormMovementThread(renderPositionCalculator, playerId);
        this.player = player;
        this.playerId = playerId;
    }


    /**
     * Still moving boolean.
     *
     * @return the boolean
     */
    public synchronized boolean stillMoving() {
        return wormMovementThread.stillMoving();
    }


    /**
     * Used to draw the worm to the game field.
     *
     * @param batch       draws the worm to the stage
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Coordinates coordinatesCurrent = wormMovementThread.getCoordinatesCurrent();
        batch.draw(player, coordinatesCurrent.getX(), coordinatesCurrent.getY(), WORM_SIZE, WORM_SIZE);


    }

    /**
     * Used to move the worm to the target field.
     *
     * @param elevatorStart Start Field
     * @param elevatorEnd   Target Field
     */
    public void teleport(Field elevatorStart, Field elevatorEnd) {
        wormMovementThread.teleport(elevatorStart, elevatorEnd);
    }


    /**
     * Gets player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
    }
}