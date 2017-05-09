package core.assets.assets.game;


/**
 * Created by Muhi on 11.04.2017.
 */


public class Player {

    private Field currentField;


    public Player(Field currentField) {

        this.currentField = currentField;
    }

    public void move() {
        currentField = currentField.getNextField();
    }

    /*    public void move(int steps){


            //Field field = currentField;

            for (int i = 0; i < steps; i++) {

                currentField = currentField.getNextField();

            }

            //this.currentField = field;

        }
    */
    public Field getCurrentField() {

        return currentField;
    }

    public void setCurrentField(Field field) {
        currentField = field;
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
