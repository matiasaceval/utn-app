package ar.edu.utn.mdp.utnapp.fetch.models;

import org.json.JSONObject;

import java.util.ArrayList;

public final class Subject {
    private String subject;
    private String zoom;
    private String code;
    private JSONObject teacher;
    private JSONObject timetable;
    private JSONObject exam;
    private JSONObject makeupExam;
    private ArrayList<JSONObject> extra;
}