package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.Activity;
import ar.edu.utn.mdp.utnapp.events.calendar.CalendarEventAdapter;
import ar.edu.utn.mdp.utnapp.fetch.models.CalendarSchema;
import ar.edu.utn.mdp.utnapp.fetch.models.Holiday;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.calendar.CalendarModel;
import ar.edu.utn.mdp.utnapp.user.UserContext;
import ar.edu.utn.mdp.utnapp.calendar.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    HashSet<CalendarSchema> events = new HashSet<>();
    RecyclerView eventsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        UserContext.verifyUserConnection(this);
        User user = UserContext.getUser(this);

        eventsRV = findViewById(R.id.calendar_recycler_view);
        eventsRV.setLayoutManager(new LinearLayoutManager(this));

        CalendarView cv = findViewById(R.id.calendar_view);
        cv.setEventHandler(date -> {
            List<CalendarSchema> list = getEventsFromDate(date);
            clearAdapter();
            setAdapter(list);
        });

        Dialog progress = new ProgressDialog(this);
        CalendarModel.getHoliday(CalendarActivity.this, "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                progress.dismiss();
                events.addAll(Holiday.parse(response));
                cv.addEvents(events);
            }

            @Override
            public void onError(int statusCode) {
                progress.dismiss();
                ErrorDialog.handler(statusCode, CalendarActivity.this);
            }
        });

        CalendarModel.getActivity(CalendarActivity.this, "fullYear", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Activity.parse(response));
                cv.addEvents(events);
            }

            @Override
            public void onError(int statusCode) {
                ErrorDialog.handler(statusCode, CalendarActivity.this);
            }
        });
    }

    private void clearAdapter() {
        eventsRV.setAdapter(null);
    }

    private void setAdapter(List<CalendarSchema> events) {
        CalendarEventAdapter adapter = new CalendarEventAdapter(events, this);
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