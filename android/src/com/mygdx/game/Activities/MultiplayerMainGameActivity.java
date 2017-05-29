package com.mygdx.game.Activities;


import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.GUI.MultiplayerMain;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Multiplayer main game activity.
 */
public class MultiplayerMainGameActivity extends AndroidApplication {

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

        initialize(new MultiplayerMain(playerList, intent.getLongExtra(CharacterSelect.SEED_RANDOM, 0)), cfg);

    }
}