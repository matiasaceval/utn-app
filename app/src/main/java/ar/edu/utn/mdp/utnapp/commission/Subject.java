package ar.edu.utn.mdp.utnapp.commission;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashSet;
import java.util.Objects;

public class Subject {
    private String name;
    private int id;
    private int year;
    private boolean subscribed;

    public Subject(String name, int id, int year) {
        this.name = name;
        this.id = id;
        this.year = year;
    }

    public static HashSet<String> parse (JSONArray response){
        HashSet<String> subjects = new HashSet<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                subjects.add((String) response.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id && year == subject.year && Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, year);
    }

    @NonNull
    @Override
    public String toString() {
        return year + "-com" + id + "-" + name;
    }
}
