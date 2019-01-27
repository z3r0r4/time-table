package com.zero.zero.timetable.tabs.timetable_webview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.zero.zero.timetable.R;
import com.zero.zero.timetable.tabs.TimeTableDisplay;

public class TimeTable1Fragment extends TimeTableFragment {

    private static final String TAG = "TimeTable1Fragment";
    private static WebView sTimeTable = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;

        setIdentificationNumbers(1, R.id.Table1, R.id.tV1, R.id.pB1);

        //get access to the fragment_tab1.xml (Pointer)
        view = inflater.inflate(R.layout.fragment_tab1, container, false);

        initializeWebView(view);

        TimeTableDisplay.ViewOVP(mTimeTable, view, getLoginData());

        sTimeTable = mTimeTable;

        return view;
    }

    public static void reload() {
        sTimeTable.loadUrl("about:blank");
        sTimeTable.reload();
    }
}
