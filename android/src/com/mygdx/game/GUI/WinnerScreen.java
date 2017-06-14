package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * Created by muhamed on 14.06.17.
 */

public class WinnerScreen {


    private Label winnerText;

    public WinnerScreen(String playerName, Stage stage) {

        winnerText = new Label(playerName + " hat das Spiel gewonnen !!", getLabelStyle());
        winnerText.setWidth(500f);
        winnerText.setPosition(Gdx.graphics.getWidth() / 2 - winnerText.getWidth(), Gdx.graphics.getHeight() / 2);

        stage.addActor(winnerText);
    }

    private BitmapFont getFont() {

        BitmapFont font = new BitmapFont();
        font.getData().setScale(5f);
        return font;
    }

    private LabelStyle getLabelStyle() {

        return new LabelStyle(getFont(), Color.BLACK);
    }
}
