package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.receiver.EventAlarmReceiver;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sessionManager = new SessionManager(getApplicationContext());

        scheduleAlarm();
    }

    private void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), EventAlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, EventAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMilis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMilis, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), EventAlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, EventAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
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

        LatLng location = sessionManager.getLatLnt();

        if (location == null) {
            // Add a marker in Sydney and move the camera
            location = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
        else {
            mMap.addMarker(new MarkerOptions().position(location).title("Marker Bitches!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

            }
        });

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng latLng = cameraPosition.target;
                Log.d("Camera", "(" + latLng.latitude + " ," + latLng.longitude + ")");
            }
        });
    }

}
