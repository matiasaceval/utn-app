package ar.edu.utn.mdp.utnapp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.util.HashSet;

import ar.edu.utn.mdp.utnapp.errors.ErrorDialog;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.Activity;
import ar.edu.utn.mdp.utnapp.fetch.models.CalendarSchema;
import ar.edu.utn.mdp.utnapp.fetch.models.Holiday;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.calendar.CalendarModel;
import ar.edu.utn.mdp.utnapp.user.UserFunctions;
import ar.edu.utn.mdp.utnapp.utils.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        UserFunctions.verifyUserConnection(this);
        User user = UserFunctions.getUser(this);

        HashSet<CalendarSchema> events = new HashSet<>();

        CalendarView cv = findViewById(R.id.calendar_view);
        cv.setEventHandler(event -> {
            System.out.println(event);
        });


        Dialog progress = new ProgressDialog(this);
        CalendarModel.getHoliday(CalendarActivity.this, "", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                progress.dismiss();
                events.addAll(Holiday.parse(response));
            }

            @Override
            public void onError(int statusCode) {
                progress.dismiss();
                ErrorDialog.handler(statusCode, CalendarActivity.this);
            }
        });

        CalendarModel.getActivity(CalendarActivity.this, "", new CallBackRequest<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                events.addAll(Activity.parse(response));
            }

            @Override
            public void onError(int statusCode) {
                ErrorDialog.handler(statusCode, CalendarActivity.this);
            }
        });

        cv.setEvents(events);
    }
}