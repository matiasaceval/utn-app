package ar.edu.utn.mdp.utnapp.register;

import android.content.Context;

import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.UserFunctions;

public class Register {
    private String name;
    private String email;
    private String password;
    private Context ctx;

    public Register(String name, String email, String password, Context ctx) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ctx = ctx;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public String setEmail(String email) {
        return this.email = email;
    }

    public String setPassword(String password) {
        return this.password = password;
    }

    public String validateRegister(String confirmPassword) {
        if (name.isEmpty()) {
            return "name";
        }
        if (name.length() > ctx.getResources().getInteger(R.integer.name_length)) {
            return "nameLength";
        }
        if (email.isEmpty()) {
            return "email";
        }
        if (email.length() > ctx.getResources().getInteger(R.integer.email_length)) {
            return "emailLength";
        }
        if (!UserFunctions.isValidEmail(email)) {
            return "emailInvalid";
        }
        if (password.isEmpty()) {
            return "password";
        } else if (!isPasswordSecure(false)) {
            return "passwordSecurity";
        }
        if (confirmPassword.isEmpty()) {
            return "confirmPassword";
        }
        return password.equals(confirmPassword) ? "success" : "passwordMismatch";
    }

    public boolean isPasswordSecure(boolean strongMode) {
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
