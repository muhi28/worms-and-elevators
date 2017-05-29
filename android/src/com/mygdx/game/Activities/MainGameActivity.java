package com.mygdx.game.Activities;


import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.GUI.Main;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Main game activity.
 */
public class MainGameActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

        Intent intent = getIntent();

        String color1 = "";
        String color2 = "";
        String color3 = "";
        String color4 = "";


        if (intent.hasExtra("Player_Color")) {

            color1 = intent.getStringExtra("Player_Color");
            Object[] playerOne = new Object[3];
            playerOne[0] = color1;

            color2 = intent.getStringExtra("Player_color");
            Object[] playerTwo = new Object[3];
            playerTwo[0] = color2;

            color3 = intent.getStringExtra("Player_Color");
            Object[] playerThree = new Object[3];
            playerThree[0] = color3;

            color4 = intent.getStringExtra("Player_Color");
            Object[] playerFour = new Object[3];
            playerFour[0] = color4;

            List<Object[]> playerList = new ArrayList<>();
            playerList.add(playerOne);
            playerList.add(playerTwo);
            playerList.add(playerThree);
            playerList.add(playerFour);


            initialize(new Main(playerList), cfg);
        }
    }
}