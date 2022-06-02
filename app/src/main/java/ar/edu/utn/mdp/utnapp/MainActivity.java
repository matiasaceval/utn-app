package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.events.LoginEvent;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.calendar.CalendarModel;
import ar.edu.utn.mdp.utnapp.user.UserFunctions;
import ar.edu.utn.mdp.utnapp.utils.Network;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView);
        Button btn = findViewById(R.id.logout);
        Button btn2 = findViewById(R.id.holidays);

        UserFunctions.verifyUserConnection(this);
        User user = UserFunctions.getUser(this);

        tv.setText(String.format("Hi %s! Your email is %s and your role is %s.", user.getName(), user.getEmail(), user.getRole()));

        btn.setOnClickListener(view -> {
            LoginEvent.logout(MainActivity.this);
            finish();
        });

        btn2.setOnClickListener(view -> {
            if (!Network.isNetworkConnected(this, true)) return;

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
                    ErrorDialog.handler(statusCode, MainActivity.this);
                }
            });
        });
    }
}