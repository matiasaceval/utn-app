package ar.edu.utn.mdp.utnapp;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ar.edu.utn.mdp.utnapp.errors.ErrorLayout;
import ar.edu.utn.mdp.utnapp.events.LoginEvent;


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
        Map<String, TextInputEditText> credentialsMap = new HashMap<>();
        credentialsMap.put("password", password);
        credentialsMap.put("email", email);

        TextView signUpButton = findViewById(R.id.signUpButton);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(LoginEvent.clickOnLoginButton(this, layouts, credentialsMap));
        signUpButton.setOnClickListener(LoginEvent.clickOnSignUpButton(LoginActivity.this, layouts));

        email.setOnFocusChangeListener((v, hasFocus) -> ErrorLayout.clearError(layouts));
        password.setOnFocusChangeListener((v, hasFocus) -> ErrorLayout.clearError(layouts));
    }
}