package com.mygdx.game.display;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;

/**
 * Created by dog on 11.04.17.
 */

public class RenderPositionCalculator {

    public static final int FIELD_SIZE = 108;

    private final GameField game;
    private static final int xStartOfField = -108;
    private static final int yStartOfField = 600;


    public RenderPositionCalculator(GameField game) {
        this.game = game;
    }

    public Coordinates getCoordinatesOfPlayer() {
        Field field = game.getPlayer().getCurrentField();
        return new Coordinates(xStartOfField + (field.getPosX() * FIELD_SIZE),
                yStartOfField + (field.getPosY() * FIELD_SIZE));
    }

    public Coordinates getCoordinatesOfField(int fieldNumber) {
        Field field = game.getFieldFrom(fieldNumber);
        int xPos = field.getPosX() * FIELD_SIZE + xStartOfField;
        int yPos = field.getPosY() * FIELD_SIZE + yStartOfField;

        return new Coordinates(xPos, yPos);

    }
}
