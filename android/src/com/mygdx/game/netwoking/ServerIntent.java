package com.mygdx.game.netwoking;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mygdx.game.util.ToastNotifierImpl;


/**
 * The type Server intent.
 */
public class ServerIntent extends IntentService {

    /**
     * The Tag.
     */
    static final String TAG = "AndroidServerSocket";

    private Context appContext;
    private NetworkUtils networkUtils;


    /**
     * Instantiates a new Server intent.
     */
    public ServerIntent() {
        super("ServerIntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        networkUtils = new NetworkUtils(appContext);
        Log.d(ServerIntent.TAG, String.format("listening on port = %s:%d", networkUtils.wifiIpAddress(), Server.PORT));
        Server server = new Server(new ToastNotifierImpl(appContext));
        server.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        appContext = getBaseContext();//Get the context here
        return super.onStartCommand(intent, flags, startId);
    }


}
