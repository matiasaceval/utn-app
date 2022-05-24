package ar.edu.utn.mdp.utnapp.fetch.models;

public enum Roles {

    USER("user"),
    TEACHER("teacher"),
    ADMIN("admin");
    String name;

    Roles(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
