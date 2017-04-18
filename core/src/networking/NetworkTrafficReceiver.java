package networking;


import java.util.Observable;
import java.util.Observer;

public class NetworkTrafficReceiver implements Observer {

    private final FromNetworkProcessor fromNetworkProcessor;

    public NetworkTrafficReceiver(FromNetworkProcessor fromNetworkProcessor) {
        this.fromNetworkProcessor = fromNetworkProcessor;
        NetworkManager.addNetworkListener(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        fromNetworkProcessor.receiveMessage(String.valueOf(o));
    }

}
