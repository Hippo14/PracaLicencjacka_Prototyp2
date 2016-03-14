package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;

/**
 * Created by MSI on 2015-11-24.
 */
public class EventsFragment extends Fragment {

    FloatingActionButton FAB;

    TextView test;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_events, container, false);


        FAB = (FloatingActionButton) view.findViewById(R.id.fab_btn);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(view.findViewById(R.id.frameLayout), "Hello World!", Snackbar.LENGTH_LONG).show();

//                Snackbar.make(view.findViewById(R.id.frameLayout), "Click on row to know more details", Snackbar.LENGTH_LONG)
//                        .setAction("OK", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        }).show();
            }
        });



        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        String url ="http://lubiekokosy.pl/php/android.php";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                test.setText("Response: " + response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                test.setText(error.getMessage());
//            }
//        }
//        );
//
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        queue.add(jsonObjectRequest);



    }

}