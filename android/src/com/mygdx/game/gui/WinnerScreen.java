package com.mygdx.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import static com.mygdx.game.gui.DisplaySizeRatios.X_LABEL;
import static com.mygdx.game.gui.DisplaySizeRatios.Y_LABEL;


/**
 * Created by muhamed on 14.06.17.
 */

public class WinnerScreen {


    private Label winnerText;

    public WinnerScreen(String playerName, Stage stage) {

        winnerText = new Label(playerName + " gewinnt!!", getLabelStyle());
        winnerText.setWidth(450f);
        winnerText.setPosition(X_LABEL - 40, Y_LABEL);

        stage.addActor(winnerText);
    }

    private BitmapFont getFont() {

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4f);
        return font;
    }

    private LabelStyle getLabelStyle() {

        return new LabelStyle(getFont(), Color.BLACK);
    }
}
