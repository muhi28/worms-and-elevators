package com.mygdx.game.display;

import android.util.Log;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Render position calculator.
 */
public class RenderPositionCalculator {

    /**
     * The constant FIELD_SIZE.
     */
    public static final int FIELD_SIZE = 108;

    private final GameField game;
    private static final int xStartOfField = -108;
    private static final int yStartOfField = 600;


    /**
     * Instantiates a new Render position calculator.
     *
     * @param game the game
     */
    public RenderPositionCalculator(GameField game) {
        this.game = game;
    }

    /**
     * Gets coordinates of player.
     *
     * @return the coordinates of player
     */
    public Coordinates getCoordinatesOfPlayer() {
        Field field = game.getPlayer().getCurrentField();
        return new Coordinates(xStartOfField + (field.getPosX() * FIELD_SIZE),
                yStartOfField + (field.getPosY() * FIELD_SIZE));
    }

    /**
     * Gets player field.
     *
     * @return the player field
     */
    public Field getPlayerField() {
        return game.getPlayer().getCurrentField();
    }

    /**
     * Gets coordinates between.
     *
     * @param from the from
     * @param to   the to
     * @return the coordinates between
     */
    public List<Coordinates> getCoordinatesBetween(Field from, Field to) {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        Field nextField = from.getNextField();

        int fields = to.getFieldnumber() - nextField.getFieldnumber();
        Log.d("RenderPosition", "fields: " + fields);
        while (true) {
            Coordinates coordinatesOfField = getCoordinatesOfField(nextField);
            coordinates.add(coordinatesOfField);
            nextField = nextField.getNextField();
            if (coordinatesOfField.equals(getCoordinatesOfField(to))){
                break;
            }
        }
        //coordinates.add(getCoordinatesOfField(nextField.getFieldnumber()));
        return coordinates;
    }

    /**
     * Gets coordinates of field.
     *
     * @param fieldNumber the field number
     * @return the coordinates of field
     */
    public Coordinates getCoordinatesOfField(int fieldNumber) {
        Field field = game.getFieldFrom(fieldNumber);
        int xPos = field.getPosX() * FIELD_SIZE + xStartOfField;
        int yPos = field.getPosY() * FIELD_SIZE + yStartOfField;

        return new Coordinates(xPos, yPos);

    }


    /**
     * Gets coordinates of field.
     *
     * @param field the field
     * @return the coordinates of field
     */
    public Coordinates getCoordinatesOfField(Field field) {
        return getCoordinatesOfField(field.getFieldnumber());
    }
}
