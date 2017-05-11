package com.mygdx.game.cheat;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The type Cheat count down.
 */
public class CheatCountDown extends Actor {
    private static final int CHEAT_TOUCH_AREA_SIZE = 100;
    private BitmapFont font;
    private boolean visible = false;
    private Integer currentDiceValue;
    private CheatCountThread countThread;

    /**
     * Instantiates a new Cheat count down.
     */
    public CheatCountDown() {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(2f);
        font.setColor(Color.BLACK);
        countThread = new CheatCountThread(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (visible) {
            synchronized (this) {
                font.draw(batch, currentDiceValue.toString(), 50, 160);
            }

        }
    }

    /**
     * Increase current dice value.
     */
    public synchronized void increaseCurrentDiceValue() {
        currentDiceValue++;
        if (currentDiceValue > 6) {
            currentDiceValue = 1;
        }
    }

    private void startCount() {
        visible = true;
        currentDiceValue = 1;
        countThread.start();
    }

    private void stopCount() {
        visible = false;
        countThread.interrupt();

    }

    /**
     * Touch down boolean.
     *
     * @param screenX the screen x
     * @param screenY the screen y
     * @return the boolean
     */
    public boolean touchDown(int screenX, int screenY) {
        if (!visible && screenX < CHEAT_TOUCH_AREA_SIZE
                && screenY > Gdx.graphics.getHeight() - CHEAT_TOUCH_AREA_SIZE) {
            startCount();
            return true;
        }

        return false;
    }

    /**
     * Cheating is active boolean.
     *
     * @return the boolean
     */
    public boolean cheatingIsActive() {
        return visible;
    }

    /**
     * Stop count down integer.
     *
     * @return the integer
     */
    public Integer stopCountDown() {
        stopCount();
        countThread = new CheatCountThread(this);
        return currentDiceValue;

    }

}
