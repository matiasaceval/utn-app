package ar.edu.utn.mdp.utnapp;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.Roles;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.HTTP_STATUS;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup.RegisterModel;
import ar.edu.utn.mdp.utnapp.utils.Password;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        TextInputLayout nameLayout = findViewById(R.id.nameLayout);
        TextInputLayout emailLayout = findViewById(R.id.emailLayout);
        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        TextInputLayout confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        List<TextInputLayout> layouts = Arrays.asList(nameLayout, emailLayout, passwordLayout, confirmPasswordLayout);

        TextInputEditText confirmPassword = findViewById(R.id.confirmPassword);
        TextInputEditText password = findViewById(R.id.password);
        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText name = findViewById(R.id.name);
        TextView login = findViewById(R.id.logInButton);
        ImageView back = findViewById(R.id.backToLogin);
        Button register = findViewById(R.id.register);

        login.setOnClickListener(view -> {
            UserFunctions.clearError(layouts);
            onBackPressed();
        });
        back.setOnClickListener(view -> {
            UserFunctions.clearError(layouts);
            onBackPressed();
        });

        register.setOnClickListener(view -> {
            if (!UserFunctions.isNetworkConnected(this, true)) return;

            UserFunctions.clearError(layouts);
            final String nameText = Objects.requireNonNull(name.getText()).toString().trim();
            final String emailText = Objects.requireNonNull(email.getText()).toString().trim();
            final String passwordText = Objects.requireNonNull(password.getText()).toString().trim();
            User user = new User(nameText, emailText, passwordText, Roles.USER.getName());

            if (UserFunctions.existInputError(this, layouts)) return;

            if (!Password.isPasswordSecure(this, passwordText, false)) {
                UserFunctions.setError(passwordLayout, getString(R.string.password_not_secure));
                return;
            }

            if (!passwordText.equals(Objects.requireNonNull(confirmPassword.getText()).toString().trim())) {
                UserFunctions.setError(confirmPasswordLayout, getResources().getString(R.string.password_mismatch));
                return;
            }

            Dialog dialog = new ProgressDialog(this);

            RegisterModel.registerUser(this, user, new CallBackRequest<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    dialog.dismiss();
                    LoginModel.loginUser(RegisterActivity.this, user, new CallBackRequest<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            ActivityCompat.finishAffinity(RegisterActivity.this);
                        }
                    });
                }

                @Override
                public void onError(int statusCode) {
                    dialog.dismiss();
                    if (statusCode == HTTP_STATUS.CLIENT_ERROR_CONFLICT) {
                        UserFunctions.setError(emailLayout, getResources().getString(R.string.email_already_exists));
                    }
                }
            });
        });

        name.setOnFocusChangeListener((v, hasFocus) -> UserFunctions.clearError(layouts));
        email.setOnFocusChangeListener((v, hasFocus) -> UserFunctions.clearError(layouts));
        password.setOnFocusChangeListener((v, hasFocus) -> UserFunctions.clearError(layouts));
        confirmPassword.setOnFocusChangeListener((v, hasFocus) -> UserFunctions.clearError(layouts));
    }
}