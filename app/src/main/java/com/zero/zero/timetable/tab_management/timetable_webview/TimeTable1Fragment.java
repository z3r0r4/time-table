package com.zero.zero.timetable.tab_management.timetable_webview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.zero.zero.timetable.R;
import com.zero.zero.timetable.tab_management.TimeTableDisplay;
//TODO replace with inline implementation of TTFragment
public class TimeTable1Fragment extends TimeTableFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;

        setIdentificationNumbers(1, R.id.Table1, R.id.tV1, R.id.pB1);

        //get access to the fragment_tab1.xml (Pointer)
        view = inflater.inflate(R.layout.fragment_tab1, container, false);

        initializeWebView(view);

        TimeTableDisplay.ViewOVP(mTimeTable, getLoginData());

        return view;
    }

}
