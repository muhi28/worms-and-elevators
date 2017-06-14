package com.mygdx.game.dice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.mygdx.game.util.SoundHandler;

import java.util.Map;
import java.util.Random;

/**
 * Created by Patrick on 05.04.2017.
 * <p>
 * Class Dice has two variables rang and dice_p
 * Until now only one relevant method is implemented (rollTheDice).
 */
public class Dice {

    private int range;
    private final Random random;


    private Integer result;

    /*
          Constructor initializes range with delivered value and dice_p with dice_idle.png
         */
    public Dice(int range, long randomSeed) {
        this.range = range;
        this.random = new Random(randomSeed);
    }


    /**
     * Instantiates a new Dice.
     *
     * @param range the range
     */
    public Dice(int range) {
        this(range,  new Random().nextLong());
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public Integer getResult() {
        return result;
    }


    /**
     * The method rollTheDice generates a random number and sets the texture of the dice.
     *
     * @return : the random value
     */
    public int rollTheDice() {

        SoundHandler.getMusicManager().shuffle();

        result = random.nextInt(this.range - 1 + 1) + 1;         //(max - min + 1) + min
        return result;
    }


    /**
     * The method cheatDice sets the diceTextureIdle on the texture the player clicked.
     *
     * @param dice_p the dice p
     * @return : the int value of the dice number
     */
    public int cheatDice(Texture dice_p) {
        int result = 0;

        /*
            The paths from the dice texture are saved, in the String Array pathOfDices..
         */


        //Here I get the path from the given texture
        String pathOfGivenTexture = ((FileTextureData) dice_p.getTextureData()).getFileHandle().path();


        for (int i = 1; i <= this.getRange(); i++) {
            String vgl = ((FileTextureData) (new Texture(Gdx.files.internal("dice_" + i + ".png"))).getTextureData()).getFileHandle().path();

            if (vgl.equals(pathOfGivenTexture)) {
                result = i;
            }
        }

        /*
        sets diceTextureIdle depending on the given texture
         */
        /*
        switch (result) {
            case 1:
                this.diceTextureIdle = (new Texture(Gdx.files.internal("dice_one.png")));
                break;
            case 2:
                this.diceTextureIdle = (new Texture(Gdx.files.internal("dice_two.png")));
                break;
            case 3:
                this.diceTextureIdle = (new Texture(Gdx.files.internal("dice_three.png")));
                break;
            case 4:
                this.diceTextureIdle = (new Texture(Gdx.files.internal("dice_four.png")));
                break;
            case 5:
                this.diceTextureIdle = (new Texture(Gdx.files.internal("dice_five.png")));
                break;
            case 6:
                this.diceTextureIdle = (new Texture(Gdx.files.internal("dice_six.png")));
                break;
            default:
                System.out.println("Falscher Value uebergeben!");

        }*/
        return result;

    }

    /**
     * Create animation animation.
     *
     * @return the animation
     */
    public Animation createAnimation(Map<Integer, Texture> textureMap) {
        TextureRegion[] text = new TextureRegion[this.getRange()];

        for (int i = 0; i <= this.getRange() - 1; i++) {
            text[i] = new TextureRegion(textureMap.get(i+1));
        }

        //Cast to Object because of warning.
        Animation diceAnimation = new Animation(0.15f, (Object[]) text);
        return diceAnimation;
    }
}