package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.AddEventFragment;
import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.ManageEventsFragment;
import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.MapsFragment;
import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.UserProfileFragment;
import wmp.uksw.pl.pracalicencjacka_prototyp2.template.MyActivityTemplate;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

public class MenuActivity extends MyActivityTemplate {

    private ProfileUser profileUser;

    PagerAdapter fragmentPagerAdapter;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("User Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Maps"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Event"));
        tabLayout.addTab(tabLayout.newTab().setText("Manage Events"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        fragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(fragmentPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private static int NUM_ITEMS;

        public MyPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.NUM_ITEMS = tabCount;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            // TODO Switch
            switch (position) {
                case 0:
                    return new UserProfileFragment();
                case 1:
                    return new MapsFragment();
                case 2:
                    return new AddEventFragment();
                case 3:
                    return new ManageEventsFragment();
            }
            return null;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
