package ar.edu.utn.mdp.utnapp;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.net.HttpURLConnection;
import java.util.regex.Pattern;

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
}
