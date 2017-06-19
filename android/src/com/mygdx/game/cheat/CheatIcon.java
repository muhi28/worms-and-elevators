package com.mygdx.game.cheat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.CustomLogger;

import static com.mygdx.game.GUI.DisplaySizeRatios.CHEAT_ICON_SIZE;
import static com.mygdx.game.GUI.DisplaySizeRatios.X_CHEAT_ICON;
import static com.mygdx.game.GUI.DisplaySizeRatios.Y_CHEAT_ICON;


/**
 *Created by Klemens on 09.06.2017.
 */

public class CheatIcon extends Actor {

    private static final CustomLogger LOGGER = new CustomLogger("Cheat Icon:");

    private Sprite sprite;
    private static boolean visible = false;

    public CheatIcon(){
        Texture texture = new Texture(Gdx.files.internal("CheatIcon.png"));
        this.sprite = new Sprite(texture);
        sprite.setBounds(X_CHEAT_ICON, Y_CHEAT_ICON, CHEAT_ICON_SIZE, CHEAT_ICON_SIZE);
    }

    public static void setVisibility(boolean visibility){
        visible = visibility;
        if(visibility){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(4000);
                        CheatIcon.setVisibility(false);
                    } catch (InterruptedException e) {

                        LOGGER.error("Interrupted!!", e);
                        Thread.currentThread().interrupt();
                    }
                }
            });

            t.start();
        }
        }


    public void draw(Batch batch) {

        if (visible) {
            sprite.draw(batch);

        }
    }

    public boolean touchDown (int screenX, int screenY){
        return visible &&screenX > X_CHEAT_ICON && screenY > Gdx.graphics.getHeight() - CHEAT_ICON_SIZE ;

    }


}
