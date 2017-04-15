package game_logic;

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


    private final int feldnummer;

    public Field(int px, int py, int feldnummer) {


        this.posX = px;
        this.posY = py;
        this.feldnummer = feldnummer;
    }

    public void setNextFeld(Field nextField) {

        this.nextField = nextField;
    }

    public Field getNextFeld() {

        return nextField;
    }

    public int getFeldnummer() {

        return feldnummer;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}
