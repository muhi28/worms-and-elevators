package com.mygdx.game.display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.game.Field;
import com.mygdx.game.util.CustomLogger;

import static com.mygdx.game.GUI.DisplaySizeRatios.WORM_SIZE;


/**
 * The type Worm.
 */
public class Worm extends Actor {

    private static final CustomLogger LOGGER = new CustomLogger("WORM");

    private final Sprite player;
    private final String playerId;
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
    public boolean stillMoving() {
        return wormMovementThread.stillMoving();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Coordinates coordinatesCurrent = wormMovementThread.getCoordinatesCurrent();
        batch.draw(player, coordinatesCurrent.getX(), coordinatesCurrent.getY(), WORM_SIZE, WORM_SIZE);


    }


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