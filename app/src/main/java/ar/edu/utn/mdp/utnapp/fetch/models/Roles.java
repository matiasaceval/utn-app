package ar.edu.utn.mdp.utnapp.fetch.models;

public enum Roles {

    USER("user"),
    TEACHER("teacher"),
    ADMIN("admin");

    private final String name;
    Roles(String name){
        this.name = name;
    }

    public String getRole() {
        return name;
    }
}
