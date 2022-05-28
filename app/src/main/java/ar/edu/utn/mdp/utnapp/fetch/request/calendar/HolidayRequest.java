package ar.edu.utn.mdp.utnapp.fetch.request.calendar;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.edu.utn.mdp.utnapp.fetch.request.user.UserModel;


public class HolidayRequest extends JsonObjectRequest {


    public HolidayRequest(int method, String url, JSONObject body, Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener){
        super(method, url, body, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            Map<String, String> responseHeaders = response.headers;
            if (responseHeaders != null) {
                String cookies = responseHeaders.get("Cookie");

                System.out.println("holidays cookies: " + cookies);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.parseNetworkResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        System.out.println("MATIAS TE AMO ARRIBA" + UserModel.cookie);
        headers.put("Content-Type", "application/json");
        headers.put("Cookie", UserModel.cookie);
        headers.put("access_token", UserModel.cookie.split("=")[1]);
        System.out.println("MATIAS TE AMO"+ UserModel.cookie);
        return headers;
    }
}
