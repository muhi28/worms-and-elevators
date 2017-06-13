package com.mygdx.game.display;

import android.util.Log;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.GUI.DisplaySizeRatios.FIELD_SIZE;
import static com.mygdx.game.GUI.DisplaySizeRatios.X_START_FIELD;
import static com.mygdx.game.GUI.DisplaySizeRatios.Y_START_FIELD;

public class RenderPositionCalculator {


    private final GameField game;


    public RenderPositionCalculator(GameField game) {
        this.game = game;
    }

    public Coordinates getCoordinatesOfPlayer(String playerId) {

        Field field = game.getPlayer(playerId).getCurrentField();
        return new Coordinates(X_START_FIELD + (field.getPosX() * FIELD_SIZE),
                Y_START_FIELD + (field.getPosY() * FIELD_SIZE));
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
            if (coordinatesOfField.equals(getCoordinatesOfField(to))) {
                break;
            }
        }

        return coordinates;
    }

    public Coordinates getCoordinatesOfField(int fieldNumber) {
        Field field = game.getFieldFrom(fieldNumber);
        int xPos = field.getPosX() * FIELD_SIZE + X_START_FIELD;
        int yPos = field.getPosY() * FIELD_SIZE + Y_START_FIELD;

        return new Coordinates(xPos, yPos);

    }


    public Coordinates getCoordinatesOfField(Field field) {
        return getCoordinatesOfField(field.getFieldnumber());
    }
}
