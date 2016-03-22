package wmp.uksw.pl.pracalicencjacka_prototyp2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;
import wmp.uksw.pl.pracalicencjacka_prototyp2.R;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

/**
 * Created by MSI on 2016-01-14.
 */
public class UserFragment extends Fragment {

    Card card;
    CardHeader header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager(getContext());
        ProfileUser profileUser = sessionManager.getProfileUser();

        card = new Card(getContext(), R.layout.carddemo_example_inner_content);
        header = new CardHeader(getContext());
        header.setTitle("User");
        card.addCardHeader(header);
        card.setTitle(profileUser.getName() + " " + profileUser.getEmail() + " " + profileUser.getAccountType() + " " + profileUser.getPassword());
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        CardViewNative cardViewNative = (CardViewNative) view.findViewById(R.id.carddemo);
        cardViewNative.setCard(card);

        return view;
    }

}
