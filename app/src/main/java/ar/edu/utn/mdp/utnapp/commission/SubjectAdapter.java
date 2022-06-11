package ar.edu.utn.mdp.utnapp.commission;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import ar.edu.utn.mdp.utnapp.R;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    List<Subject> subscriptionList;
    List<String> subjectsList;
    private int commissionId;
    private int commissionYear;

    public SubjectAdapter(Commission commission, List<Subject> subscriptionList) {
        this.subjectsList = commission.getSubjects();
        this.commissionId = commission.getId();
        this.commissionYear = commission.getYear();
        this.subscriptionList = subscriptionList;
    }

    @NonNull
    @Override
    public SubjectAdapter.SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_commission_child_subject, parent, false);
        return new SubjectViewHolder(view, subscriptionList);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.SubjectViewHolder holder, int position) {
        String subject = subjectsList.get(position);
        Subject cardSubject = new Subject(subject, commissionId, commissionYear);
        holder.checkBox.setChecked(subscriptionList.contains(cardSubject));
        holder.checkBox.setTag(cardSubject);
        holder.subject.setText(subject);
    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView subject;
        CheckBox checkBox;
        RelativeLayout boxLayout;
        List<Subject> subscriptionList;

        public SubjectViewHolder(@NonNull View parent, List<Subject> subscriptionList) {
            super(parent);
            this.subscriptionList = subscriptionList;
            subject = parent.findViewById(R.id.subject_name);
            checkBox = parent.findViewById(R.id.checkbox);
            boxLayout = parent.findViewById(R.id.box_layout);

            boxLayout.setOnClickListener(v -> {
                Subject subject = (Subject) checkBox.getTag();
                checkBox.setChecked(!checkBox.isChecked());
                if (checkBox.isChecked()) {
                    System.out.println("Checked");
                    subject.setSubscribed(true);
                    if (!this.subscriptionList.contains(subject)) {
                        this.subscriptionList.add(subject);
                    }
                } else {
                    System.out.println("Unchecked");
                    this.subscriptionList.remove(subject);
                    subject.setSubscribed(false);
                }
            });

        }
    }

}