package ar.edu.utn.mdp.utnapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.utn.mdp.utnapp.errors.ErrorLayout;
import ar.edu.utn.mdp.utnapp.events.RegisterEvent;


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
        Map<String, TextInputEditText> credentialsMap = new HashMap<>();
        credentialsMap.put("password", password);
        credentialsMap.put("email", email);
        credentialsMap.put("name", name);
        credentialsMap.put("confirmPassword", confirmPassword);


        TextView login = findViewById(R.id.logInButton);
        ImageView backArrow = findViewById(R.id.backToLogin);
        Button register = findViewById(R.id.register);

        login.setOnClickListener(RegisterEvent.clickOnBackToLogin(this, layouts));
        backArrow.setOnClickListener(RegisterEvent.clickOnBackToLogin(this, layouts));
        register.setOnClickListener(RegisterEvent.clickOnRegisterButton(this, layouts, credentialsMap));

        name.setOnFocusChangeListener((v, hasFocus) -> ErrorLayout.clearError(layouts));
        email.setOnFocusChangeListener((v, hasFocus) -> ErrorLayout.clearError(layouts));
        password.setOnFocusChangeListener((v, hasFocus) -> ErrorLayout.clearError(layouts));
        confirmPassword.setOnFocusChangeListener((v, hasFocus) -> ErrorLayout.clearError(layouts));

    }
}