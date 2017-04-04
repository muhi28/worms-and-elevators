package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Muhi on 03.04.2017.
 */

public class MainMenu extends Activity {


    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_activity);

    }

    public void onButtonClickInstr(View v){

        intent = new Intent(this, Instruction.class);
        startActivity(intent);
    }

}
