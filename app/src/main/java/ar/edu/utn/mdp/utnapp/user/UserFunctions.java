package ar.edu.utn.mdp.utnapp.user;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.events.LoginEvent;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginConnection;
import ar.edu.utn.mdp.utnapp.utils.Password;

public final class UserFunctions {

    public static User getUser(Context ctx) {
        final SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String email = userPrefs.getString("email", "null");
        final String name = userPrefs.getString("name", "null");
        final String role = userPrefs.getString("role", "null");
        return new User(name, email, role);
    }

    public static User getUserCredentials(Context ctx) {
        final SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String email = userPrefs.getString("email", "null");
        final String password = userPrefs.getString("password", "null");
        final String decodedPassword = !password.equals("null") ? Password.decode(password) : "null";
        return new User(email, decodedPassword);
    }

    public static void verifyUserConnection(Context ctx) {
        final int statusCode = LoginConnection.verifyAccountIntegration(ctx);

        switch (statusCode) {
            case HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED:
                LoginEvent.logout(ctx);
                break;
            case HTTP_STATUS.REDIRECTION_TEMPORARY_REDIRECT:
                LoginEvent.logUserAgain(ctx);
                break;
        }
    }

}
