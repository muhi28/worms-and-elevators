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

    Sprite sprite;
    Texture texture;
    boolean visible = true;
    int lastDiceValue;

    public CheatIcon(){
        this.texture = new Texture(Gdx.files.internal("CheatIcon.png"));
        this.sprite = new Sprite(texture);
        sprite.setBounds(980, 30, 70, 70);
    }

    public void draw(Batch batch) {

        if (visible) {
                sprite.draw(batch);

        }
    }


}
