package com.mygdx.game.netwoking;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import static android.content.Context.WIFI_SERVICE;

public final class NetworkUtils {

    private final Context appContext;

    public NetworkUtils(Context appContext) {
        this.appContext = appContext;
    }

    public boolean isPhoneConnectedToWifi() {
        return wifiIpAddress() != null;
    }

    public String wifiIpAddress() {
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(WIFI_SERVICE);
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
