package ar.edu.utn.mdp.utnapp.fetch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Activity extends CalendarSchema {

    public Activity(String activity, LocalDateTime start, LocalDateTime end) {
        super(activity, start, end, Type.ACTIVITY);
    }

    public static List<Activity> parse(JSONArray response) {
        if (response == null) return null;

        List<Activity> list = new ArrayList<>();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject object = response.getJSONObject(i);
                list.add(new Activity(object.getString("activity"),
                        LocalDateTime.parse(object.getString("start"), pattern),
                        LocalDateTime.parse(object.getString("end"), pattern)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}