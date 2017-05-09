package com.mygdx.game.netwoking;


import java.util.Observable;
import java.util.Observer;

public class NetworkTrafficSender implements Observer {

    private final ToNetworkProcessor toNetworkProcessor;

    public NetworkTrafficSender(ToNetworkProcessor toNetworkProcessor) {
        this.toNetworkProcessor = toNetworkProcessor;
        NetworkManager.addNetworkSender(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        toNetworkProcessor.sendMessage(String.valueOf(o));
    }

}
