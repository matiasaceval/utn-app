package ar.edu.utn.mdp.utnapp.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.utn.mdp.utnapp.LoginActivity;
import ar.edu.utn.mdp.utnapp.MainActivity;
import ar.edu.utn.mdp.utnapp.ProgressDialog;
import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.SubscriptionActivity;
import ar.edu.utn.mdp.utnapp.errors.ErrorLayout;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.Roles;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup.RegisterModel;
import ar.edu.utn.mdp.utnapp.utils.Network;
import ar.edu.utn.mdp.utnapp.utils.Password;

public class RegisterEvent {

    public static View.OnClickListener clickOnBackToLogin(Context ctx, List<TextInputLayout> layouts) {
        return view -> {
            ErrorLayout.clearError(layouts);
            Intent intent = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(intent);
        };
    }

    public static View.OnClickListener clickOnRegisterButton(Context ctx, List<TextInputLayout> layouts, Map<String, TextInputEditText> credential) {
        return view -> {
            if (!Network.isNetworkConnected(ctx, true)) return;
            ErrorLayout.clearError(layouts);
            if (ErrorLayout.existInputError(ctx, layouts)) return;

            final String nameText = credential.get("name").getText().toString().trim();
            final String emailText = credential.get("email").getText().toString().trim();
            final String passwordText = credential.get("password").getText().toString().trim();
            final String passwordConfirmText = credential.get("confirmPassword").getText().toString().trim();

            Map<String, TextInputLayout> layoutMap = new HashMap<>();
            copyLayoutListToMap(layoutMap, layouts);

            if (!Password.isPasswordSecure(ctx, passwordText, false)) {
                ErrorLayout.setError(layoutMap.get("password"), ctx.getString(R.string.password_not_secure));
                return;
            }
            if (!passwordText.equals(passwordConfirmText)) {
                ErrorLayout.setError(layoutMap.get("confirmPassword"), ctx.getString(R.string.password_mismatch));
                return;
            }

            Dialog dialog = new ProgressDialog(ctx);
            User user = new User(nameText, emailText, passwordText, Roles.USER.getName());

            RegisterModel.registerUser(ctx, user, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {

                    dialog.dismiss();
                    LoginModel.loginUser(ctx, user, new CallBackRequest<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Intent intent = new Intent(ctx, SubscriptionActivity.class);
                            ctx.startActivity(intent);
                            ((Activity) ctx).finish();
                        }
                    });

                }

                @Override
                public void onError(int statusCode) {
                    dialog.dismiss();
                    if (statusCode == HTTP_STATUS.CLIENT_ERROR_CONFLICT) {
                        ErrorLayout.setError(layoutMap.get("email"), ctx.getString(R.string.email_already_exists));
                    }
                }
            });
        };
    }


    private static void copyLayoutListToMap(Map<String, TextInputLayout> credentialsMap, List<TextInputLayout> layouts) {
        credentialsMap.put("name", layouts.get(0));
        credentialsMap.put("email", layouts.get(1));
        credentialsMap.put("password", layouts.get(2));
        credentialsMap.put("confirmPassword", layouts.get(3));
    }
}
