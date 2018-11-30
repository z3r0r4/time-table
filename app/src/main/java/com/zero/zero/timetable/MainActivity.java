package com.zero.zero.timetable;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zero.zero.timetable.LoginManagement.LoginDialogFragment;
import com.zero.zero.timetable.MyTimeTableManagement.MyTimeTableFragment;
import com.zero.zero.timetable.NotificationManagement.NotificationsFragment;
import com.zero.zero.timetable.SettingManagement.SettingsFragment;
import com.zero.zero.timetable.TabManagement.TabFragment;

import receive.HTMLFetcher;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private int clickedNavItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //necessary

        HTMLFetcher.initializeFetcher("***REMOVED***", "***REMOVED***", "***REMOVED***");
//                        String[] s = readLoginData().split(":");
//                        Log.d(s[0]+s[1],TAG);
//                        HTMLFetcher.initializeFetcher("http://"+getString(R.string.ovp_link)+"1.htm",s[0],s[1]);
//                        HTMLFetcher.initializeFetcher("http/***REMOVED***1.htm", "***REMOVED***", "***REMOVED***");
//                        Log.d(Arrays.toString(HTMLFetcher.getData("Q2").get(0)),TAG);


        //cause the original toolbar was removed
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // this conncets the button at top to the action of opening the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

       mDrawerLayout.addDrawerListener(toggle);
      toggle.syncState();


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
                                                        Log.d("Showing LoginDialog", TAG);
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


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
//        super.onBackPressed();
    }
/**
 * USE THIS IF DRAWER DOESNT LAG
 * **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

//        clickedNavItem = menuItem.getItemId();
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
//                Log.d(Arrays.toString(HTMLFetcher.getData("Q1").get(0)),TAG);
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                Log.d("Showing LoginDialog", TAG);
                LoginDialogFragment.showLogin(this);
                break;
////doesnt work yet
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}



