package ar.edu.utn.mdp.utnapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private View view;
    private CalendarView cv;
    private RecyclerView eventsRV;
    private final HashSet<CalendarSchema> events = new HashSet<>();

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

        cv = view.findViewById(R.id.calendar_view);
        cv.setEventHandler(date -> {
            List<CalendarSchema> list = getEventsFromDate(date);
            clearAdapter();
            setAdapter(list);
        });

        // TODO: Implement an actual Observer pattern
        boolean[] done = new boolean[3];

        ProgressBar progressIndicator = view.findViewById(R.id.indeterminate_linear_indicator);
        CalendarModel.getHoliday(view.getContext(), "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Holiday.parse(response));
                done[0] = true;
                fakeObserver(done, progressIndicator);
            }

            @Override
            public void onError(int statusCode) {
                done[0] = true;
                fakeObserver(done, progressIndicator);
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        CalendarModel.getActivity(view.getContext(), "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Activity.parse(response));
                done[1] = true;
                fakeObserver(done, progressIndicator);
            }

            @Override
            public void onError(int statusCode) {
                done[1] = true;
                fakeObserver(done, progressIndicator);
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        int commission = 2;
        int year = 1;
        CommissionModel.getSubjectsByCommission(view.getContext(), year, commission, new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Subject.toCalendarSchemaList(Subject.parse(response, year, commission)));
                done[2] = true;
                fakeObserver(done, progressIndicator);
            }

            @Override
            public void onError(int statusCode) {
                done[2] = true;
                fakeObserver(done, progressIndicator);
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        return view;
    }

    private void fakeObserver(final boolean[] done, ProgressBar progressIndicator) {
        for (boolean b : done) if (!b) return;
        progressIndicator.setVisibility(View.GONE);
        cv.addEvents(events);
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