package ar.edu.utn.mdp.utnapp.fetch.request;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginConnection;
import ar.edu.utn.mdp.utnapp.fetch.request.user_auth.login.LoginModel;


public class JSONObjectRequest extends JsonObjectRequest {

    public JSONObjectRequest(int method, String url, JSONObject body, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, body, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Cookie", LoginConnection.getCookie());
        return headers;
    }
}
