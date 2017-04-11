package logik;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TouchableAction;

import org.w3c.dom.Text;

import Fields.Dice;

/**
 * Created by Muhi on 11.04.2017.
 */

public class Player extends Sprite implements InputProcessor{

    private static final String PATH = "player_blau_icon.png";
    private static final int WIDTH = (int) (Feld.WIDTH * 0.9);
    private static final int HEIGHT = (int) (Feld.HEIGHT * 0.9);


    private Vector2 position, dimensions;
    private Sprite sprite;
    private Dice dice;

    private Feld feld;

    public Player(TextureRegion textureRegion, Feld field){

        //position = new Vector2(field.getX(),field.getY());

        //dimensions = new Vector2(width,height);

        //sprite = new Sprite(new Texture(Gdx.files.internal("player_blau_icon.png")));
        //sprite.setSize(dimensions.x,dimensions.y);

        super(textureRegion,(int)field.getX(),(int)field.getY(),WIDTH,HEIGHT);
        dice = new Dice(6);

        this.feld = field;
    }

    private void move(){

        Feld nextField = this.feld.getNextFeld();
        this.setPosition(nextField.getX(),nextField.getY());
        this.feld = feld.getNextFeld();

    }



    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        move();

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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

    public static TextureRegion createTextureRegion(){

        TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("player_blau_icon.png")),WIDTH,HEIGHT);

        return textureRegion;
    }

}
