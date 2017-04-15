package logic;

/**
 * Created by dog on 11.04.17.
 */

public class Player {
    private Field currentField;

    public Player(Field currentField) {
        this.currentField = currentField;
    }

    public void move(int steps) {
        Field fieldAfterMove = currentField;
        for (int i = 0; i < steps; i++) {
            fieldAfterMove = fieldAfterMove.getNextField();
        }

        this.currentField = fieldAfterMove;
    }

    public Field getCurrentField() {
        return currentField;
    }
}
