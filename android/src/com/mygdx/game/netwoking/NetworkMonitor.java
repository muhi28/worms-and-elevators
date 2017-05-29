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
    private static NetworkMonitor NETWORK_MONITOR;


    private Context appContext;
    private NetworkTrafficReceiver networkTrafficReceiver;
    private boolean monitorMessageGot;


    private NetworkMonitor(Context appContext) {
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

        Thread sendBeaconsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    NetworkManager.send(CONNECTION_MONITOR_MESSAGE);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        sendBeaconsThread.start();

        while (true) {
            try {
                synchronized (this) {
                    monitorMessageGot = false;
                }

                Thread.sleep(2000);
                synchronized (this) {
                    if (!monitorMessageGot) {
                        showToast("Lost network connection!");
                        Thread.sleep(3000);
                        throw new RuntimeException("Lost network connection!");
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
        NETWORK_MONITOR = networkMonitor;
    }

    /**
     * Is connected boolean.
     *
     * @return the boolean
     */
    public static boolean isConnected() {
        synchronized (NETWORK_MONITOR) {
            return NETWORK_MONITOR.monitorMessageGot;
        }
    }
}
