package com.mygdx.game.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mygdx.game.R;

/**
 * Created by Muhi on 04.04.2017.
 */

/**
 * The type Instruction.
 */
public class Instruction extends Activity {

    private Intent intent;

    /**
     * The onCreate-Method is used to set the content view of the class to the main menu activity.
     *
     * @param savedInstanceState ... Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.einleitung_activity);

    }

    /**
     * This method is used to switch the current screen back to the main menu screen if the back button is pressed.
     *
     * @param view ... View
     */
    public void onClickGoBack(View view) {

        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


}
