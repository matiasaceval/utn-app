package ar.edu.utn.mdp.utnapp.fetch.models;

public final class User {
    private String name = null;
    private String username = null;
    private String password = null;
    private String role = "user";

    public User(){
    }

    public User(String username, String password){
        setUsername(username);
        setPassword(password);
    }

    public User(String name, String username, String role){
        setName(name);
        setUsername(username);
        setRole(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
