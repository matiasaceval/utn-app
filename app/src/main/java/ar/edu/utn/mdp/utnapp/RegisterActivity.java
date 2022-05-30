package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.utn.mdp.utnapp.register.Register;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        LinearLayout confirmPasswordLinearLayout = findViewById(R.id.confirmPasswordLinearLayout);
        LinearLayout passwordLinearLayout = findViewById(R.id.passwordLinearLayout);
        ImageView seeConfirmPassword = findViewById(R.id.viewConfirmPassword);
        ImageView seePassword = findViewById(R.id.viewPassword);
        ImageView back = findViewById(R.id.backToLogin);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        EditText password = findViewById(R.id.password);
        EditText email = findViewById(R.id.email);
        EditText name = findViewById(R.id.name);
        Button register = findViewById(R.id.register);
        TextView login = findViewById(R.id.logInButton);

        login.setOnClickListener(view -> onBackPressed());

        back.setOnClickListener(view -> onBackPressed());

        seePassword.setOnClickListener(view -> UserFunctions.showPassword(password, seePassword));

        seeConfirmPassword.setOnClickListener(view -> UserFunctions.showPassword(confirmPassword, seeConfirmPassword));

        password.setOnFocusChangeListener((view, bool) -> UserFunctions.focusLinearLayout(passwordLinearLayout, bool));

        confirmPassword.setOnFocusChangeListener((view, bool) -> UserFunctions.focusLinearLayout(confirmPasswordLinearLayout, bool));

        register.setOnClickListener(view -> {
                    final String nameText = name.getText().toString();
                    final String emailText = email.getText().toString();
                    final String passwordText = password.getText().toString();
                    final String confirmPasswordText = confirmPassword.getText().toString();
                    Register userField = new Register(nameText, emailText, passwordText, this);
                    switch (userField.validateRegister(confirmPasswordText)) {
                        case "name":
                            name.setError("Name is required");
                            name.requestFocus();
                            break;
                        case "nameLength":
                            name.setError("Name is too long");
                            name.requestFocus();
                            break;
                        case "email":
                            email.setError("Email is required");
                            email.requestFocus();
                            break;
                        case "emailLength":
                            email.setError("Email is too long");
                            email.requestFocus();
                            break;
                        case "emailInvalid":
                            email.setError("Email is invalid");
                            email.requestFocus();
                            break;
                        case "password":
                            password.setError("Password is required");
                            password.requestFocus();
                            break;
                        case "confirmPassword":
                            confirmPassword.setError("Confirm password is required");
                            confirmPassword.requestFocus();
                            break;
                        case "passwordMismatch":
                            confirmPassword.setError("Passwords do not match");
                            confirmPassword.requestFocus();
                            break;
                        case "passwordSecurity":
                            password.setError("Password must be 8-64 characters long, with a number, a lowercase letter, and an uppercase letter. It's also recommended to use a special character.");
                            password.requestFocus();
                            break;
                        default:
                            // TODO: REGISTER USER WITH REQUEST /AUTH/SIGNUP
                            Dialog dialog = new Dialog(this);
                            dialog.setContentView(R.layout.dialog_error);
                            dialog.show();
                            break;
                    }

                }
        );
    }
}