package ar.edu.utn.mdp.utnapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
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

        commissionList.add(new Commission(1, 1));
        commissionList.add(new Commission(2, 1));
        commissionList.add(new Commission(3, 1));
        commissionList.add(new Commission(4, 1));
        commissionList.add(new Commission(5, 1));
        commissionList.add(new Commission(6, 1));
        commissionList.add(new Commission(7, 1));
        commissionList.add(new Commission(8, 1));
        commissionList.add(new Commission(9, 1));
        commissionList.add(new Commission(10, 1));

        commissionList.add(new Commission(1, 2));
        commissionList.add(new Commission(2, 2));
        commissionList.add(new Commission(3, 2));
        commissionList.add(new Commission(4, 2));
        commissionList.add(new Commission(5, 2));
    }
}