package ar.edu.utn.mdp.utnapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.mdp.utnapp.commission.Commission;
import ar.edu.utn.mdp.utnapp.commission.CommissionAdapter;
import ar.edu.utn.mdp.utnapp.commission.Subscription;
import ar.edu.utn.mdp.utnapp.events.SubscriptionEvent;
import ar.edu.utn.mdp.utnapp.fetch.models.Subject;

public class SubscriptionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Commission> commissionList;
    List<Subscription> subscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.subscriptionList = new ArrayList<>(50);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        if (getSupportActionBar() != null) getSupportActionBar().hide();


        recyclerView = findViewById(R.id.recycler_view);
        Button submit = findViewById(R.id.submit_subscription);

        boolean update = false;
        List<String> previousList = getIntent().getStringArrayListExtra("subscriptions");
        if (previousList != null) {
            update = true;
            subscriptionList.clear();
            submit.setText(this.getResources().getString(R.string.subscription_button_text_update));

            for (String s : previousList) {
                Subject subject = Subject.split(s);
                this.subscriptionList.add(new Subscription(subject.getSubject(), subject.getCommission(), subject.getYear()));
            }
        }

        populateCommissionList();
        setRecyclerView();

        submit.setOnClickListener(SubscriptionEvent.clickOnSubscription(this, subscriptionList, update));


    }

    private void setRecyclerView() {
        CommissionAdapter adapter = new CommissionAdapter(commissionList, subscriptionList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void populateCommissionList() {
        commissionList = new ArrayList<>();

        final String[] FIRST_YEAR_SUBJECTS = {"Matemática", "Programación I", "Laboratorio I", "Sistema de Procesamiento de Datos", "Inglés I"};
        final String[] SECOND_YEAR_SUBJECTS = {"Elementos de Investigación Operativa", "Programación III", "Laboratorio III", "Organización Contable de la Empresa", "Organización Empresarial"};
        final int FIRST_YEAR = 1;
        final int SECOND_YEAR = 2;
        final int FIRST_YEAR_COMMISSION_SIZE = getResources().getInteger(R.integer.first_year_commission_amount);
        final int SECOND_YEAR_COMMISSION_SIZE = getResources().getInteger(R.integer.second_year_commission_amount);
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