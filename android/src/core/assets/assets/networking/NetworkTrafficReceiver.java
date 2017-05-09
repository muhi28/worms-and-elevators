package core.assets.assets.networking;


import java.util.Observable;
import java.util.Observer;

public class NetworkTrafficReceiver implements Observer {

    private final FromNetworkProcessor fromNetworkProcessor;

    public NetworkTrafficReceiver(FromNetworkProcessor fromNetworkProcessor) {
        this.fromNetworkProcessor = fromNetworkProcessor;
        core.assets.assets.networking.NetworkManager.addNetworkListener(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        fromNetworkProcessor.receiveMessage(String.valueOf(o));
    }

}
