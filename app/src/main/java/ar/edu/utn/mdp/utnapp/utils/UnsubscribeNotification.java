package ar.edu.utn.mdp.utnapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.Normalizer;

import me.pushy.sdk.Pushy;
import me.pushy.sdk.util.exceptions.PushyException;

public class UnsubscribeNotification extends AsyncTask<String, Void, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private final Context ctx;

    public UnsubscribeNotification(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    protected final Boolean doInBackground(String... params) {
        try {
            for (String param : params) {
                String p = Normalizer.normalize(param, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .replaceAll(" ", "_");
                Pushy.unsubscribe(p, ctx);
                Log.d("UnsubscribeNotification", "Unsubscribed from: " + p);
            }
            return true;
        } catch (PushyException e) {
            e.printStackTrace();
            return false;
        }
    }
}
