package com.mygdx.game.netwoking;


import java.util.Observable;
import java.util.Observer;

/**
 * The type Network traffic sender.
 */
public class NetworkTrafficSender implements Observer {

    private final ToNetworkProcessor toNetworkProcessor;

    /**
     * Instantiates a new Network traffic sender.
     *
     * @param toNetworkProcessor the to network processor
     */
    public NetworkTrafficSender(ToNetworkProcessor toNetworkProcessor) {
        this.toNetworkProcessor = toNetworkProcessor;
        NetworkManager.addNetworkSender(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        toNetworkProcessor.sendMessage(String.valueOf(o));
    }

}
