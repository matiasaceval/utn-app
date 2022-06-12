package ar.edu.utn.mdp.utnapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.utn.mdp.utnapp.events.LoginEvent;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.utils.Network;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView);
        Button btn = findViewById(R.id.logout);
        Button btn2 = findViewById(R.id.holidays);

        UserContext.verifyUserConnection(this);
        User user = UserContext.getUser(this);

        tv.setText(String.format("Hi %s! Your email is %s and your role is %s.", user.getName(), user.getEmail(), user.getRole()));

        btn.setOnClickListener(view -> {
            LoginEvent.logout(MainActivity.this);
            finish();
        });

        btn2.setOnClickListener(view -> {
            if (!Network.isNetworkConnected(this, true)) return;

            UserContext.verifyUserConnection(this, null, null, () -> {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            });
        });
    }
}