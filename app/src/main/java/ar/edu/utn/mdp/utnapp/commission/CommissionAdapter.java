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

    List<Commission> commissionList;

    public CommissionAdapter(List<Commission> commissionList) {
        this.commissionList = commissionList;
    }

    @NonNull
    @Override
    public CommissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new CommissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommissionViewHolder holder, int position) {

        Commission commission = commissionList.get(position);

        holder.commissionId.setText("Comision " + commission.getId());
        holder.year.setText("Año: " + commission.getYear());

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



        public CommissionViewHolder(@NonNull View parent) {
            super(parent);

            commissionId = parent.findViewById(R.id.commission_name);
            year = parent.findViewById(R.id.year_details);

            linearLayout = parent.findViewById(R.id.linear_layout);
            expandableLayout = parent.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Commission commission = commissionList.get(getAdapterPosition());
                    commission.setExpandable(!commission.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
