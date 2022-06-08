package ar.edu.utn.mdp.utnapp.fetch.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Subject {
    private final String subject;
    private final String zoom;
    private final String code;
    private final JSONObject teacher;
    private final JSONObject timetable;
    private final JSONObject exam;
    private final JSONObject makeupExam;
    private final ArrayList<JSONObject> extra;

    public Subject(String subject, String zoom, String code, JSONObject teacher, JSONObject timetable, JSONObject exam, JSONObject makeupExam, ArrayList<JSONObject> extra) {
        this.subject = subject;
        this.zoom = zoom;
        this.code = code;
        this.teacher = teacher;
        this.timetable = timetable;
        this.exam = exam;
        this.makeupExam = makeupExam;
        this.extra = extra;
    }

    public List<CalendarSchema> getActivities() {
        List<CalendarSchema> list = new ArrayList<>();
        if (exam != null) {
            try {
                LocalDate firstDate = LocalDate.parse(exam.getString("first"));
                LocalDate secondDate = LocalDate.parse(exam.getString("second"));
                list.add(new CalendarSchema("Primer parcial", firstDate.atStartOfDay(), firstDate.atStartOfDay(), CalendarSchema.Type.EXAM));
                list.add(new CalendarSchema("Segundo parcial", secondDate.atStartOfDay(), secondDate.atStartOfDay(), CalendarSchema.Type.EXAM));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (makeupExam != null) {
            try {
                LocalDate firstDate = LocalDate.parse(makeupExam.getString("first"));
                LocalDate secondDate = LocalDate.parse(makeupExam.getString("second"));
                list.add(new CalendarSchema("Primer recuperatorio", firstDate.atStartOfDay(), firstDate.atStartOfDay(), CalendarSchema.Type.MAKEUP_EXAM));
                list.add(new CalendarSchema("Segundo recuperatorio", secondDate.atStartOfDay(), secondDate.atStartOfDay(), CalendarSchema.Type.MAKEUP_EXAM));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (extra != null) {
            for (JSONObject jsonObject : extra) {
                try {
                    String name = jsonObject.getString("name");
                    LocalDate date = LocalDate.parse(jsonObject.getString("date"));
                    list.add(new CalendarSchema(name, date.atStartOfDay(), date.atStartOfDay(), CalendarSchema.Type.EXTRA));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
}