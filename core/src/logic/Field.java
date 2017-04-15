package logic;


public class Field {


    //x position im spielfeld
    private final int horicontalPosition;
    //y position im spielfeld
    private final int verticalPosition;
    private final int number;

    private Field nextField;

    public Field(int horicontalPosition, int verticalPosition, int number) {
        this.horicontalPosition = horicontalPosition;
        this.verticalPosition = verticalPosition;
        this.number = number;
    }

    public void setNextField(Field nextField) {
        this.nextField = nextField;
    }

    public Field getNextField() {
        return nextField;
    }

    public int getHoricontalPosition() {
        return horicontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public int getNumber() {
        return number;
    }
}
