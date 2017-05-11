package com.mygdx.game.game;

/**
 * The type Field.
 */
public class Field {

    /**
     * The enum Field typs.
     */
    public enum FieldTyps {

        /**
         * Normal field typs.
         */
        NORMAL,
        /**
         * Wurm field typs.
         */
        WURM,
        /**
         * Aufzug field typs.
         */
        AUFZUG
    }

    private int posX;
    private int posY;

    private Field nextField;
    private Field previousField;
    private final int fieldnumber;

    /**
     * Instantiates a new Field.
     *
     * @param py          the py
     * @param px          the px
     * @param fieldnumber the fieldnumber
     */
    public Field(int py, int px, int fieldnumber) {

        this.posX = px;
        this.posY = py;
        this.fieldnumber = fieldnumber;
    }


    /**
     * Sets next field.
     *
     * @param nextField the next field
     */
    public void setNextField(Field nextField) {

        this.nextField = nextField;
    }

    /**
     * Gets next field.
     *
     * @return the next field
     */
    public Field getNextField() {

        return nextField;
    }

    /**
     * Sets previous field.
     *
     * @param previousField the previous field
     */
    public void setPreviousField(Field previousField) {

        this.previousField = previousField;
    }

    /**
     * Gets previous.
     *
     * @return the previous
     */
    public Field getPrevious() {

        return previousField;
    }

    /**
     * Gets fieldnumber.
     *
     * @return the fieldnumber
     */
    public int getFieldnumber() {

        return fieldnumber;
    }

    /**
     * Gets pos x.
     *
     * @return the pos x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Gets pos y.
     *
     * @return the pos y
     */
    public int getPosY() {
        return posY;
    }


    /**
     * Same field boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean sameField(Field other){
        return fieldnumber == other.fieldnumber;
    }

}
