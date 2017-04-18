package com.mygdx.game.netwoking;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import networking.NetworkManager;
import networking.NetworkTrafficSender;
import networking.ToNetworkProcessor;


public class Server extends IntentService {
    public static final int PORT = 12345;
    static final String TAG = "AndroidServerSocket";

    private Context appContext;
    private NetworkUtils networkUtils;
    private PrintStream out;

    private NetworkTrafficSender networkTrafficSender;


    public static String INIT_MESSAGE = "START_SERVER";

    public Server() {
        super("Server");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Server currentInstance = this;
        networkTrafficSender = new NetworkTrafficSender(new ToNetworkProcessor() {
            public void sendMessage(String message) {
                currentInstance.sendMessage(message);
            }
        });

        Log.d(Server.TAG, "onHandleIntent");
        ServerSocket listener = null;
        networkUtils = new NetworkUtils(appContext);
        try {
            listener = new ServerSocket(PORT);
            Log.d(Server.TAG, String.format("listening on port = %s:%d", networkUtils.wifiIpAddress(), PORT));
            while (true) {
                Log.d(Server.TAG, "waiting for client");
                BufferedReader in = null;
                synchronized (this) {
                    Socket socket = listener.accept();
                    showToast(String.format("client connected from: %s", socket.getRemoteSocketAddress().toString()));
                    Log.d(Server.TAG, String.format("client connected from: %s", socket.getRemoteSocketAddress().toString()));
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintStream(socket.getOutputStream());
                }
                for (String inputLine; (inputLine = in.readLine()) != null; ) {
                    Log.d(Server.TAG, "received");
                    NetworkManager.received(inputLine);
                    // showToast("received: " + inputLine);
                    Log.d(Server.TAG, inputLine);
                }
            }
        } catch (IOException e) {
            Log.d(Server.TAG, e.toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        appContext = getBaseContext();//Get the context here
        return super.onStartCommand(intent, flags, startId);
    }


    //Use this method to show toast
    private void showToast(final String toastMsg) {
        if (null != appContext) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext, toastMsg, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public void sendMessage(String toSend) {
        synchronized (this) {
            if (out != null) {
                out.println(toSend);
                out.flush();
            }
        }
    }
}
