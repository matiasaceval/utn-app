package ar.edu.utn.mdp.utnapp.fetch.request.calendar;

import android.content.Context;

import ar.edu.utn.mdp.utnapp.fetch.request.API_URL;
import ar.edu.utn.mdp.utnapp.fetch.request.IRequestCallBack;

public final class CalendarModel  {

    public static void getHoliday(Context ctx, final IRequestCallBack callBack) {
        final String URL_HOLIDAY = API_URL.HOLIDAY.getURL();
        System.out.println(URL_HOLIDAY);

        //JSONArrayObjectRequest request = new JSONArrayObjectRequest(Request.Method.GET, URL_HOLIDAY, null, callBack)


        /*
        HolidayRequest request = new HolidayRequest(Request.Method.GET, URL_HOLIDAY, null,
                response ->  {

                    if (callBack != null) callBack.onSuccess(response);
        },error -> {
            System.out.println(error);
        });
        */

        //RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }
}
