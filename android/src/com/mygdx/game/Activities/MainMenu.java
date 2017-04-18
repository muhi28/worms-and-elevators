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


    private SensorManager sm;
    private float acelVal; //Curent acceleration value and qravity
    private float acelLast; //Last acceleration value and gravity
    private float shake; //Acceleration value differ from gravity

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

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        player = new Player();
        player.setSpielername(text.getText().toString());



        //button = (Button)findViewById(R.id.instr_button);

        //onChangetoInstruction(button);
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float)Math.sqrt((double)(x*x + y*y +z*z));
            float delta = acelVal - acelLast;
            shake = shake *0.9f + delta;

            if(shake > 8){

                Toast toast = Toast.makeText(getApplicationContext(),"Schüttle mich nicht", Toast.LENGTH_LONG);
                toast.show();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

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
