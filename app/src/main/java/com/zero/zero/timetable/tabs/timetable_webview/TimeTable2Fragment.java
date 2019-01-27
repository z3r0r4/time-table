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

public class TimeTable2Fragment extends TimeTableFragment {

    private static final String TAG = "TimeTable2Fragment";
    private static WebView sTimeTable = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        String loginData;

        setIdentificationNumbers(2, R.id.Table2);

        //get access to the fragment_tab1.xml (Pointer)
        view = inflater.inflate(R.layout.fragment_tab2, container, false);

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

