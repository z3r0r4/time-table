package com.zero.zero.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.zero.zero.timetable.HTMLFetcher.OVPEasyFetcher;
import com.zero.zero.timetable.LoginManagement.LoginDialogFragment;
import com.zero.zero.timetable.MyTimeTableManagement.MyTimeTableFragment;
import com.zero.zero.timetable.NotificationManagement.NotificationsFragment;
import com.zero.zero.timetable.SettingManagement.SettingsFragment;
import com.zero.zero.timetable.TabManagement.TabFragment;


//FINISHED: make it necessary to input the password instead of hardcoding it
//FINISHED reload webview better plz
//REFACTOR so that it can be used in every tabfragment
//refractor
//FINISHED add a drawer space for all differnet kind of stuff
//FINISHED translate all the shorts to actual names
//WORKING ON IT add the own timetable to autoshow those lessons that are canceled
//automagically notify when the plan is updated and if one of my lessons is canceled

//WORKING ON IT add information about current date and week on top of the app or in info space

//ideas: merge the automatic timetable creator (maybe ocr maybe file based) with this ovp. then save the created timetable and mark the lessons that are canceled
// or substitute lessons
//
//future: add controller for grades and homework and plan for exams

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private int clickedNavItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //necessary
        OVPEasyFetcher.initializeContext(this);
        OVPEasyFetcher.init("http://rudolph-brandes-gymnasium.de/ovp/heute/subst_001.htm", "schueler", "Did1VP-PW");

        //TODO put this into a separate class
        //readd Toolbar because the original toolbar was removed
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add a drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        //add a navigationbar into the drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // this conncets the button at top to the action of opening the drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MyTimeTableFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_timetable);
        }

        /**
         * USE THIS IF FRAGMENT OPENING LAGGS BECAUSE OF SLOW DEVICE
         * **/
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                                            @Override
                                            public void onDrawerSlide(View drawerView, float slideOffset) {
                                            }

                                            @Override
                                            public void onDrawerOpened(View drawerView) {
                                            }

                                            @Override
                                            public void onDrawerStateChanged(int newState) {
                                            }

                                            @Override
                                            public void onDrawerClosed(View drawerView) {
                                                //Set your new fragment here
                                                switch (clickedNavItem) {
                                                    case R.id.nav_ovp:
                                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                new TabFragment()).commit();
                                                        break;

                                                    case R.id.nav_timetable:
                                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                new MyTimeTableFragment()).commit();
                                                        break;
                                                    case R.id.nav_notifications:
                                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                new NotificationsFragment()).commit();
                                                        break;
                                                    case R.id.nav_login:
                                                        Log.i(TAG, "OPEN LoginDialog");
                                                        LoginDialogFragment.showLogin(MainActivity.this);
                                                        break;
                                                    //doesnt work yet
                                                    case R.id.nav_settings:
                                                        Intent intent = new Intent(MainActivity.this, SettingsFragment.class);
                                                        startActivity(intent);
                                                        break;
                                                }
                                            }
                                        }

        );
    }

    /**
     * USE THIS IF DRAWER DOESNT LAG
     **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

//        clickedNavItem = menuItem.getItemId(); // USE THIS IF FRAGMENT OPENING LAGGS BECAUSE OF SLOW DEVICE
        switch (menuItem.getItemId()) {
            case R.id.nav_ovp:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TabFragment()).commit();
                break;

            case R.id.nav_timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyTimeTableFragment()).commit();
                break;
            case R.id.nav_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotificationsFragment()).commit();
                break;
            case R.id.nav_login:
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "OPEN LoginDialog");
                LoginDialogFragment.showLogin(this);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
//        super.onBackPressed();
    }

}



