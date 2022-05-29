package ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class UserRegisterRequest extends JsonObjectRequest {

    public UserRegisterRequest(int method, String url, JSONObject body, Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener){
        super(method, url, body, listener, errorListener);
    }
}
