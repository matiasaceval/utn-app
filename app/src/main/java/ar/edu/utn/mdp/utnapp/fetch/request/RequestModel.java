package ar.edu.utn.mdp.utnapp.fetch.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Base64;

import ar.edu.utn.mdp.utnapp.fetch.models.User;

public abstract class RequestModel {

    private static final String URL_BASE = "https://utn-api.herokuapp.com/api";

    public static String cookie;
    private static SharedPreferences userPrefs;
    private static SharedPreferences cookiePrefs;

    public static int verifyAccountIntegration(Context ctx) {
        userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        cookiePrefs = ctx.getSharedPreferences("Cookies", Context.MODE_PRIVATE);

        final String email = userPrefs.getString("email", "null");
        final String password = userPrefs.getString("password", "null");
        final String cookieBody = cookiePrefs.getString("access_token", "null");
        final User user = new User(email, password);

        if (cookieBody.equals("null") || !user.canLogin()) {
            return HttpURLConnection.HTTP_UNAUTHORIZED;
        }

        final String[] chunks = cookieBody.split("=")[1].split("\\.");

        try {
            final JSONObject payload = new JSONObject(decode(chunks[1]));
            if (payload.getLong("exp") < (System.currentTimeMillis() / 1000)) {
                return HttpURLConnection.HTTP_UNAUTHORIZED; // TODO: should throw session expired exception
            }
            return HttpURLConnection.HTTP_OK;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return HttpURLConnection.HTTP_UNAUTHORIZED;
    }

    public static void loginUser(Context ctx, User user, final LoginCallBack callBack) {
        final String URL = URL_BASE + "/auth/login";
        JSONObject body = userLoginBodyObject(user);

        try {
            UserRequest request = new UserRequest(Request.Method.POST, URL, body,
                    response -> {
                        try {
                            userPrefs.edit().putString("name", response.getString("name")).apply();
                            userPrefs.edit().putString("email", response.getString("email")).apply();
                            userPrefs.edit().putString("role", response.getString("role")).apply();
                            userPrefs.edit().putString("password", user.getPassword()).apply();
                            cookiePrefs.edit().putString("access_token", cookie).apply();
                            if (callBack != null) callBack.onSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                if (callBack != null) callBack.onError(error.networkResponse.statusCode);
                error.printStackTrace();
            });
            request.setShouldCache(false);
            RequestSingleton.getInstance(ctx).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject userLoginBodyObject(User user) {
        JSONObject body = new JSONObject();
        try {
            body.put("email", user.getEmail());
            body.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public static void setCookie(String cookie) {
        RequestModel.cookie = cookie;
    }
}
