package com.mygdx.game.display;

import com.mygdx.game.game.Field;
import com.mygdx.game.util.CustomLogger;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.GUI.DisplaySizeRatios.WORM_MOVEMENT;


/**
 * The type Worm.
 */
public final class WormMovementThread implements Runnable {

    private static final CustomLogger LOGGER = new CustomLogger("WORM");


    private final RenderPositionCalculator renderPositionCalculator;
    private final String playerId;
    private Coordinates coordinatesCurrent;
    private Field fieldCurrent;
    private List<Coordinates> coordinates = new ArrayList<>();

    private Coordinates targetCoordinates = null;
    private final Thread thread;


    /**
     * Instantiates a new Worm movement thread.
     *
     * @param renderPositionCalculator the render position calculator
     * @param playerId                 the player id
     */
    public WormMovementThread(RenderPositionCalculator renderPositionCalculator, final String playerId) {

        this.playerId = playerId;
        this.renderPositionCalculator = renderPositionCalculator;
        this.coordinatesCurrent = renderPositionCalculator.getCoordinatesOfPlayer(playerId);
        this.fieldCurrent = renderPositionCalculator.getPlayerField(playerId);
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Gets coordinates current.
     *
     * @return the coordinates current
     */
    public synchronized Coordinates getCoordinatesCurrent() {
        return coordinatesCurrent;
    }

    private synchronized void setCoordinatesCurrent(Coordinates current) {
        this.coordinatesCurrent = current;
    }


    /**
     * Still moving boolean.
     *
     * @return the boolean
     */
    public boolean stillMoving() {
        return !fieldCurrent.sameField(renderPositionCalculator.getPlayerField(playerId));
    }


    /**
     * Teleport.
     *
     * @param field the field
     */
    public void teleport(Field field) {
        targetCoordinates = null;
        setCoordinatesCurrent(renderPositionCalculator.getCoordinatesOfField(field));
        coordinates.clear();
    }


    private static int narrowCoordinates(int sourceCoordinate, int targetCoordinate) {
        if (targetCoordinate > sourceCoordinate) {
            return sourceCoordinate + WORM_MOVEMENT;
        } else if (targetCoordinate == sourceCoordinate) {
            return sourceCoordinate;
        } else {
            return sourceCoordinate - WORM_MOVEMENT;
        }
    }


    @Override
    public void run() {
        do {

            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Field targetPlayerField = renderPositionCalculator.getPlayerField(playerId);


            Coordinates coordinatesCurrent = getCoordinatesCurrent();
            if (renderPositionCalculator.getCoordinatesOfField(targetPlayerField).equals(coordinatesCurrent)
                    && !fieldCurrent.sameField(targetPlayerField)) {
                LOGGER.debug("Ziel erreicht");
                fieldCurrent = targetPlayerField;
            }


            if (coordinates.isEmpty() && targetCoordinates == null) {


                if (fieldCurrent.sameField(targetPlayerField)) {

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

                setCoordinatesCurrent(new Coordinates(x, y));

//            LOGGER.debug( "move to: " + targetCoordinates);
//            LOGGER.debug("current to: " + coordinatesCurrent);
                if (targetCoordinates.equals(coordinatesCurrent)) {
                    LOGGER.debug("set tartet to null");
                    targetCoordinates = null;
                }

            }
        } while (true);
    }
}