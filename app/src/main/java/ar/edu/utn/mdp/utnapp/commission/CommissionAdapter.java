package ar.edu.utn.mdp.utnapp.commission;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.utn.mdp.utnapp.R;

public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.CommissionViewHolder> {

    List<Subject> subscriptionList;
    List<Commission> commissionList;

    public CommissionAdapter(List<Commission> commissionList, List<Subject> subscriptionList) {
        this.commissionList = commissionList;
        this.subscriptionList = subscriptionList;
    }

    @NonNull
    @Override
    public CommissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_commission, parent, false);
        return new CommissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommissionViewHolder holder, int position) {

        Commission commission = commissionList.get(position);
        String commissionId = "Comision X";
        String year = "Año X";
        holder.commissionId.setText(commissionId.replace("X", String.valueOf(commission.getId())));
        holder.year.setText(year.replace("X", String.valueOf(commission.getYear())));

        SubjectAdapter subjectAdapter = new SubjectAdapter(commission, subscriptionList);

        holder.subjectsRecyclerView.setAdapter(subjectAdapter);
        holder.subjectsRecyclerView.setHasFixedSize(true);

        boolean isExpandable = commissionList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return commissionList.size();
    }

    public class CommissionViewHolder extends RecyclerView.ViewHolder {

        TextView commissionId, year;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        RecyclerView subjectsRecyclerView;


        public CommissionViewHolder(@NonNull View parent) {
            super(parent);

            commissionId = parent.findViewById(R.id.commission_name);
            year = parent.findViewById(R.id.year_details);
            subjectsRecyclerView = parent.findViewById(R.id.recycler_child_view);
            linearLayout = parent.findViewById(R.id.linear_layout);
            expandableLayout = parent.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(v -> {
                Commission commission = commissionList.get(getAdapterPosition());
                commission.setExpandable(!commission.isExpandable());
                notifyItemChanged(getAdapterPosition());
                System.out.println("Clicked");

            });

        }
    }
}
