package com.zero.zero.timetable.Tabs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;
//USELESS FOR NOW
public class TabFragmentActivity extends FragmentActivity {
    private static final String TAG = "tabs";

    private static SectionsPageAdapter mSectionsPageAdapter;

    private static ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //Setting a sections adapter to add tabs into
    mSectionsPageAdapter =new SectionsPageAdapter(getSupportFragmentManager());
    //Setting up the Viewpager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);

    setupViewPager(mViewPager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);
//        Log.d(TAG,"userstuff: "+loadLoginData());
}
private void setupViewPager(ViewPager viewPager){
    SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
    adapter.addFragment(new Tab1Fragment(), "Gestern");
    adapter.addFragment(new Tab2Fragment(), "Heute");
   // adapter.addFragment(new Tab3Fragment(), "Morgen");
    viewPager.setAdapter(adapter);
}

}
