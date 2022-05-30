package ar.edu.utn.mdp.utnapp;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import java.util.HashMap;
import java.util.Map;


import ar.edu.utn.mdp.utnapp.events.RegisterEvent;
import ar.edu.utn.mdp.utnapp.fetch.models.Roles;
import ar.edu.utn.mdp.utnapp.fetch.models.User;


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
            User user = new User(nameText, emailText, passwordText, Roles.USER.getName());

            Map<String, EditText>editTextMap = new HashMap<>();
            editTextMap.put("name", name);
            editTextMap.put("email", email);
            editTextMap.put("password", password);
            editTextMap.put("confirmPassword", confirmPassword);

            // Sign up user on click
            RegisterEvent.signUp(this, user, editTextMap);
            /*
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_error);
            dialog.show();
            */
        });
    }
}