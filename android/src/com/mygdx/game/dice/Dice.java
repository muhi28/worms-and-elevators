package com.mygdx.game.dice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

/**
 * Created by Patrick on 05.04.2017.
 * <p>
 * Class Dice has two variables rang and dice_p
 * Until now only one relevant method is implemented (rollTheDice).
 */

public class Dice {

    private int range;
    private Texture dice_p;
    private final boolean loadPictures;


    private int result;


    /*
      Constructor initializes range with delivered value and dice_p with dice_idle.png
     */
    public Dice(int range, boolean loadPictures) {
        this.range = range;
        if (loadPictures) {
            this.dice_p = new Texture(Gdx.files.internal("dice_idle.png"));
        }
        this.loadPictures = loadPictures;
    }

    public Dice(int range) {
        this(range, true);
    }

    public int getRange() {
        return range;
    }

    public int getResult() {
        return result;
    }

    public Texture getDice_p() {
        return dice_p;
    }

    /**
     * The method rollTheDice generates a random number and sets the texture of the dice.
     *
     * @return : the random value
     */
    public int rollTheDice() {
        result = (int) (Math.random() * this.range + 1);

        /*
        sets dice_p on the rolled texture
         */
        if (loadPictures) {
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
        }
        return result;
    }

    /**
     * The method cheatDice sets the dice_p on the texture the player clicked.
     *
     * @return : the int value of the dice number
     */


    public int cheatDice(Texture dice_p) {
        int result = 0;

        /*
            The paths from the dice texture are saved, in the String Array pathOfDices..
         */

        String[] pathOfDices = {
                ((FileTextureData) (new Texture(Gdx.files.internal("dice_one.png"))).getTextureData()).getFileHandle().path(),
                ((FileTextureData) (new Texture(Gdx.files.internal("dice_two.png"))).getTextureData()).getFileHandle().path(),
                ((FileTextureData) (new Texture(Gdx.files.internal("dice_three.png"))).getTextureData()).getFileHandle().path(),
                ((FileTextureData) (new Texture(Gdx.files.internal("dice_four.png"))).getTextureData()).getFileHandle().path(),
                ((FileTextureData) (new Texture(Gdx.files.internal("dice_five.png"))).getTextureData()).getFileHandle().path(),
                ((FileTextureData) (new Texture(Gdx.files.internal("dice_six.png"))).getTextureData()).getFileHandle().path()
        };

        //Here I get the path from the given texture
        String pathOfGivenTexture = ((FileTextureData) dice_p.getTextureData()).getFileHandle().path();


        for (int i = 0; i < pathOfDices.length; i++) {
            if (pathOfDices[i].equals(pathOfGivenTexture)) {
                i++;
                result = i;
            }
        }

        /*
        sets dice_p depending on the given texture
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

    public Animation createAnimation() {
        TextureRegion tex1 = new TextureRegion(new Texture(Gdx.files.internal("dice_one.png")));
        TextureRegion tex2 = new TextureRegion(new Texture(Gdx.files.internal("dice_two.png")));
        TextureRegion tex3 = new TextureRegion(new Texture(Gdx.files.internal("dice_three.png")));
        TextureRegion tex4 = new TextureRegion(new Texture(Gdx.files.internal("dice_four.png")));
        TextureRegion tex5 = new TextureRegion(new Texture(Gdx.files.internal("dice_five.png")));
        TextureRegion tex6 = new TextureRegion(new Texture(Gdx.files.internal("dice_six.png")));

        Animation diceAnimation = new Animation(0.15f, tex1, tex2, tex3, tex4, tex5, tex6);
        return diceAnimation;
    }


}