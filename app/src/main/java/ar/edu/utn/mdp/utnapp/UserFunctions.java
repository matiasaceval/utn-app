package ar.edu.utn.mdp.utnapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.Roles;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup.Register;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup.RegisterModel;
import ar.edu.utn.mdp.utnapp.utils.Password;

public final class UserFunctions {
    public static boolean isValidEmail(String email) {

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pat = Pattern.compile(EMAIL_PATTERN);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static void focusLinearLayout(LinearLayout passwordLinearLayout, boolean bool) {
        passwordLinearLayout.setBackgroundResource(bool ? R.drawable.ic_line_focused : R.drawable.ic_line);
    }

    public static void showPassword(EditText password, ImageView seePassword) {
        final int cursorPosition = password.getSelectionStart();
        if (password.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            seePassword.setImageResource(R.drawable.ic_eye_close);
        } else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            seePassword.setImageResource(R.drawable.ic_eye);
        }
        password.setSelection(cursorPosition);
    }

    public static void handleErrorDialog(int statusCode, Context ctx) {
        switch (statusCode) {
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                ErrorDialog.show(ctx, "Invalid credentials", "Email or password is wrong. Please try again.", R.drawable.ic_alert);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                ErrorDialog.show(ctx, "Error", "Internal server error", R.drawable.ic_cloud_offline);
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                ErrorDialog.show(ctx, "Error", "Service unavailable. Please try again later.", R.drawable.ic_cloud_offline);
                break;
            default:
                ErrorDialog.show(ctx, "Error", "Unknown error. Please report it to the support.", R.drawable.ic_warning);
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
        ((Activity) ctx).finish();
    }

    public static void logUserAgain(Context ctx) {
        try {
            final User user = getUserCredentials(ctx);
            if (user.canLogin()) {
                LoginModel.loginUser(ctx, user, null);
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



}
