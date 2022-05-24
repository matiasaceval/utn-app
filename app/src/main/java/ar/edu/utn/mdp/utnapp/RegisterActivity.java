package ar.edu.utn.mdp.utnapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView back = findViewById(R.id.backToLogin);

        TextView login = findViewById(R.id.logInButton);

        login.setOnClickListener(view -> onBackPressed());

        back.setOnClickListener(view -> onBackPressed());


    }
}