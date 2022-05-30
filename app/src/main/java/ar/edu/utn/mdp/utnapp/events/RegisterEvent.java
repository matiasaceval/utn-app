package ar.edu.utn.mdp.utnapp.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.Map;

import ar.edu.utn.mdp.utnapp.MainActivity;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup.Register;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup.RegisterModel;

public class RegisterEvent {

    public static void signUp(@NonNull Context ctx, User user, Map<String, EditText> editText) {

        switch (Register.validateRegister(ctx, user, editText.get("confirmPassword").getText().toString())) {
            case "name":
                editText.get("name").setError("Name is required");
                editText.get("name").requestFocus();

                break;
            case "nameLength":
                editText.get("name").setError("Name is too long");
                editText.get("name").requestFocus();
                break;
            case "email":
                editText.get("email").setError("Email is required");
                editText.get("email").requestFocus();
                break;
            case "emailLength":
                editText.get("email").setError("Email is too long");
                editText.get("email").requestFocus();
                break;
            case "emailInvalid":
                editText.get("email").setError("Email is invalid");
                editText.get("email").requestFocus();

                break;
            case "password":
                editText.get("password").setError("Password is required");
                editText.get("password").requestFocus();
                break;
            case "confirmPassword":
                editText.get("confirmPassword").setError("Password is required");
                editText.get("confirmPassword").requestFocus();
                break;
            case "passwordMismatch":
                editText.get("password").setError("Password does not match");
                editText.get("password").requestFocus();
                break;
            case "passwordSecurity":
                editText.get("password").setError("Password must be 8-64 characters long, with a number, a lowercase letter, and an uppercase letter. It's also recommended to use a special character.");
                editText.get("password").requestFocus();
                break;
            default:
                //first validate the user fields, then send the request to the server
                RegisterModel.registerUser(ctx, user, new CallBackRequest<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Intent intent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(intent);
                        ((Activity) ctx).finish();
                    }

                    @Override
                    public void onError(int statusCode) {

                    }
                });
        }
    }
}
