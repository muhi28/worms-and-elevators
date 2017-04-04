package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import Screens.PlayScreen;

/**
 * Created by Muhi on 04.04.2017.
 */

public class CharacterSelect extends Activity {

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_select_activity);


    }

    public void onClickStartGame(View view){

        intent = new Intent(this, PlayScreen.class);
        startActivity(intent);
    }

    public void onClickGoBackToMenu(View view){

        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
