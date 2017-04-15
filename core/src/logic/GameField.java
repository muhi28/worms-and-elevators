package logic;


import java.util.ArrayList;
import java.util.List;

public class GameField {

    public static final int NUMBER_OF_HORIZONTAL_FIELDS = 10;
    public static final int NUMBER_OF_VERTICAL_FIELDS = 10;

    private final List<Field> fields;
    private final Player player;

    private GameField(List<Field> fields, Player player) {
        this.fields = fields;
        this.player = player;
    }

    public Field getFieldFrom(int verticalPosition, int horizontalPosition) {
        for (Field field : fields) {
            if (field.getHoricontalPosition() == horizontalPosition && field.getVerticalPosition() == verticalPosition) {
                return field;
            }
        }

        throw new RuntimeException("Field not found!");
    }

    public Field getFieldFrom(int number) {
        for (Field field : fields) {
            if (field.getNumber() == number) {
                return field;
            }
        }

        throw new RuntimeException("Field not found!");
    }


    public Field getStart() {
        return getFieldFrom(0, 0);
    }

    public List<Field> getFields() {
        return fields;
    }

    public Player getPlayer() {
        return player;
    }

    public Field getFieldOfPlayer() {
        return player.getCurrentField();
    }

    public static GameField createGameField() {
        List<Field> fields = new ArrayList<Field>();

        int number = NUMBER_OF_HORIZONTAL_FIELDS * NUMBER_OF_VERTICAL_FIELDS;
        Field goal = new Field(NUMBER_OF_VERTICAL_FIELDS, NUMBER_OF_HORIZONTAL_FIELDS, number--);

        fields.add(goal);

        for (int i = NUMBER_OF_VERTICAL_FIELDS; i > 0; i--) {

            for (int j = NUMBER_OF_HORIZONTAL_FIELDS; j > 0; j--) {

                if (i == NUMBER_OF_HORIZONTAL_FIELDS && j == NUMBER_OF_VERTICAL_FIELDS) {
                    continue;
                }

                Field feld = new Field(j, i, number--);

                feld.setNextField(fields.get(fields.size() - 1));
                fields.add(feld);
            }
        }

        for (Field field : fields) {

        }

        Player singlePlayer = new Player(fields.get(fields.size() - 1));


        return new GameField(fields, singlePlayer);
    }
}
