package ar.edu.utn.mdp.utnapp.user;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.HashSet;

import ar.edu.utn.mdp.utnapp.events.LoginEvent;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.Function;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginConnection;
import ar.edu.utn.mdp.utnapp.utils.Password;

public final class UserContext {

    public static User getUser(Context ctx) {
        final SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String email = userPrefs.getString("email", "null");
        final String name = userPrefs.getString("name", "null");
        final String role = userPrefs.getString("role", "null");
        HashSet<String> subscription = (HashSet<String>) userPrefs.getStringSet("subscription", null);
        return new User(name, email, role, subscription);
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
            case HTTP_STATUS.SUCCESS_OK:
                break;
        }
    }

    public static void verifyUserConnection(Context ctx, @Nullable Function unauthorized, @Nullable Function redirect, @Nullable Function success) {
        final int statusCode = LoginConnection.verifyAccountIntegration(ctx);

        switch (statusCode) {
            case HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED:
                if (unauthorized != null) unauthorized.execute();
                else LoginEvent.logout(ctx);
                break;
            case HTTP_STATUS.REDIRECTION_TEMPORARY_REDIRECT:
                if (redirect != null) redirect.execute();
                else LoginEvent.logUserAgain(ctx);
                break;
            case HTTP_STATUS.SUCCESS_OK:
                if (success != null) success.execute();
                break;
        }
    }


}
