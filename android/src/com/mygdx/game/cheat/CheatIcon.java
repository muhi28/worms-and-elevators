package com.mygdx.game.cheat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * Created by Klemens on 09.06.2017.
 */

public class CheatIcon extends Actor {

    private Sprite sprite;
    private static final int TOUCH_AREA_WIDTH = 950;
    private static final int TOUCH_AREA_HEIGHT = 60;
    private static boolean visible = false;

    public CheatIcon(){
        Texture texture = new Texture(Gdx.files.internal("CheatIcon.png"));
        this.sprite = new Sprite(texture);
        sprite.setBounds(980, 30, 70, 70);
    }

    public static void changeVisibility(boolean visibility){
        visible = visibility;
    }

    public void draw(Batch batch) {

        if (visible) {
            sprite.draw(batch);

        }
    }

    public boolean touchDown (int screenX, int screenY){

        if(visible && screenX > TOUCH_AREA_WIDTH && screenY > TOUCH_AREA_HEIGHT ){
            return true;
        }

        return false;
    }


}
