package ar.edu.utn.mdp.utnapp.fetch.request.commission;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONArray;

import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONArrayRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;
import ar.edu.utn.mdp.utnapp.user.UserContext;

public class CommissionModel {

    public static void getSubjectsByCommission(@NonNull Context ctx, int year, int commission, CallBackRequest<JSONArray> callBack) {
        UserContext.verifyUserConnection(ctx);
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
