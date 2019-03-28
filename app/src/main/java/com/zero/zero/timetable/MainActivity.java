package com.zero.zero.timetable;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.zero.zero.timetable.login.LoginDialogFragment;
import com.zero.zero.timetable.mytimetable.MyTimeTableFragment;
import com.zero.zero.timetable.notifications.NotificationsFragment;
import com.zero.zero.timetable.settings.SettingsFragment;
import com.zero.zero.timetable.tab_management.TabFragment;
//TODO improve background fetching
//TODO correct references to Context
//---less important----//
// TODO: 2019-03-27   add notification when lesson is canceled
// TODO: 2019-03-27   add personal timetable creator
// TODO: 2019-03-27   auto add lessons into calendar
// TODO: 2019-03-27   auto remove canceled lessons
// TODO: 2019-03-27   add grade tracker

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private static Context MainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainContext = this;


        //read Toolbar (Bar at top) because the original toolbar was removed
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO put this into a separate class (maybe?)
        //declare a drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        //add a navigationbar into the drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // this conncets the button at top to the action of opening the drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //CHANGE THIS TO START INTO THE SPECIFIED FRAGMENT
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MyTimeTableFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_timetable);
        }
    }

    //TODO improve loading of Fragments to improve performance
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_ovp:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TabFragment(), "TabFragment").commit();
                break;

            case R.id.nav_timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyTimeTableFragment(), "MyTimeTableFragment").commit();
                break;
            case R.id.nav_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotificationsFragment(), "NotificationsFragment").commit();
                break;
            case R.id.nav_login:
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "OPEN LoginDialog");
                LoginDialogFragment.showLogin(this);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment(), "SettingsFragment").commit();
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
    }

    public static void setToolbarTitle(int Title, Activity actv) {
        Activity activity = actv;
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if (toolbar != null)
            activity.setTitle(Title);
    }

    public static Context getContext() {
        return MainContext;
    }

}



