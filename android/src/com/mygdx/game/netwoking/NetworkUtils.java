package com.mygdx.game.netwoking;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import static android.content.Context.WIFI_SERVICE;

/**
 * The type Network utils.
 */
public final class NetworkUtils {

    private final Context appContext;

    /**
     * Instantiates a new Network utils.
     *
     * @param appContext the app context
     */
    public NetworkUtils(Context appContext) {
        this.appContext = appContext;
    }

    /**
     * Is phone connected to wifi boolean.
     *
     * @return the boolean
     */
    public boolean isPhoneConnectedToWifi() {
        return wifiIpAddress() != null;
    }

    /**
     * Wifi ip address string.
     *
     * @return the string
     */
    public String wifiIpAddress() {
        WifiManager wifiManager = (WifiManager) appContext.getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.", ex);
            ipAddressString = null;
        }

        return ipAddressString;
    }
}
