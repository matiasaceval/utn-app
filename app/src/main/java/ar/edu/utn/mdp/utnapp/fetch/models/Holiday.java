package ar.edu.utn.mdp.utnapp.fetch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Holiday extends CalendarSchema {
    private final String category;

    public Holiday(String activity, String category, LocalDateTime start, LocalDateTime end) {
        super(activity, start, end, Type.HOLIDAY);
        this.category = category;
    }

    public static List<Holiday> parse(JSONArray response) {
        if (response == null) return null;

        List<Holiday> list = new ArrayList<>();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject object = response.getJSONObject(i);
                list.add(new Holiday(object.getString("activity"),
                        object.getString("category"),
                        LocalDateTime.parse(object.getString("start"), pattern),
                        LocalDateTime.parse(object.getString("end"), pattern)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}