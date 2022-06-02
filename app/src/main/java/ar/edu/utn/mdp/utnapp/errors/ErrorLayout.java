package ar.edu.utn.mdp.utnapp.errors;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.utils.Email;

public class ErrorLayout {
    public static void setError(TextInputLayout input, String error) {
        input.setError(error);
    }

    public static void clearError(List<TextInputLayout> inputs) {
        for (TextInputLayout input : inputs) {
            input.setError(null);
        }
    }

    public static boolean existInputError(Context ctx, List<TextInputLayout> inputs) {
        final Resources res = ctx.getResources();
        final String emailHint = res.getString(R.string.email);

        final String invalidEmail = res.getString(R.string.invalid_email);
        final String fieldRequired = res.getString(R.string.field_required);

        for (TextInputLayout input : inputs) {
            TextInputEditText editText = (TextInputEditText) input.getEditText();
            final String hint = Objects.requireNonNull(Objects.requireNonNull(editText).getHint()).toString().trim();
            final String text = Objects.requireNonNull(editText.getText()).toString().trim();

            if (text.isEmpty()) {
                ErrorLayout.setError(input, String.format(fieldRequired, hint));
                return true;
            }

            if (hint.equals(emailHint) && !Email.isValidEmail(text)) {
                ErrorLayout.setError(input, invalidEmail);
                return true;
            }
        }
        return false;
    }
}
