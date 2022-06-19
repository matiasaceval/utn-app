package ar.edu.utn.mdp.utnapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.Normalizer;

import me.pushy.sdk.Pushy;
import me.pushy.sdk.util.exceptions.PushyException;

public class SubscribeNotification extends AsyncTask<String, Void, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private final Context ctx;

    public SubscribeNotification(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    protected final Boolean doInBackground(String... params) {
        try {
            if (!Pushy.isRegistered(ctx)) Pushy.register(ctx);

            for (String param : params) {
                String p = Normalizer.normalize(param, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .replaceAll(" ", "_");
                Pushy.subscribe(p, ctx);
                Log.d("SubscribeNotification", "Subscribed to: " + p);
            }
            return true;
        } catch (PushyException e) {
            e.printStackTrace();
            return false;
        }
    }
}
