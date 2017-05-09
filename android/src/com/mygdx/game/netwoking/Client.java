package com.mygdx.game.netwoking;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import core.assets.assets.networking.NetworkManager;
import core.assets.assets.networking.NetworkTrafficSender;
import core.assets.assets.networking.ToNetworkProcessor;

public class Client extends Thread {

    static final String TAG = "ClientSocket";
    public static String INIT_MESSAGE = "START_CLIENT";

    private final String ipAddress;

    private PrintStream out;
    private NetworkTrafficSender networkTrafficSender;


    public Client(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public void run() {
        final Client currentInstance = this;
        networkTrafficSender = new NetworkTrafficSender(new ToNetworkProcessor() {
            public void sendMessage(String message) {
                currentInstance.sendMessage(message);
            }
        });

        try {
            System.out.println("Starting Connection");
            Socket s = null;
            BufferedReader in;
            synchronized (this) {
                s = new Socket(ipAddress, Server.PORT);
                out = new PrintStream(s.getOutputStream());
                System.out.println("Connection DONE");
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

    public void sendMessage(String toSend) {
        synchronized (this) {
            if (out != null) {
                Log.d(Client.TAG, "Will send:" + toSend);
                out.println(toSend);
                out.flush();
            }
        }

    }
}
