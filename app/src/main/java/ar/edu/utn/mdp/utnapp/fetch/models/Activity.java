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
        for (int i = 0; i < response.length(); i++) {
            try {
                Activity object = parse(response.getJSONObject(i));
                if (object != null) list.add(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static Activity parse(JSONObject response) {
        if (response == null) return null;

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        try {
            return new Activity(response.getString("activity"),
                    LocalDateTime.parse(response.getString("start"), pattern),
                    LocalDateTime.parse(response.getString("end"), pattern));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}