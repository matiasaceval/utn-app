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
import ar.edu.utn.mdp.utnapp.user.UserContext;

public final class CalendarModel {

    public static void getHoliday(@NonNull Context ctx, String query, CallBackRequest<JSONArray> callBack) {
        UserContext.verifyUserConnection(ctx);

        String URL_HOLIDAY = API_URL.HOLIDAY.getURL();
        if (!query.isEmpty()) {
            URL_HOLIDAY = concatQuery(URL_HOLIDAY,query);
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
        UserContext.verifyUserConnection(ctx);

        String URL_HOLIDAY_NEXT = API_URL.HOLIDAY_NEXT.getURL();
        if (!query.isEmpty()) {
            URL_HOLIDAY_NEXT = concatQuery(URL_HOLIDAY_NEXT,query);

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
        UserContext.verifyUserConnection(ctx);

        String URL_ACTIVITY = API_URL.ACTIVITY.getURL();
        if (!query.isEmpty()) {
            URL_ACTIVITY = concatQuery(URL_ACTIVITY,query);
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
        UserContext.verifyUserConnection(ctx);

        String URL_ACTIVITY_NEXT = API_URL.ACTIVITY_NEXT.getURL();
        if (!query.isEmpty()) {
            URL_ACTIVITY_NEXT = concatQuery(URL_ACTIVITY_NEXT,query);
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

    private static String concatQuery(String url, String query) {
        return query.equals("fullYear")
                ? url.concat("?date=01/01/" + LocalDate.now().getYear())
                : url.concat("?date=" + query);
    }
}
