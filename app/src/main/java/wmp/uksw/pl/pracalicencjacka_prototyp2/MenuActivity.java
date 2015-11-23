package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import wmp.uksw.pl.pracalicencjacka_prototyp2.template.MyActivityTemplate;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

public class MenuActivity extends MyActivityTemplate {

    private ProfileUser profileUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        TextView accountType = (TextView) findViewById(R.id.accountType);

        profileUser = sessionManager.getProfileUser();

        name.setText(profileUser.getName());
        email.setText(profileUser.getEmail());
        accountType.setText(profileUser.getAccountType());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }
}
