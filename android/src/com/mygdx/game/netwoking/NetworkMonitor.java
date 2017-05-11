package com.mygdx.game.netwoking;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * The type Network monitor.
 */
public class NetworkMonitor extends Thread {

    /**
     * The constant CONNECTION_MONITOR_MESSAGE.
     */
    public static final String CONNECTION_MONITOR_MESSAGE = "beep";


    private Context appContext;
    private NetworkTrafficReceiver networkTrafficReceiver;
    private boolean monitorMessageGot;


    /**
     * Instantiates a new Network monitor.
     *
     * @param appContext the app context
     */
    public NetworkMonitor(Context appContext) {
        this.appContext = appContext;
        this.monitorMessageGot = false;
    }

    @Override
    public void run() {

        final NetworkMonitor currentInstance = this;
        networkTrafficReceiver = new NetworkTrafficReceiver(new FromNetworkProcessor() {
            public void receiveMessage(String message) {
                currentInstance.processMessageFromNetwork(message);
            }
        });


        while (true) {
            try {
                synchronized (this) {
                    monitorMessageGot = false;
                }
                NetworkManager.send(CONNECTION_MONITOR_MESSAGE);
                Thread.sleep(200);
                NetworkManager.send(CONNECTION_MONITOR_MESSAGE);
                Thread.sleep(200);
                NetworkManager.send(CONNECTION_MONITOR_MESSAGE);
                Thread.sleep(200);
                synchronized (this) {
                    if (!monitorMessageGot) {
                        //showToast("Lost network connection!");
                        Thread.sleep(3000);
                        //throw new RuntimeException("Lost network connection!"); //todo
                    }
                }


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            }
        }
    }

    private void processMessageFromNetwork(String message) {
        if (message.equals(CONNECTION_MONITOR_MESSAGE)) {
            synchronized (this) {
                monitorMessageGot = true;
            }
        }
    }

    private void showToast(final String toastMsg) {
        if (null != appContext) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext, toastMsg, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    /**
     * Start monitor.
     *
     * @param appContext the app context
     */
    public static void startMonitor(Context appContext) {
        NetworkMonitor networkMonitor = new NetworkMonitor(appContext);
        networkMonitor.start();
    }
}
