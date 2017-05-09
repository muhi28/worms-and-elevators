package core.assets.assets.game;

/**
 * Created by Muhi on 11.04.2017.
 */

public class Field {

    public enum FieldTyps {

        NORMAL,
        WURM,
        AUFZUG
    }

    private int posX, posY;

    private Field nextField;
    private Field previousField;

    private FieldTyps fieldType;


    private final int fieldnumber;

    public Field(int py, int px, int fieldnumber) {


        this.posX = px;
        this.posY = py;
        // this.fieldType = fieldTyps;

        this.fieldnumber = fieldnumber;
    }


    public void setNextField(Field nextField) {

        this.nextField = nextField;
    }

    public Field getNextField() {

        return nextField;
    }

    public void setPreviousField(Field previousField) {

        this.previousField = previousField;
    }

    public Field getPrevious() {

        return previousField;
    }

    public int getFieldnumber() {

        return fieldnumber;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public FieldTyps getFieldtype() {

        return fieldType;
    }

}
