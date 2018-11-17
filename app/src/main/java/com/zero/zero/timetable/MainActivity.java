package com.zero.zero.timetable;

        import android.support.design.widget.TabLayout;
        import android.support.v4.app.DialogFragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.view.ViewPager;

        import android.os.Bundle;
        import android.util.Log;




public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    //make it necessary to input the password instead of hardcoding it -----------------------did it
    // reload webview better plz
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //necessary
        Log.d(TAG,"onCreate: Starting.");
        Log.d(TAG,"onCreate: calling Logincheck.");

        showLogin();
        Log.d(TAG,"onCreate: Initializing Tabs.");
        //Setting a sections adapter to add tabs into
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        //Setting up the Viewpager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//        Log.d(TAG,"userstuff: "+loadLoginData());
    }
    public void showLogin() {
        DialogFragment newFragment = new LoginDialogFragment();
        newFragment.show(getSupportFragmentManager(), "login");
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Gestern");
        adapter.addFragment(new Tab2Fragment(), "Heute");
        adapter.addFragment(new Tab3Fragment(), "Morgen");
        viewPager.setAdapter(adapter);
    }
}


