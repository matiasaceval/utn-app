package ar.edu.utn.mdp.utnapp.fetch.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

import ar.edu.utn.mdp.utnapp.fetch.models.User;

public final class UserRequest implements IRequest {

    public static JsonObjectRequest login(Context ctx, User user) {
        final String URL = URL_BASE + "/auth/login";
        JSONObject body = new JSONObject();

        SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences cookiePrefs = ctx.getSharedPreferences("Cookies", Context.MODE_PRIVATE);

        try {
            body.put("username", user.getUsername());
            body.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, body,
                response -> {
                    try {
                        userPrefs.edit().putString("name", response.getString("name")).apply();
                        userPrefs.edit().putString("username", response.getString("username")).apply();
                        userPrefs.edit().putString("role", response.getString("role")).apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            error.printStackTrace();
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String accessCookie = null;
                    if (responseHeaders != null) {
                        String cookies = responseHeaders.get("Set-Cookie");
                        accessCookie = Objects.requireNonNull(cookies).split(";")[0];
                    }
                    cookiePrefs.edit().putString("access_token", accessCookie).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };

        jsonObjectRequest.setShouldCache(false);
        return jsonObjectRequest;
    }
}

