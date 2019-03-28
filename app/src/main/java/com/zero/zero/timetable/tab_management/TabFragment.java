package com.zero.zero.timetable.tab_management;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zero.zero.timetable.MainActivity;
import com.zero.zero.timetable.R;
import com.zero.zero.timetable.mytimetable.MyTimeTableFragment;
import com.zero.zero.timetable.tab_management.timetable_webview.TimeTableFragment;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    private final static String TAG = TabFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //---INFO---//
        Log.i(TAG, "OPEN TabFragment");
        MainActivity.setToolbarTitle(R.string.ovp_fragment_title, getActivity());
        //---INFO---//
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create TimeTableFragments containing a WebView for each day
        final TimeTableFragment mTT1Fragment = new TimeTableFragment();
        final TimeTableFragment mTT2Fragment = new TimeTableFragment();
        mTT1Fragment.setIdentificationNumbers(1, R.id.webViewTTable, R.id.textViewLoad, R.id.progressBar);
        mTT2Fragment.setIdentificationNumbers(2, R.id.webViewTTable, R.id.textViewLoad, R.id.progressBar);
        //reference the layout of the used fragment
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);


        ViewPager viewPager = view.findViewById(R.id.viewpager); //wraps the tabs?

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(mTT1Fragment, getString(R.string.tab1title));
        adapter.addFragment(mTT2Fragment, getString(R.string.tab2title));

        viewPager.setAdapter(adapter);

        TabLayout tabs = view.findViewById(R.id.result_tabs); //references the tablayoutthingy from the wrapping fragmentviewpager xml
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab_reload = view.findViewById(R.id.sync_btn); //references the fab from the wrapping fragmentviewpager xml
        fab_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTT1Fragment.reload();
                mTT2Fragment.reload();
            }
        });

        return view;
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
