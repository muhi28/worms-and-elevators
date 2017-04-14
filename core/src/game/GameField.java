package game;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */

public class GameField {


    private static final int numberofVerticals = 8;
    private static final int numberofHorizontal = 8;


    private final List<Field> fields;

    private static int [] fieldnumbers = new int[64];

    private final Player player;

    public GameField(List<Field> fields, Player player){

        createGameField();

        this.player = player;
        this.fields = fields;


    }

   public Field getFieldfromPos(int vertical, int horizontal){

       for(Field field : fields){

           if(field.getPosX() == horizontal && field.getPosY() == vertical){

               return field;
           }
       }

       throw new RuntimeException("Field wurde nicht gefunden!");
   }

    public Field getStartField(){

        return getFieldfromPos(0,0);
    }

    public List<Field> getFields(){

        return fields;
    }

    public Player getPlayer(){

        return player;
    }

    public Field getFieldofPlayer(){

        return player.getCurrentField();
    }

    public static GameField createGameField(){

        List<Field> fields = new ArrayList<Field>();

        Field goal = new Field(numberofVerticals, numberofHorizontal,fields.size() - 1);

        fields.add(goal);

        for (int i = 0; i < numberofVerticals; i++) {

            for (int j = 0; j < numberofHorizontal; j++) {

                if(i == 0 && j == 0){

                    continue;
                }


                Field field = new Field(i,j,fields.size());

                field.setNextField(fields.get(fields.size()));
                fields.add(field);
            }

        }

        getFieldNumbers(fields);

        Elevator.generateElevator();

        Player spieler = new Player(fields.get(fields.size() - 1));

        return new GameField(fields, spieler);

    }



    public static int[] getFieldNumbers(List<Field> gameField){

        for (int i = 0; i < gameField.size(); i++){

            fieldnumbers[i] = gameField.get(i).getFeldnummer();
        }

        return fieldnumbers;
    }
}
