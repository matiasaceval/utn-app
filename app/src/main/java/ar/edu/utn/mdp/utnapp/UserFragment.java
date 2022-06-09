package ar.edu.utn.mdp.utnapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        btn.setOnClickListener(view -> {
            LoginEvent.logout(getContext());
            ActivityCompat.finishAffinity((Activity) view.getContext());
        });

        return view;
    }
}