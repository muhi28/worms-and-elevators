package com.mygdx.game.dice;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

        result = random.nextInt(this.range) + 1;         //(max - min + 1) + min
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
        return new Animation(0.15f, (Object[]) text);
    }
}