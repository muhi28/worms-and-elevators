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

    /**
     * Logger.
     */
    private static final CustomLogger LOGGER = new CustomLogger("WORM");

    /**
     * Used to calculate the position of the worm.
     */
    private final RenderPositionCalculator renderPositionCalculator;

    /**
     * ID from the player.
     */
    private final String playerId;

    /**
     * Current coordinates of player field.
     */
    private Coordinates coordinatesCurrent;

    /**
     * Current player field.
     */
    private Field fieldCurrent;

    /**
     * List which is used to hold the coordinates between two fields.
     */
    private List<Coordinates> coordinates = new ArrayList<>();

    /**
     *
     */
    private Coordinates targetCoordinates = null;
    private Field teleportFieldEnd;
    private final Thread thread;
    private Field teleportFieldStart;

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

    /**
     * Used to set the current coordinates.
     *
     * @param current current Coordinates
     */
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
     * Used to teleport the player from one elevator field to another.
     *
     * @param elevatorStart Start Field
     * @param elevatorEnd   Goal Field
     */
    public void teleport(Field elevatorStart, Field elevatorEnd) {
        this.teleportFieldStart = elevatorStart;
        this.teleportFieldEnd = elevatorEnd;

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
                LOGGER.error("Interrupted!!", e);
                Thread.currentThread().interrupt();
            }
            Field targetPlayerField = renderPositionCalculator.getPlayerField(playerId);

            if (teleportFieldEnd != null) {
                targetPlayerField = teleportFieldStart;
            }

            Coordinates currentCoordinates = getCoordinatesCurrent();
            checkTargetField(currentCoordinates, targetPlayerField);


            if (coordinates.isEmpty() && targetCoordinates == null) {
                reinitializeCoordinates(targetPlayerField);

            } else {

                initializeNarrowCoordinates(currentCoordinates);
            }
        } while (true);
    }

    private void checkTargetField(Coordinates currentCoordinates, Field targetPlayerField) {

        if (renderPositionCalculator.getCoordinatesOfField(targetPlayerField).equals(currentCoordinates)
                && !fieldCurrent.sameField(targetPlayerField)) {
            LOGGER.debug("Ziel erreicht");
            fieldCurrent = targetPlayerField;

            clearFieldsAndCoordinates();
        }
    }

    private void reinitializeCoordinates(Field targetPlayerField) {

        if (fieldCurrent.sameField(targetPlayerField)) {
            clearFieldsAndCoordinates();
        } else {
            coordinates = renderPositionCalculator.getCoordinatesBetween(fieldCurrent, targetPlayerField);
            LOGGER.debug("getCoordinatesBetween");
        }
    }

    private void initializeNarrowCoordinates(Coordinates currentCoordinates) {
        if (targetCoordinates == null) {
            targetCoordinates = coordinates.remove(0);
        }
        int x = narrowCoordinates(currentCoordinates.getX(), targetCoordinates.getX());
        int y = narrowCoordinates(currentCoordinates.getY(), targetCoordinates.getY());

        setCoordinatesCurrent(new Coordinates(x, y));

        if (targetCoordinates.equals(currentCoordinates)) {
            LOGGER.debug("set target to null");
            targetCoordinates = null;
        }
    }

    private void clearFieldsAndCoordinates() {
        if (teleportFieldEnd != null) {
            setCoordinatesCurrent(renderPositionCalculator.getCoordinatesOfField(teleportFieldEnd));
            teleportFieldEnd = null;
            teleportFieldStart = null;
            targetCoordinates = null;
            coordinates.clear();
        }
    }
}