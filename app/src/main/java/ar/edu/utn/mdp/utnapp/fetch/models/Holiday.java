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
        for (int i = 0; i < response.length(); i++) {
            try {
                Holiday object = parse(response.getJSONObject(i));
                if (object != null) list.add(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static Holiday parse(JSONObject response) {
        if (response == null) return null;

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        try {
            return new Holiday(response.getString("activity"),
                    response.getString("category"),
                    LocalDateTime.parse(response.getString("start"), pattern),
                    LocalDateTime.parse(response.getString("end"), pattern));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}