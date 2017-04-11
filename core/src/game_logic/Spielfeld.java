package game_logic;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */

public class Spielfeld {


    private static final int numberofVerticals = 8;
    private static final int numberofHorizontal = 8;


    private final List<Field> fields;

    private final Player player;

    public Spielfeld(List<Field> fields, Player player){

       this.fields = fields;
        this.player = player;
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

    public  Spielfeld createGameField(){

        List<Field> fields = new ArrayList<Field>();

        Field goal = new Field(numberofVerticals, numberofHorizontal,fields.size() - 1);

        fields.add(goal);

        for (int i = 0; i < numberofVerticals; i++) {

            for (int j = 0; j < numberofHorizontal; j++) {

                if(i == 0 && j == 0){

                    continue;
                }

                Field field = new Field(i,j,fields.size()-1);

                field.setNextFeld(fields.get(fields.size() - 1));
                fields.add(field);
            }

        }

        Player spieler = new Player(fields.get(fields.size() - 1));

        return new Spielfeld(fields, spieler);

    }


}
