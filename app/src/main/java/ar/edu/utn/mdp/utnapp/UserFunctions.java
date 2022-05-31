package ar.edu.utn.mdp.utnapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Objects;

import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;
import ar.edu.utn.mdp.utnapp.utils.Email;
import ar.edu.utn.mdp.utnapp.utils.Password;

public final class UserFunctions {

    public static void handleErrorDialog(int statusCode, Context ctx) {
        handleErrorDialog(statusCode, ctx, null);
    }

    public static void handleErrorDialog(int statusCode, Context ctx, View.OnClickListener onClickListener) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                ErrorDialog.show(ctx, "Invalid credentials", "Email or password is wrong. Please try again.", R.drawable.ic_alert, onClickListener);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                ErrorDialog.show(ctx, "Error", "Internal server error", R.drawable.ic_cloud_offline, onClickListener);
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                ErrorDialog.show(ctx, "Error", "Service unavailable. Please try again later.", R.drawable.ic_cloud_offline, onClickListener);
                break;
            default:
                ErrorDialog.show(ctx, "Error", "Unknown error. Please report it to the support.", R.drawable.ic_warning, onClickListener);
                break;
        }
    }

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

    public static void logout(Context ctx) {
        final SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        final SharedPreferences cookiePrefs = ctx.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
        userPrefs.edit().clear().apply();
        cookiePrefs.edit().clear().apply();
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(intent);
        ActivityCompat.finishAffinity((Activity) ctx);
    }

    public static void logUserAgain(Context ctx) {
        try {
            final User user = getUserCredentials(ctx);
            if (user.canLogin()) {
                LoginModel.loginUser(ctx, user, new CallBackRequest<JSONObject>() {
                    @Override
                    public void onError(int statusCode) {
                        // this event will be triggered only if user in database is corrupted or deleted
                        handleErrorDialog(statusCode, ctx, view -> logout(ctx));
                    }
                });
            } else {
                ErrorDialog.show(ctx, "Unexpected error", "Please login again.", view -> logout(ctx));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDialog.show(ctx, "Unexpected error", e.getMessage(), view -> logout(ctx));
        }
    }

    public static void verifyConnection(Context ctx) {
        final int statusCode = LoginModel.verifyAccountIntegration(ctx);

        switch (statusCode) {
            case HTTP_STATUS.CLIENT_ERROR_UNAUTHORIZED:
                logout(ctx);
                break;
            case HTTP_STATUS.REDIRECTION_TEMPORARY_REDIRECT:
                logUserAgain(ctx);
                break;
        }
    }

    public static void setError(TextInputLayout input, String error) {
        input.setError(error);
    }

    public static boolean existInputError(Context ctx, List<TextInputLayout> inputs) {
        final Resources res = ctx.getResources();
        final String emailHint = res.getString(R.string.email);
        final String passwordHint = res.getString(R.string.password);

        final String invalidEmail = res.getString(R.string.invalid_email);
        final String invalidPassword = res.getString(R.string.invalid_password);
        final String fieldRequired = res.getString(R.string.field_required);

        for (TextInputLayout input : inputs) {
            TextInputEditText editText = (TextInputEditText) input.getEditText();
            final String hint = Objects.requireNonNull(Objects.requireNonNull(editText).getHint()).toString();
            final String text = Objects.requireNonNull(editText.getText()).toString();

            if (text.isEmpty()) {
                setError(input, String.format(fieldRequired, hint));
                return true;
            }

            if (hint.equals(emailHint) && !Email.isValidEmail(text)) {
                setError(input, invalidEmail);
                return true;
            }

            if (hint.equals(passwordHint) && !Password.isPasswordSecure(ctx, text, false)) {
                setError(input, invalidPassword);
                return true;
            }
        }
        return false;
    }

    public static void clearError(List<TextInputLayout> inputs) {
        for (TextInputLayout input : inputs) {
            input.setError(null);
        }
    }
}
