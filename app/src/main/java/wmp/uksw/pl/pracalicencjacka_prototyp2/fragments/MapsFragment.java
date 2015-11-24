package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;

/**
 * Created by MSI on 2015-11-24.
 */
public class MapsFragment extends SupportMapFragment {

    public MapsFragment() {
        super();
    }

    public static MapsFragment newInstance() {
        MapsFragment mapsFragment = new MapsFragment();
        return mapsFragment;
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        initMap();

        return v;
    }

    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getActivity(), "onDestroy();", Toast.LENGTH_SHORT).show();
    }

    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), "onPause();", Toast.LENGTH_SHORT).show();
    }

    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "onResume();", Toast.LENGTH_SHORT).show();
    }

    public void onDestroyView() {
        super.onDestroyView();
        //Toast.makeText(getActivity(), "onDestroyView();", Toast.LENGTH_SHORT).show();
    }

    private void initMap() {
        UiSettings settings = getMap().getUiSettings();
    }

}
