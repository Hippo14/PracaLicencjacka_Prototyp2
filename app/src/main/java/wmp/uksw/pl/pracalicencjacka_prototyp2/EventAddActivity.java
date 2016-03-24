package wmp.uksw.pl.pracalicencjacka_prototyp2;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wmp.uksw.pl.pracalicencjacka_prototyp2.adapter.SearchAdapter;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.Validate;

public class EventAddActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private EditText inputLocation;

    private Marker marker;

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        inputLocation = (EditText) findViewById(R.id.etLocation);

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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()), 12.0f));
            }
        }
    }

}
