package com.mygdx.game.netwoking;

import java.util.Observable;


public class NetworkManager extends Observable {

    private static final NetworkManager NETWORK_LISTENER = new NetworkManager();
    private static final NetworkManager NETWORK_SENDER = new NetworkManager();

    public static void received(String received) {
        NETWORK_LISTENER.setChanged();
        NETWORK_LISTENER.notifyObservers(received);
    }

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

    public static void send(String send) {
        send(send, false);
    }


    public static void addNetworkListener(NetworkTrafficReceiver add) {
        NETWORK_LISTENER.addObserver(add);
    }

    public static void addNetworkSender(NetworkTrafficSender add) {
        NETWORK_SENDER.addObserver(add);
    }

    public static boolean senderServerIsAttached() {
        return NETWORK_SENDER.countObservers() >= 1;
    }

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
