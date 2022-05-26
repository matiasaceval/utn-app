package ar.edu.utn.mdp.utnapp;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
}
