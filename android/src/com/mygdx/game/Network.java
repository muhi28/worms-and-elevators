package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mygdx.game.netwoking.NetworkUtils;
import com.mygdx.game.netwoking.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Muhi on 04.04.2017.
 */

/**
 *
 */
public class Network extends Activity{

    private  Intent intent;
    private NetworkUtils networkUtils;

    /**
     * The onCreate-Method is used to set the content view of the class to the main menu activity.
     *
     * @param savedInstanceState ... Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_activity);
        networkUtils = new NetworkUtils(this.getApplicationContext());

        final TextView textView = (TextView)findViewById(R.id.ip_address);
        textView.setText(networkUtils.wifiIpAddress());
    }

    /**
     * This method is used to switch the current screen back to the main menu screen if the back button is pressed.
     *
     * @param view ... View
     */
    public void onClickGoBack(View view){

        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


    public void onClickConnect(View view) {
       final TextView textView = (TextView)findViewById(R.id.ip_address_server);
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    System.out.println("Starting Connection");
                    String text = textView.getText().toString();
                    Socket s = new Socket(text, Server.PORT);
                    System.out.println("Connection DONE");
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF("This is a message frome the client!");
                    dos.flush();
                    dos.close();
                    s.close();
                    System.out.println("Closing socket");
                } catch (UnknownHostException e){
                    System.out.println("There was an Unknown Erorr:");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("There was an IOException:");
                    e.printStackTrace();
                }
            }
        };
        t.start();
        Toast.makeText(this, "Messagge Sent...", Toast.LENGTH_SHORT).show();
    }

    public void onClickStartServer(View view) {
        if(networkUtils.isPhoneConnectedToWifi()){
            showToast(String.format("starting server %s on port: %d", networkUtils.wifiIpAddress(), Server.PORT));
            this.startService(new Intent(this, Server.class));
        }
        else {
            showToast("Please connect to wifi before starting server!");
        }
    }


    private void showToast(String toastMessage) {
        Toast.makeText(this.getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
    }
}
