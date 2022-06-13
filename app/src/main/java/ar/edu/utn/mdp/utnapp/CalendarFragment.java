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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        HashMap<Integer, HashSet<Integer>> commissionsToFetch = getCommissionsToFetch(user.getSubscription());
        HashMap<String, Boolean> done = generateFakeObserver(commissionsToFetch);

        ProgressBar progressIndicator = view.findViewById(R.id.indeterminate_linear_indicator);
        CalendarModel.getHoliday(view.getContext(), "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Holiday.parse(response));
                done.put("holiday", true);
                fakeObserver(done, progressIndicator);
            }

            @Override
            public void onError(int statusCode) {
                done.put("holiday", true);
                fakeObserver(done, progressIndicator);
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        CalendarModel.getActivity(view.getContext(), "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Activity.parse(response));
                done.put("activity", true);
                fakeObserver(done, progressIndicator);
            }

            @Override
            public void onError(int statusCode) {
                done.put("activity", true);
                fakeObserver(done, progressIndicator);
                ErrorDialog.handler(statusCode, view.getContext());
            }
        });

        for (Map.Entry<Integer, HashSet<Integer>> entry : commissionsToFetch.entrySet()) {
            for (Integer commission : entry.getValue()) {
                CommissionModel.getSubjectsByCommission(view.getContext(), entry.getKey(), commission, new CallBackRequest<JSONArray>() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        done.put(entry.getKey() + "-" + commission, true);
                        List<Subject> subjects = Subject.parse(response, entry.getKey(), commission);
                        List<Subject> filtered = Subject.filter(subjects, user.getSubscription());
                        events.addAll(Subject.toCalendarSchemaList(filtered));
                        fakeObserver(done, progressIndicator);
                    }

                    @Override
                    public void onError(int statusCode) {
                        fakeObserver(done, progressIndicator);
                        ErrorDialog.handler(statusCode, view.getContext());
                    }
                });
            }
        }

        return view;
    }

    private HashMap<Integer, HashSet<Integer>> getCommissionsToFetch(HashSet<String> subscriptions) {
        HashMap<Integer, HashSet<Integer>> commissionsToFetch = new HashMap<>();
        for (String s : subscriptions) {
            String[] split = s.split("-");
            int year = Integer.parseInt(split[0]);
            int commission = Integer.parseInt(split[1].split("com")[1]);

            if (!commissionsToFetch.containsKey(year)) {
                commissionsToFetch.put(year, new HashSet<>());
            }

            Objects.requireNonNull(commissionsToFetch.get(year)).add(commission);
        }
        return commissionsToFetch;
    }

    private HashMap<String, Boolean> generateFakeObserver(HashMap<Integer, HashSet<Integer>> commissionsToFetch) {
        HashMap<String, Boolean> done = new HashMap<>();
        done.put("holiday", false);
        done.put("activity", false);

        for (Map.Entry<Integer, HashSet<Integer>> entry : commissionsToFetch.entrySet()) {
            for (Integer commission : entry.getValue()) {
                done.put(entry.getKey() + "-" + commission, false);
            }
        }
        return done;
    }

    private void fakeObserver(HashMap<String, Boolean> done, ProgressBar progressIndicator) {
        for (Boolean value : done.values()) if (!value) return;
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