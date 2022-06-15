package ar.edu.utn.mdp.utnapp.fetch.request.user_auth.signup;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.utn.mdp.utnapp.fetch.callbacks.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.models.User;
import ar.edu.utn.mdp.utnapp.fetch.request.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONObjectRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;
import ar.edu.utn.mdp.utnapp.utils.Password;

public class RegisterModel {


    public static void registerUser(@NonNull Context ctx, User user, CallBackRequest<JSONObject> callBack) {
        final SharedPreferences userPrefs = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String URL_SIGNUP = API_URL.SIGNUP.getURL();
        JSONObject body = userRegisterBodyObject(user);

        JSONObjectRequest request = new JSONObjectRequest(Request.Method.POST, URL_SIGNUP, body,
                response -> {
                    userPrefs.edit().putString("name", user.getName()).apply();
                    userPrefs.edit().putString("email", user.getEmail()).apply();
                    userPrefs.edit().putString("role", user.getRole()).apply();
                    userPrefs.edit().putString("password", Password.encode(user.getPassword())).apply();
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    private static JSONObject userRegisterBodyObject(User user) {
        JSONObject body = new JSONObject();
        try {
            body.put("email", user.getEmail());
            body.put("name", user.getName());
            body.put("password", user.getPassword());
            body.put("role", user.getRole());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }
}
