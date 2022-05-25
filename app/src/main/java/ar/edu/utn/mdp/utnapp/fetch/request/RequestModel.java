package ar.edu.utn.mdp.utnapp.fetch.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.net.HttpURLConnection;

import ar.edu.utn.mdp.utnapp.fetch.models.User;

public abstract class RequestModel implements IRequest {

    private static SharedPreferences userPrefs;
    private static SharedPreferences cookiePrefs;

    /*return false: go to Login, return true: logged successfully*/
    public static int verifyAccountIntegration(Context ctx){
        System.out.println("HOLAKEVIN");

        userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        cookiePrefs = ctx.getSharedPreferences("Cookies", Context.MODE_PRIVATE);

        final String username = userPrefs.getString("username", "null");
        final String password = userPrefs.getString("password", "null");
        final User user = new User(username, password);
        final String cookieBody = cookiePrefs.getString("access_token", "null");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("CookieBody: " + cookieBody);
        System.out.println("User: " + user);

        if(cookieBody.equals("null") || !user.canLogin()){
            return HttpURLConnection.HTTP_CONFLICT;
        }

        String[] chunks = cookieBody.split("=")[1].split("\\.");

        try{
            JSONObject payload = new JSONObject(decode(chunks[1]));
            System.out.println("Payload: "+payload);
            if(payload.getLong("exp") > (System.currentTimeMillis() / 1000)) {
                return HttpURLConnection.HTTP_OK;
            }
            return loginUser(ctx, user);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return -1;
    }

    public static int loginUser(Context ctx, User user){
        final String URL = URL_BASE + "/auth/login";
        JSONObject body = new JSONObject();
        try {
            UserRequest request = new UserRequest(Request.Method.POST, URL, body,
            response -> {
                try {
                    userPrefs.edit().putString("name", response.getString("name")).apply();
                    userPrefs.edit().putString("username", response.getString("username")).apply();
                    userPrefs.edit().putString("role", response.getString("role")).apply();
                    userPrefs.edit().putString("password", user.getPassword()).apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                error.printStackTrace();
            });
            request.setShouldCache(false);
            RequestSingleton.getInstance(ctx).addToRequestQueue(request);
            cookiePrefs.edit().putString("access_token", request.getCookie()).apply();
            return request.getStatusCode();
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpURLConnection.HTTP_INTERNAL_ERROR;
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
}
