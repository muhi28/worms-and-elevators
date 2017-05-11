package com.mygdx.game.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mygdx.game.R;
import com.mygdx.game.netwoking.Client;
import com.mygdx.game.netwoking.NetworkMonitor;
import com.mygdx.game.netwoking.NetworkUtils;
import com.mygdx.game.netwoking.Server;

import com.mygdx.game.netwoking.FromNetworkProcessor;
import com.mygdx.game.netwoking.GameSync;
import com.mygdx.game.netwoking.NetworkManager;
import com.mygdx.game.netwoking.NetworkTrafficReceiver;


/**
 * The type Network.
 */
public class Network extends Activity {

    private Intent intent;
    private NetworkUtils networkUtils;
    private NetworkTrafficReceiver networkTrafficReceiver;

    /**
     * The onCreate-Method is used to set the content view of the class to the main menu activity.
     *
     * @param savedInstanceState ... Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        GameSync.getSync().startMultiplayerGame();

        final Network currentInstance = this;
        networkTrafficReceiver = new NetworkTrafficReceiver(new FromNetworkProcessor() {
            public void receiveMessage(String message) {
                currentInstance.processMessageFromNetwork(message);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_activity);
        networkUtils = new NetworkUtils(this.getApplicationContext());

        final TextView textView = (TextView) findViewById(R.id.ip_address);
        textView.setText(networkUtils.wifiIpAddress());
    }


    private void disableControls() {
        final TextView textView = (TextView) findViewById(R.id.ip_address_server);
        textView.setFocusable(false);

        final Button buttonConnect = (Button) findViewById(R.id.connect);
        buttonConnect.setClickable(false);

        final Button buttonServer = (Button) findViewById(R.id.start_server);
        buttonServer.setClickable(false);
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


    /**
     * On click connect client to server.
     *
     * @param view the view
     */
    public void onClickConnectClientToServer(View view) {
        GameSync.getSync().waitForOtherPlayer();
        final TextView textView = (TextView) findViewById(R.id.ip_address_server);

        Client client = new Client(textView.getText().toString());
        client.start();

        disableControls();
        NetworkManager.sendWithDelay(Client.INIT_MESSAGE);
    }

    /**
     * On click start server.
     *
     * @param view the view
     */
    public void onClickStartServer(View view) {


        GameSync.getSync().waitForOtherPlayer();
        if (networkUtils.isPhoneConnectedToWifi()) {
            showToast(String.format("starting server %s on port: %d", networkUtils.wifiIpAddress(), Server.PORT));
            this.startService(new Intent(this, Server.class));
            disableControls();
            final TextView textView = (TextView) findViewById(R.id.network_status);
            textView.setText("Waiting for client..");


        } else {
            showToast("Please connect to wifi before starting server!");
        }
    }


    private void showToast(String toastMessage) {
        Toast.makeText(this.getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
    }

    /**
     * Process message from network.
     *
     * @param reveivedFromNetwork the reveived from network
     */
    public void processMessageFromNetwork(final String reveivedFromNetwork) {

        //Server receives init message from client
        if (reveivedFromNetwork.equals(Client.INIT_MESSAGE)) {
            NetworkMonitor.startMonitor(getApplicationContext());
            setNetworkStatus("Client connected");
            NetworkManager.send(Server.INIT_MESSAGE);
            intent = new Intent(this, CharacterSelect.class);
            startActivity(intent);
        }


        //Client receives init message from server
        if (reveivedFromNetwork.equals(Server.INIT_MESSAGE)) {
            NetworkMonitor.startMonitor(getApplicationContext());
            setNetworkStatus("Server connected");
            intent = new Intent(this, CharacterSelect.class);
            startActivity(intent);
        }

    }


    private void setNetworkStatus(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView textView = (TextView) findViewById(R.id.network_status);
                textView.setText(text);
            }
        });
    }
}
