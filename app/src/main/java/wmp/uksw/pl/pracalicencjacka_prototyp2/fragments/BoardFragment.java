package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wmp.uksw.pl.pracalicencjacka_prototyp2.AppController;
import wmp.uksw.pl.pracalicencjacka_prototyp2.MapsActivity;
import wmp.uksw.pl.pracalicencjacka_prototyp2.R;
import wmp.uksw.pl.pracalicencjacka_prototyp2.SearchEventActivity;
import wmp.uksw.pl.pracalicencjacka_prototyp2.adapter.EventAdapter;
import wmp.uksw.pl.pracalicencjacka_prototyp2.conf.URL;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.VolleyErrorHelper;
import wmp.uksw.pl.pracalicencjacka_prototyp2.models.EventRow;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

/**
 * Created by MSI on 2016-01-14.
 */
public class BoardFragment extends Fragment {

    private ListView listView;
    private EventAdapter eventAdapter;
    private SessionManager sessionManager;
    private List<EventRow> list;

    private FloatingActionButton searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        ProfileUser profileUser = sessionManager.getProfileUser();

        // Add to board list
        addToBoard();
    }

    private void addToBoard() {
        list = new ArrayList<>();
//        list.add(new EventRow("", "Android ID", Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID)));
//        list.add(new EventRow("", "Event name", "Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description "));
//        list.add(new EventRow("", "Event name", "Event description"));
        eventAdapter = new EventAdapter(getActivity().getApplicationContext(), list);

        getAddedEvents(Double.parseDouble("52.2296756"), Double.parseDouble("21.0122287"), new VolleyCallback() {
            @Override
            public void onSuccess() {

                eventAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getAddedEvents(final Double latitude, final Double longitude, final VolleyCallback callback) {
        String tag_string_req = "req_getAddedEvents";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL.URL_GET_ADDEDEVENTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONArray events = jObj.getJSONArray("events");

                        for (int i = 0; i < events.length(); i++) {
                            list.add(new EventRow(R.mipmap.ic_board_user, events.getJSONObject(i).getString("username"), "Event added at " + events.getJSONObject(i).getString("title")));
                        }

                        callback.onSuccess();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(getView().findViewById(R.id.boardFragment),
                                errorMsg, Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Snackbar.make(getView().findViewById(R.id.boardFragment), "Json error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView().findViewById(R.id.boardFragment),
                        VolleyErrorHelper.getMessage(error, getContext()), Snackbar.LENGTH_LONG).show();
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        searchButton = (FloatingActionButton) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchEventActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        listView = (ListView) view.findViewById(android.R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setAdapter(eventAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public interface VolleyCallback {
        void onSuccess();
    }

}
