package Fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Patrick on 05.04.2017.
 *
 * Class Dice has two variables rang and dice_p
 * Until now only one relevant method is implemented (rollTheDice).
 */

public class Dice {

    private int range;
    private Texture dice_p;


    /*
      Constructor initializes range with delivered value and dice_p with dice_idle.png
     */
    public Dice(int range)
    {
        this.range = range;
        this.dice_p = new Texture(Gdx.files.internal("dice_idle.png"));
    }

    public int getRange() {
        return range;
    }

    public Texture getDice_p() {
        return dice_p;
    }
    /**
     * The method rollTheDice generates a random number and sets the texture of the dice.
     * @return : the random value
     */
    public int rollTheDice()
    {
        int result = (int)(Math.random()*this.range+1);

        /*
        sets dice_p on the rolled texture
         */
        switch (result) {
            case 1:
                this.dice_p = (new Texture(Gdx.files.internal("dice_one.png")));
                break;
            case 2:
                this.dice_p = (new Texture(Gdx.files.internal("dice_two.png")));
                break;
            case 3:
                this.dice_p = (new Texture(Gdx.files.internal("dice_three.png")));
                break;
            case 4:
                this.dice_p = (new Texture(Gdx.files.internal("dice_four.png")));
                break;
            case 5:
                this.dice_p = (new Texture(Gdx.files.internal("dice_five.png")));
                break;
            case 6:
                this.dice_p = (new Texture(Gdx.files.internal("dice_six.png")));
                break;
            default:
                System.out.println("Falscher Value uebergeben!");

        }
        return result;
    }
}
