package com.example.shoaib.gomusic;


import android.content.ContentResolver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentAdapter adapterForViewPager;

    private static final int totalFragmentsCount = 5;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        contentResolver = getContentResolver();

        tabLayout = (TabLayout) findViewById(R.id.MaterialTabs);

        TabLayout.Tab songsTab = tabLayout.newTab().setText("Songs");
        TabLayout.Tab ArtistsTab = tabLayout.newTab().setText("Artists");
        TabLayout.Tab albumsTab = tabLayout.newTab().setText("Albums");
        TabLayout.Tab playlistTab = tabLayout.newTab().setText("PlayLists");
        TabLayout.Tab soundCloudTab = tabLayout.newTab().setText("SoundCloud");


        tabLayout.addTab(songsTab);
        tabLayout.addTab(ArtistsTab);
        tabLayout.addTab(albumsTab);
        tabLayout.addTab(playlistTab);
        tabLayout.addTab(soundCloudTab);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(Color.LTGRAY, Color.WHITE);

        //tabLayout.setElevation(2.0f);
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

        int [] tabIconsArray = {R.drawable.songs_tabicon,R.drawable.artist_tabicon,
                                R.drawable.album_tabicon,R.drawable.playlist_tabicon,R.drawable.soundcloud_tabicon};

        LayoutInflater mInflater = getLayoutInflater();


        for (int tabs=0 ; tabs <tabLayout.getTabCount() ; tabs++)
        {
            View mView = mInflater.inflate(getResources().getLayout(R.layout.tabs_custom_view),null);
            ((TextView)mView.findViewById(R.id.tabText)).setText((getResources().getStringArray(R.array.tab_names)[tabs]).toUpperCase());
            ((ImageView)mView.findViewById(R.id.tabImage)).setImageResource(tabIconsArray[tabs]);
            tabLayout.getTabAt(tabs).setCustomView(mView);

        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapterForViewPager = new MyFragmentAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapterForViewPager);  //passing an extended Fragment Pager Adapter
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                tabLayout.getTabAt(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);



            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


// Adapter of Fragments to be Passed to the ViewPager.
// This Section Specifies What only and only FragmentPagerAdapter Should do.


    public class MyFragmentAdapter extends FragmentPagerAdapter {


        public MyFragmentAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            if (position == 0) {
               // fragment = new songsFragment();
                fragment = new songsFragment_withRecycler();
            } else if (position == 1) {
                fragment = new artistsFragment();
            } else if (position == 2) {
                fragment = new albumsFragment();
            }
            else if (position == 3) {
                fragment = new playListFragment();
            }
            else if (position == 4) {
                fragment = new soundCloudFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {

            return totalFragmentsCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tab_names)[position];
        }
    }
}
