package com.mygdx.game.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mygdx.game.Players.Player;
import com.mygdx.game.R;
import com.mygdx.game.netwoking.NetworkUtils;


/**
 *Created by Muhi on 03.04.2017.
 */

/**
 * Main Menu is the start screen of the game, in which the player has the option to choose their player
 * and start the game or read the instructions of the game itself.
 */
public class MainMenu extends Activity {


    private Intent intent;
    private EditText text;
    private Player player;
    private NetworkUtils networkUtils;
    private MediaPlayer mediaPlayer;
    private Boolean startMusic = true;

    /**
     * onCreate-Method is used to set the content view of the class to the main menu activity.
     *
     * @param savedInstanceState ... Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);
        networkUtils = new NetworkUtils(this.getApplicationContext());
        text = (EditText) findViewById(R.id.inputname_edittxt);

        Intent in = getIntent();
        mediaPlayer = MediaPlayer.create(MainMenu.this, R.raw.music);

        startMusic = in.getBooleanExtra("music", true);


        if (startMusic) {
            mediaPlayer.start();
        } else {


            mediaPlayer.stop();

        }

        player = new Player(text.getText().toString());
    }

    /**
     * This method is used to switch from the Main Menu into the Instruction Screen
     * if the instruction button is pressed.
     *
     * @param v ... View
     */
    public void onButtonClickCreateInstr(View v) {

        intent = new Intent(this, Instruction.class);
        mediaPlayer.stop();
        startActivity(intent);
    }

    /**
     * This method is also used to switch from the Main Menu into the Character
     * Selection Screen if the character selection button is pressed.
     *
     * @param v ... View
     */
    public void onClickChangeToCharacterSelect(View v) {

        if ("".equals(text.getText().toString())) {

            text.setError("Bitte geben Sie einen Namen ein");

            return;
        }
        intent = new Intent(this, CharacterSelect.class);
        intent.putExtra("Playername", text.getText().toString());

        mediaPlayer.stop();
        startActivity(intent);
    }

    /**
     * On button click start networking.
     *
     * @param view the view
     */
    public void onButtonClickStartNetworking(View view) {

        if ("".equals(text.getText().toString())) {

            text.setError("Bitte geben Sie einen Namen ein");

            return;
        }


        intent = new Intent(this, Network.class);
        mediaPlayer.stop();

        startActivity(intent);

    }

    /**
     * On click option button.
     *
     * @param v the v
     */
    public void onClickOptionButton(View v) {
        intent = new Intent(this, OptionActivity.class);
        mediaPlayer.stop();

        startActivity(intent);

    }
}
