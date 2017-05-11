package com.mygdx.game.Activities;


import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.mygdx.game.GUI.Main;


/**
 * The type Main game activity.
 */
public class MainGameActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

        Intent intent = getIntent();

        String color = "";

        if (intent.hasExtra("Player_Color")) {

            color = intent.getStringExtra("Player_Color");
        }

        initialize(new Main(color), cfg);
    }
}