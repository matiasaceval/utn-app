package ar.edu.utn.mdp.utnapp.fetch;

public enum API_URL {
    BASE("https://utn-api.herokuapp.com/api"),
    ACTIVITY(BASE.getURL() + "/calendar/activity"),
    HOLIDAY(BASE.getURL() + "/calendar/holiday"),
    ACTIVITY_NEXT(BASE.getURL() + "/calendar/activity/next"),
    HOLIDAY_NEXT(BASE.getURL() + "/calendar/holiday/next"),
    COMMISSION(BASE.getURL() + "/commission"),

    LOGIN(BASE.getURL() + "/auth/login"),
    SIGNUP(BASE.getURL() + "/auth/signup"),
    USER(BASE.getURL() + "/auth/user");

    private final String name;

    API_URL(String name) {
        this.name = name;
    }

    public String getURL() {
        return name;
    }
}