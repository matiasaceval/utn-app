package ar.edu.utn.mdp.utnapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.mdp.utnapp.commission.Commission;
import ar.edu.utn.mdp.utnapp.commission.CommissionAdapter;
import ar.edu.utn.mdp.utnapp.commission.Subject;
import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;

public class SubscriptionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Commission> commissionList;
    List<Subject> subscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.subscriptionList = new ArrayList<>(50);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        recyclerView = findViewById(R.id.recycler_view);

        populateCommissionList();
        setRecyclerView();
        Button submit = findViewById(R.id.submit_subscription);

        submit.setOnClickListener(v -> {

            if (subscriptionList.size() < 1) {
                new ErrorDialog(this, "😱 Whoops!", "Parece que te has olvidado de seleccionar al menos una materia...", R.drawable.ic_alert);
            } else {
                // TODO: Implementar el envio de la suscripcion
            }

        });


    }

    private void setRecyclerView() {
        CommissionAdapter adapter = new CommissionAdapter(commissionList, subscriptionList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void populateCommissionList() {
        commissionList = new ArrayList<>();

        final String[] FIRST_YEAR_SUBJECTS = {"Matematica I", "Programacion I", "Laboratorio I", "Sistema de Procesamiento de Datos", "Ingles I"};
        final String[] SECOND_YEAR_SUBJECTS = {"Elementos de Investigacion Operativa", "Programacion III", "Laboratorio III", "Organizacion Contable de la Empresa", "Organizacion empresarial"};
        final int FIRST_YEAR = 1;
        final int SECOND_YEAR = 2;
        final int FIRST_YEAR_COMMISSION_SIZE = 10;
        final int SECOND_YEAR_COMMISSION_SIZE = 5;
        List<String> firstYearSubjectsList = new ArrayList<>(Arrays.asList(FIRST_YEAR_SUBJECTS));
        List<String> secondYearSubjectsList = new ArrayList<>(Arrays.asList(SECOND_YEAR_SUBJECTS));
        firstYearSubjectsList.sort(String::compareTo);
        secondYearSubjectsList.sort(String::compareTo);

        for (int i = 0; i < Math.max(FIRST_YEAR_COMMISSION_SIZE, SECOND_YEAR_COMMISSION_SIZE); i++) {
            if (i < FIRST_YEAR_COMMISSION_SIZE) {
                commissionList.add(new Commission(i + 1, FIRST_YEAR, firstYearSubjectsList));
            }
            if (i < SECOND_YEAR_COMMISSION_SIZE) {
                commissionList.add(new Commission(i + 1, SECOND_YEAR, secondYearSubjectsList));
            }

        }
        commissionList.sort(Comparator.comparingInt(Commission::getYear));
    }
}