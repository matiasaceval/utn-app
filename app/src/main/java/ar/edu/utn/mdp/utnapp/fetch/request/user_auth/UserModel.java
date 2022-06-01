package ar.edu.utn.mdp.utnapp.fetch.request.user_auth;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONArray;

import ar.edu.utn.mdp.utnapp.fetch.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONArrayRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;

public class UserModel {
    public static void getAllUser(@NonNull Context ctx, CallBackRequest<JSONArray> callBack) {
        final String URL_USER = API_URL.USER.getURL();

        JSONArrayRequest request = new JSONArrayRequest(Request.Method.GET, URL_USER, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }
    // TODO: Put user & Delete user
}
