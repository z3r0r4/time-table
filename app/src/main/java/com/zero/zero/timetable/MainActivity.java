package com.zero.zero.timetable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.zero.zero.timetable.Login.LoginDialogFragment;
import com.zero.zero.timetable.Tabs.SectionsPageAdapter;
import com.zero.zero.timetable.Tabs.Tab1Fragment;
import com.zero.zero.timetable.Tabs.Tab2Fragment;
import com.zero.zero.timetable.Tabs.Tab3Fragment;
import com.zero.zero.timetable.Tabs.TabFragmentActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    //FINISHED: make it necessary to input the password instead of hardcoding it
    //reload webview better plz
    //refractor
    //add a drawer space for all differnet kind of stuff
    //translate all the shorts to actual names
    //add the own timetable to autoshow those lessons that are canceled
    //automagically notify when the plan is updated and if one of my lessons is canceled


    //add information about current date and week on top of the app or in info space

    //ideas: merge the automatic timetable creator (maybe ocr maybe file based) with this ovp. then save the created timetable and mark the lessons that are canceled
    // or substitute lessons
    //
    //future: add controller for grades and homework and plan for exams
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //necessary
        //cause the original tolbar was removed
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

       // this conncets the cute button at top to the action of opening the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        Log.d(TAG, "onCreate: Starting.");
//
//        Log.d(TAG, "onCreate: calling Logincheck.");
//        showLogin();
//
//
//        //put into own class and stuff
//        Log.d(TAG, "onCreate: Initializing Tabs.");
//        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager()); //Setting a sections adapter to add tabs into
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        mViewPager = (ViewPager) findViewById(R.id.container);   //Setting up the Viewpager with the sections adapter.
//        setupViewPager(mViewPager);
//        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    public void showLogin() {
        DialogFragment newFragment = new LoginDialogFragment();
        newFragment.show(getSupportFragmentManager(), "login");
    }

    //put into own class and stuff
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Gestern");
        adapter.addFragment(new Tab2Fragment(), "Heute");
        //adapter.addFragment(new Tab3Fragment(), "Morgen");
        viewPager.setAdapter(adapter);
    }
}


