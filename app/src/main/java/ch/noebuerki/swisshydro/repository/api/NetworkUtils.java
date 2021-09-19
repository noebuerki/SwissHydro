package ch.noebuerki.swisshydro.repository.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

import java.util.concurrent.TimeUnit;

public class NetworkUtils {

    @SuppressLint("StaticFieldLeak")
    private static NetworkUtils instance;

    private final Context context;
    private long lastCheck = 1;
    private boolean isConnected;


    private NetworkUtils(Context context) {
        this.context = context;
    }

    public static NetworkUtils getInstance() {
        if (instance == null) {
            throw new RuntimeException("call getInstance(Context) first!");
        }

        return instance;
    }

    public static NetworkUtils getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUtils(context);
        }

        return instance;
    }

    public boolean checkConnection() {
        long currentTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        if (currentTimeInSeconds - lastCheck >= 10) {
            lastCheck = currentTimeInSeconds;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network activeNetwork = connectivityManager.getActiveNetwork();

            isConnected = activeNetwork != null;
        }

        return isConnected;
    }
}
