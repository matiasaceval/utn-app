package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        TextInputLayout emailLayout = findViewById(R.id.emailLayout);
        List<TextInputLayout> layouts = Arrays.asList(emailLayout, passwordLayout);

        TextInputEditText password = findViewById(R.id.password);
        TextInputEditText email = findViewById(R.id.email);

        TextView signUpButton = findViewById(R.id.signUpButton);
        Button login = findViewById(R.id.login);


        login.setOnClickListener(view -> {
            UserFunctions.clearError(layouts);
            final String mail = Objects.requireNonNull(email.getText()).toString().trim();
            final String pass = Objects.requireNonNull(password.getText()).toString().trim();
            final User usr = new User(mail, pass);

            if (UserFunctions.existInputError(this, layouts)) return;

            Dialog progress = new ProgressDialog(this);

            LoginModel.loginUser(LoginActivity.this, usr, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    progress.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(int statusCode) {
                    progress.dismiss();
                    UserFunctions.handleErrorDialog(statusCode, LoginActivity.this);
                }
            });
        });

        signUpButton.setOnClickListener(view -> {
            UserFunctions.clearError(layouts);
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        email.setOnFocusChangeListener((v, hasFocus) -> UserFunctions.clearError(layouts));
        password.setOnFocusChangeListener((v, hasFocus) -> UserFunctions.clearError(layouts));
    }
}