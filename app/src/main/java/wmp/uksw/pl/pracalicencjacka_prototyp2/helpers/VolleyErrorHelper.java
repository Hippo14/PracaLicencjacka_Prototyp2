package wmp.uksw.pl.pracalicencjacka_prototyp2.helpers;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;

/**
 * Created by MSI on 2016-03-14.
 */
public class VolleyErrorHelper {

    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError)
            return context.getResources().getString(R.string.timeout);
        else if (isServerProblem(error))
            return handleServerError(error, context);
        else if (isNetworkProblem(error))
            return context.getResources().getString(R.string.nointernet);

        return context.getResources().getString(R.string.generic_error);
    }

    private static String handleServerError(Object error, Context context) {
        VolleyError volleyError = (VolleyError) error;
        NetworkResponse networkResponse = ((VolleyError) error).networkResponse;

        if (networkResponse != null) {
            switch (networkResponse.statusCode) {
                case 500:
                    return "Generic Error";
                case 404:
                case 422:
                case 401:
                    try {
                        // server might return error like this { "error": "Some error occured" }
                        // Use "Gson" to parse the result
                        HashMap<String, String> result = new Gson().fromJson(new String(networkResponse.data),
                                new TypeToken<Map<String, String>>() {
                                }.getType());
                        if (result != null && result.containsKey("error"))
                            return result.get("error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Invalid request
                    return ((VolleyError) error).getMessage();
                default:
                    return context.getResources().getString(R.string.timeout);
            }
        }

        return context.getResources().getString(R.string.generic_error);
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError || error instanceof AuthFailureError);
    }

    private static boolean isNetworkProblem (Object error){
        return (error instanceof NetworkError || error instanceof NoConnectionError);
    }

}
