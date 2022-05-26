package ar.edu.utn.mdp.utnapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.LoginCallBack;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        Button login = findViewById(R.id.login);

        TextView signUpButton = findViewById(R.id.signUpButton);

        login.setOnClickListener(view -> {
            final String user = username.getText().toString().trim();
            final String pass = password.getText().toString().trim();
            final User usr = new User(user, pass);

            ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Processing...");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();

            RequestModel.loginUser(LoginActivity.this, usr, new LoginCallBack() {
                @Override
                public void onSuccess() {
                    pd.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(int statusCode) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        });

        signUpButton.setOnClickListener(view -> {
            /// TODO: Register verification
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}