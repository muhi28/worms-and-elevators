package com.mygdx.game.activities;


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

        List<String> playerList = new ArrayList<>();
        String colorPlayer = intent.getStringExtra(CharacterSelect.PLAYER_COLOR_KEY);
        playerList.add(colorPlayer);
        String colorPlayerOther = intent.getStringExtra(CharacterSelect.OTHER_PLAYER_COLOR_KEY);
        playerList.add(colorPlayerOther);

        initialize(new Main(playerList, intent.getLongExtra(CharacterSelect.SEED_RANDOM, 0)), cfg);
    }
}