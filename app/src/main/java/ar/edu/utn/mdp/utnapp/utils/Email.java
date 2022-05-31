package ar.edu.utn.mdp.utnapp.utils;

import java.util.regex.Pattern;

public final class Email {
    public static boolean isValidEmail(String email) {

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pat = Pattern.compile(EMAIL_PATTERN);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
