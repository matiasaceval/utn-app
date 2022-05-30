package ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup;

import android.content.Context;

import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.UserFunctions;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.utils.Password;

public class Register {

    public static String validateRegister(Context ctx, User user, String confirmPassword) {
        if (user.getName().isEmpty()) {
            return "name";
        }
        if (user.getName().length() > ctx.getResources().getInteger(R.integer.name_length)) {
            return "nameLength";
        }
        if (user.getEmail().isEmpty()) {
            return "email";
        }
        if (user.getEmail().length() > ctx.getResources().getInteger(R.integer.email_length)) {
            return "emailLength";
        }
        if (!UserFunctions.isValidEmail(user.getEmail())) {
            return "emailInvalid";
        }
        if (user.getPassword().isEmpty()) {
            return "password";
        } else if (!Password.isPasswordSecure(ctx, user.getPassword(), false)) {
            return "passwordSecurity";
        }
        if (confirmPassword.isEmpty()) {
            return "confirmPassword";
        }
        return user.getPassword().equals(confirmPassword) ? "success" : "passwordMismatch";
    }

}