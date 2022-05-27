package ar.edu.utn.mdp.utnapp.fetch.models;

public final class User {
    private String name = "null";
    private String email = "null";
    private String password = "null";
    private String role = "user";

    public User() {
    }

    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public User(String name, String email, String role) {
        setName(name);
        setEmail(email);
        setRole(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean canLogin(){
        return !email.equals("null") && !password.equals("null");
    }
}
