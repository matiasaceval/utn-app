package ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.utils.Password;

public class LoginConnection {

    private static String cookie;
    public static int verifyAccountIntegration(@NonNull Context ctx) {
        final SharedPreferences cookiePrefs = ctx.getSharedPreferences("Cookie", Context.MODE_PRIVATE);

        String cookieBody = cookiePrefs.getString("access_token", "null");
        final User user = UserContext.getUserCredentials(ctx);

        if (cookieBody.equals("null") || !user.canLogin()) {
            return HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED;
        }

        setCookie(cookieBody);

        final String[] chunks = cookieBody.split("=")[1].split("\\.");

        try {
            final JSONObject payload = new JSONObject(Password.decode(chunks[1]));
            if (payload.getLong("exp") < (System.currentTimeMillis() / 1000)) {
                return HTTP_STATUS.REDIRECTION_TEMPORARY_REDIRECT;
            }
            return HTTP_STATUS.SUCCESS_OK;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED;
    }

    public static String getCookie() {
        return cookie;
    }

    public static void setCookie(String cookie) {
        LoginConnection.cookie = cookie;
    }
}
