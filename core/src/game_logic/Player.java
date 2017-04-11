package game_logic;



/**
 * Created by Muhi on 11.04.2017.
 */


public class Player {

    private Field currentField;


    public Player(Field currentField){

        this.currentField = currentField;
    }

    private void move(int steps){


        Field field = currentField;

        for (int i = 0; i < steps; i++) {

            field = field.getNextFeld();
        }

        this.currentField = field;

    }

    public Field getCurrentField(){

        return currentField;
    }

    /*public void update(){

        // Drücke A um das Würfeln durchzuführen

        if(Gdx.input.isKeyPressed(Input.Keys.A)){

            position.x = dice.rollTheDice();
        }
        sprite.setPosition(position.x,position.y);
    }

    public void render(SpriteBatch spriteBatch){

        sprite.draw(spriteBatch);
    }

     **/

}