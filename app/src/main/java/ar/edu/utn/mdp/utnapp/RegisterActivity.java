package ar.edu.utn.mdp.utnapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        LinearLayout passwordLinearLayout = findViewById(R.id.passwordLinearLayout);
        LinearLayout confirmPasswordLinearLayout = findViewById(R.id.confirmPasswordLinearLayout);

        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmPassword);

        ImageView back = findViewById(R.id.backToLogin);
        ImageView seePassword = findViewById(R.id.viewPassword);
        ImageView seeConfirmPassword = findViewById(R.id.viewConfirmPassword);

        Button register = findViewById(R.id.register);

        TextView login = findViewById(R.id.logInButton);

        login.setOnClickListener(view -> onBackPressed());

        back.setOnClickListener(view -> onBackPressed());

        seePassword.setOnClickListener(view -> UserFunctions.showPassword(password, seePassword));

        seeConfirmPassword.setOnClickListener(view -> UserFunctions.showPassword(confirmPassword, seeConfirmPassword));

        password.setOnFocusChangeListener((view, bool) -> UserFunctions.focusLinearLayout(passwordLinearLayout, bool));

        confirmPassword.setOnFocusChangeListener((view, bool) -> UserFunctions.focusLinearLayout(confirmPasswordLinearLayout, bool));

        register.setOnClickListener(view -> { /* TODO: Validations and registration itself */ });
    }
}