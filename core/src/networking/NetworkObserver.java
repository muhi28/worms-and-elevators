package networking;

import java.util.Observable;
import java.util.Observer;


public class NetworkObserver extends Observable {

    private static final NetworkObserver NETWORK_OBSERVER = new NetworkObserver();

    public static void received(String received) {
        NETWORK_OBSERVER.setChanged();
        NETWORK_OBSERVER.notifyObservers(received);
    }

    public static void addNetworkListener(Observer add) {
        NETWORK_OBSERVER.addObserver(add);
    }
}
