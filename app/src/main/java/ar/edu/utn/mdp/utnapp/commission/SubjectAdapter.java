package ar.edu.utn.mdp.utnapp.commission;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import ar.edu.utn.mdp.utnapp.R;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    List<String> subjectsList;

    public SubjectAdapter(List<String> subjectsList) {
        this.subjectsList = subjectsList;
    }

    @NonNull
    @Override
    public SubjectAdapter.SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.SubjectViewHolder holder, int position) {
        String subject = subjectsList.get(position);
        holder.subject.setText(subject);
    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView subject;

        public SubjectViewHolder(@NonNull View parent) {
            super(parent);
            subject = parent.findViewById(R.id.subject_name);
        }
    }

}