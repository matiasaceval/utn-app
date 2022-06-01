package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.calendar.CalendarModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView);
        Button btn = findViewById(R.id.logout);
        Button btn2 = findViewById(R.id.holidays);

        UserFunctions.verifyConnection(this);
        User user = UserFunctions.getUser(this);

        tv.setText(String.format("Hi %s! Your email is %s and your role is %s.", user.getName(), user.getEmail(), user.getRole()));

        btn.setOnClickListener(view -> {
            UserFunctions.logout(MainActivity.this);
            finish();
        });

        btn2.setOnClickListener(view -> {
            Dialog progress = new ProgressDialog(this);
            String query = "?date=02/02/2022";
            CalendarModel.getNextHoliday(MainActivity.this, query, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    progress.dismiss();
                    tv.setText(response.toString());
                }

                @Override
                public void onError(int statusCode) {
                    progress.dismiss();
                    UserFunctions.handleErrorDialog(statusCode, MainActivity.this);
                }
            });
        });
    }
}