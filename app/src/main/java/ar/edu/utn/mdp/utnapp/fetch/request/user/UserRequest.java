package ar.edu.utn.mdp.utnapp.fetch.request.user;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class UserRequest extends JsonObjectRequest {


    public UserRequest(int method, String url, JSONObject body, Response.Listener<JSONObject> listener,
                                    Response.ErrorListener errorListener){
        super(method, url, body, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            Map<String, String> responseHeaders = response.headers;
            String accessCookie;
            if (responseHeaders != null) {
                String cookies = responseHeaders.get("Set-Cookie");
                if (cookies != null) {
                    accessCookie = cookies.split(";")[0];
                    UserModel.setCookie(accessCookie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.parseNetworkResponse(response);
    }
}

