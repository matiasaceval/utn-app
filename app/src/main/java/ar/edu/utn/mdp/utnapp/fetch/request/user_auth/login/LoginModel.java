package ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.fetch.request.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.user.UserFunctions;
import ar.edu.utn.mdp.utnapp.utils.Password;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;

public final class LoginModel {

    public static void loginUser(@NonNull Context ctx, User user, CallBackRequest<JSONObject> callBack) {

        final SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        final SharedPreferences cookiePrefs = ctx.getSharedPreferences("Cookie", Context.MODE_PRIVATE);

        final String URL_LOGIN = API_URL.LOGIN.getURL();
        JSONObject body = userLoginBodyObject(user);
        try {
            UserLoginRequest request = new UserLoginRequest(Request.Method.POST, URL_LOGIN, body,
                    response -> {
                        try {
                            userPrefs.edit().putString("name", response.getString("name")).apply();
                            userPrefs.edit().putString("email", response.getString("email")).apply();
                            userPrefs.edit().putString("role", response.getString("role")).apply();
                            userPrefs.edit().putString("password", Password.encode(user.getPassword())).apply();
                            cookiePrefs.edit().putString("access_token", LoginConnection.getCookie()).apply();
                            if (callBack != null) callBack.onSuccess(null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                try {
                    if (callBack != null) callBack.onError(error.networkResponse.statusCode);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            });
            request.setShouldCache(false);
            RequestSingleton.getInstance(ctx).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject userLoginBodyObject(User user) {
        JSONObject body = new JSONObject();
        try {
            body.put("email", user.getEmail());
            body.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }

}
