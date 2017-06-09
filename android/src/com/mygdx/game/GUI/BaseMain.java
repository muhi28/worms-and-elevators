package com.mygdx.game.GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.cheat.CheatIcon;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.game.GameField;

/**
 * Created by dog on 29.05.17.
 */

class BaseMain extends ApplicationAdapter {
    protected static OrthographicCamera camera;
    protected static Dice dice;
    protected static GameField gameField;
    protected static CheatCountDown cheatCountDown;
    protected static CheatIcon cheatIcon;
    protected static Sprite diceSprite;
    private static int time = 0;
    private static boolean diceAnimationActive = false;

    /**
     * Gets dice.
     *
     * @return the dice
     */
    public  static  Dice getDice() {
        return dice;
    }

    /**
     * Gets game field.
     *
     * @return the game field
     */
    public static GameField getGameField() {
        return gameField;
    }

    /**
     * Gets cheat countdown.
     *
     * @return the cheat countdown
     */
    public static CheatCountDown getCheatCountdown() {
        return cheatCountDown;
    }

    /**
     * Gets camera.
     *
     * @return the camera
     */
    public static OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Gets dice sprite.
     *
     * @return the dice sprite
     */
    public static Sprite getDiceSprite() {
        return diceSprite;
    }

    /**
     * Sets dice animation true.
     */
    public static void setDiceAnimationTrue() {
        diceAnimationActive = true;
    }

    /**
     * Do dice animation.
     *
     * @param batch the batch
     */
    public static void doDiceAnimation(SpriteBatch batch) {
        float x = Gdx.graphics.getWidth() / 2 - 100;
        float y = Gdx.graphics.getHeight() / 2 - 800;
        float i = 200;
        diceSprite.draw(batch);
        Animation a = dice.createAnimation();

        if (diceAnimationActive) {
            batch.draw((TextureRegion) a.getKeyFrame((float) (Math.random() * dice.getRange() + 1), true), x, y, i, i);
            time++;
            if (time > 12) {
                diceAnimationActive = false;
                time = 0;
            }

        }


    }
}
