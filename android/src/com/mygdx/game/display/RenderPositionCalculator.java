package com.mygdx.game.display;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;

/**
 * Created by dog on 11.04.17.
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
}
