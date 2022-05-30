package ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

import ar.edu.utn.mdp.utnapp.UserFunctions;
import ar.edu.utn.mdp.utnapp.fetch.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;

public final class LoginModel {

    public static String cookie;
    private static SharedPreferences userPrefs;
    private static SharedPreferences cookiePrefs;

    public static int verifyAccountIntegration(@NonNull Context ctx) {
        userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        cookiePrefs = ctx.getSharedPreferences("Cookie", Context.MODE_PRIVATE);

        final String cookieBody = cookiePrefs.getString("access_token", "null");
        final User user = UserFunctions.getUserCredentials(ctx);

        if (cookieBody.equals("null") || !user.canLogin()) {
            return HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED;
        }

        cookie = cookieBody;

        final String[] chunks = cookieBody.split("=")[1].split("\\.");

        try {
            final JSONObject payload = new JSONObject(decode(chunks[1]));
            if (payload.getLong("exp") < (System.currentTimeMillis() / 1000)) {
                return HTTP_STATUS.REDIRECTION_TEMPORARY_REDIRECT;
            }
            return HTTP_STATUS.SUCCESS_OK;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED;
    }

    public static void loginUser(@NonNull Context ctx, User user, CallBackRequest<JSONObject> callBack) {
        final String URL_LOGIN = API_URL.LOGIN.getURL();
        JSONObject body = userLoginBodyObject(user);
        try {
            UserLoginRequest request = new UserLoginRequest(Request.Method.POST, URL_LOGIN, body,
                    response -> {
                        try {
                            userPrefs.edit().putString("name", response.getString("name")).apply();
                            userPrefs.edit().putString("email", response.getString("email")).apply();
                            userPrefs.edit().putString("role", response.getString("role")).apply();
                            userPrefs.edit().putString("password", encode(user.getPassword())).apply();
                            cookiePrefs.edit().putString("access_token", cookie).apply();
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

    private static String encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public static void setCookie(String cookie) {
        LoginModel.cookie = cookie;
    }

    public static String getCookie() {
        return cookie;
    }
}
