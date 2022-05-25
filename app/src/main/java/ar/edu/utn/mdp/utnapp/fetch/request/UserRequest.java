package ar.edu.utn.mdp.utnapp.fetch.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

public class UserRequest extends JsonObjectRequest {

    private int mStatusCode;
    private String cookie;

    public UserRequest(int method, String url, JSONObject body, Response.Listener<JSONObject> listener,
                                    Response.ErrorListener errorListener){
        super(method, url, body, listener, errorListener);
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public String getCookie(){
        return cookie;
    }
    
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        mStatusCode = response.statusCode;
        try {
            Map<String, String> responseHeaders = response.headers;
            String accessCookie = null;
            if (responseHeaders != null) {
                String cookies = responseHeaders.get("Set-Cookie");
                accessCookie = Objects.requireNonNull(cookies).split(";")[0];
            }
            cookie = accessCookie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.parseNetworkResponse(response);
    }
}

