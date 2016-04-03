package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wmp.uksw.pl.pracalicencjacka_prototyp2.adapter.SearchAdapter;
import wmp.uksw.pl.pracalicencjacka_prototyp2.template.MyActivityTemplate;

public class SearchEventActivity extends MyActivityTemplate {

    private ListView listView;
    private SearchAdapter searchAdapter;
    private List<Address> list;
    private LatLng stateLocation;
    JSONObject location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = (ListView) findViewById(android.R.id.list);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // Getting EditText
//                EditText etLocation = (EditText) findViewById(R.id.etSearch);
//                // Getting string
//                String location = etLocation.getText().toString();
//
//                if (location != null && !location.equals(""))
//                    new GeoCoderTask().execute(location);

                // Save searched location as (x, y)
                LatLng location = new LatLng(Double.parseDouble("52.2296756"), Double.parseDouble("21.0122287"));
                sessionManager.setLatLng(location);

                // Intent to GoogleMaps Activity
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        };

        // Set button listener
        btnSearch.setOnClickListener(onClickListener);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Go to MapsActivity

                // Save searched location as (x, y)
                LatLng location = new LatLng(list.get(position).getLatitude(), list.get(position).getLongitude());
                sessionManager.setLatLng(location);

                // Intent to GoogleMaps Activity
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        };

        // ListView onItemClickListener
        listView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search_event;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GeoCoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = new ArrayList<>();

//            String tag_string_req = "req_getLocation";
//
//
//            String UrlCity = "http://maps.googleapis.com/maps/api/geocode/json?address=" + params[0] + "&sensor=false";
//
//            JsonObjectRequest stateReq = new JsonObjectRequest(Request.Method.GET, UrlCity, null, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//
//                    try {
//                        // Get JSON Array called "results" and then get the 0th
//                        // complete object as JSON
//                        location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
//                        // Get the value of the attribute whose name is
//                        // "formatted_string"
//                        stateLocation = new LatLng(location.getDouble("lat"), location.getDouble("lng"));
//                    } catch (JSONException e1) {
//                        e1.printStackTrace();
//
//                    }
//                }
//
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("Error.Response", error.toString());
//                }
//            });
//            // add it to the RequestQueue
//            // Adding request to request queue
//            AppController.getInstance().addToRequestQueue(stateReq, tag_string_req);

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
                Toast.makeText(getBaseContext(), "No location found", Toast.LENGTH_SHORT).show();
            }

            list = addresses;

            // Set searchadapter
            searchAdapter = new SearchAdapter(getBaseContext(), addresses);

            // Set listview adapter
            listView.setAdapter(searchAdapter);
        }
    }


}
