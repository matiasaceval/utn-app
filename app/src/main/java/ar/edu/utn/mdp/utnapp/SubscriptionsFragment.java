package ar.edu.utn.mdp.utnapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

import ar.edu.utn.mdp.utnapp.events.subscription.SubscriptionAdapter;
import ar.edu.utn.mdp.utnapp.user.UserContext;

public class SubscriptionsFragment extends Fragment {

    private View view;
    private RecyclerView subscriptionsRV;
    public static FragmentActivity SubscriptionsFragmentActivity;

    public SubscriptionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        HashSet<String> subscriptions = UserContext.getUser(view.getContext()).getSubscription();

        FloatingActionButton editSubscriptions = view.findViewById(R.id.subscription_modify_subscriptions);
        editSubscriptions.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), SubscriptionActivity.class);
            intent.putStringArrayListExtra("subscriptions", new ArrayList<>(subscriptions));
            startActivity(intent);
            SubscriptionsFragmentActivity = getActivity();
        });

        subscriptionsRV = view.findViewById(R.id.subscription_recycler_view);
        subscriptionsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        clearAdapter();
        setAdapter(subscriptions);

        return view;
    }

    private void clearAdapter() {
        subscriptionsRV.setAdapter(null);
    }

    private void setAdapter(HashSet<String> events) {
        SubscriptionAdapter adapter = new SubscriptionAdapter(events);
        subscriptionsRV.setAdapter(adapter);
    }
}