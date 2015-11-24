package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wmp.uksw.pl.pracalicencjacka_prototyp2.R;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

/**
 * Created by MSI on 2015-11-24.
 */
public class UserProfileFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;

    private SessionManager sessionManager;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView accountType = (TextView) view.findViewById(R.id.accountType);

        ProfileUser profileUser = sessionManager.getProfileUser();

        name.setText(profileUser.getName());
        email.setText(profileUser.getEmail());
        accountType.setText(profileUser.getAccountType());


        return view;
    }

}
