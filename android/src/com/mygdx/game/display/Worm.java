package com.mygdx.game.display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.game.Field;
import com.mygdx.game.util.CustomLogger;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Worm.
 */
public class Worm extends Actor {

    private final static CustomLogger LOGGER = new CustomLogger("WORM");

    private final Sprite player;
    private final RenderPositionCalculator renderPositionCalculator;
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
    public Worm(Sprite player, RenderPositionCalculator renderPositionCalculator) {
        this.player = player;
        this.renderPositionCalculator = renderPositionCalculator;
        this.coordinatesCurrent = renderPositionCalculator.getCoordinatesOfPlayer();
        this.fieldCurrent = renderPositionCalculator.getPlayerField();
    }

    /**
     * Still moving boolean.
     *
     * @return the boolean
     */
    public boolean stillMoving() {
        return !fieldCurrent.sameField(renderPositionCalculator.getPlayerField());
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Field targetPlayerField = renderPositionCalculator.getPlayerField();


        if (renderPositionCalculator.getCoordinatesOfField(targetPlayerField).equals(coordinatesCurrent)
                && !fieldCurrent.sameField(targetPlayerField)) {
            LOGGER.debug("Ziel erreicht");
            fieldCurrent = targetPlayerField;
        }


        if (coordinates.isEmpty() && targetCoordinates == null) {


            if (fieldCurrent.sameField(targetPlayerField)) {
                batch.draw(player, coordinatesCurrent.getX(), coordinatesCurrent.getY());
            } else {
                coordinates = renderPositionCalculator.getCoordinatesBetween(fieldCurrent, targetPlayerField);
                LOGGER.debug("getCoordinatesBetween");
            }
        } else {

            if (targetCoordinates == null) {

                targetCoordinates = coordinates.remove(0);
                LOGGER.debug("new field");
            }
            int x = narrowCoordinates(coordinatesCurrent.getX(), targetCoordinates.getX());
            int y = narrowCoordinates(coordinatesCurrent.getY(), targetCoordinates.getY());

            coordinatesCurrent = new Coordinates(x, y);
            batch.draw(player, coordinatesCurrent.getX(), coordinatesCurrent.getY());
            LOGGER.debug( "move to: " + targetCoordinates);
            LOGGER.debug("current to: " + coordinatesCurrent);
            if (targetCoordinates.equals(coordinatesCurrent)) {
                LOGGER.debug("set tartet to null");
                targetCoordinates = null;
            }

        }

    }


    private static int narrowCoordinates(int sourceCoordinate, int targetCoordinate) {
        if (targetCoordinate > sourceCoordinate) {
            return sourceCoordinate + 4;
        } else if (targetCoordinate == sourceCoordinate) {
            return sourceCoordinate;
        } else {
            return sourceCoordinate - 4;
        }
    }

}