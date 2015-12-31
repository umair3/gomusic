package com.example.shoaib.gomusic;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adapters_Miscellenous.Application_Class;
import adapters_Miscellenous.SoundCloud_Class;

public class MainActivity extends AppCompatActivity {

  //  private CoordinatorLayout appBarLayout;
  //  private AppBarLayout mToolBar;
    private Toolbar toolBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentAdapter adapterForViewPager;
    private static FragmentTransaction mTransaction;

    private static final int totalFragmentsCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolBar);

        // Fragment transaction object initialized. it will be used to change the fragments when user logs in.
        mTransaction =getSupportFragmentManager().beginTransaction();
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

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapterForViewPager = new MyFragmentAdapter(getSupportFragmentManager());

       // tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
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


    public class MyFragmentAdapter extends FragmentPagerAdapter implements soundCloudFragment.from_SoundCloudFragment_SignedIn {

        FragmentManager mCurrentFragmentManager;
        Fragment fragment = null;
        //Does whatever need to be done to add new fragment for loggedIn soundCloud Fragment in ViewPager
        @Override
        public void onSignIn_InvokeNewFragment() {



        }
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);

            mCurrentFragmentManager =fm;
        }

        @Override
        public Fragment getItem(int position) {


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

                    Application_Class.getsInstance();
                if (SoundCloud_Class.getInstance().getOnceLoggedIn())
                {
                    fragment = new soundCloudLoggedIn_Fragment();
                }
                else
                {
                    //fragment = new soundCloudFragment(this);
                    fragment = new rootFragment_toContain();
                }



            }
            return fragment;
        }

        @Override
        public int getCount() {

            return totalFragmentsCount;
        }



//        @Override
//        public CharSequence getPageTitle(int position) {
//            return getResources().getStringArray(R.array.tab_names)[position];
//        }
    }

    // Method responsible for bringin in the new fragment when the user has signed in
    // this is called from the SoundCloud_class.java , when the background thread
    // completes the sign in process.
    public static void instantiateLoggedInFragment ()
    {
				/*
				 * IMPORTANT: We use the "root frame" defined in
				 * "root_fragment.xml" as the reference to replace fragment
				 */

                mTransaction.replace(R.id.root_frame, new soundCloudLoggedIn_Fragment());

				/*
				 * IMPORTANT: The following lines allow us to add the fragment
				 * to the stack and return to it later, by pressing back
				 */
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        mTransaction.addToBackStack(null);

        mTransaction.commit();
    }
}
