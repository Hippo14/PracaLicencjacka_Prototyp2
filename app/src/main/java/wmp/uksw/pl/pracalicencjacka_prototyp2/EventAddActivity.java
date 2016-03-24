package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wmp.uksw.pl.pracalicencjacka_prototyp2.adapter.SearchAdapter;
import wmp.uksw.pl.pracalicencjacka_prototyp2.conf.URL;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.Validate;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.VolleyErrorHelper;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

public class EventAddActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private EditText inputLocation;
    private EditText inputDescription;

    private Marker marker;

    private Button btnAddEvent;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        marker = null;
        progressDialog = new ProgressDialog(getApplicationContext());

        btnAddEvent = (Button) findViewById(R.id.btnAddEvent);

        inputLocation = (EditText) findViewById(R.id.etLocation);
        inputDescription = (EditText) findViewById(R.id.etDescription);

        inputLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String location = inputLocation.getText().toString();
                    if (location != null && !location.equals(""))
                        new GeoCoderTask().execute(location);
                }
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker != null && marker.getTitle() != null && inputDescription.getText() != null)
                    addEvent(marker.getTitle(), inputDescription.getText().toString().trim(), marker.getPosition().latitude, marker.getPosition().longitude);
            }
        });

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if (marker != null)
                            marker.remove();

                        // Set up temporary marker for user
                        final MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(latLng.latitude, latLng.longitude));

                        Geocoder geoCoder = new Geocoder(getBaseContext());
                        Address address = null;
                        try {
                            address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        options.title(address.getAddressLine(0));
                        marker = mMap.addMarker(options);
                        marker.showInfoWindow();
                        // Set camera to temporary marker
                        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(latLng, mMap.getCameraPosition().zoom);
                        mMap.animateCamera(center);
                    }
                });
            }
        });


    }

    private void addEvent(final String name, final String description, final Double latitude, final Double longitude) {
        String tag_string_req = "req_addEvent";

        final SessionManager sessionManager = new SessionManager(getApplicationContext());

        progressDialog.setMessage("Adding event...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL.URL_ADD_EVENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        //TODO ADD EVENT ????

                        // Launch main activity
                        Intent intent = new Intent(EventAddActivity.this,
                                MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(findViewById(R.id.relativeLayoutAddEvent),
                                errorMsg, Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.relativeLayoutAddEvent), "Json error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.relativeLayoutAddEvent),
                        VolleyErrorHelper.getMessage(error, getApplicationContext()), Snackbar.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("description", description);
                params.put("latitude", latitude.toString());
                params.put("longitude", longitude.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private class GeoCoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = new ArrayList<>();

            try {
                addresses = geocoder.getFromLocationName(params[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses == null || addresses.size() == 0) {
                Snackbar.make(findViewById(R.id.relativeLayoutAddEvent), "No location found", Snackbar.LENGTH_LONG).show();
            } else {
                LatLng location = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                if (marker != null)
                    marker.remove();
                marker = mMap.addMarker(new MarkerOptions().position(location).title(addresses.get(0).getAddressLine(0)));
                marker.showInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()), 12.0f));
            }
        }
    }

}
