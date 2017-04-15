package com.mygdx.game.display;

import logic.Field;
import logic.GameField;

/**
 * Created by dog on 11.04.17.
 */

public class RenderPositionCalculator {
    public final static int FIELD_SIZE = 32;
    private final GameField game;
    private final int xStartOfField = 0;
    private final int yStartOfField = 0;


    public RenderPositionCalculator(GameField game) {
        this.game = game;
    }

    public Coordinates getCoordinatesOfPlayer() {
        Field field = game.getPlayer().getCurrentField();
        return new Coordinates(xStartOfField + (field.getHoricontalPosition() * FIELD_SIZE),
                yStartOfField + (field.getVerticalPosition() * FIELD_SIZE));
    }

    public Coordinates getCoordinatesOfField(int fieldNumber) {
        Field field = game.getFieldFrom(fieldNumber);
        int xPos = field.getHoricontalPosition() * FIELD_SIZE + xStartOfField;
        int yPos = field.getVerticalPosition() * FIELD_SIZE + yStartOfField;

        return new Coordinates(xPos, yPos);

    }
}
