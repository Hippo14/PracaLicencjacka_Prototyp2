package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;

/**
 * Created by MSI on 2016-01-14.
 */
public class UserFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        return view;
    }

}
