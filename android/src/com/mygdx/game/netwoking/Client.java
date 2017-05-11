package com.mygdx.game.netwoking;


import com.badlogic.gdx.utils.Logger;

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
     * The constant LOGGER.
     */
    public static Logger LOGGER = new Logger("Client");

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
            LOGGER.info("Starting Connection");
            Socket s = null;
            BufferedReader in;
            synchronized (this) {
                s = new Socket(ipAddress, Server.PORT);
                out = new PrintStream(s.getOutputStream());
                LOGGER.info("Connection DONE");
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            }
            while (true) {

                for (String inputLine; (inputLine = in.readLine()) != null; ) {
                    LOGGER.info("received: " + inputLine);
                    NetworkManager.received(inputLine);
                }
            }


        } catch (UnknownHostException e) {
            LOGGER.info("There was an Unknown Error: ", e);
        } catch (IOException e) {
            LOGGER.info("There was an IOException: ", e);
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
                LOGGER.info("Will send:" + toSend);
                out.println(toSend);
                out.flush();
            }
        }

    }
}
