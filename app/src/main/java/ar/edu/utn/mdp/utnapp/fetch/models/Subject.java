package ar.edu.utn.mdp.utnapp.fetch.models;

import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ar.edu.utn.mdp.utnapp.R;

public final class Subject {
    private final String subject;
    private final String zoom;
    private final String code;
    private final Map<String, String> teacher;
    private final Map<String, String> timetable;
    private final Map<String, LocalDate> exam;
    private final Map<String, LocalDate> makeupExam;
    private final ArrayList<Map<String, String>> extra;

    public Subject(String subject, String zoom, String code, Map<String, String> teacher, Map<String, String> timetable, Map<String, LocalDate> exam, Map<String, LocalDate> makeupExam, ArrayList<Map<String, String>> extra) {
        this.subject = subject;
        this.zoom = zoom;
        this.code = code;
        this.teacher = teacher;
        this.timetable = timetable;
        this.exam = exam;
        this.makeupExam = makeupExam;
        this.extra = extra;
    }

    public static List<Subject> parse(JSONArray response) {
        if (response == null) return null;

        List<Subject> list = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                Subject object = parse(response.getJSONObject(i));
                if (object != null) list.add(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static Subject parse(JSONObject response) {
        if (response == null) return null;

        try {
            return new Subject(response.getString("subject"),
                    response.getString("zoom"),
                    response.getString("code"),
                    getTeacherFromJSON(response.getJSONObject("teacher")),
                    getTimetableFromJSON(response.getJSONObject("timetable")),
                    getExamFromJSON(response.getJSONObject("exam")),
                    getExamFromJSON(response.getJSONObject("makeupExam")),
                    getExtraFromJSON(response.getJSONArray("extra")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Map<String, String>> getExtraFromJSON(JSONArray extra) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        try {
            for (int i = 0; i < extra.length(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("name", extra.getJSONObject(i).getString("name"));
                map.put("date", extra.getJSONObject(i).getString("date"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Map<String, String> getTimetableFromJSON(JSONObject timetable) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("monday", timetable.getString("monday"));
            map.put("tuesday", timetable.getString("tuesday"));
            map.put("wednesday", timetable.getString("wednesday"));
            map.put("thursday", timetable.getString("thursday"));
            map.put("friday", timetable.getString("friday"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static Map<String, LocalDate> getExamFromJSON(JSONObject exam) {
        Map<String, LocalDate> map = new HashMap<>();
        try {
            map.put("first", LocalDate.parse(exam.getString("first")));
            map.put("second", LocalDate.parse(exam.getString("second")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static Map<String, String> getTeacherFromJSON(JSONObject teacher) {
        if (teacher == null) return null;

        Map<String, String> map = new HashMap<>();
        try {
            map.put("name", teacher.getString("name"));
            map.put("email", teacher.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<CalendarSchema> toCalendarSchemaList(List<Subject> subjects) {
        List<CalendarSchema> list = new ArrayList<>();
        for (Subject subject : subjects) {
            list.addAll(subject.getActivities());
        }
        return list;
    }

    public List<CalendarSchema> getActivities() {
        List<CalendarSchema> list = new ArrayList<>();
        Resources resources = Resources.getSystem();
        String first = resources.getString(R.string.subject_prefix_first);
        String second = resources.getString(R.string.subject_prefix_second);

        Map<String, LocalDate> exam = this.getExam();
        if (exam != null) {
            String title = resources.getString(R.string.subject_title_exam) + "(" + this.getSubject() + ")";
            list.add(new CalendarSchema(first + " " + title, Objects.requireNonNull(exam.get("first")).atStartOfDay(), CalendarSchema.Type.EXAM));
            list.add(new CalendarSchema(second + " " + title, Objects.requireNonNull(exam.get("second")).atStartOfDay(), CalendarSchema.Type.EXAM));
        }

        Map<String, LocalDate> makeup = this.getMakeupExam();
        if (makeup != null) {
            String title = resources.getString(R.string.subject_title_makeup_exam) + "(" + this.getSubject() + ")";
            list.add(new CalendarSchema(first + " " + title, Objects.requireNonNull(makeup.get("first")).atStartOfDay(), CalendarSchema.Type.MAKEUP_EXAM));
            list.add(new CalendarSchema(second + " " + title, Objects.requireNonNull(makeup.get("second")).atStartOfDay(), CalendarSchema.Type.MAKEUP_EXAM));
        }

        ArrayList<Map<String, String>> extra = this.getExtra();
        if (extra != null) {
            for (Map<String, String> map : extra) {
                String name = map.get("name");
                LocalDate date = LocalDate.parse(map.get("date"));
                list.add(new CalendarSchema(name, date.atStartOfDay(), CalendarSchema.Type.EXTRA));
            }
        }

        return list;
    }

    public String getSubject() {
        return subject;
    }

    public String getZoom() {
        return zoom;
    }

    public String getCode() {
        return code;
    }

    public Map<String, String> getTeacher() {
        return teacher;
    }

    public Map<String, String> getTimetable() {
        return timetable;
    }

    public Map<String, LocalDate> getExam() {
        return exam;
    }

    public Map<String, LocalDate> getMakeupExam() {
        return makeupExam;
    }

    public ArrayList<Map<String, String>> getExtra() {
        return extra;
    }
}