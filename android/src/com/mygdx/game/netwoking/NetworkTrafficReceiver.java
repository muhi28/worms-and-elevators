package com.mygdx.game.netwoking;


import java.util.Observable;
import java.util.Observer;

/**
 * The type Network traffic receiver.
 */
public class NetworkTrafficReceiver implements Observer {

    private final FromNetworkProcessor fromNetworkProcessor;

    /**
     * Instantiates a new Network traffic receiver.
     *
     * @param fromNetworkProcessor the from network processor
     */
    public NetworkTrafficReceiver(FromNetworkProcessor fromNetworkProcessor) {
        this.fromNetworkProcessor = fromNetworkProcessor;
        NetworkManager.addNetworkListener(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        fromNetworkProcessor.receiveMessage(String.valueOf(o));
    }

}
