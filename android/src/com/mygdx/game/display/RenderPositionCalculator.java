package com.mygdx.game.display;

import android.util.Log;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;

import java.util.ArrayList;
import java.util.List;

public class RenderPositionCalculator {

    public static final int FIELD_SIZE = 108;

    private final GameField game;
    private static final int xStartOfField = -108;
    private static final int yStartOfField = 600;


    public RenderPositionCalculator(GameField game) {
        this.game = game;
    }

    public Coordinates getCoordinatesOfPlayer(String playerId) {
        Field field = game.getPlayer(playerId).getCurrentField();
        return new Coordinates(xStartOfField + (field.getPosX() * FIELD_SIZE),
                yStartOfField + (field.getPosY() * FIELD_SIZE));
    }

    public Field getPlayerField(String playerId) {
        return game.getPlayer(playerId).getCurrentField();
    }

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

        return coordinates;
    }

    public Coordinates getCoordinatesOfField(int fieldNumber) {
        Field field = game.getFieldFrom(fieldNumber);
        int xPos = field.getPosX() * FIELD_SIZE + xStartOfField;
        int yPos = field.getPosY() * FIELD_SIZE + yStartOfField;

        return new Coordinates(xPos, yPos);

    }


    public Coordinates getCoordinatesOfField(Field field) {
        return getCoordinatesOfField(field.getFieldnumber());
    }
}
