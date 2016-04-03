package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import wmp.uksw.pl.pracalicencjacka_prototyp2.conf.URL;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.VolleyErrorHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SessionManager sessionManager;

    private ScheduledThreadPoolExecutor scheduler = null;

    private static final int _REFRESH_INTERVAL = 60 * 1;
    private boolean isRunning = false;

    private List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d("EventService", "onCreate()");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sessionManager = new SessionManager(getApplicationContext());
        markerList = new ArrayList<>();

        //scheduleAlarm();
    }

    private void scheduleAlarm() {
//        sessionManager.setLatLng(mMap.getCameraPosition().target);
//        final MarkerOptions options = new MarkerOptions();
//        options.position(mMap.getCameraPosition().target);
//        options.title("ITS ME!");
//        mMap.addMarker(options);

        Log.d("EventService", "Start Task");
        isRunning = true;

        scheduler = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);

        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // Get location
                LatLng location = sessionManager.getLatLnt();
//                // Add marker
//                final MarkerOptions options = new MarkerOptions();
//                options.position(location);
//                options.title("ITS ME!");
//                android.os.Handler handler1 = new android.os.Handler(Looper.getMainLooper());
//                handler1.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mMap.addMarker(options).showInfoWindow();
//                    }
//                });

                // Handle event action
                getEvents(location.latitude, location.longitude);
            }
        }, 1, 15, TimeUnit.SECONDS);


//        Intent intent = new Intent(getApplicationContext(), EventAlarmReceiver.class);
//        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, EventAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        long firstMilis = System.currentTimeMillis();
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMilis, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 1000 * _REFRESH_INTERVAL, pendingIntent);
    }

    public void cancelAlarm() {
        isRunning = false;

        if (scheduler != null) {
            scheduler.shutdown();
            try {
                scheduler.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("EventService", "Shutdown Task");
        }


//        Intent intent = new Intent(getApplicationContext(), EventAlarmReceiver.class);
//        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, EventAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);

        final LatLng location = sessionManager.getLatLnt();

        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(location, 12.0f);
        mMap.animateCamera(center);

//        if (location == null) {
//            // Add a marker in Sydney and move the camera
//            location = new LatLng(-34, 151);
//            mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//        }
//        else {
//            mMap.addMarker(new MarkerOptions().position(location).title("Marker Bitches!"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//        }

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (!isRunning)
                    scheduleAlarm();

                sessionManager.setLatLng(location);

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(latLng, mMap.getCameraPosition().zoom);
                        mMap.animateCamera(center);
                        sessionManager.setLatLng(latLng);
                    }
                });

                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        sessionManager.setLatLng(cameraPosition.target);
                        LatLng latLng = cameraPosition.target;
                        Log.d("Camera", "(" + latLng.latitude + " ," + latLng.longitude + ")");
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("EventService", "onBackPressed()");

        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        cancelAlarm();
        startActivity(intent);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("EventService", "onPause()");
        //cancelAlarm();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("EventService", "onStop()");
        cancelAlarm();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("EventService", "onResume()");
        if (!isRunning)
            scheduleAlarm();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("EventService", "onStart()");
        if (!isRunning)
            scheduleAlarm();
    }

    private void getEvents(final double latitude, final double longitude) {
        String tag_string_req = "req_getEvents";

        Log.d("EventService", "Start getting events");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL.URL_GET_EVENTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Log.d("EventService", "Success...");

                        // Set marker list
                        JSONArray events = jObj.getJSONArray("events");

                        Log.d("EventService", "Clear map...");
                        mMap.clear();

                        Log.d("EventService", "Setting marker list...");
                        for(int i = 0; i < events.length(); i++) {
                            MarkerOptions options = new MarkerOptions();
                            options.title(events.getJSONObject(i).getString("name"));
                            options.position(new LatLng(events.getJSONObject(i).getDouble("latitude"), events.getJSONObject(i).getDouble("longitude")));

                            Marker marker = mMap.addMarker(options);
                            markerList.add(marker);
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(findViewById(R.id.relativeLayoutMap),
                                errorMsg, Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.relativeLayoutMap), "Json error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.relativeLayoutMap),
                        VolleyErrorHelper.getMessage(error, getApplicationContext()), Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), VolleyErrorHelper.getMessage(error, getApplicationContext()), Toast.LENGTH_LONG).show();
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

        Log.d("EventService", "End getting events");

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
