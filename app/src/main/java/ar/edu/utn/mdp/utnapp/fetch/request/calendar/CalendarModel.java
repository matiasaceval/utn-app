package ar.edu.utn.mdp.utnapp.fetch.request.calendar;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONArray;

import ar.edu.utn.mdp.utnapp.fetch.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.callback_request.CallBackRequest;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;

public final class CalendarModel  {

    public static void getHoliday(@NonNull Context ctx, CallBackRequest<JSONArray> callBack) {
        final String URL_HOLIDAY = API_URL.HOLIDAY.getURL();
        System.out.println(URL_HOLIDAY);

        JSONArrayRequest request = new JSONArrayRequest(Request.Method.GET, URL_HOLIDAY, null,
                response -> {
                    if (callBack != null) callBack.onSuccess(response);
                }, error -> {
            error.printStackTrace();
            callBack.onError(error.networkResponse.statusCode);
        });
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }
}
