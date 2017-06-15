package com.mygdx.game.display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.game.Field;
import com.mygdx.game.util.CustomLogger;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.GUI.DisplaySizeRatios.WORM_MOVEMENT;
import static com.mygdx.game.GUI.DisplaySizeRatios.WORM_SIZE;


/**
 * The type Worm.
 */
public class Worm extends Actor {

    private static final CustomLogger LOGGER = new CustomLogger("WORM");

    private final Sprite player;
    private final RenderPositionCalculator renderPositionCalculator;
    private final String playerId;
    private Coordinates coordinatesCurrent;
    private Field fieldCurrent;
    private List<Coordinates> coordinates = new ArrayList<>();

    private Coordinates targetCoordinates = null;


    /**
     * Instantiates a new Worm.
     *
     * @param player                   the player
     * @param renderPositionCalculator the render position calculator
     */
    public Worm(Sprite player, RenderPositionCalculator renderPositionCalculator, String playerId) {
        this.player = player;
        this.playerId = playerId;
        this.renderPositionCalculator = renderPositionCalculator;
        this.coordinatesCurrent = renderPositionCalculator.getCoordinatesOfPlayer(playerId);
        this.fieldCurrent = renderPositionCalculator.getPlayerField(playerId);
    }

    /**
     * Still moving boolean.
     *
     * @return the boolean
     */
    public boolean stillMoving() {
        return !fieldCurrent.sameField(renderPositionCalculator.getPlayerField(playerId));
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Field targetPlayerField = renderPositionCalculator.getPlayerField(playerId);


        if (renderPositionCalculator.getCoordinatesOfField(targetPlayerField).equals(coordinatesCurrent)
                && !fieldCurrent.sameField(targetPlayerField)) {
            LOGGER.debug("Ziel erreicht");
            fieldCurrent = targetPlayerField;
        }


        if (coordinates.isEmpty() && targetCoordinates == null) {


            if (fieldCurrent.sameField(targetPlayerField)) {
                batch.draw(player, coordinatesCurrent.getX(), coordinatesCurrent.getY(), WORM_SIZE, WORM_SIZE);
            } else {
                coordinates = renderPositionCalculator.getCoordinatesBetween(fieldCurrent, targetPlayerField);
                LOGGER.debug("getCoordinatesBetween");
            }
        } else {

            if (targetCoordinates == null) {

                targetCoordinates = coordinates.remove(0);
             //   LOGGER.debug("new field");
            }
            int x = narrowCoordinates(coordinatesCurrent.getX(), targetCoordinates.getX());
            int y = narrowCoordinates(coordinatesCurrent.getY(), targetCoordinates.getY());

            coordinatesCurrent = new Coordinates(x, y);
            batch.draw(player, coordinatesCurrent.getX(), coordinatesCurrent.getY(), WORM_SIZE, WORM_SIZE);
//            LOGGER.debug( "move to: " + targetCoordinates);
//            LOGGER.debug("current to: " + coordinatesCurrent);
            if (targetCoordinates.equals(coordinatesCurrent)) {
                LOGGER.debug("set tartet to null");
                targetCoordinates = null;
            }

        }

    }

    public void teleport(Field field){
        targetCoordinates = null;
        coordinatesCurrent = renderPositionCalculator.getCoordinatesOfField(field);
        coordinates.clear();
    }

    private static int narrowCoordinates(int sourceCoordinate, int targetCoordinate) {
        int newCoordinates = narrowCoordinatesPrimitive(sourceCoordinate, targetCoordinate);

        if(newCoordinates >= targetCoordinate )
        {
            return targetCoordinate;
        }

        return newCoordinates;
    }



    private static int narrowCoordinatesPrimitive(int sourceCoordinate, int targetCoordinate) {
        if (targetCoordinate > sourceCoordinate) {
            return sourceCoordinate + WORM_MOVEMENT;
        } else if (targetCoordinate == sourceCoordinate) {
            return sourceCoordinate;
        } else {
            return sourceCoordinate - WORM_MOVEMENT;
        }
    }

    public String getPlayerId() {
        return playerId;
    }
}