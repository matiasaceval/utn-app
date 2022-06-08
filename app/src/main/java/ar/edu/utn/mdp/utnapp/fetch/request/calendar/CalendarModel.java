package ar.edu.utn.mdp.utnapp.fetch.request.calendar;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;

import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONArrayRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.JSONObjectRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;
import ar.edu.utn.mdp.utnapp.user.UserFunctions;

public final class CalendarModel {

    public static void getHoliday(@NonNull Context ctx, String query, CallBackRequest<JSONArray> callBack) {
        UserFunctions.verifyUserConnection(ctx);

        String URL_HOLIDAY = API_URL.HOLIDAY.getURL();
        if (!query.isEmpty()) {
            if (query.equals("fullYear"))
                URL_HOLIDAY = URL_HOLIDAY.concat("?date=01/01/" + LocalDate.now().getYear());
            else
                URL_HOLIDAY = URL_HOLIDAY.concat("?date=" + query);
        }

        JSONArrayRequest request = new JSONArrayRequest(Request.Method.GET, URL_HOLIDAY, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    public static void getNextHoliday(@NonNull Context ctx, String query, CallBackRequest<JSONObject> callBack) {
        UserFunctions.verifyUserConnection(ctx);

        String URL_HOLIDAY_NEXT = API_URL.HOLIDAY_NEXT.getURL();
        if (!query.isEmpty()) {
            if (query.equals("fullYear"))
                URL_HOLIDAY_NEXT = URL_HOLIDAY_NEXT.concat("?date=01/01/" + LocalDate.now().getYear());
            else
                URL_HOLIDAY_NEXT = URL_HOLIDAY_NEXT.concat("?date=" + query);
        }
        JSONObjectRequest request = new JSONObjectRequest(Request.Method.GET, URL_HOLIDAY_NEXT, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);

    }

    public static void getActivity(@NonNull Context ctx, String query, CallBackRequest<JSONArray> callBack) {
        UserFunctions.verifyUserConnection(ctx);

        String URL_ACTIVITY = API_URL.ACTIVITY.getURL();
        if (!query.isEmpty()) {
            if (query.equals("fullYear"))
                URL_ACTIVITY = URL_ACTIVITY.concat("?date=01/01/" + LocalDate.now().getYear());
            else
                URL_ACTIVITY = URL_ACTIVITY.concat("?date=" + query);
        }
        JSONArrayRequest request = new JSONArrayRequest(Request.Method.GET, URL_ACTIVITY, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    public static void getNextActivity(@NonNull Context ctx, String query, CallBackRequest<JSONObject> callBack) {
        UserFunctions.verifyUserConnection(ctx);

        String URL_ACTIVITY_NEXT = API_URL.ACTIVITY_NEXT.getURL();
        if (!query.isEmpty()) {
            if (query.equals("fullYear"))
                URL_ACTIVITY_NEXT = URL_ACTIVITY_NEXT.concat("?date=01/01/" + LocalDate.now().getYear());
            else
                URL_ACTIVITY_NEXT = URL_ACTIVITY_NEXT.concat("?date=" + query);
        }
        JSONObjectRequest request = new JSONObjectRequest(Request.Method.GET, URL_ACTIVITY_NEXT, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }
    // TODO : PUT/POST/DELETE HOLIDAY & ACTIVITY

}
