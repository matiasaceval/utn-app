package ar.edu.utn.mdp.utnapp.fetch.request.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Base64;

import ar.edu.utn.mdp.utnapp.fetch.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.IRequestCallBack;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;

public final class UserModel {

    public static String cookie;
    private static SharedPreferences userPrefs;
    private static SharedPreferences cookiePrefs;

    public static int verifyAccountIntegration(Context ctx) {
        userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        cookiePrefs = ctx.getSharedPreferences("Cookies", Context.MODE_PRIVATE);

        final String password = userPrefs.getString("password", "null");
        final String email = userPrefs.getString("email", "null");
        final String cookieBody = cookiePrefs.getString("access_token", "null");
        final User user = new User(email, password);

        if (cookieBody.equals("null") || !user.canLogin()) {
            return HttpURLConnection.HTTP_UNAUTHORIZED;
        }

        user.setPassword(decode(password));
        cookie = cookieBody;

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

    public static void loginUser(Context ctx, User user, final IRequestCallBack callBack) {
        final String URL_LOGIN = API_URL.LOGIN.getURL();
        System.out.println(URL_LOGIN);

        JSONObject body = userLoginBodyObject(user);
        try {
            UserRequest request = new UserRequest(Request.Method.POST, URL_LOGIN, body,
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
        UserModel.cookie = cookie;
    }


    public static String getCookie() {
        return cookie;
    }
}
