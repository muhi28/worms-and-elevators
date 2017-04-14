package game;

/**
 * Created by Muhi on 11.04.2017.
 */

public class Field {

    public enum FieldTyps {

        NORMAL,
        WURM,
        AUFZUG
    }

    private float posX, posY;

    private Field nextField;
    private Field previousField;

    private FieldTyps fieldType;

    private static  final int WIDTH = 64;
    private static final int HEIGHT = 64;


    private final int feldnummer;

    public Field(float px, float py, int feldnummer){


        this.posX = px;
        this.posY = py;
       // this.fieldType = fieldTyps;

        this.feldnummer = feldnummer;
    }


    public void setNextField(Field nextField){

        this.nextField = nextField;
    }

    public Field getNextField(){

        return nextField;
    }

    public void setPreviousField(Field previousField){

        this.previousField = previousField;
    }
    public Field getPrevious(){

        return previousField;
    }
    public int getFeldnummer(){

        return feldnummer;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public FieldTyps getFieldtype() {

        return fieldType;
    }

}
