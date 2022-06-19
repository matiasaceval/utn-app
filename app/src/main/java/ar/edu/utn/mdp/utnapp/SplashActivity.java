package ar.edu.utn.mdp.utnapp;

import static ar.edu.utn.mdp.utnapp.events.LoginEvent.logout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONObject;

import java.util.Set;

import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.utils.Network;
import ar.edu.utn.mdp.utnapp.utils.SubscribeNotification;
import me.pushy.sdk.Pushy;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    LottieAnimationView anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // notifications
        Pushy.listen(this);
        if (!Pushy.isRegistered(this)) {
            new SubscribeNotification(this).execute("app-news");
        }

        // theme
        final SharedPreferences userPrefs = getSharedPreferences("User", Context.MODE_PRIVATE);
        final SharedPreferences prefs = getSharedPreferences("Theme", MODE_PRIVATE);
        boolean isNightModeOn = prefs.getBoolean("isNightModeOn", true);
        AppCompatDelegate.setDefaultNightMode(isNightModeOn ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        // activity
        anim = findViewById(R.id.loadingDots);
        UserContext.verifyUserConnection(SplashActivity.this, null, () -> {
            try {
                final User user = UserContext.getUserCredentials(SplashActivity.this);
                if (!user.canLogin()) {
                    anim.cancelAnimation();
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
        }, () -> {
            Set<String> list = userPrefs.getStringSet("subscription", null);

            if (list != null && !list.isEmpty()) {
                goToMain();
            } else {
                Intent intent = new Intent(this, SubscriptionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void goToMain() {
        anim.cancelAnimation();
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