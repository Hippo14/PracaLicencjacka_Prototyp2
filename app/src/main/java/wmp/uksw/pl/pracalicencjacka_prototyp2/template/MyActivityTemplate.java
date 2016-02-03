package wmp.uksw.pl.pracalicencjacka_prototyp2.template;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;

import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;

/**
 * Created by MSI on 2015-11-19.
 */
public abstract class MyActivityTemplate extends AppCompatActivity {

    protected SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creates SessionManager object that helps with managing
        sessionManager = new SessionManager(getContext());

        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    protected abstract Context getContext();
}
