package ar.edu.utn.mdp.utnapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import ar.edu.utn.mdp.utnapp.events.LoginEvent;

public class UserFragment extends Fragment {

    View view;

    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        Button btn = view.findViewById(R.id.logout);
        Button theme = view.findViewById(R.id.theme);

        btn.setOnClickListener(view -> {
            LoginEvent.logout(getContext());
            ActivityCompat.finishAffinity((Activity) view.getContext());
        });

        SharedPreferences prefs = view.getContext().getSharedPreferences("Theme", MODE_PRIVATE);
        boolean[] isNightModeOn = {prefs.getBoolean("isNightModeOn", true)};

        theme.setOnClickListener(view -> {
            if (isNightModeOn[0]) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                prefs.edit().putBoolean("isNightModeOn", false).apply();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                prefs.edit().putBoolean("isNightModeOn", true).apply();
            }
            isNightModeOn[0] = !isNightModeOn[0];
        });

        return view;
    }
}