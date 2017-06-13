package com.mygdx.game.display;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.GUI.DisplaySizeRatios.FIELD_SIZE;



/**
 * The type Single field.
 */
public class SingleField extends Actor {

    private final Texture field;
    private final RenderPositionCalculator renderPositionCalculator;
    private final int fieldNumber;

    /**
     * Instantiates a new Single field.
     *
     * @param field                    the field
     * @param renderPositionCalculator the render position calculator
     * @param fieldNumber              the field number
     */
    public SingleField(Texture field, RenderPositionCalculator renderPositionCalculator, int fieldNumber) {
        this.field = field;
        this.renderPositionCalculator = renderPositionCalculator;
        this.fieldNumber = fieldNumber;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Coordinates coordinates = renderPositionCalculator.getCoordinatesOfField(fieldNumber);
        batch.draw(field, coordinates.getX(), coordinates.getY(), FIELD_SIZE, FIELD_SIZE);
    }
}