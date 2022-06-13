package ar.edu.utn.mdp.utnapp.fetch.request.commission;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.events.LoginEvent;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONArrayRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;
import ar.edu.utn.mdp.utnapp.user.UserContext;

public class CommissionModel {

    public static void getSubjectsByCommission(@NonNull Context ctx, int year, int commission, CallBackRequest<JSONArray> callBack) {
        int statusCode = UserContext.verifyUserConnection(ctx);

        if (statusCode == HTTP_STATUS.REDIRECTION_TEMPORARY_REDIRECT || statusCode == HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED) {
            LoginEvent.logUserAgain(ctx, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    getSubjectsByCommission(ctx, year, commission, callBack);
                }

                @Override
                public void onError(int statusCode) {
                    callBack.onError(statusCode);
                }
            });
            return;
        }

        String URL_COM = API_URL.COMMISSION.getURL();
        URL_COM = URL_COM.concat("/" + commission + "/" + year);

        JSONArrayRequest request = new JSONArrayRequest(Request.Method.GET, URL_COM, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }
}
