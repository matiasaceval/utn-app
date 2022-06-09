package ar.edu.utn.mdp.utnapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ar.edu.utn.mdp.utnapp.calendar.CalendarView;
import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.events.calendar.CalendarEventAdapter;
import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.Activity;
import ar.edu.utn.mdp.utnapp.fetch.models.CalendarSchema;
import ar.edu.utn.mdp.utnapp.fetch.models.Holiday;
import ar.edu.utn.mdp.utnapp.fetch.models.Subject;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.calendar.CalendarModel;
import ar.edu.utn.mdp.utnapp.fetch.request.commission.CommissionModel;
import ar.edu.utn.mdp.utnapp.user.UserContext;

public class CalendarFragment extends Fragment {

    View view;
    private final HashSet<CalendarSchema> events = new HashSet<>();
    private RecyclerView eventsRV;

    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        User user = UserContext.getUser(view.getContext());

        eventsRV = view.findViewById(R.id.calendar_recycler_view);
        eventsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        CalendarView cv = view.findViewById(R.id.calendar_view);
        cv.setEventHandler(date -> {
            List<CalendarSchema> list = getEventsFromDate(date);
            clearAdapter();
            setAdapter(list);
        });

        LinearProgressIndicator progressIndicator = view.findViewById(R.id.calendar_progress_indicator);
        CalendarModel.getHoliday(view.getContext(), "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                progressIndicator.setVisibility(View.GONE);
                events.addAll(Holiday.parse(response));
                cv.addEvents(events);
            }

            @Override
            public void onError(int statusCode) {
                progressIndicator.setVisibility(View.GONE);
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        CalendarModel.getActivity(view.getContext(), "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Activity.parse(response));
                cv.addEvents(events);
            }

            @Override
            public void onError(int statusCode) {
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        CommissionModel.getSubjectsByCommission(view.getContext(), 5, 1, new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Subject.toCalendarSchemaList(Subject.parse(response)));
                cv.addEvents(events);
            }

            @Override
            public void onError(int statusCode) {
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        return view;
    }

    private void clearAdapter() {
        eventsRV.setAdapter(null);
    }

    private void setAdapter(List<CalendarSchema> events) {
        CalendarEventAdapter adapter = new CalendarEventAdapter(events, getContext());
        eventsRV.setAdapter(adapter);
    }

    private List<CalendarSchema> getEventsFromDate(LocalDate date) {
        List<CalendarSchema> list = new ArrayList<>();
        for (CalendarSchema e : events) {
            LocalDate start = e.getStart().toLocalDate();
            LocalDate end = e.getEnd().toLocalDate();

            if (start.equals(date)) list.add(e);
            else if (end.equals(date)) list.add(e);
            else if (betweenDates(date, start, end)) list.add(e);
        }
        return list;
    }

    private boolean betweenDates(LocalDate date, LocalDate start, LocalDate end) {
        return date.isAfter(start) && date.isBefore(end);
    }
}