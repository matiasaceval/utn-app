package ar.edu.utn.mdp.utnapp.events.subscription;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import ar.edu.utn.mdp.utnapp.R;

public class SubscriptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    ArrayList<String> subscriptions;

    public SubscriptionAdapter(HashSet<String> subscriptions) {
        ArrayList<String> list = new ArrayList<>(subscriptions);

        list.sort(byYearComSubject());

        this.subscriptions = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_subscription, parent, false);
            return new SubscriptionAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_subscription_fragment, parent, false);
            return new SubscriptionAdapter.FooterViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SubscriptionAdapter.ViewHolder && position < subscriptions.size()) {
            final String subscription = subscriptions.get(position);
            final String[] splitted = subscription.split("-");
            final String year = splitted[0];
            final String com = splitted[1].split("com")[1];
            final String subject = splitted[2];
            String ordinal = ordinals(Integer.parseInt(year));
            ordinal = ordinal.substring(ordinal.length() - 2);

            ((ViewHolder) holder).subject.setText(subject);
            ((ViewHolder) holder).com_year.setText("Comisión " + com + "  ·  " + year + ordinal + " año");
        }
    }

    @Override
    public int getItemCount() {
        return subscriptions.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == subscriptions.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView subject;
        private final TextView com_year;

        public ViewHolder(@NonNull View view) {
            super(view);

            subject = view.findViewById(R.id.subscription_subject);
            com_year = view.findViewById(R.id.subscription_com_year);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;

        public FooterViewHolder(@NonNull View view) {
            super(view);

            icon = view.findViewById(R.id.footer_subscription_icon);
        }
    }

    @NonNull
    private Comparator<String> byYearComSubject() {
        return (o1, o2) -> {
            String[] split1 = o1.split("-");
            String[] split2 = o2.split("-");
            int year1 = Integer.parseInt(split1[0]);
            int year2 = Integer.parseInt(split2[0]);
            int commission1 = Integer.parseInt(split1[1].split("com")[1]);
            int commission2 = Integer.parseInt(split2[1].split("com")[1]);
            String subject1 = split1[2];
            String subject2 = split2[2];
            if (year1 < year2) {
                return -1;
            } else if (year1 > year2) {
                return 1;
            } else {
                if (commission1 < commission2) {
                    return -1;
                } else if (commission1 > commission2) {
                    return 1;
                } else {
                    return Integer.compare(subject1.compareTo(subject2), 0);
                }
            }
        };
    }

    private String ordinals(int num) {
        final String[] ordinals = {"Primero", "Segundo", "Tercero", "Cuarto"};
        return ordinals[num - 1];
    }
}
