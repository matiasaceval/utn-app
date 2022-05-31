package ar.edu.utn.mdp.utnapp.utils;

import android.content.Context;

import java.util.Base64;

import ar.edu.utn.mdp.utnapp.R;

public final class Password {
    public static String encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    public static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public static boolean isPasswordSecure(Context ctx, String password, boolean strongMode) {
        String specialCharacters = "[!\"#\\\\$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~]";

        final String strongBaseRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*" + specialCharacters + ")(?=\\S+$).";
        final String weakBaseRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).";

        String baseRegex = strongMode ? strongBaseRegex : weakBaseRegex;
        int min = ctx.getResources().getInteger(R.integer.password_min_length);
        int max = ctx.getResources().getInteger(R.integer.password_max_length);
        String values = "{" + min + "," + max + "}" + "$";
        String regex = baseRegex + values;
        return password.matches(regex);
    }

}
