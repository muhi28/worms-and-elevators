package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.button;

/**
 * Created by Muhi on 04.04.2017.
 */

public class Instruction extends Activity{

    private  Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.einleitung_activity);

    }

    public void onClickGoBack(View view){

        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


}
