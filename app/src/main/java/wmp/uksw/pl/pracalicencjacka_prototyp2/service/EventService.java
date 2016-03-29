package wmp.uksw.pl.pracalicencjacka_prototyp2.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wmp.uksw.pl.pracalicencjacka_prototyp2.AppController;
import wmp.uksw.pl.pracalicencjacka_prototyp2.MenuActivity;
import wmp.uksw.pl.pracalicencjacka_prototyp2.R;
import wmp.uksw.pl.pracalicencjacka_prototyp2.conf.URL;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.VolleyErrorHelper;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

/**
 * Created by KMacioszek on 2016-03-22.
 */
public class EventService extends IntentService {

    private SessionManager sessionManager;

    public EventService() {
        super("Default constructor");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public EventService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sessionManager = new SessionManager(getApplicationContext());
        LatLng location = sessionManager.getLatLnt();
        // Handle event action
        getEvents(location.latitude, location.longitude);
        Log.d("EventService", "Service working");
    }

    private void getEvents(final double latitude, final double longitude) {
        String tag_string_req = "req_getEvents";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL.URL_GET_EVENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
//                        Snackbar.make(findViewById(R.id.layoutRegister),
//                                errorMsg, Snackbar.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    Snackbar.make(findViewById(R.id.layoutRegister), "Json error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Snackbar.make(findViewById(R.id.layoutRegister),
//                        VolleyErrorHelper.getMessage(error, getApplicationContext()), Snackbar.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), VolleyErrorHelper.getMessage(error, getApplicationContext()), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude", Double.toString(latitude));
                params.put("longitude", Double.toString(longitude));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
