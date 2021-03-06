package ar.edu.utn.mdp.utnapp.fetch.request.user_auth;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.events.LoginEvent;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONArrayRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONObjectRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.utils.Email;

public class UserModel {
    public static void getAllUsers(@NonNull Context ctx, CallBackRequest<JSONArray> callBack) {
        String URL_USER = API_URL.USER.getURL();

        JSONArrayRequest request = new JSONArrayRequest(Request.Method.GET, URL_USER, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    public static void getUserByEmail(@NonNull Context ctx, String emailParam, CallBackRequest<JSONObject> callBack) {
        String URL_USER = API_URL.USER.getURL();

        if (!emailParam.isEmpty() && Email.isValidEmail(emailParam)) {
            URL_USER = URL_USER.concat("/" + emailParam);
        }

        JSONObjectRequest request = new JSONObjectRequest(Request.Method.GET, URL_USER, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    public static void updateUser(@NonNull Context ctx, User user, CallBackRequest<JSONObject> callBack) {
        int statusCode = UserContext.verifyUserConnection(ctx);

        if (statusCode == HTTP_STATUS.REDIRECTION_TEMPORARY_REDIRECT || statusCode == HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED) {
            LoginEvent.logUserAgain(ctx, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    updateUser(ctx, user, callBack);
                }

                @Override
                public void onError(int statusCode) {
                    callBack.onError(statusCode);
                }
            });
            return;
        }

        String URL_USER = API_URL.USER.getURL();

        URL_USER = URL_USER.concat("/" + user.getEmail());
        JSONObject body = userToBodyObject(user);

        JSONObjectRequest request = new JSONObjectRequest(Request.Method.PUT, URL_USER, body,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    private static JSONObject userToBodyObject(User user) {
        JSONObject body = new JSONObject();
        try {

            body.put("email", user.getEmail());
            body.put("role", user.getRole());
            body.put("name", user.getName());
            body.put("subscription", user.getSubscription());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }


}
