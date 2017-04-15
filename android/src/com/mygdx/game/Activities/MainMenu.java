package com.mygdx.game.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mygdx.game.Activities.CharacterSelect;
import com.mygdx.game.Activities.Instruction;
import com.mygdx.game.Players.Player;
import com.mygdx.game.R;
import com.mygdx.game.netwoking.NetworkUtils;


/**
 * Created by Muhi on 03.04.2017.
 */

/**
 * Main Menu is the start screen of the game, in which the player has the option to choose their player
 * and start the game or read the instructions of the game itself.
 */
public class MainMenu extends Activity {


    private Intent intent;
    // private Button button;
    private EditText text;
    private Player player;
    private NetworkUtils networkUtils;
    private MediaPlayer mediaPlayer;


    private CharacterSelect characterSelect;

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

        mediaPlayer = MediaPlayer.create(MainMenu.this, R.raw.music);
        mediaPlayer.start();

        player = new Player();
        player.setSpielername(text.getText().toString());



        //button = (Button)findViewById(R.id.instr_button);

        //onChangetoInstruction(button);
    }

   /* public void onChangetoInstruction(Button button){

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                intent = new Intent(MainMenu.this,Instruction.class);
                startActivity(intent);
            }
        });

    }*/

    /**
     * This method is used to switch from the Main Menu into the Instruction Screen
     * if the instruction button is pressed.
     *
     * @param v ... View
     */
    public void onButtonClickCreateInstr(View v) {

        intent = new Intent(this, Instruction.class);
        startActivity(intent);
    }

    /**
     * This method is also used to switch from the Main Menu into the Character
     * Selection Screen if the character selection button is pressed.
     *
     * @param v ... View
     */
    public void onClickChangeToCharacterSelect(View v) {

        intent = new Intent(this, CharacterSelect.class);

        intent.putExtra("Playername", text.getText().toString());

        mediaPlayer.stop();
        startActivity(intent);
    }
    public void onButtonClickStartNetworking(View view) {
        intent = new Intent(this, Network.class);

        startActivity(intent);

    }
}
