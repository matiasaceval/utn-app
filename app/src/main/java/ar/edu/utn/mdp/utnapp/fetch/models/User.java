package ar.edu.utn.mdp.utnapp.fetch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public final class User {
    private String name = "null";
    private String email = "null";
    private String password = "null";
    private String role = "user";
    private HashSet<String> subscription = new HashSet<>();

    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public User(String name, String email, String role) {
        setName(name);
        setEmail(email);
        setRole(role);
    }

    public User(String name, String email, String password, String role) {
        setName(name);
        setEmail(email);
        setRole(role);
        setPassword(password);
    }

    public User(String name, String email, String role, HashSet<String> subscription) {
        setName(name);
        setEmail(email);
        setRole(role);
        setSubscription(subscription);
    }

    public void setSubscription(HashSet<String> subscription) {
        this.subscription = subscription;
    }

    public static User parse(JSONObject response) {
        if (response == null) return null;

        try {
            return new User(response.getString("name"),
                    response.getString("email"),
                    response.getString("role"),
                    getSubscriptionFromJSON(response.getJSONArray("subscription")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static HashSet<String> getSubscriptionFromJSON(JSONArray subscription){
        HashSet<String> subscriptionList = new HashSet<>();

        try {
            for (int i = 0; i < subscription.length(); i++) {
                subscriptionList.add((String) subscription.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subscriptionList;

    }

    public void addSubscription(String newSubscription){
        this.subscription.add(newSubscription);
    }

    public HashSet<String> getSubscription() {
        return subscription;
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
