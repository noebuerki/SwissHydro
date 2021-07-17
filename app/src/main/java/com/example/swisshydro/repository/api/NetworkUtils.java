package com.example.swisshydro.repository.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

public class NetworkUtils {

    private static boolean isConnected;

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        isConnected = activeNetwork != null;
        return isConnected;
    }

    public static void isConnected(boolean isConnected) {
        NetworkUtils.isConnected = isConnected;
    }
}
