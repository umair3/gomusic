package com.example.shoaib.gomusic;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import adapters_Miscellenous.musicService;

public class MainActivity extends AppCompatActivity implements musicService.MusicServiceCallBackInterface {

    //  private CoordinatorLayout appBarLayout;
    //  private AppBarLayout mToolBar;
    private Toolbar toolBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentAdapter adapterForViewPager;
    private static FragmentTransaction mTransaction;
    private static View bottomInformer;

    private static final int totalFragmentsCount = 5;


    //Fields related to Connection with Service
    private musicService mMusicService;
    private boolean mMusicServiceBound = false;
    private ServiceConnection mServiceConnection;
    private Intent mPlayIntent;


    private View tab0_songs_tabLayout_Reference;
    private View tab1_artist_tabLayout_Reference;
    private View tab2_albums_tabLayout_Reference;
    private View tab3_playlist_tabLayout_Reference;
    private View tab4_soundcloud_tabLayout_Reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolBar);


        // Fragment transaction object initialized. it will be used to change the fragments when user logs in.
        mTransaction = getSupportFragmentManager().beginTransaction();
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
        //tabLayout.setTabTextColors(Color.LTGRAY, Color.WHITE);
        tabLayout.setTabTextColors(Color.LTGRAY, Color.BLACK);
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

        int[] tabIconsArray = {R.drawable._ic_songs_icon_grey_24dp, R.drawable._ic_artist_grey_24dp,
                R.drawable._ic_album_icon_grey_24dp, R.drawable._ic_playlist_icon_grey_name, R.drawable._ic_soundcloud_temporary__icon_grey_24dp};

        LayoutInflater mInflater = getLayoutInflater();


        for (int tabs = 0; tabs < tabLayout.getTabCount(); tabs++) {
            //View mView = mInflater.inflate(getResources().getLayout(R.layout.tabs_custom_view), null);
            if (tabs==0)
            {
                tab0_songs_tabLayout_Reference = mInflater.inflate(getResources().getLayout(R.layout.tabs_custom_view), null);
                ((TextView) tab0_songs_tabLayout_Reference.findViewById(R.id.tabText)).setText((getResources().getStringArray(R.array.tab_names)[tabs]).toUpperCase());
                ((TextView) tab0_songs_tabLayout_Reference.findViewById(R.id.tabText)).setHighlightColor(Color.BLACK);
                ((ImageView) tab0_songs_tabLayout_Reference.findViewById(R.id.tabImage)).setImageResource(tabIconsArray[tabs]);
                tabLayout.getTabAt(tabs).setCustomView(tab0_songs_tabLayout_Reference);
            }
            else if (tabs==1)
            {
                tab1_artist_tabLayout_Reference= mInflater.inflate(getResources().getLayout(R.layout.tabs_custom_view), null);
                ((TextView) tab1_artist_tabLayout_Reference.findViewById(R.id.tabText)).setText((getResources().getStringArray(R.array.tab_names)[tabs]).toUpperCase());
                ((TextView) tab1_artist_tabLayout_Reference.findViewById(R.id.tabText)).setHighlightColor(Color.BLACK);
                ((ImageView) tab1_artist_tabLayout_Reference.findViewById(R.id.tabImage)).setImageResource(tabIconsArray[tabs]);
                tabLayout.getTabAt(tabs).setCustomView(tab1_artist_tabLayout_Reference);
            }
            else if (tabs==2)
            {
                tab2_albums_tabLayout_Reference= mInflater.inflate(getResources().getLayout(R.layout.tabs_custom_view), null);
                ((TextView) tab2_albums_tabLayout_Reference.findViewById(R.id.tabText)).setText((getResources().getStringArray(R.array.tab_names)[tabs]).toUpperCase());
                ((TextView) tab2_albums_tabLayout_Reference.findViewById(R.id.tabText)).setHighlightColor(Color.BLACK);
                ((ImageView) tab2_albums_tabLayout_Reference.findViewById(R.id.tabImage)).setImageResource(tabIconsArray[tabs]);
                tabLayout.getTabAt(tabs).setCustomView(tab2_albums_tabLayout_Reference);
            }
            else if (tabs==3)
            {
                tab3_playlist_tabLayout_Reference= mInflater.inflate(getResources().getLayout(R.layout.tabs_custom_view), null);
                ((TextView) tab3_playlist_tabLayout_Reference.findViewById(R.id.tabText)).setText((getResources().getStringArray(R.array.tab_names)[tabs]).toUpperCase());
                ((TextView) tab3_playlist_tabLayout_Reference.findViewById(R.id.tabText)).setHighlightColor(Color.BLACK);
                ((ImageView) tab3_playlist_tabLayout_Reference.findViewById(R.id.tabImage)).setImageResource(tabIconsArray[tabs]);
                tabLayout.getTabAt(tabs).setCustomView(tab3_playlist_tabLayout_Reference);
            }
            else
            {
                tab4_soundcloud_tabLayout_Reference= mInflater.inflate(getResources().getLayout(R.layout.tabs_custom_view), null);
                ((TextView) tab4_soundcloud_tabLayout_Reference.findViewById(R.id.tabText)).setText((getResources().getStringArray(R.array.tab_names)[tabs]).toUpperCase());
                ((TextView) tab4_soundcloud_tabLayout_Reference.findViewById(R.id.tabText)).setHighlightColor(Color.BLACK);
                ((ImageView) tab4_soundcloud_tabLayout_Reference.findViewById(R.id.tabImage)).setImageResource(tabIconsArray[tabs]);
                tabLayout.getTabAt(tabs).setCustomView(tab4_soundcloud_tabLayout_Reference);
            }


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

        //getting a reference to the bottom Informer Layout
        bottomInformer = findViewById(R.id.bottom_informert);
        ImageButton mButton = (ImageButton) bottomInformer.findViewById(R.id.bottom_informer_playPauseButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMusicService!=null)
                {
                    if (mMusicService.isSongPlaying())
                    {
                        mMusicService.pausePlayer();
                    }
                    else
                    {
                        mMusicService.resumePlayer();
                    }
                }
            }
        });

//        final FloatingActionButton fab_to_Play_Pause = (FloatingActionButton) findViewById(R.id.fabPlayPause);
//        fab_to_Play_Pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mMusicService!=null)
//                {
//                    if (mMusicService.isSongPlaying())
//                    {
//                        mMusicService.pausePlayer();
//                        fab_to_Play_Pause.setImageResource(R.drawable._ic_play_icon_24dp);
//                    }
//                    else
//                    {
//                        mMusicService.resumePlayer();
//                        fab_to_Play_Pause.setImageResource(R.drawable._ic_pause_icon_grey_24dp);
//
//                    }
//                }
//            }
//        });
        //Developing a Service Connection
        mServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                musicService.musicBinder mMusicBinder = (musicService.musicBinder) iBinder;
                mMusicService = mMusicBinder.getServiceInstanceForClient();
                //Public Method in MusicService that register the ServiceCallBack InterFace
                //And also also updates the Bottom informer by the Method inside this
                //Registering method.
                mMusicService.registerInterface(MainActivity.this);
                mMusicServiceBound = true;


            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mMusicServiceBound = false;

            }
        };

        if (mPlayIntent == null) {

            mPlayIntent = new Intent(this, musicService.class);
            bindService(mPlayIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
            startService(mPlayIntent);
            //Register ServiceCallBack InterFace


        }


    }



    @Override
    protected void onStart() {
        super.onStart();

        //LayoutInflater layoutInflater =getLayoutInflater();
        //layoutInflater.inflate(R.id.bottom_informer,g);


//        if (mMusicService!=null)
//        {
//            String[] arr =mMusicService.RetriveLastSavedSongData();
//
//            TextView mArtist = (TextView) bottomInformer.findViewById(R.id.bottom_informer_currentArtist);
//            TextView mSong  = (TextView)  bottomInformer.findViewById(R.id.bottom_informer_currentSong);
//
//            mArtist.setText(arr[0]);
//            mSong.setText(arr[1]);
//        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //Following two statements needed when i have to implement a seperate search activity,
        // which in this case im not going to do.
        // setIntent(intent);
        // handleIntent(intent);


    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // doMySearch(query);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //Not using the statement below, it links the searchable configuration with the
        //searchView widget, that is to a new searchable activity which in this case
        // we are not implementing, the search is going to take place within the current activity.
        //  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + viewPager.getCurrentItem());
                if (fragment instanceof soundCloudLoggedIn_Fragment) {
                    ((soundCloudLoggedIn_Fragment) fragment).searchStringForRequest(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + viewPager.getCurrentItem());
                if (fragment instanceof songsFragment_withRecycler) {
                    ((songsFragment_withRecycler) fragment).mSongsListAdapter.getFilter().filter(newText);
                } else if (fragment instanceof albumsFragment) {
                    ((albumsFragment) fragment).mAlbumsRecylerAdapter.getFilter().filter(newText);
                } else {

                }

                return false;
            }
        });

        // searchView.setOnCa

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
    protected void onDestroy() {
        super.onDestroy();

//        mMusicService.saveSongDataForRetrievel();
    }

    @Override
    public void UpdateBottomInfromerDisplay(final singleSongItem anySong) {

        TextView mArtist = (TextView) bottomInformer.findViewById(R.id.bottom_informer_currentArtist);
        TextView mSong = (TextView) bottomInformer.findViewById(R.id.bottom_informer_currentSong);
        final ImageView mImage = (ImageView) bottomInformer.findViewById(R.id.bottom_informer_currentArt);
        mSong.setText(anySong.getSongTitle());
        mArtist.setText(anySong.getSongArtistTile());
        if (anySong.getSongIconId() != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap mBitmap = BitmapFactory.decodeFile(anySong.getSongIconId());
                    mImage.setImageBitmap(mBitmap);

                }
            });

        }

    }

    @Override
    public void Set_MainActivity_BottomInfromer_SongData_ForFirstTime(singleSongItem anySong) {

        UpdateBottomInfromerDisplay(anySong);
    }

    @Override
    public void Update_PlayPauseButton_State(boolean whatState) {

        final ImageButton imageButton = (ImageButton) bottomInformer.findViewById(R.id.bottom_informer_playPauseButton);
        if (whatState==true)
        {
            imageButton.setImageResource(R.drawable._ic_pause_circle_icon_black_24dp);
           // imageButton.setBackgroundResource(R.drawable._ic_pause_circle_icon_black_24dp);

        }
        else
        {
            imageButton.setImageResource(R.drawable._ic_play_circle_icon_black_24dp);
          //  imageButton.setBackgroundResource(R.drawable._ic_play_circle_icon_black_24dp);
        }
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

            mCurrentFragmentManager = fm;
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
            } else if (position == 3) {
                fragment = new playListFragment();
            } else if (position == 4) {

//                    Application_Class.getsInstance();
//                if (SoundCloud_Class.getInstance().getOnceLoggedIn())
//                {
//                    fragment = new soundCloudLoggedIn_Fragment();
//                }
//                else
//                {
//                    //fragment = new soundCloudFragment(this);
//                    fragment = new rootFragment_toContain();
//                }


                if (getSharedPreferences("ApplicationPrefs", Context.MODE_PRIVATE).getBoolean("first_Login", false) == true) {
                    fragment = new soundCloudLoggedIn_Fragment();
                } else {
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
    public static void instantiateLoggedInFragment() {
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
