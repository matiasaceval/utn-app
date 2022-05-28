package ar.edu.utn.mdp.utnapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.IRequestCallBack;
import ar.edu.utn.mdp.utnapp.fetch.request.user.UserModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        LinearLayout passwordLinearLayout = findViewById(R.id.passwordLinearLayout);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        Button login = findViewById(R.id.login);

        TextView signUpButton = findViewById(R.id.signUpButton);

        ImageView seePassword = findViewById(R.id.viewPassword);

        login.setOnClickListener(view -> {
            final String mail = email.getText().toString().trim();
            final String pass = password.getText().toString().trim();
            final User usr = new User(mail, pass);

            if (mail.isEmpty()) {
                email.setError("Email is required");
                email.requestFocus();
                return;
            }

            if (pass.isEmpty()) {
                password.setError("Password is required");
                password.requestFocus();
                return;
            }

            if (!UserFunctions.isValidEmail(mail)) {
                Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_LONG).show();
                return;
            }

            // TODO: Make our own progress dialog
            ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Processing...");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();

            UserModel.loginUser(LoginActivity.this, usr, new IRequestCallBack() {
                @Override
                public void onSuccess(JSONObject response) {
                    pd.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(int statusCode) {
                    pd.dismiss();
                    switch (statusCode) {
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            ErrorDialog.show(LoginActivity.this, "Invalid credentials", "Email or password is wrong. Please try again.", R.drawable.ic_alert);
                            break;
                        case HttpURLConnection.HTTP_INTERNAL_ERROR:
                            ErrorDialog.show(LoginActivity.this, "Error", "Internal server error", R.drawable.ic_cloud_offline);
                            break;
                        case HttpURLConnection.HTTP_UNAVAILABLE:
                            ErrorDialog.show(LoginActivity.this, "Error", "Service unavailable. Please try again later.", R.drawable.ic_cloud_offline);
                            break;
                        default:
                            ErrorDialog.show(LoginActivity.this, "Error", "Unknown error. Please report it to the support.", R.drawable.ic_warning);
                            break;
                    }
                }
            });
        });

        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        seePassword.setOnClickListener(view -> UserFunctions.showPassword(password, seePassword));

        password.setOnFocusChangeListener((view, bool) -> UserFunctions.focusLinearLayout(passwordLinearLayout, bool));
    }
}