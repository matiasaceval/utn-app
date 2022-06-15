package ar.edu.utn.mdp.utnapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import ar.edu.utn.mdp.utnapp.fetch.models.Subject;

public class SubjectActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        String subjectStr = getIntent().getStringExtra("subject");
        if (subjectStr == null) {
            finish();
            return;
        }

        Subject subject;
        try {
            subject = Subject.parse(new JSONObject(subjectStr));
        } catch (JSONException e) {
            e.printStackTrace();
            finish();
            return;
        }

        TextView title = findViewById(R.id.subject_textview_subject_name);

        TextInputEditText teacher = findViewById(R.id.subject_input_teacher);
        TextInputEditText code = findViewById(R.id.subject_input_code);

        TextView monday = findViewById(R.id.subject_timetable_day_monday);
        TextView tuesday = findViewById(R.id.subject_timetable_day_tuesday);
        TextView wednesday = findViewById(R.id.subject_timetable_day_wednesday);
        TextView thursday = findViewById(R.id.subject_timetable_day_thursday);
        TextView friday = findViewById(R.id.subject_timetable_day_friday);

        TextView firstMonday = findViewById(R.id.subject_timetable_hour_monday);
        TextView firstTuesday = findViewById(R.id.subject_timetable_hour_tuesday);
        TextView firstWednesday = findViewById(R.id.subject_timetable_hour_wednesday);
        TextView firstThursday = findViewById(R.id.subject_timetable_hour_thursday);
        TextView firstFriday = findViewById(R.id.subject_timetable_hour_friday);

        TextView secondMonday = findViewById(R.id.subject_timetable_second_hour_monday);
        TextView secondTuesday = findViewById(R.id.subject_timetable_second_hour_tuesday);
        TextView secondWednesday = findViewById(R.id.subject_timetable_second_hour_wednesday);
        TextView secondThursday = findViewById(R.id.subject_timetable_second_hour_thursday);
        TextView secondFriday = findViewById(R.id.subject_timetable_second_hour_friday);

        TextInputEditText firstExam = findViewById(R.id.subject_input_exam_first);
        TextInputEditText secondExam = findViewById(R.id.subject_input_exam_second);

        TextInputEditText firstMakeupExam = findViewById(R.id.subject_input_makeup_exam_first);
        TextInputEditText secondMakeupExam = findViewById(R.id.subject_input_makeup_exam_second);

        TextInputEditText zoom = findViewById(R.id.subject_input_zoom);

        title.setText(subject.getSubject());
        teacher.setText(subject.getTeacher().get("name"));
        code.setText(subject.getCode());

        String getMonday = subject.getTimetable().get("monday");
        String getTuesday = subject.getTimetable().get("tuesday");
        String getWednesday = subject.getTimetable().get("wednesday");
        String getThursday = subject.getTimetable().get("thursday");
        String getFriday = subject.getTimetable().get("friday");

        setTimetableDay(monday, firstMonday, secondMonday, getMonday);
        setTimetableDay(tuesday, firstTuesday, secondTuesday, getTuesday);
        setTimetableDay(wednesday, firstWednesday, secondWednesday, getWednesday);
        setTimetableDay(thursday, firstThursday, secondThursday, getThursday);
        setTimetableDay(friday, firstFriday, secondFriday, getFriday);

        firstExam.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(subject.getExam().get("first")));
        secondExam.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(subject.getExam().get("second")));

        firstMakeupExam.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(subject.getMakeupExam().get("first")));
        secondMakeupExam.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(subject.getMakeupExam().get("second")));

        String getZoom = subject.getZoom();
        zoom.setText(getZoom.equals("null") ? "No disponible." : getZoom);
    }

    private void setTimetableDay(TextView day, TextView firstHour, TextView secondHour, String timetable) {
        if (timetable.equals("null")) {
            day.setAlpha(0.5f);
            firstHour.setAlpha(0.5f);
            secondHour.setAlpha(0.5f);

            firstHour.setText("—");
            secondHour.setText("—");
        } else {
            String[] timetableSplit = timetable.split("-");
            firstHour.setText(timetableSplit[0]);
            secondHour.setText(timetableSplit[1]);
        }
    }
}