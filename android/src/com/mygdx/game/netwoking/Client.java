package com.mygdx.game.netwoking;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import networking.GameSync;
import networking.NetworkManager;

public class Client extends Thread implements Observer {

    static final String TAG = "ClientSocket";
    public static String INIT_MESSAGE = "START_CLIENT";

    private final String ipAddress;

    private PrintStream out;


    public Client(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting Connection");
            Socket s = null;
            BufferedReader in;
            synchronized (this) {
                s = new Socket(ipAddress, Server.PORT);
                out = new PrintStream(s.getOutputStream());


                System.out.println("Connection DONE");
                GameSync.useMultiplayerGame();
                NetworkManager.addNetworkSender(this);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            }
            while (true) {

                for (String inputLine; (inputLine = in.readLine()) != null; ) {
                    Log.d(Client.TAG, "received");
                    NetworkManager.received(inputLine);
                    Log.d(Client.TAG, inputLine);
                }
            }


        } catch (UnknownHostException e) {
            System.out.println("There was an Unknown Erorr:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an IOException:");
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (out != null) {
                Log.d(Client.TAG, "Will send:" + String.valueOf(arg));
                out.println(String.valueOf(arg));
                out.flush();
            }
        }

    }
}
