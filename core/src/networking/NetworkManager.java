package networking;

import java.util.Observable;
import java.util.Observer;


public class NetworkManager extends Observable {

    private static final NetworkManager NETWORK_LISTENER = new NetworkManager();
    private static final NetworkManager NETWORK_SENDER = new NetworkManager();

    public static void received(String received) {
        NETWORK_LISTENER.setChanged();
        NETWORK_LISTENER.notifyObservers(received);
    }

    public static void send(String send) {
        NETWORK_SENDER.setChanged();
        NETWORK_SENDER.notifyObservers(send);
    }

    public static void addNetworkListener(Observer add) {
        NETWORK_LISTENER.addObserver(add);
    }

    public static void addNetworkSender(Observer add) {
        NETWORK_SENDER.addObserver(add);
    }

    public static boolean senderServerIsAttached() {
        return NETWORK_SENDER.countObservers() >= 1;
    }

    public static void sendWithDelay(final String send) {
        Thread deleayedThread = new Thread() {
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
