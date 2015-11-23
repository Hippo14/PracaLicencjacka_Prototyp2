package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;

/**
 * Created by MSI on 2015-11-24.
 */
public class UserProfileFragment extends android.support.v4.app.Fragment {

    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static UserProfileFragment newInstance(int page, String title) {
        UserProfileFragment firstFragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        firstFragment.setArguments(args);
        return firstFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " --- " + title);

        return view;
    }

}
