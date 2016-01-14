package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.BoardFragment;
import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.EventsFragment;
import wmp.uksw.pl.pracalicencjacka_prototyp2.fragments.UserFragment;
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
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_board));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_events));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_user));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        fragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(1);
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
                    return new BoardFragment();
                case 1:
                    return new EventsFragment();
                case 2:
                    return new UserFragment();
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
