package com.mygdx.game.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.mygdx.game.R;

/**
 * Created by Muhi on 04.04.2017.
 */

/**
 * This Class is used to give players the option to choose a specific color for their players.
 */
public class CharacterSelect extends Activity {

    private Intent intent;

    private TextView chosenPlayer, playername;

    private String color;

    /**
     * The onCreate-Method is used to set the content view of the class to the main menu activity.
     *
     * @param savedInstanceState ... Bundle
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.character_select_activity);

        //Character auswahl
        chosenPlayer = (TextView)findViewById(R.id.chosen_player_textview);

        //Spielername

        playername = (TextView) findViewById(R.id.spielername_textview);

        Intent intent = getIntent();

        if(intent.hasExtra("Playername")){

            String name =  intent.getStringExtra("Playername");

            playername.setText(String.format(" %s",name));
            playername.setVisibility(View.VISIBLE);
        }

       // ((TextView)findViewById(R.id.spielername_textview)).setText(player.getSpielername());


    }

    /**
     * This method starts the game after the player has chosen a character
     * and the start game button is pressed.
     *
     * @param view ... View
     *
     */
    public void onClickStartGame(View view){

        intent = new Intent(this, MainGameActivity.class);

        intent.putExtra("Player_Color",color);

        startActivity(intent);
    }

    /**
     * onClickGoBackToMenu switches from the character selection screen back
     * to the main menu screen if the button is pressed.
     *
     * @param view
     */
    public void onClickGoBackToMenu(View view){

        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void redButtonclicked(View view){

        chosenPlayer.setText("Es wurde die rote Spielfigur gewählt.");
        chosenPlayer.setVisibility(View.VISIBLE);

        color = "red";

    }

    public void blueButtonclicked(View view){

        chosenPlayer.setText("Es wurde die blaue Spielfigur gewählt.");
        chosenPlayer.setVisibility(View.VISIBLE);

        color = "blau";
    }

    public void greenButtonclicked(View view){

        chosenPlayer.setText("Es wurde die grüne Spielfigur gewählt.");
        chosenPlayer.setVisibility(View.VISIBLE);

        color = "green";
    }

    public void yellowButtonclicked(View view){

        chosenPlayer.setText("Es wurde die gelbe Spielfigur gewählt.");
        chosenPlayer.setVisibility(View.VISIBLE);

        color = "yellow";
    }

}
