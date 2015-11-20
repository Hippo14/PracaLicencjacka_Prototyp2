package wmp.uksw.pl.pracalicencjacka_prototyp2.template;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MSI on 2015-11-19.
 */
public abstract class MyActivityTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    protected abstract Context getContext();
}
