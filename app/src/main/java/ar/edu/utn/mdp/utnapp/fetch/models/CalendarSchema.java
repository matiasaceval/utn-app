package ar.edu.utn.mdp.utnapp.fetch.models;

import java.time.LocalDateTime;

public class CalendarSchema {
    public enum Type {ACTIVITY, HOLIDAY, EXAM, MAKEUP_EXAM, EXTRA}

    private final String activity;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Type type;

    public CalendarSchema(String activity, LocalDateTime start, LocalDateTime end, Type type) {
        this.activity = activity;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public String getActivity() {
        return activity;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Type getType() {
        return type;
    }
}
