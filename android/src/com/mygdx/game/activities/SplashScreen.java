package com.mygdx.game.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mygdx.game.R;

/**
 * Created by Muhi on 04.04.2017.
 */
public class SplashScreen extends Activity {


    private Intent intent;

    private static final int SPLASH_TIME_OUT = 3000;

    /**
     * onCreate-Method is used to set the content view of the class to the main menu activity.
     * Further the thread which is within the method is used to make the splash screen sleep for 3000
     * milliseconds before it switches to the main menu screen.
     *
     * @param savedInstanceState ... Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                intent = new Intent(SplashScreen.this, MainMenu.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
