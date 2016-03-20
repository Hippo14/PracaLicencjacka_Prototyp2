package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;
import wmp.uksw.pl.pracalicencjacka_prototyp2.SearchEventActivity;
import wmp.uksw.pl.pracalicencjacka_prototyp2.adapter.EventAdapter;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
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

        list = new ArrayList<>();
        list.add(new EventRow("", "Android ID", Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID)));
        list.add(new EventRow("", "Event name", "Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description Event description "));
        list.add(new EventRow("", "Event name", "Event description"));

        eventAdapter = new EventAdapter(getActivity().getApplicationContext(), list);
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

}
