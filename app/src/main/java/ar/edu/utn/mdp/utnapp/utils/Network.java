package ar.edu.utn.mdp.utnapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;

public class Network {

    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static boolean isNetworkConnected(Context ctx, boolean showError) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

        if (!isConnected && showError) {
            new ErrorDialog(ctx, "No internet connection", "Verify your internet connection and try again.", R.drawable.ic_cloud_offline);
        }
        return isConnected;
    }
    public static void clearCache(Context ctx) {
        try {
            final SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
            final SharedPreferences cookiePrefs = ctx.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
            userPrefs.edit().clear().apply();
            cookiePrefs.edit().clear().apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
