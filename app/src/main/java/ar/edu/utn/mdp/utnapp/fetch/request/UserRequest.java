package ar.edu.utn.mdp.utnapp.fetch.request;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.fetch.models.User;

public final class UserRequest implements IRequest {

    public static JsonObjectRequest login(User user){
        final String URL = URL_BASE + "/auth/login";
        JSONObject body = new JSONObject();
        try {
            body.put("username", user.getUsername());
            body.put("password", user.getPassword());
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, body,
        response -> {
            System.out.println(response);
        }, error -> {
            System.out.println(error);
        });

        System.out.println(jsonObjectRequest);
        return jsonObjectRequest;
    }
}

