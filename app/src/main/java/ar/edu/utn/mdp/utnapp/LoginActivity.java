package ar.edu.utn.mdp.utnapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login);

        TextView createAccount = (TextView) findViewById(R.id.createAccountButton);

        login.setOnClickListener(view -> {
            /// TODO: User verification
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        createAccount.setOnClickListener(view -> {
            /// TODO: RegisterActivity
        });
    }
}