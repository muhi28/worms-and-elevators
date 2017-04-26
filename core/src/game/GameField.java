package game;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */

public class GameField {


    private static final int numberofVerticals = 10;
    private static final int numberofHorizontal = 10;


    private final List<Field> fields;
    private static Field goal;


    private static int[] fieldnumbers = new int[numberofVerticals*numberofHorizontal];

    private final Player player;

    public GameField(List<Field> fields, Player player) {

        this.player = player;
        this.fields = fields;


    }

    public Field getFieldfromPos(int vertical, int horizontal) {

        for (Field field : fields) {

            if (field.getPosX() == horizontal && field.getPosY() == vertical) {

                return field;
            }
        }

        throw new RuntimeException("Field wurde nicht gefunden!");
    }

    public Field getStartField() {

        return getFieldfromPos(0, 0);
    }

    public List<Field> getFields() {

        return fields;
    }

    public Player getPlayer() {

        return player;
    }

    public Field getFieldofPlayer() {

        return player.getCurrentField();
    }

    public Field getGoal(){

        return goal;
    }

    public static GameField createGameField() {

        List<Field> fields = new ArrayList<Field>();

        int number = numberofVerticals * numberofHorizontal;

         goal = new Field(numberofVerticals, numberofHorizontal, number--);

        fields.add(goal);

        for (int i = numberofHorizontal; i > 0; i--) {

            for (int j = numberofVerticals; j > 0; j--) {

                if (i == numberofHorizontal && j == numberofVerticals) {

                    continue;
                }


                Field field = new Field(i, j, number--);

                field.setNextField(fields.get(fields.size() - 1));
                fields.add(field);
            }

        }

        getFieldNumbers(fields);

        Elevator.generateElevator();

        Player spieler = new Player(fields.get(fields.size() - 1));

        return new GameField(fields, spieler);

    }

    public Field getFieldFrom(int number) {
        for (Field field : fields) {
            if (field.getFieldnumber() == number) {
                return field;
            }
        }

        throw new RuntimeException("Field not found!");
    }


    public static int[] getFieldNumbers(List<Field> gameField) {

        for (int i = 0; i < gameField.size(); i++) {

            fieldnumbers[i] = gameField.get(i).getFieldnumber();
        }

        return fieldnumbers;
    }

    public List<Field> snakeOrder(List<Field> originalList){            //this method changes the order of our List of fields, so that the players "slither" across the board instead of the sequential movement

        return originalList;
    }
}
