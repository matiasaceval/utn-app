package ar.edu.utn.mdp.utnapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.net.HttpURLConnection;

import ar.edu.utn.mdp.utnapp.fetch.request.RequestModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.hello);

        try {
            if(RequestModel.verifyAccountIntegration(MainActivity.this) == HttpURLConnection.HTTP_CONFLICT){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            tv.setText("Entro al try");
        } catch (Exception e) {
            e.printStackTrace();
            tv.setText("Entro al catch");
        }
        //sino seguimo bla bla
    }
}