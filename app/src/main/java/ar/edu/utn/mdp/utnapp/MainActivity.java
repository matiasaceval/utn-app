package ar.edu.utn.mdp.utnapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.user.UserContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        if (getIntent().getBooleanExtra("UPDATED", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        UserContext.verifyUserConnection(this);
        User user = UserContext.getUser(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}