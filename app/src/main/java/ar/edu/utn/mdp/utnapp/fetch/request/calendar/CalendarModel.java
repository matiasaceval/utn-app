package ar.edu.utn.mdp.utnapp.fetch.request.calendar;

import android.content.Context;

import com.android.volley.Request;

import ar.edu.utn.mdp.utnapp.fetch.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.IRequestCallBack;
import ar.edu.utn.mdp.utnapp.fetch.request.RequestSingleton;

public final class CalendarModel  {

    public static void getHoliday(Context ctx, final IRequestCallBack callBack) {
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
