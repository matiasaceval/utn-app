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

import java.util.List;
import java.util.Map;

import ar.edu.utn.mdp.utnapp.LoginActivity;
import ar.edu.utn.mdp.utnapp.MainActivity;
import ar.edu.utn.mdp.utnapp.ProgressDialog;
import ar.edu.utn.mdp.utnapp.RegisterActivity;
import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.errors.ErrorLayout;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.utils.Network;

public final class LoginEvent {
    public static View.OnClickListener clickOnLoginButton(Context ctx, List<TextInputLayout> layouts, Map<String, TextInputEditText>credentials) {
        return view -> {
            if (!Network.isNetworkConnected(ctx, true)) return;

            ErrorLayout.clearError(layouts);

            final String mail = credentials.get("email").getText().toString().trim();
            final String pass = credentials.get("password").getText().toString().trim();

            final User usr = new User(mail, pass);

            if (ErrorLayout.existInputError(ctx, layouts)) return;

            Dialog progress = new ProgressDialog(ctx);

            LoginModel.loginUser(ctx, usr, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    progress.dismiss();
                    Intent intent = new Intent(ctx, MainActivity.class);
                    ctx.startActivity(intent);
                    ((Activity)ctx).finish();
                }

                @Override
                public void onError(int statusCode) {
                    progress.dismiss();
                    ErrorDialog.handler(statusCode, ctx);
                }
            });
        };

    }

    public static View.OnClickListener clickOnSignUpButton(Context ctx, List<TextInputLayout> layouts) {
        return view -> {
            ErrorLayout.clearError(layouts);
            Intent intent = new Intent(ctx, RegisterActivity.class);
            ctx.startActivity(intent);
        };
    }

    public static void logUserAgain(Context ctx) {
        try {
            final User user = UserContext.getUserCredentials(ctx);
            if (user.canLogin()) {
                LoginModel.loginUser(ctx, user, new CallBackRequest<JSONObject>() {
                    @Override
                    public void onError(int statusCode) {
                        // this event will be triggered only if user in database is corrupted or deleted
                        ErrorDialog.handler(statusCode, ctx, view -> logout(ctx));
                    }
                });
            } else {
                new ErrorDialog(ctx, "Unexpected error", "Please login again.", view -> logout(ctx));
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorDialog(ctx, "Unexpected error", e.getMessage(), view -> logout(ctx));
        }
    }

    public static void logUserAgain(Context ctx, CallBackRequest<JSONObject> callback) {
        try {
            final User user = UserContext.getUserCredentials(ctx);
            if (user.canLogin()) {
                LoginModel.loginUser(ctx, user, callback);
            } else {
                new ErrorDialog(ctx, "Unexpected error", "Please login again.", view -> logout(ctx));
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorDialog(ctx, "Unexpected error", e.getMessage(), view -> logout(ctx));
        }
    }

    public static void logout(Context ctx) {
        Network.clearCache(ctx);
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(intent);
        ActivityCompat.finishAffinity((Activity) ctx);
    }
}
