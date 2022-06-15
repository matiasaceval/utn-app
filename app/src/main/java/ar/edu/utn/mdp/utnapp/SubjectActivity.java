package ar.edu.utn.mdp.utnapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.Subject;
import ar.edu.utn.mdp.utnapp.fetch.request.commission.CommissionModel;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        String subjectStr = getIntent().getStringExtra("subject");
        if (subjectStr == null) {
            finish();
            return;
        }

        Subject subject = Subject.split(subjectStr);

        CommissionModel.getSubjectByCommission(this, subject, new CallBackRequest<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Subject responseSubject = Subject.parse(response);

                System.out.println(responseSubject);
                /* aca mostras subj */
            }

            @Override
            public void onError(int statusCode) {
                new ErrorDialog(
                        SubjectActivity.this,
                        "Error",
                        "Error al obtener la materia",
                        R.drawable.ic_warning,
                        v -> finish());

            }
        });
    }
}