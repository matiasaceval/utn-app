package ar.edu.utn.mdp.utnapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.edu.utn.mdp.utnapp.commission.Commission;
import ar.edu.utn.mdp.utnapp.commission.CommissionAdapter;

public class SubscriptionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Commission> commissionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        recyclerView = findViewById(R.id.recycler_view);

        populateCommissionList();
        setRecyclerView();


    }

    private void setRecyclerView() {
        CommissionAdapter adapter = new CommissionAdapter(commissionList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void populateCommissionList() {
        commissionList = new ArrayList<>();

        final String[] FIRST_YEAR_SUBJECTS = {"Matematica I", "Programacion I", "Laboratorio I", "Sistema de Procesamiento de Datos", "Ingles I"};
        final String[] SECOND_YEAR_SUBJECTS = {"Elementos de Investigacion Operativa", "Programacion III", "Laboratorio III", "Organizacion Contable de la Empresa", "Organizacion empresarial"};
        List<String> firstYearSubjectsList = new ArrayList<>(Arrays.asList(FIRST_YEAR_SUBJECTS));
        List<String> secondYearSubjectsList = new ArrayList<>(Arrays.asList(SECOND_YEAR_SUBJECTS));
        firstYearSubjectsList.sort(String::compareTo);
        secondYearSubjectsList.sort(String::compareTo);
        commissionList.add(new Commission(1, 1, firstYearSubjectsList));
        commissionList.add(new Commission(2, 1, firstYearSubjectsList));
        commissionList.add(new Commission(3, 1, firstYearSubjectsList));
        commissionList.add(new Commission(4, 1, firstYearSubjectsList));
        commissionList.add(new Commission(5, 1, firstYearSubjectsList));
        commissionList.add(new Commission(6, 1, firstYearSubjectsList));
        commissionList.add(new Commission(7, 1, firstYearSubjectsList));
        commissionList.add(new Commission(8, 1, firstYearSubjectsList));
        commissionList.add(new Commission(9, 1, firstYearSubjectsList));
        commissionList.add(new Commission(10, 1, firstYearSubjectsList));

        commissionList.add(new Commission(1, 2, secondYearSubjectsList));
        commissionList.add(new Commission(2, 2, secondYearSubjectsList));
        commissionList.add(new Commission(3, 2, secondYearSubjectsList));
        commissionList.add(new Commission(4, 2, secondYearSubjectsList));
        commissionList.add(new Commission(5, 2, secondYearSubjectsList));
    }
}