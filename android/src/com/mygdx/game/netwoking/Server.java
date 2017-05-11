package com.mygdx.game.netwoking;


import android.util.Log;

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

        Log.d(ServerIntent.TAG, "onHandleIntent");
        ServerSocket listener;
        try {
            listener = new ServerSocket(PORT);

            while (true) {
                Log.d(ServerIntent.TAG, "waiting for client");
                BufferedReader in;
                synchronized (this) {
                    Socket socket = listener.accept();
                    toastNotifier.showToast(String.format("client connected from: %s", socket.getRemoteSocketAddress().toString()));
                    Log.d(ServerIntent.TAG, String.format("client connected from: %s", socket.getRemoteSocketAddress().toString()));
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintStream(socket.getOutputStream());
                }
                for (String inputLine; (inputLine = in.readLine()) != null; ) {
                    Log.d(ServerIntent.TAG, "received");
                    NetworkManager.received(inputLine);

                    Log.d(ServerIntent.TAG, inputLine);
                }
            }
        } catch (IOException e) {
            Log.d(ServerIntent.TAG, e.toString(), e);
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
