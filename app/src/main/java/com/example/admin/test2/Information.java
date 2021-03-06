package com.example.admin.test2;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class Information extends AppCompatActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    Toolbar infoToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the Adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        infoToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(infoToolBar);

        // Set up the ViewPager with the sections Adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    // Métodos de la interfaz ActionBar.TabListener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    // Métodos de la interfaz ViewPager.OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getSupportActionBar().setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A placeholder fragment containing a simple view.

     public static class PlaceholderFragment extends Fragment {
     /**
     * The fragment argument representing the section number for this
     * fragment.

     private static final String ARG_SECTION_NUMBER = "section_number";

     public PlaceholderFragment() {
     }

     /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    /**
     public static PlaceholderFragment newInstance(int sectionNumber) {
     PlaceholderFragment fragment = new PlaceholderFragment();
     Bundle args = new Bundle();
     args.putInt(ARG_SECTION_NUMBER, sectionNumber);
     fragment.setArguments(args);
     return fragment;
     }

     }
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment tabFragment = null;

            switch (position){
                case 0:
                    tabFragment = new General();
                    break;
                case 1:
                    tabFragment = new Mantenimientos();
                    break;
                case 2:
                    tabFragment = new Graficas();
                    break;
            }
            return tabFragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String section = null;

            switch (position) {
                case 0:
                    section = "GENERAL";
                    break;
                case 1:
                    section = "MANTENIMIENTOS";
                    break;
                case 2:
                    section = "GRAFICAS";
                    break;
            }
            return section;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent i;
        switch (id){
            case R.id.action_menu:
                i = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
                break;
            case R.id.action_scan:
                i = new Intent(getApplicationContext(),QRScan.class);
                startActivity(i);
                break;
            case R.id.action_busqueda:
                i = new Intent(getApplicationContext(),Search.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
