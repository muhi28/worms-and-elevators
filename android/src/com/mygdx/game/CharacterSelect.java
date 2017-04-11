package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mygdx.game.Players.Player;

import Screens.PlayScreen;

/**
 * Created by Muhi on 04.04.2017.
 */

/**
 * This Class is used to give players the option to choose a specific color for their players.
 */
public class CharacterSelect extends Activity {

    private Intent intent;

    private TextView textView;

    private Player player;


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

        textView = (TextView)findViewById(R.id.chosen_player_textview);

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

        intent = new Intent(this, PlayScreen.class);
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

        textView.setText("Es wurde die rote Spielfigur gewählt.");
        textView.setVisibility(View.VISIBLE);

    }

    public void blueButtonclicked(View view){

        textView.setText("Es wurde die blaue Spielfigur gewählt.");
        textView.setVisibility(View.VISIBLE);

        intent = new Intent(this,GameClass.class);
        startActivity(intent);


    }

    public void greenButtonclicked(View view){

        textView.setText("Es wurde die grüne Spielfigur gewählt.");
        textView.setVisibility(View.VISIBLE);

    }

    public void yellowButtonclicked(View view){

        textView.setText("Es wurde die gelbe Spielfigur gewählt.");
        textView.setVisibility(View.VISIBLE);

    }

}
