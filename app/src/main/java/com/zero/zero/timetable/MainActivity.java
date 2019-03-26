package com.zero.zero.timetable;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
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

//FINISHED: make it necessary to input the password instead of hardcoding it
//FINISHED reload webview better plz
//REFACTOR so that it can be used in every tabfragment
//refactor
//FINISHED add a drawer space for all differnet kind of stuff
//FINISHED translate all the shorts to actual names

//improve reloading of webview
//improve loading of fragments (splashscreen maybe)

//add the own timetable to auto-show those lessons that are canceled
//add a storage layout space or whatever to store the data
//add a converter
//extract all courses from the Q2 plan
//make the user chose which courses he is taking

//automagically notify when the plan is updated and if one of my lessons is canceled

//FINISHED add information about current date and week on top of the app or in info space

//ideas: merge the automatic timetable creator (maybe ocr maybe file based) with this ovp. then save the created timetable and mark the lessons that are canceled
// or substitute lessons

//future: add controller for grades and homework and plan for exams
//TODO fix references to Context
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private static Context MainContext;
    private int clickedNavItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainContext = this;


        //read Toolbar (Bar at top) because the original toolbar was removed
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO put this into a separate class
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

    //TODO improve loading of Fragments to supress laggs
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        clickedNavItem = menuItem.getItemId(); // USE THIS IF FRAGMENT OPENING LAGGS BECAUSE OF SLOW DEVICE
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
  public static Context getContext(){
        return MainContext;
  }

}



