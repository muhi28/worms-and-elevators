package com.mygdx.game.netwoking;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The type Client.
 */
public class Client extends Thread {

    /**
     * The Tag.
     */
    static final String TAG = "ClientSocket";
    /**
     * The constant INIT_MESSAGE.
     */
    public static final String INIT_MESSAGE = "START_CLIENT";

    private final String ipAddress;

    private PrintStream out;
    private NetworkTrafficSender networkTrafficSender;


    /**
     * Instantiates a new Client.
     *
     * @param ipAddress the ip address
     */
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
            Log.d(TAG,"There was an Unknown Error: ",e);
        } catch (IOException e) {
            Log.d(TAG,"There was an IOException: ",e);
        }

    }

    /**
     * Send message.
     *
     * @param toSend the to send
     */
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
