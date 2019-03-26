package com.zero.zero.timetable.tab_management;

import android.os.Bundle;
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
import com.zero.zero.timetable.tab_management.timetable_webview.TimeTable1Fragment;
import com.zero.zero.timetable.tab_management.timetable_webview.TimeTable2Fragment;
import com.zero.zero.timetable.tab_management.timetable_webview.TimeTableFragment;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    private static final String TAG = "TabFragment";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final TimeTableFragment mTT1Fragment = new TimeTableFragment();
        final TimeTableFragment mTT2Fragment = new TimeTableFragment();
        mTT1Fragment.setIdentificationNumbers(1, R.id.Table1, R.id.tV1, R.id.pB1);
        mTT2Fragment.setIdentificationNumbers(2, R.id.Table2, R.id.tV2, R.id.pB2);


        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(mTT1Fragment, getString(R.string.tab1title)); //TODO create instances of TTfragment instead of TT1fragment...
        adapter.addFragment(mTT2Fragment, getString(R.string.tab2title));
        viewPager.setAdapter(adapter);

        TabLayout tabs = view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab_reload = view.findViewById(R.id.sync_btn);
        fab_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTT1Fragment.reload();
                mTT2Fragment.reload();
            }
        });

        return view;

    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new TimeTable1Fragment(), getString(R.string.tab1title)); //TODO create instances of TTfragment instead of TT1fragment...
        adapter.addFragment(new TimeTable2Fragment(), getString(R.string.tab2title));
        viewPager.setAdapter(adapter);
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
