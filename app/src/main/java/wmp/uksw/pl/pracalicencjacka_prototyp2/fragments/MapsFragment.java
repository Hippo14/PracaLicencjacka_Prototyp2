package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;

/**
 * Created by MSI on 2015-11-24.
 */
public class MapsFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    private GoogleMap map;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        return view;
    }

}
