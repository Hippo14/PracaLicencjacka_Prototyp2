package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.UserProfileFragment;
import wmp.uksw.pl.pracalicencjacka_prototyp2.template.MyActivityTemplate;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

public class MenuActivity extends MyActivityTemplate {

    private ProfileUser profileUser;

    FragmentPagerAdapter fragmentPagerAdapter;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Snackbar snackbar = Snackbar.make(relativeLayout, "Selected page position: " + position, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {

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

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private static int NUM_ITEMS = 1;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            // TODO Switch
            switch (position) {
                case 0:
                    return UserProfileFragment.newInstance(0, "User Profile");
            }
            return null;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
