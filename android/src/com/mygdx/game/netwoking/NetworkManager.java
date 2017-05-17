package com.mygdx.game.netwoking;

import java.util.Observable;


/**
 * The type Network manager.
 */
public class NetworkManager extends Observable {

    private static NetworkManager NETWORK_LISTENER = new NetworkManager();
    private static NetworkManager NETWORK_SENDER;
    private static boolean IS_SERVER;

    public static void initialize(boolean isServer) {
        if (NETWORK_SENDER != null) {
            throw new RuntimeException("Network manager already initialised!");
        }
        IS_SERVER = isServer;
        NETWORK_SENDER = new NetworkManager();
    }

    public static boolean isServer() {
        return IS_SERVER;
    }

    public static boolean isClient() {
        return !isServer();
    }

    /**
     * Received.
     *
     * @param received the received
     */
    public static void received(String received) {
        NETWORK_LISTENER.setChanged();
        NETWORK_LISTENER.notifyObservers(received);
    }

    /**
     * Send.
     *
     * @param send           the send
     * @param useOtherThread the use other thread
     */
    public static void send(final String send, boolean useOtherThread) {
        NETWORK_SENDER.setChanged();
        if (useOtherThread) {
            Thread deleayedThread = new Thread() {

                @Override
                public void run() {
                    NetworkManager.send(send);
                }
            };

            deleayedThread.start();
        } else {
            NETWORK_SENDER.notifyObservers(send);
        }
    }

    /**
     * Send.
     *
     * @param send the send
     */
    public static void send(String send) {
        send(send, false);
    }


    /**
     * Add network listener.
     *
     * @param add the add
     */
    public static void addNetworkListener(NetworkTrafficReceiver add) {
        NETWORK_LISTENER.addObserver(add);
    }

    /**
     * Add network sender.
     *
     * @param add the add
     */
    public static void addNetworkSender(NetworkTrafficSender add) {
        NETWORK_SENDER.addObserver(add);
    }

    /**
     * Sender server is attached boolean.
     *
     * @return the boolean
     */
    public static boolean senderServerIsAttached() {
        return NETWORK_SENDER.countObservers() >= 1;
    }

    /**
     * Send with delay.
     *
     * @param send the send
     */
    public static void sendWithDelay(final String send) {
        Thread deleayedThread = new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NetworkManager.send(send);
            }
        };

        deleayedThread.start();
    }

}
