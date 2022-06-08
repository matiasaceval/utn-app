package ar.edu.utn.mdp.utnapp;

import static ar.edu.utn.mdp.utnapp.events.LoginEvent.logout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.utils.Network;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        UserContext.verifyUserConnection(SplashActivity.this, null, () -> {
            try {
                final User user = UserContext.getUserCredentials(SplashActivity.this);
                if (!user.canLogin()) {
                    logout(SplashActivity.this);
                    return;
                }
                LoginModel.loginUser(SplashActivity.this, user, new CallBackRequest<JSONObject>() {

                    @Override
                    public void onSuccess(JSONObject response) {
                        goToMain();
                    }

                    @Override
                    public void onError(int statusCode) {
                        // this event will be triggered only if user in database is corrupted or deleted
                        ErrorDialog.handler(statusCode, SplashActivity.this, errorCallback());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                new ErrorDialog(SplashActivity.this, "Unexpected error", e.getMessage(), errorCallback());
            }
        }, this::goToMain);
    }

    private void goToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private View.OnClickListener errorCallback() {
        return view -> {
            Network.clearCache(SplashActivity.this);
            finish();
        };
    }
}