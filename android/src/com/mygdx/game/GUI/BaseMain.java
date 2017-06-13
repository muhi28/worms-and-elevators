package com.mygdx.game.GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.cheat.CheatIcon;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.game.GameField;

import static com.mygdx.game.GUI.DisplaySizeRatios.DICE_SIZE;
import static com.mygdx.game.GUI.DisplaySizeRatios.X_DICE;
import static com.mygdx.game.GUI.DisplaySizeRatios.Y_DICE;


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
    public static Dice getDice() {
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

    public static CheatIcon getCheatIcon() {
        return cheatIcon;
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

        diceSprite.draw(batch);
        Animation a = dice.createAnimation();

        if (diceAnimationActive) {
            batch.draw((TextureRegion) a.getKeyFrame((float) (Math.random() * dice.getRange() + 1), true), X_DICE, Y_DICE, DICE_SIZE, DICE_SIZE);
            time++;
            if (time > 12) {
                diceAnimationActive = false;
                time = 0;
            }

        }


    }
}
