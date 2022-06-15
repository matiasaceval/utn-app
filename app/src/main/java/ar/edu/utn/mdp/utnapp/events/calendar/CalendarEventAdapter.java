package ar.edu.utn.mdp.utnapp.events.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import ar.edu.utn.mdp.utnapp.R;
import ar.edu.utn.mdp.utnapp.fetch.models.CalendarSchema;

public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.ViewHolder> {

    private final List<CalendarSchema> events;
    private final Context ctx;

    public CalendarEventAdapter(List<CalendarSchema> events, Context ctx) {
        this.events = events;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CalendarSchema event = events.get(position);
        switch (event.getType()) {
            case EXAM:
                holder.type.setText(R.string.cardview_event_type_exam);
                break;
            case HOLIDAY:
                holder.type.setText(R.string.cardview_event_type_holiday);
                break;
            case ACTIVITY:
                holder.type.setText(R.string.cardview_event_type_activity);
                break;
            case MAKEUP_EXAM:
                holder.type.setText(R.string.cardview_event_type_makeup_exam);
                break;
            case EXTRA:
                if (event.getActivity().contains("Parcial")) {
                    holder.type.setText(R.string.cardview_event_type_exam);
                } else if (event.getActivity().contains("Recuperatorio")) {
                    holder.type.setText(R.string.cardview_event_type_makeup_exam);
                } else {
                    holder.type.setText(R.string.cardview_event_type_extra);
                }
                break;
        }

        holder.title.setText(event.getActivity());

        LocalDate start = event.getStart().toLocalDate();
        LocalDate end = event.getEnd().toLocalDate();
        String formattedStart = start.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        String formattedEnd = end.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

        StringBuilder text = new StringBuilder();
        if (!start.isEqual(end)) text.append(formattedStart).append("  -  ").append(formattedEnd);
        else
            text.append(formattedStart).append("  ·  ").append(ctx.getResources().getString(R.string.cardview_event_allday));

        holder.details.setText(text);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView type;
        private final TextView details;

        public ViewHolder(@NonNull View view) {
            super(view);

            details = view.findViewById(R.id.event_details);
            title = view.findViewById(R.id.event_title);
            type = view.findViewById(R.id.event_type);
        }
    }
}
