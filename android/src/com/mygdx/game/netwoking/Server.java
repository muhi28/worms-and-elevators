package com.mygdx.game.netwoking;


import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.util.ToastNotifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type Server.
 */
public class Server {
    /**
     * The constant LOGGER.
     */
    public static Logger LOGGER = new Logger("SERVER");
    /**
     * The constant PORT.
     */
    public static final int PORT = 12345;

    private PrintStream out;
    private final ToastNotifier toastNotifier;

    private NetworkTrafficSender networkTrafficSender;


    /**
     * The constant INIT_MESSAGE.
     */
    public static final String INIT_MESSAGE = "START_SERVER";

    /**
     * Instantiates a new Server.
     *
     * @param toastNotifier the toast notifier
     */
    public Server(ToastNotifier toastNotifier) {
        this.toastNotifier = toastNotifier;
    }


    /**
     * Start.
     */
    public void start() {
        final Server currentInstance = this;
        networkTrafficSender = new NetworkTrafficSender(new ToNetworkProcessor() {
            public void sendMessage(String message) {
                currentInstance.sendMessage(message);
            }
        });

        LOGGER.info("onHandleIntent");
        ServerSocket listener;
        try {
            listener = new ServerSocket(PORT);

            while (true) {
                LOGGER.debug("waiting for client");
                BufferedReader in;
                synchronized (this) {
                    Socket socket = listener.accept();
                    toastNotifier.showToast(String.format("client connected from: %s", socket.getRemoteSocketAddress().toString()));
                    LOGGER.debug(String.format("client connected from: %s", socket.getRemoteSocketAddress().toString()));
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintStream(socket.getOutputStream());
                }
                for (String inputLine; (inputLine = in.readLine()) != null; ) {
                    LOGGER.info("received: " + inputLine);
                    NetworkManager.received(inputLine);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
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
                out.println(toSend);
                out.flush();
            }
        }
    }
}
