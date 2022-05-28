package ar.edu.utn.mdp.utnapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import ar.edu.utn.mdp.utnapp.fetch.request.IRequestCallBack;
import ar.edu.utn.mdp.utnapp.fetch.request.calendar.CalendarModel;
import ar.edu.utn.mdp.utnapp.fetch.request.user.UserModel;

public class MainActivity extends AppCompatActivity {

    private static SharedPreferences userPrefs;
    private static SharedPreferences cookiePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        userPrefs = getSharedPreferences("User", Context.MODE_PRIVATE);
        cookiePrefs = getSharedPreferences("Cookies", Context.MODE_PRIVATE);

        TextView tv = findViewById(R.id.textView);
        Button btn = findViewById(R.id.logout);

        try {
            final int statusCode = UserModel.verifyAccountIntegration(MainActivity.this);
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                logout(MainActivity.this);
                finish();
            }

            final String email = userPrefs.getString("email", "null");
            final String name = userPrefs.getString("name", "null");
            final String role = userPrefs.getString("role", "null");

            tv.setText(String.format("Hi %s! Your email is %s and your role is %s.", name, email, role));
        } catch (Exception e) {
            e.printStackTrace();
        }



        btn.setOnClickListener(view -> {
            CalendarModel.getHoliday(MainActivity.this, new IRequestCallBack() {
                @Override
                public void onSuccess(JSONObject response) {
                    System.out.println(response);


                }

                @Override
                public void onError(int statusCode) {
                    System.out.println(statusCode);
                }
            });
            finish();
        });
    }

    public static void logout(Context ctx) {
        userPrefs.edit().clear().apply();
        cookiePrefs.edit().clear().apply();
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(intent);
    }
}